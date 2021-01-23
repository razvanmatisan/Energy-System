package strategies;

import entities.Producer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PriceStrategy implements StrategyPriorities {
    @Override
    public List<Producer> chooseProducers(List<Producer> producers, int limit) {
        List<Producer> copyProducers = new ArrayList<>(producers);

        copyProducers.sort(Comparator.comparing(Producer::getPriceKW)
                        .thenComparing(Producer::getEnergyPerDistributor, Comparator.reverseOrder())
                        .thenComparing(Producer::getId));

        copyProducers.removeIf(producer -> producer.getMaxDistributors() == producer.getClients().size());
        List<Producer> finalProducers = new ArrayList<>();

        int actualEnergy = 0;
        for (Producer producer : copyProducers) {
            if (actualEnergy < limit) {
                finalProducers.add(producer);
                actualEnergy += producer.getEnergyPerDistributor();
            }
        }

        return finalProducers;
    }
}
