package strategies;

import entities.Producer;

import java.util.List;

public interface StrategyPriorities {
    void chooseProducers(List<Producer> producers);
}
