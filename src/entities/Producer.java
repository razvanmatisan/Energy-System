package entities;

import fileio.InputProducer;

import java.util.ArrayList;
import java.util.List;

public final class Producer implements Entity, Observable {
    private int id;
    private EnergyType energyType;
    private int maxDistributors;
    private double priceKW;
    private int energyPerDistributor;

    /**
     * Field that is used for Green Strategy.
     */
    private final boolean isRenewable;

    private List<MonthlyStat> monthlyStats = new ArrayList<>();
    private final List<Observer> clients = new ArrayList<>();

    public Producer(InputProducer inputProducer) {
        this.id = inputProducer.getId();
        this.energyType = inputProducer.getEnergyType();
        this.isRenewable = energyType.isRenewable();
        this.maxDistributors = inputProducer.getMaxDistributors();
        this.priceKW = inputProducer.getPriceKW();
        this.energyPerDistributor = inputProducer.getEnergyPerDistributor();
    }

    /**
     * Method that sets the energy per distributor
     * All the producers are then notified.
     */
    public void setEnergyPerDistributor(int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
        notifyAllObservers();
    }

    /**
     * Method that adds an observer to list.
     */
    @Override
    public void addObserver(Observer observer) {
        clients.add(observer);
    }

    /**
     * Method that removes an observer from the list
     */
    @Override
    public void removeObserver(Observer observer) {
        clients.remove(observer);
    }

    /**
     * Method that notifies all observers
     */
    @Override
    public void notifyAllObservers() {
        clients.forEach(Observer::update);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Observer> getClients() {
        return clients;
    }

    public boolean isRenewable() {
        return isRenewable;
    }

    public List<MonthlyStat> getMonthlyStats() {
        return monthlyStats;
    }

    public void setMonthlyStats(List<MonthlyStat> monthlyStats) {
        this.monthlyStats = monthlyStats;
    }

    /**
     * Method that adds a monthly stat for the current producer.
     */
    public void addMonthlyStat(MonthlyStat monthlyStat) {
        monthlyStats.add(monthlyStat);
    }

    public EnergyType getEnergyType() {
        return energyType;
    }

    public void setEnergyType(EnergyType energyType) {
        this.energyType = energyType;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public void setMaxDistributors(int maxDistributors) {
        this.maxDistributors = maxDistributors;
    }

    public double getPriceKW() {
        return priceKW;
    }

    public void setPriceKW(double priceKW) {
        this.priceKW = priceKW;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    @Override
    public void pay(Entity entity, String typeEntity) {
        System.out.println("DO NOTHING BY NOW!");
    }

    @Override
    public void getPaid(long money) {
        System.out.println("DO NOTHING BY NOW!");
    }
}
