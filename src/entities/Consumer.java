package entities;

import fileio.InputConsumer;
import simulation.Simulation;
import utils.Constants;

import java.util.List;

public final class Consumer implements Entity {
    private final int id;
    private final int monthlyIncome;

    private int budget;
    private boolean isBankrupt;
    /**
     * The debt a consumer has if he/she does not pay for a month.
     */
    private long debt;
    private Contract contract;

    public Consumer(final InputConsumer consumer) {
        this.id = consumer.getId();
        this.budget = consumer.getInitialBudget();
        this.monthlyIncome = consumer.getMonthlyIncome();
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(final Contract contract) {
        this.contract = contract;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public int getId() {
        return id;
    }

    public int getBudget() {
        return budget;
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    /**
     * Method that adds salary to the current budget.
     */
    public void addSalary() {
        budget += monthlyIncome;
    }

    /**
     * Method that decrement the number of remaining months
     * that the current contract has.
     */
    public void decrementContract() {
        if (contract != null) {
            contract.decrementLength();
        }
    }

    /**
     * @return the distributor with whom the consumer signed the contract.
     */
    public Distributor findContract() {
        Simulation simulation = Simulation.getInstance();
        List<Distributor> distributors = simulation.getActiveDistributors();

        return distributors.stream()
                .filter(distributor -> distributor.getContracts()
                        .contains(contract)).findFirst().orElse(null);
    }

    /**
     * Method that removes the current contract from distributor.
     * @param distributors list of active distributors.
     */
    public void removeContract(final List<Distributor> distributors) {
        for (Distributor distributor : distributors) {
            if (distributor.getContracts().contains(contract)) {
                distributor.removeContract(contract);
                break;
            }
        }
    }

    /**
     * Method that implements the action of paying to a distributor.
     * @param entity the distributor
     */
    private void payToDistributor(final Entity entity) {
        long price = contract.getPrice();
        long lastDebt = debt;

        if (lastDebt != 0) {
            if ((price + lastDebt) > budget) {
                isBankrupt = true;
            } else {
                budget -= (lastDebt + price);
                if (entity != null) {
                    entity.getPaid(lastDebt + price);
                }
                debt = 0;
            }
        } else {
            if (price > budget) {
                debt = Math.round(Math.floor(Constants.DEBT_PERCENT * price));
            } else {
                budget -= price;
                if (entity != null) {
                    entity.getPaid(price);
                }
            }
        }
    }

    /**
     * @param entity payee
     * @param typeEntity type of entity
     */
    @Override
    public void pay(final Entity entity, final String typeEntity) {
        switch (typeEntity) {
            case (Constants.DISTRIBUTOR) -> payToDistributor(entity);
            default -> throw new IllegalStateException("Unexpected value: " + typeEntity);
        }
    }

    /**
     * @param money sum of money an entity receives.
     */
    @Override
    public void getPaid(final long money) {

    }
}
