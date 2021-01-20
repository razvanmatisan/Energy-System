package fileio;

import entities.Consumer;
import entities.Distributor;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that stores all information for output.
 */
public final class Output {
    private final List<OutputConsumer> consumers = new ArrayList<>();
    private final List<OutputDistributor> distributors = new ArrayList<>();

    public Output(final List<Consumer> consumers, final List<Distributor> distributors) {
        for (Consumer consumer : consumers) {
            OutputConsumer outputConsumer = new OutputConsumer();

            outputConsumer.setId(consumer.getId());
            outputConsumer.setIsBankrupt(consumer.isBankrupt());
            outputConsumer.setBudget(consumer.getBudget());
            this.consumers.add(outputConsumer);
        }

        for (Distributor distributor : distributors) {
            OutputDistributor outputDistributor = new OutputDistributor();

            outputDistributor.setId(distributor.getId());
            outputDistributor.setIsBankrupt(distributor.isBankrupt());
            outputDistributor.setBudget(distributor.getBudget());
            outputDistributor.setContracts(distributor.getContracts());

            this.distributors.add(outputDistributor);
        }
    }

    public List<OutputConsumer> getConsumers() {
        return consumers;
    }

    /**
     * Setter for consumers field
     */
    public void setConsumers(final List<Consumer> consumers) {
        for (Consumer consumer : consumers) {
            OutputConsumer outputConsumer = new OutputConsumer();

            outputConsumer.setId(consumer.getId());
            outputConsumer.setIsBankrupt(consumer.isBankrupt());
            outputConsumer.setBudget(consumer.getBudget());
            this.consumers.add(outputConsumer);
        }
    }

    public List<OutputDistributor> getDistributors() {
        return distributors;
    }

    /**
     * Setter for distributors field
     */
    public void setDistributors(final List<Distributor> distributors) {
        for (Distributor distributor : distributors) {
            OutputDistributor outputDistributor = new OutputDistributor();

            outputDistributor.setId(distributor.getId());
            outputDistributor.setIsBankrupt(distributor.isBankrupt());
            outputDistributor.setBudget(distributor.getBudget());
            outputDistributor.setContracts(distributor.getContracts());

            this.distributors.add(outputDistributor);
        }
    }
}
