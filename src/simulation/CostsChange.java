package simulation;

import entities.Distributor;

import java.util.List;

public final class CostsChange {
    private int id;
    private int infrastructureCost;
    private int productionCost;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(final int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    public int getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(final int productionCost) {
        this.productionCost = productionCost;
    }

    /**
     * Method that sets costs changes for a specific distributor.
     */
    public void update(final List<Distributor> distributors) {
        Distributor distributor = distributors.stream()
                .filter(entity -> id == entity.getId())
                .findAny()
                .orElse(null);
        if (distributor != null) {
            distributor.setInfrastructureCost(infrastructureCost);
            distributor.setProductionCost(productionCost);
        }
    }
}
