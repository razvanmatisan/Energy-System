package fileio;


public abstract class InputEntity {
    /**
     * @return the id of an entity.
     */
    public abstract int getId();

    /**
     * Method that sets the id of an entity.
     */
    public abstract void setId(int id);

    /**
     * @return the initialBudget of an entity.
     */
    public abstract int getInitialBudget();

    /**
     * Method that sets the initialBudget of an entity.
     */
    public abstract void setInitialBudget(int initialBudget);
}
