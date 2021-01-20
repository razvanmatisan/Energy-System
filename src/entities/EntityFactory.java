package entities;

import fileio.InputConsumer;
import fileio.InputDistributor;
import fileio.InputEntity;
import utils.Constants;

/**
 * Factory Method class for creating Entity objects.
 */
public final class EntityFactory {
    private static EntityFactory factory;

    private EntityFactory() {

    }

    /**
     * @return the instance of the singleton class.
     */
    public static EntityFactory getInstance() {
        if (factory == null) {
            factory = new EntityFactory();
        }

        return factory;
    }

    /**
     * Method that convert an InputEntity object into an Entity one.
     * @param inputEntity an InputEntity object
     * @param typeEntity type of entity
     * @return an Entity object
     */
    public Entity createEntity(final InputEntity inputEntity,
                                               final String typeEntity) {

        switch (typeEntity) {
            case (Constants.CONSUMER) -> {
                return new Consumer((InputConsumer) inputEntity);
            }
            case (Constants.DISTRIBUTOR) -> {
                return new Distributor((InputDistributor) inputEntity);
            }
            default -> throw new IllegalStateException("Unexpected value: " + typeEntity);
        }
    }
}
