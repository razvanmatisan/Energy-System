package fileio;

/**
 * Class used for parsing information about a consumer from input.
 */
public final class InputConsumer extends InputEntity {
    private int id;
    private int initialBudget;
    private int monthlyIncome;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getInitialBudget() {
        return initialBudget;
    }

    public void setInitialBudget(final int initialBudget) {
        this.initialBudget = initialBudget;
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(final int monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }
}
