package simulation;

import entities.Distributor;
import entities.Entity;
import entities.Producer;

import java.util.ArrayList;
import java.util.List;

public class ProducerChanges implements EntityChanges {
    private int id;
    private int energyPerDistributor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

    @Override
    public void update(List<? extends Entity> entities) {
        List<Producer> producers = (List<Producer>) entities;

        producers.stream()
                .filter(entity -> id == entity.getId())
                .findAny()
                .ifPresent(producer -> producer.setEnergyPerDistributor(energyPerDistributor));
    }
}
