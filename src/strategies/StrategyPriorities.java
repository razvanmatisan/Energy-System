package strategies;

import entities.Producer;

import java.util.List;

public interface StrategyPriorities {
    List<Producer> chooseProducers(List<Producer> producers, int limit);

}
