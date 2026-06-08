package dc82.model;

import java.util.Objects;

/**
 * A status effect (e.g. poisoned, stunned, burning) that can be attached
 * to an {@link EffectConsumer} (Creature, Attribute, or Item).
 * Game logic interprets statuses for alternative behavior.
 */
public class Status {

    private String name;
    private int duration;

    public Status(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** Duration in turns. 0 means permanent until removed. */
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Status status)) return false;
        return duration == status.duration && Objects.equals(name, status.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, duration);
    }

    @Override
    public String toString() {
        return name + " (" + duration + " turns)";
    }
}
