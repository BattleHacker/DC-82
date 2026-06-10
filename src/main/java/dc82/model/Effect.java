package dc82.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * An effect represents a change in game logic.
 * <ul>
 *   <li>{@link Type#CHANGE_VALUE} – permanently changes an attribute's value, XP, or talent.</li>
 *   <li>{@link Type#MODIFY_VALUE} – temporarily modifies an attribute's effective value.</li>
 * </ul>
 * An effect may also carry {@link Status} instances that are applied to
 * an {@link EffectConsumer} (Creature, Attribute, or Item).
 */
public class Effect {

    public enum Type {
        CHANGE_VALUE,
        MODIFY_VALUE
    }

    public enum Field {
        VALUE,
        XP,
        TALENT
    }

    private Type type;
    private Field field;
    private double amount;
    private int duration;
    private List<Status> statuses;

    // No-arg constructor for LibGDX Json deserialization only
    public Effect() {
        this.statuses = new ArrayList<>();
    }

    /** Constructor for a CHANGE_VALUE effect (permanent, no duration). */
    public Effect(Type type, Field field, double amount) {
        this(type, field, amount, 0, null);
    }

    /** Constructor for a MODIFY_VALUE effect (temporary, always targets VALUE). */
    public Effect(Type type, double amount, int duration) {
        this(type, Field.VALUE, amount, duration, null);
    }

    /** Constructor with type, field, amount and duration (no statuses). */
    public Effect(Type type, Field field, double amount, int duration) {
        this(type, field, amount, duration, null);
    }

    /** Full constructor that accepts all fields. */
    public Effect(Type type, Field field, double amount, int duration, List<Status> statuses) {
        this.type = type;
        this.field = field;
        this.amount = amount;
        this.duration = duration;
        this.statuses = statuses != null ? new ArrayList<>(statuses) : new ArrayList<>();
    }

    /**
     * Applies the numeric change of this effect (CHANGE_VALUE only) to the given attribute.
     * Delegates to the polymorphic {@link Attribute#applyChange(Field, int)}.
     */
    public void applyChange(Attribute attr) {
        if (type != Type.CHANGE_VALUE) return;
        attr.applyChange(field, amount);
    }

    /**
     * Applies all statuses carried by this effect to the given consumer.
     */
    public void applyStatuses(EffectConsumer consumer) {
        for (Status status : statuses) {
            consumer.addStatus(status);
        }
    }

    /** Adds a single status to this effect's payload. */
    public void addStatus(Status status) {
        this.statuses.add(status);
    }

    // -- Setters (for LibGDX Json deserialization) ---------------------

    public void setType(Type type) {
        this.type = type;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses != null ? new ArrayList<>(statuses) : new ArrayList<>();
    }

    // -- Getters -------------------------------------------------------

    public Type getType() {
        return type;
    }

    public Field getField() {
        return field;
    }

    public double getAmount() {
        return amount;
    }

    public int getDuration() {
        return duration;
    }

    public List<Status> getStatuses() {
        return Collections.unmodifiableList(statuses);
    }

    // -- Object overrides ----------------------------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Effect effect)) return false;
        return amount == effect.amount
                && duration == effect.duration
                && type == effect.type
                && field == effect.field
                && Objects.equals(statuses, effect.statuses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, field, amount, duration, statuses);
    }

    @Override
    public String toString() {
        String s = type + " " + field + " " + amount;
        if (duration > 0) s += " for " + duration + " turns";
        if (!statuses.isEmpty()) s += " +statuses=" + statuses;
        return s;
    }
}
