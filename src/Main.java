import entities.Consumer;
import entities.Distributor;
import entities.Producer;
import fileio.*;
import simulation.Database;
import simulation.MonthlyUpdate;
import simulation.Simulation;

import java.util.List;

/**
 * Entry point to the simulation
 */
public final class Main {

    private Main() { }

    /**
     * Main function which reads the input file and starts simulation
     *
     * @param args input and output files
     * @throws Exception might error when reading/writing/opening files, parsing JSON
     */
    public static void main(final String[] args) throws Exception {
        InputLoader inputLoader = new InputLoader(args[0]);
        Input input = inputLoader.readData();

        int numberOfTurns = input.getNumberOfTurns();
        InitialData initialData = input.getInitialData();

        List<InputConsumer> inputConsumers = initialData.getConsumers();
        List<InputDistributor> inputDistributors = initialData.getDistributors();
        List<InputProducer> inputProducers = initialData.getProducers();

        List<MonthlyUpdate> monthlyUpdates = input.getMonthlyUpdates();

        Database database = Database.getInstance();
        database.addEntities(inputConsumers, inputDistributors, inputProducers);

        List<Consumer> consumers = database.getConsumers();
        List<Distributor> distributors = database.getDistributors();
        List<Producer> producers = database.getProducers();

        Simulation simulation = Simulation.getInstance();
        simulation.setGame(numberOfTurns, monthlyUpdates,
                consumers, distributors, producers);
        simulation.run();
        simulation.exit();

        OutputLoader outputLoader = new OutputLoader(args[1]);
        outputLoader.writeData(new Output(consumers, distributors, producers));

        database.removeEntities();
    }
}
