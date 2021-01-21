package simulation;

import entities.*;
import utils.Constants;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Class that is used for implementing the logic of program
 */
public final class Simulation {
    private int numberOfTurns;
    private final List<MonthlyUpdate> monthlyUpdates = new ArrayList<>();

    /**
     * List of consumers that are still in game.
     */
    private final List<Consumer> activeConsumers = new ArrayList<>();

    /**
     * List of distributors that are still in game.
     */
    private final List<Distributor> activeDistributors = new ArrayList<>();

    /**
     * List of producers that are still in game.
     */
    private final List<Producer> activeProducers = new ArrayList<>();

    private static Simulation simulation;

    private Simulation() {

    }

    /**
     * @return the instance of singleton class
     */
    public static Simulation getInstance() {
        if (simulation == null) {
            simulation = new Simulation();
        }
        return simulation;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public List<Distributor> getActiveDistributors() {
        return activeDistributors;
    }

    /**
     * Method that adds consumers for the simulation.
     */
    public void addConsumers(final List<Consumer> consumers) {
        this.activeConsumers.addAll(consumers);
    }

    /**
     * Method that sets the monthly offer for all distributors.
     */
    private void setOfferAllDistributor() {
        activeDistributors.forEach(Distributor::setOffer);
    }

    /**
     * Method that returns the distributor with the best offer for that month.
     * @return distributor with the best offer.
     */
    private Distributor getDistributorBestOffer() {
        return activeDistributors
                .stream()
                .min(Comparator.comparing(Distributor::getOffer))
                .orElseThrow(NoSuchElementException::new);
    }

    /**
     * Method that adds salaries to all consumers.
     */
    public void addSalaryAllConsumers() {
        activeConsumers.forEach(Consumer::addSalary);
    }

    /**
     * Method that creates and set contracts to all consumers
     * that do not have any.
      * @param distributorBestOffer distributor that has the
     *                             best offer for that month
     */
    private void setNewContracts(final Distributor distributorBestOffer) {
        for (Consumer consumer : activeConsumers) {
            Contract contract = consumer.getContract();
            if (contract == null || contract.getRemainedContractMonths() == 0) {
                int id = consumer.getId();
                long offer = distributorBestOffer.getOffer();
                int contractLength = distributorBestOffer.getContractLength();

                Contract newContract = new Contract(id, offer, contractLength);
                consumer.setContract(newContract);
                distributorBestOffer.addContract(newContract);
            }
        }
    }

    /**
     * Method that allows all consumers to pay to their distributors.
     */
    private void paymentAllConsumers() {
        activeConsumers.forEach(consumer -> {
            Distributor distributor = consumer.findContract();
            consumer.pay(distributor, Constants.DISTRIBUTOR);
        });
    }

    /**
     * Method that allows all distributors to pay.
     */
    private void paymentAllDistributors() {
        for (Distributor distributor : activeDistributors) {
            distributor.pay();
        }
    }

    /**
     * Method that allows all entities to pay.
     */
    private void paymentAll() {
        paymentAllConsumers();
        paymentAllDistributors();
    }

    /**
     * Method that removes all finished contracts.
     */
    private void removeFinishedContracts() {
        for (Distributor distributor : activeDistributors) {
            distributor.removeFinishedContracts();
        }
    }

    /**
     * Method that decrements the number of remaining months
     * for each active contract.
     */
    private void decrementAllContractsLength() {
        for (Consumer consumer : activeConsumers) {
            consumer.decrementContract();
        }
    }

    /**
     * Method that removes all contracts from
     * bankrupt consumers.
     */
    private void removeContractsBankrupts() {
        activeConsumers.stream().filter(Consumer::isBankrupt)
                .forEach(consumer -> consumer.removeContract(activeDistributors));
    }

    private static void removeBankruptsFromProducer(Producer producer) {
        producer.getClients().forEach(observer -> {
            Distributor distributor = (Distributor) observer;
            if (distributor.isBankrupt()) {
                producer.removeObserver(observer);
            }
        });
    }

    /**
     * Method that removes all bankrupts.
     */
    private void removeAllBankrupts() {
        removeContractsBankrupts();
        activeConsumers.removeIf(Consumer::isBankrupt);
        activeProducers.forEach(Simulation::removeBankruptsFromProducer);
        activeDistributors.removeIf(Distributor::isBankrupt);
    }

    /**
     * Method that sets players, monthly updates and number of turns.
     * @param numberTurns number of months
     * @param updates monthly updates
     * @param consumers initial consumer players
     * @param distributors initial distributor players.
     */
    public void setGame(final int numberTurns, final List<MonthlyUpdate> updates,
                        final List<Consumer> consumers, final List<Distributor> distributors,
                        final List<Producer> producers) {
        this.numberOfTurns = numberTurns;
        this.monthlyUpdates.addAll(updates);
        this.activeConsumers.addAll(consumers);
        this.activeDistributors.addAll(distributors);
        this.activeProducers.addAll(producers);
    }

    /**
     * Method that runs the simulation
     */
    public void run() {
        for (Distributor distributor : activeDistributors) {
            // 2.a)
            distributor.executeStrategy(activeProducers);
            // 2.b)
            distributor.calculateProductionCost();
        }

        // 3.
        setOfferAllDistributor();
        Distributor distributorBestOffer = getDistributorBestOffer();

        removeFinishedContracts();
        setNewContracts(distributorBestOffer);

        addSalaryAllConsumers();
        paymentAll();
        removeAllBankrupts();

        decrementAllContractsLength();

        for (int i = 0; i < numberOfTurns; i++) {
            // Inceput de luna
            MonthlyUpdate monthlyUpdate = monthlyUpdates.get(i);

            monthlyUpdate.updateConsumers(); // for consumers
            monthlyUpdate.updateDistributor(activeDistributors); // for distributors

            activeDistributors.forEach(Distributor::calculateProductionCost);

            setOfferAllDistributor();
            distributorBestOffer = getDistributorBestOffer();

            removeFinishedContracts();
            setNewContracts(distributorBestOffer);

            addSalaryAllConsumers();
            paymentAll();
            removeAllBankrupts();

            decrementAllContractsLength();

            // Mijlocul lunii



        }
    }

    /**
     * Method that resets the game.
     */
    public void exit() {
        simulation = null;
    }
}
