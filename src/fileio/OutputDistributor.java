package fileio;

import entities.Contract;

import java.util.List;

/**
 * Class that is used for output format for Distributor field.
 */
public final class OutputDistributor {
    private int id;
    private int energyNeededKW;
    private int contractCost;
    private int budget;
    private String producerStrategy;
    private boolean isBankrupt;

    private List<Contract> contracts;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public void setEnergyNeededKW(int energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public int getContractCost() {
        return contractCost;
    }

    public void setContractCost(int contractCost) {
        this.contractCost = contractCost;
    }

    public String getProducerStrategy() {
        return producerStrategy;
    }

    public void setProducerStrategy(String producerStrategy) {
        this.producerStrategy = producerStrategy;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    public void setIsBankrupt(final boolean isBankrupt) {
        this.isBankrupt = isBankrupt;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(final List<Contract> contracts) {
        this.contracts = contracts;
    }
}
