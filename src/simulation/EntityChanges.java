package simulation;

import entities.Distributor;
import entities.Entity;

import java.util.List;

public interface EntityChanges {
    /**
     * Method that sets costs changes for a specific distributor/producer.
     */
    void update(final List<? extends Entity> entities);
}
