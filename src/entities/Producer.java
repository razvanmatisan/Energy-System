package entities;

import fileio.InputProducer;
import simulation.MonthlyUpdate;

import java.util.ArrayList;
import java.util.List;

public final class Producer implements Entity, Observable {
    private int id;
    private String energyType;
    private int maxDistributors;
    private double priceKW;
    private int energyPerDistributor;

    private List<MonthlyStats> monthlyStats = new ArrayList<>();
    private List<Observer> clients = new ArrayList<>();

    public Producer(InputProducer inputProducer) {
        this.id = inputProducer.getId();
        this.energyType = inputProducer.getEnergyType();
        this.maxDistributors = inputProducer.getMaxDistributors();
        this.priceKW = inputProducer.getPriceKW();
        this.energyPerDistributor = inputProducer.getEnergyPerDistributor();
    }

    public void setEnergyPerDistributor(int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
        notifyAllObservers();
    }

    @Override
    public void addObserver(Observer observer) {
        clients.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        clients.remove(observer);
    }

    @Override
    public void notifyAllObservers() {
        for (Observer client : clients) {
            client.update();
        }
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

    public void setClients(List<Observer> clients) {
        this.clients = clients;
    }

    public String getEnergyType() {
        return energyType;
    }

    public void setEnergyType(String energyType) {
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

    }

    @Override
    public void getPaid(long money) {

    }
}
