package entities;

import fileio.InputDistributor;
import strategies.EnergyChoiceStrategyType;
import strategies.StrategyFactory;
import strategies.StrategyPriorities;
import utils.Constants;

import java.util.ArrayList;
import java.util.List;


public final class Distributor implements Entity, Observer {
    private int id;
    private int contractLength;
    private int budget;
    private int infrastructureCost;
    private long productionCost;
    private int energyNeededKW;
    private EnergyChoiceStrategyType producerStrategy;

    private boolean haveToChangeProducers;
    private StrategyPriorities strategy;
    private List<Producer> producers = new ArrayList<>();

    /**
     * Monthly offer for contract
     */
    private long offer;
    private boolean isBankrupt;

    private List<Contract> contracts = new ArrayList<>();

    public Distributor(final InputDistributor distributor) {
        this.id = distributor.getId();
        this.contractLength = distributor.getContractLength();
        this.budget = distributor.getInitialBudget();
        this.infrastructureCost = distributor.getInitialInfrastructureCost();
        this.energyNeededKW = distributor.getEnergyNeededKW();
        this.producerStrategy = distributor.getProducerStrategy();
        this.strategy = initStrategy();
    }

    private StrategyPriorities initStrategy() {
        StrategyFactory strategyFactory = StrategyFactory.getInstance();
        return strategyFactory.createStrategy(producerStrategy);
    }

    /**
     * Method that executes the assigned strategy
     * @param producerList list of producers on which the strategy is applied.
     */
    public void executeStrategy(List<Producer> producerList) {
        List<Producer> sortedProducers = strategy.sortProducers(producerList);
        setFinalProducers(sortedProducers);
        addDistributorToProducers();
    }

    /**
     * Method that adds the current distributor to all his producers
     */
    private void addDistributorToProducers() {
        for (Producer producer : this.producers) {
            if (!producer.getClients().contains(this)) {
                producer.addObserver(this);
            }
        }
    }

    /**
     * Method that sets the final producers, after the strategy was applied.
     */
    private void setFinalProducers(List<Producer> producerList) {
        producerList.removeIf(producer
                -> producer.getMaxDistributors() == producer.getClients().size());
        this.producers.clear();

        int actualEnergy = 0;
        for (Producer producer : producerList) {
            if (actualEnergy < energyNeededKW) {
                this.producers.add(producer);
                actualEnergy += producer.getEnergyPerDistributor();
            }
        }
    }

    /**
     * Method that removes the current distributor from all producer lists.
     */
    public void removeBankruptClients() {
        for (Producer producer : producers) {
            if (producer.getClients().contains(this)) {
                producer.removeObserver(this);
            }
        }
    }

    /**
     * Method that calculates the production cost.
     */
    public void calculateProductionCost() {
        double cost = producers.stream()
                .mapToDouble(producer
                        -> (producer.getEnergyPerDistributor() * producer.getPriceKW()))
                .sum();

        productionCost = Math.round(Math.floor(cost / 10));
    }

    /**
     * Method that sets a variable as true.
     * This was made in order to make the changes only for those
     * distributors who need to.
     */
    @Override
    public void update() {
        this.haveToChangeProducers = true;
    }

    public boolean getHaveToChangeProducers() {
        return haveToChangeProducers;
    }

    public void setHaveToChangeProducers(boolean haveToChangeProducers) {
        this.haveToChangeProducers = haveToChangeProducers;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public void setEnergyNeededKW(int energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public EnergyChoiceStrategyType getProducerStrategy() {
        return producerStrategy;
    }

    public void setProducerStrategy(EnergyChoiceStrategyType producerStrategy) {
        this.producerStrategy = producerStrategy;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getContractLength() {
        return contractLength;
    }

    public void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(final int initialInfrastructureCost) {
        this.infrastructureCost = initialInfrastructureCost;
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public void setProducers(List<Producer> producers) {
        this.producers = producers;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(final List<Contract> contracts) {
        this.contracts = contracts;
    }

    public long getOffer() {
        return offer;
    }

    /**
     * Setter for offer
     *
     * It is calculated depending on the current number of clients.
     */
    public void setOffer() {
        int numberContracts = contracts.size();

        if (numberContracts == 0) {
            this.offer = infrastructureCost + productionCost
                    + Math.round(Math.floor(Constants.PROFIT_PERCENT * productionCost));
        } else {
            this.offer
                    = Math.round(Math.floor(infrastructureCost / numberContracts) + productionCost
                    + Math.round(Math.floor(Constants.PROFIT_PERCENT * productionCost)));
        }
    }

    /**
     * Method that adds a contract to a distributor
     * @param contract the actual contract.
     */
    public void addContract(final Contract contract) {
        contracts.add(contract);
    }

    /**
     * Method that removes a contract.
     * @param contract the specific contract
     */
    public void removeContract(final Contract contract) {
        contracts.remove(contract);
    }

    /**
     * Method that implements a pay action (money is not given to anyone)
     */
    public void pay() {
        long totalCost = infrastructureCost + productionCost * contracts.size();
        budget -= totalCost;

        if (budget < 0) {
            isBankrupt = true;
        }
    }

    /**
     * Method that removes all finished contracts.
     */
    public void removeFinishedContracts() {
        contracts.removeIf(contract -> contract.getRemainedContractMonths() == 0);
    }

    /**
     * Method that implements the payment process
     *
     * Unfortunately, in this state of homework, the producer receives no money.
     *
     * In this state of the project, it has been left empty, but it will be
     * implemented after new Entities will be introduced to the simulation.
     * @param entity payee
     * @param typeEntity type of entity
     */
    @Override
    public void pay(final Entity entity, final String typeEntity) {
        long totalCost = infrastructureCost + productionCost * contracts.size();
        budget -= totalCost;

        if (budget < 0) {
            isBankrupt = true;
        }
    }

    /**
     * Method that implements the action of receiving money.
     * @param money sum of money an entity receives.
     */
    @Override
    public void getPaid(final long money) {
        budget += money;
    }
}
