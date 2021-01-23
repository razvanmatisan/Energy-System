package simulation;

import entities.Consumer;
import entities.Distributor;
import entities.EntityFactory;
import entities.Producer;
import fileio.InputConsumer;
import utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class MonthlyUpdate {
    private List<InputConsumer> newConsumers = new ArrayList<>();
    private List<DistributorChanges> distributorChanges = new ArrayList<>();
    private List<ProducerChanges> producerChanges = new ArrayList<>();

    public List<InputConsumer> getNewConsumers() {
        return newConsumers;
    }

    public void setNewConsumers(final List<InputConsumer> newConsumers) {
        this.newConsumers = newConsumers;
    }

    public List<DistributorChanges> getDistributorChanges() {
        return distributorChanges;
    }

    public void setDistributorChanges(List<DistributorChanges> distributorChanges) {
        this.distributorChanges = distributorChanges;
    }

    public List<ProducerChanges> getProducerChanges() {
        return producerChanges;
    }

    public void setProducerChanges(List<ProducerChanges> producerChanges) {
        this.producerChanges = producerChanges;
    }

    /**
     * Method that updates all consumers after monthly updates.
     */
    private void updateConsumers() {
        Simulation simulation = Simulation.getInstance();
        EntityFactory factory = EntityFactory.getInstance();
        Database database = Database.getInstance();

        List<Consumer> consumers = newConsumers.stream()
                .map(newConsumer
                        -> (Consumer) factory.createEntity(newConsumer, Constants.CONSUMER))
                .collect(Collectors.toList());

        database.addConsumers(consumers);
        simulation.addConsumers(consumers);
    }

    /**
     * Method that updates all distributors after monthly updates.
     */
    private void updateDistributor(final List<Distributor> distributors) {
        distributorChanges.forEach(distributorChange -> distributorChange.update(distributors));
        distributors.forEach(Distributor::calculateProductionCost);
    }

    /**
     * Method that updates all producers after monthly updates.
     */
    private void updateProducer(final List<Producer> producers) {
        producerChanges.forEach(producerChange -> producerChange.update(producers));
    }

    /**
     * Method that updates all entities after monthly updates.
     */
    public void updateAll(List<Distributor> distributors, List<Producer> producers) {
        updateConsumers();
        updateDistributor(distributors);
        updateProducer(producers);
    }
}
