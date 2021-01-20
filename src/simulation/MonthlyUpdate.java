package simulation;

import entities.Consumer;
import entities.Distributor;
import entities.EntityFactory;
import fileio.InputConsumer;
import utils.Constants;

import java.util.ArrayList;
import java.util.List;

public final class MonthlyUpdate {
    private List<InputConsumer> newConsumers = new ArrayList<>();
    private List<CostsChange> costsChanges = new ArrayList<>();

    public List<InputConsumer> getNewConsumers() {
        return newConsumers;
    }

    public void setNewConsumers(final List<InputConsumer> newConsumers) {
        this.newConsumers = newConsumers;
    }

    public List<CostsChange> getCostsChanges() {
        return costsChanges;
    }

    public void setCostsChanges(final List<CostsChange> costsChanges) {
        this.costsChanges = costsChanges;
    }

    /**
     * Method that executes all updates from that month.
     */
    public void update(final List<Distributor> distributors) {
        Simulation simulation = Simulation.getInstance();
        EntityFactory factory = EntityFactory.getInstance();
        Database database = Database.getInstance();

        List<Consumer> consumers = new ArrayList<>();
        for (InputConsumer newConsumer : newConsumers) {
            Consumer consumer = (Consumer) factory.createEntity(newConsumer, Constants.CONSUMER);
            consumers.add(consumer);
        }

        database.addConsumers(consumers);
        simulation.addConsumers(consumers);

        for (CostsChange costsChange : costsChanges) {
            costsChange.update(distributors);
        }
    }
}
