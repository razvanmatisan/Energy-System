package entities;

import java.util.List;

/**
 * Class that stores the monthly stat of a producer.
 */
public final class MonthlyStat {
    private int month;
    private List<Integer> distributorsIds;

    public MonthlyStat(int month, List<Integer> distributorsIds) {
        this.month = month;
        this.distributorsIds = distributorsIds;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<Integer> getDistributorsIds() {
        return distributorsIds;
    }

    public void setDistributorsIds(List<Integer> distributorsIds) {
        this.distributorsIds = distributorsIds;
    }
}
