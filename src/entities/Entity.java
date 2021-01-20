package entities;

/**
 * Entity interface with pay and getPaid methods.
 */
public interface Entity {
    /**
     * Method that allows an entity to pay another entity.
     * @param entity payee
     * @param typeEntity type of entity
     */
    void pay(Entity entity, String typeEntity);

    /**
     * Method that adds money to an entity's current budget.
     * @param money sum of money an entity receives.
     */
    void getPaid(long money);
}
