package dc82.model;

import java.util.List;

/**
 * Interface for everything that can receive {@link Status} effects.
 * Implemented by {@link Creature}, {@link Attribute} and (in future) {@code Item}.
 */
public interface EffectConsumer {

    List<Status> getStatuses();

    void addStatus(Status status);

    boolean removeStatus(String name);
}
