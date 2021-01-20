package fileio;

import simulation.MonthlyUpdate;

import java.util.List;

/**
 * Class that stores all information about input json files.
 */
public final class Input {
    private int numberOfTurns;
    private InitialData initialData;
    private List<MonthlyUpdate> monthlyUpdates;

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public InitialData getInitialData() {
        return initialData;
    }

    public List<MonthlyUpdate> getMonthlyUpdates() {
        return monthlyUpdates;
    }

    public void setNumberOfTurns(final int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public void setInitialData(final InitialData initialData) {
        this.initialData = initialData;
    }

    public void setMonthlyUpdates(final List<MonthlyUpdate> monthlyUpdates) {
        this.monthlyUpdates = monthlyUpdates;
    }
}
