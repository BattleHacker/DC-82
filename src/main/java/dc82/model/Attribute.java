package dc82.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A generic game-mechanic attribute (e.g. HP, strength, weapon skill).
 * Each attribute has a name, a numeric base value, and an xp-tracking flag.
 * <p>
 * The effective value ({@link #getValue()}) is computed by applying all
 * MODIFY_VALUE effects sequentially from top to bottom, respecting the
 * attribute's {@code minValue} / {@code maxValue} bounds at each step.
 * It also implements {@link EffectConsumer} to receive {@link Status} effects.
 */
public class Attribute implements EffectConsumer {

    private String name;
    private int baseValue;
    private boolean xpTracking;
    private int minValue;
    private int maxValue;
    private List<Effect> modifyEffects;
    private List<Status> statuses;

    public Attribute(String name, int value) {
        this(name, value, true);
    }

    public Attribute(String name, int value, boolean xpTracking) {
        this(name, value, xpTracking, 0, Integer.MAX_VALUE);
    }

    public Attribute(String name, int value, boolean xpTracking, int minValue, int maxValue) {
        this.name = name;
        this.xpTracking = xpTracking;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.baseValue = clamp(value, minValue, maxValue);
        this.modifyEffects = new ArrayList<>();
        this.statuses = new ArrayList<>();
    }

    // -- Helpers -------------------------------------------------------

    private static int clamp(int value, int min, int max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    // -- Public API ----------------------------------------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the effective value by processing all MODIFY_VALUE effects
     * sequentially (top-to-bottom), clamping to [minValue, maxValue] after
     * each effect.
     */
    public int getValue() {
        int current = baseValue;
        for (Effect effect : modifyEffects) {
            if (effect.getType() == Effect.Type.MODIFY_VALUE) {
                current += effect.getAmount();
                current = clamp(current, minValue, maxValue);
            }
        }
        return current;
    }

    /** Sets the permanent base value, clamped to [minValue, maxValue]. */
    public void setBaseValue(int value) {
        this.baseValue = clamp(value, minValue, maxValue);
    }

    /** Returns the permanent base value without temporary modifications. */
    public int getBaseValue() {
        return baseValue;
    }

    public boolean isXpTracking() {
        return xpTracking;
    }

    public void setXpTracking(boolean xpTracking) {
        this.xpTracking = xpTracking;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    // -- Modify-value effects -----------------------------------------

    /** Returns an unmodifiable view of the temporary modify-value effects. */
    public List<Effect> getModifyEffects() {
        return Collections.unmodifiableList(modifyEffects);
    }

    /** Appends a MODIFY_VALUE effect to the end of the list. */
    public void addModifyEffect(Effect effect) {
        if (effect.getType() == Effect.Type.MODIFY_VALUE) {
            modifyEffects.add(effect);
        }
    }

    /** Inserts a MODIFY_VALUE effect at the given index. */
    public void addModifyEffect(int index, Effect effect) {
        if (effect.getType() == Effect.Type.MODIFY_VALUE) {
            modifyEffects.add(index, effect);
        }
    }

    /** Removes a specific modify-value effect. Returns true if removed. */
    public boolean removeModifyEffect(Effect effect) {
        return modifyEffects.remove(effect);
    }

    // -- EffectConsumer implementation ---------------------------------

    @Override
    public List<Status> getStatuses() {
        return Collections.unmodifiableList(statuses);
    }

    @Override
    public void addStatus(Status status) {
        this.statuses.add(status);
    }

    @Override
    public boolean removeStatus(String name) {
        return statuses.removeIf(s -> s.getName().equals(name));
    }

    /**
     * Applies a CHANGE_VALUE effect for the given field.
     * Subclasses (e.g. AttributeXP) may override to handle XP/talent changes.
     */
    public void applyChange(Effect.Field field, int amount) {
        if (field == Effect.Field.VALUE) {
            setBaseValue(getBaseValue() + amount);
        }
    }

    // -- Object overrides ---------------------------------------------

    @Override
    public String toString() {
        return name + ": " + getValue() + (xpTracking ? " (xp)" : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attribute attribute)) return false;
        return baseValue == attribute.baseValue
                && xpTracking == attribute.xpTracking
                && minValue == attribute.minValue
                && maxValue == attribute.maxValue
                && Objects.equals(name, attribute.name)
                && Objects.equals(modifyEffects, attribute.modifyEffects)
                && Objects.equals(statuses, attribute.statuses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, baseValue, xpTracking, minValue, maxValue, modifyEffects, statuses);
    }
}
