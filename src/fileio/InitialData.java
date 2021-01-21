package fileio;

import java.util.List;

/**
 * Class that stores the initial data from input.
 */
public final class InitialData {
    private List<InputConsumer> consumers;
    private List<InputDistributor> distributors;
    private List<InputProducer> producers;

    public List<InputConsumer> getConsumers() {
        return consumers;
    }

    public void setConsumers(final List<InputConsumer> consumers) {
        this.consumers = consumers;
    }

    public List<InputDistributor> getDistributors() {
        return distributors;
    }

    public void setDistributors(final List<InputDistributor> distributors) {
        this.distributors = distributors;
    }

    public List<InputProducer> getProducers() {
        return producers;
    }

    public void setProducers(List<InputProducer> producers) {
        this.producers = producers;
    }
}
