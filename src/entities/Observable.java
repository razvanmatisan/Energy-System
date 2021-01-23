package entities;

public interface Observable {
    /**
     * Method that adds an Observer to a list.
     */
    void addObserver(Observer observer);

    /**
     * Method that removes an Observer from a list.
     */
    void removeObserver(Observer observer);

    /**
     * Method that notifies all observers after a change occurs.
     */
    void notifyAllObservers();
}
