package fileio;

import entities.Contract;

import java.util.List;

/**
 * Class that is used for output format for Distributor field.
 */
public final class OutputDistributor {
    private int id;
    private int budget;
    private boolean isBankrupt;

    private List<Contract> contracts;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
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
