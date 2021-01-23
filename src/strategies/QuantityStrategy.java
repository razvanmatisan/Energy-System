package strategies;

import entities.Producer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class QuantityStrategy implements StrategyPriorities {
    @Override
    public List<Producer> sortProducers(List<Producer> producers) {
        List<Producer> copyProducers = new ArrayList<>(producers);

        copyProducers.sort(Comparator
                .comparing(Producer::getEnergyPerDistributor, Comparator.reverseOrder())
                .thenComparing(Producer::getId));

        return copyProducers;
    }
}
