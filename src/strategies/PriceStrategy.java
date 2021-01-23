package strategies;

import entities.Producer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class PriceStrategy implements StrategyPriorities {
    @Override
    public List<Producer> sortProducers(List<Producer> producers) {
        List<Producer> copyProducers = new ArrayList<>(producers);

        copyProducers.sort(Comparator.comparing(Producer::getPriceKW)
                        .thenComparing(Producer::getEnergyPerDistributor, Comparator.reverseOrder())
                        .thenComparing(Producer::getId));

        return copyProducers;
    }
}
