package strategies;

import entities.Producer;

import java.util.List;

public interface StrategyPriorities {
    /**
     * Method that sorts the producers based on a strategy.
     */
    List<Producer> sortProducers(List<Producer> producers);
}
