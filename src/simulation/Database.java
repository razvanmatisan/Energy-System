package simulation;

import entities.Consumer;
import entities.Distributor;
import entities.EntityFactory;
import fileio.InputConsumer;
import fileio.InputDistributor;
import utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class that stores all entities that took part to the simulation.
 */
public final class Database {
    private final List<Consumer> consumers = new ArrayList<>();
    private final List<Distributor> distributors = new ArrayList<>();

    private static Database instance;

    private Database() {

    }

    /**
     * @return the instance of the singleton class.
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    /**
     * Setter for consumers.
     */
    public void setConsumers(final List<Consumer> consumers) {
        this.consumers.addAll(consumers);
    }

    public List<Distributor> getDistributors() {
        return distributors;
    }

    /**
     * Setter for distributors.
     */
    public void setDistributors(final List<Distributor> newDistributors) {
        this.distributors.addAll(newDistributors);
    }

    /**
     * Method that resets the instance.
     */
    public void removeEntities() {
        instance = null;
    }

    /**
     * Method that adds new Consumers to database
     */
    public void addConsumers(final List<Consumer> newConsumers) {
        this.consumers.addAll(newConsumers);
    }

    /**
     * Method that adds new Entities to database.
     * @param newConsumers new Consumers.
     * @param newDistributors new Distributors.
     */
    public void addEntities(final List<InputConsumer> newConsumers,
                            final List<InputDistributor> newDistributors) {
        EntityFactory factory = EntityFactory.getInstance();

        for (InputConsumer inputConsumer : newConsumers) {
            Consumer consumer = (Consumer) factory.createEntity(inputConsumer, Constants.CONSUMER);
            consumers.add(consumer);
        }

        for (InputDistributor inputDistributor : newDistributors) {
            Distributor distributor
                    = (Distributor) factory.createEntity(inputDistributor, Constants.DISTRIBUTOR);
            distributors.add(distributor);
        }
    }
}
