package strategies;

import entities.*;
import fileio.InputConsumer;
import fileio.InputDistributor;
import fileio.InputEntity;
import fileio.InputProducer;
import utils.Constants;

public class StrategyFactory {
    private static StrategyFactory factory;

    private StrategyFactory() {

    }

    /**
     * @return the instance of the singleton class.
     */
    public static StrategyFactory getInstance() {
        if (factory == null) {
            factory = new StrategyFactory();
        }

        return factory;
    }

    public StrategyPriorities createStrategy(final EnergyChoiceStrategyType typeStrategy) {
        switch (typeStrategy.label) {
            case Constants.GREEN -> {
                return new GreenStrategy();
            }
            case Constants.PRICE -> {
                return new PriceStrategy();
            }
            case Constants.QUANTITY -> {
                return new QuantityStrategy();
            }
            default -> throw new IllegalStateException("Unexpected value: " + typeStrategy);
        }
    }
}
