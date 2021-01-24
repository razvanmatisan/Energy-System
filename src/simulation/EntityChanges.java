package simulation;

import entities.Entity;

import java.util.List;

public interface EntityChanges {
    /**
     * Method that sets monthly changes for a specific distributor/producer.
     */
    void update(List<? extends Entity> entities);
}
