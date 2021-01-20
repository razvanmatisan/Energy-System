package fileio;

/**
 * Class that is used for output format for Consumer field.
 */
public final class OutputConsumer {
    private int id;
    private boolean isBankrupt;
    private int budget;

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
}
