package simulation;

import entities.Distributor;
import entities.Entity;

import java.util.ArrayList;
import java.util.List;

public final class DistributorChanges implements EntityChanges {
    private int id;
    private int infrastructureCost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    @Override
    public void update(List<? extends Entity> entities) {
        List<Distributor> distributors = new ArrayList<>((List<Distributor>) entities);

        distributors.stream()
                .filter(entity -> id == entity.getId())
                .findAny()
                .ifPresent(distributor -> distributor.setInfrastructureCost(infrastructureCost));
    }
}
