package fileio;

import entities.Consumer;
import entities.Distributor;
import entities.Producer;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that stores all information for output.
 */
public final class Output {
    private final List<OutputConsumer> consumers = new ArrayList<>();
    private final List<OutputDistributor> distributors = new ArrayList<>();
    private final List<OutputProducer> energyProducers = new ArrayList<>();

    public Output(final List<Consumer> consumers, final List<Distributor> distributors, final List<Producer> producers) {
        consumers.forEach(consumer -> {
            OutputConsumer outputConsumer = new OutputConsumer();

            outputConsumer.setId(consumer.getId());
            outputConsumer.setIsBankrupt(consumer.isBankrupt());
            outputConsumer.setBudget(consumer.getBudget());

            this.consumers.add(outputConsumer);
        });

        distributors.forEach(distributor -> {
            OutputDistributor outputDistributor = new OutputDistributor();

            outputDistributor.setId(distributor.getId());
            outputDistributor.setEnergyNeededKW(distributor.getEnergyNeededKW());
            outputDistributor.setContractCost(distributor.getOffer());
            outputDistributor.setBudget(distributor.getBudget());
            outputDistributor.setProducerStrategy(distributor.getProducerStrategy());
            outputDistributor.setIsBankrupt(distributor.isBankrupt());
            outputDistributor.setContracts(distributor.getContracts());

            this.distributors.add(outputDistributor);
        });

        producers.forEach(producer -> {
            OutputProducer outputProducer = new OutputProducer();
            outputProducer.setId(producer.getId());
            outputProducer.setMaxDistributors(producer.getMaxDistributors());
            outputProducer.setPriceKW(producer.getPriceKW());
            outputProducer.setEnergyType(producer.getEnergyType());
            outputProducer.setEnergyPerDistributor(producer.getEnergyPerDistributor());
            outputProducer.setMonthlyStats(producer.getMonthlyStats());
            this.energyProducers.add(outputProducer);
        });
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
            outputDistributor.setEnergyNeededKW(distributor.getEnergyNeededKW());
            outputDistributor.setContractCost(distributor.getOffer());
            outputDistributor.setBudget(distributor.getBudget());
            outputDistributor.setProducerStrategy(distributor.getProducerStrategy());
            outputDistributor.setIsBankrupt(distributor.isBankrupt());
            outputDistributor.setContracts(distributor.getContracts());

            this.distributors.add(outputDistributor);
        }
    }

    public List<OutputProducer> getEnergyProducers() {
        return energyProducers;
    }

    public void setEnergyProducers(final List<Producer> producers) {
        for (Producer producer : producers) {
            OutputProducer outputProducer = new OutputProducer();

            outputProducer.setId(producer.getId());
            outputProducer.setMaxDistributors(producer.getMaxDistributors());
            outputProducer.setPriceKW(producer.getPriceKW());
            outputProducer.setEnergyType(producer.getEnergyType());
            outputProducer.setEnergyPerDistributor(producer.getEnergyPerDistributor());
            outputProducer.setMonthlyStats(producer.getMonthlyStats());

            this.energyProducers.add(outputProducer);
        }
    }
}
