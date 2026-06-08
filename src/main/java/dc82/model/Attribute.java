package dc82.model;

import java.util.Objects;

/**
 * A generic game-mechanic attribute (e.g. HP, strength, weapon skill).
 * Each attribute has a name, a numeric value, and an xp-tracking flag.
 */
public class Attribute {

    private String name;
    private int value;
    private boolean xpTracking;

    public Attribute(String name, int value) {
        this(name, value, true);
    }

    public Attribute(String name, int value, boolean xpTracking) {
        this.name = name;
        this.value = value;
        this.xpTracking = xpTracking;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isXpTracking() {
        return xpTracking;
    }

    public void setXpTracking(boolean xpTracking) {
        this.xpTracking = xpTracking;
    }

    @Override
    public String toString() {
        return name + ": " + value + (xpTracking ? " (xp)" : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attribute attribute)) return false;
        return value == attribute.value
                && xpTracking == attribute.xpTracking
                && Objects.equals(name, attribute.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, xpTracking);
    }
}
