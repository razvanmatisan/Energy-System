package entities;

import fileio.InputDistributor;
import strategies.EnergyChoiceStrategyType;
import strategies.StrategyFactory;
import strategies.StrategyPriorities;
import utils.Constants;

import java.util.ArrayList;
import java.util.List;


public final class Distributor implements Entity, Observer {
    private int id;
    private int contractLength;
    private int budget;
    private int infrastructureCost;
    private long productionCost;
    private int energyNeededKW;
    private String producerStrategy;

    private StrategyPriorities strategy;
    List<Producer> producers = new ArrayList<>();

    /**
     * Monthly offer for contract
     */
    private long offer;
    private boolean isBankrupt;

    private List<Contract> contracts = new ArrayList<>();

    public Distributor(final InputDistributor distributor) {
        this.id = distributor.getId();
        this.contractLength = distributor.getContractLength();
        this.budget = distributor.getInitialBudget();
        this.infrastructureCost = distributor.getInitialInfrastructureCost();
        this.energyNeededKW = distributor.getEnergyNeededKW();
        this.producerStrategy = distributor.getProducerStrategy();
        this.strategy = initStrategy();
    }

    private StrategyPriorities initStrategy() {
        StrategyFactory strategyFactory = StrategyFactory.getInstance();
        return strategyFactory.createStrategy(producerStrategy);
    }

    public void executeStrategy(List<Producer> producers) {
        strategy.chooseProducers(producers);
    }

    public void calculateProductionCost() {
        productionCost = 0;
        producers.forEach(producer -> productionCost += (producer.getEnergyPerDistributor() * producer.getPriceKW()));
        productionCost = Math.round(Math.floor(productionCost/ 10));
    }

    @Override
    public void update() {

    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public void setEnergyNeededKW(int energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public String getProducerStrategy() {
        return producerStrategy;
    }

    public void setProducerStrategy(String producerStrategy) {
        this.producerStrategy = producerStrategy;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getContractLength() {
        return contractLength;
    }

    public void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(final int initialInfrastructureCost) {
        this.infrastructureCost = initialInfrastructureCost;
    }

    public long getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(final int initialProductionCost) {
        this.productionCost = initialProductionCost;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(final List<Contract> contracts) {
        this.contracts = contracts;
    }

    public long getOffer() {
        return offer;
    }

    /**
     * Setter for offer
     *
     * It is calculated depending on the current number of clients.
     */
    public void setOffer() {
        int numberContracts = contracts.size();

        if (numberContracts == 0) {
            this.offer = infrastructureCost + productionCost
                    + Math.round(Math.floor(Constants.PROFIT_PERCENT * productionCost));
        } else {
            this.offer
                    = Math.round(Math.floor(infrastructureCost / numberContracts) + productionCost
                    + Math.round(Math.floor(Constants.PROFIT_PERCENT * productionCost)));
        }
    }

    /**
     * Method that adds a contract to a distributor
     * @param contract the actual contract.
     */
    public void addContract(final Contract contract) {
        contracts.add(contract);
    }

    /**
     * Method that removes a contract.
     * @param contract the specific contract
     */
    public void removeContract(final Contract contract) {
        contracts.remove(contract);
    }

    /**
     * Method that implements a pay action (money is not given to anyone)
     */
    public void pay() {
        long totalCost = infrastructureCost + productionCost * contracts.size();
        budget -= totalCost;

        if (budget < 0) {
            isBankrupt = true;
        }
    }

    /**
     * Method that removes all finished contracts.
     */
    public void removeFinishedContracts() {
        contracts.removeIf(contract -> contract.getRemainedContractMonths() == 0);
    }

    /**
     * Method that implements the payment process
     *
     * In this state of the project, it has been left empty, but it will be
     * implemented after new Entities will be introduced to the simulation.
     * @param entity payee
     * @param typeEntity type of entity
     */
    @Override
    public void pay(final Entity entity, final String typeEntity) {

    }

    /**
     * Method that implements the action of receiving money.
     * @param money sum of money an entity receives.
     */
    @Override
    public void getPaid(final long money) {
        budget += money;
    }
}
