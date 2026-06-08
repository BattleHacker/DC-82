package dc82.model;

import java.util.Objects;

/**
 * An attribute that tracks experience points (XP) and uses a talent multiplier.
 * XP determines the attribute's value (level) via a geometric progression:
 * level 1 = 0–99 XP, level 2 = 100–299 XP, level 3 = 300–699 XP, …
 * xpTracking is always {@code true} for this type.
 */
public class AttributeXP extends Attribute {

    public static final int MAX_LEVEL = 100;

    private long xp;
    private double talent;

    public AttributeXP(String name, int value) {
        super(name, value, true, 1, MAX_LEVEL);
        this.xp = 0;
        this.talent = 1.0;
    }

    public AttributeXP(String name, int value, double talent) {
        super(name, value, true, 1, MAX_LEVEL);
        this.xp = 0;
        this.talent = requireValidTalent(talent);
    }

    public AttributeXP(String name, int value, double talent, long xp) {
        super(name, value, true, 1, MAX_LEVEL);
        this.xp = xp;
        this.talent = requireValidTalent(talent);
    }

    /**
     * Returns the current level derived from XP.
     * Level thresholds follow a geometric progression: 100, 200, 400, 800, …
     * Uses a direct logarithmic formula capped at {@code MAX_LEVEL}.
     */
    public int getLevel() {
        if (xp <= 0) return 1;
        int level = (int)(Math.log(xp / 100.0 + 1) / Math.log(2)) + 1;
        return Math.min(level, MAX_LEVEL);
    }

    /** Updates the inherited {@code value} to match {@link #getLevel()}. */
    private void syncValue() {
        setValue(getLevel());
    }

    /**
     * Adds XP to this attribute. Positive gain is multiplied by talent;
     * negative gain is divided by talent. XP and value (level) are updated.
     */
    public void addXP(long newXp) {
        if (newXp > 0) {
            xp += Math.round(newXp * talent);
        } else {
            xp += Math.round(newXp / talent);
        }
        if (xp < 0) {
            xp = 0;
        }
        syncValue();
    }

    public long getXp() {
        return xp;
    }

    public void setXp(long xp) {
        this.xp = Math.max(xp, 0);
        syncValue();
    }

    public double getTalent() {
        return talent;
    }

    public void setTalent(double talent) {
        this.talent = requireValidTalent(talent);
    }

    private static double requireValidTalent(double talent) {
        if (talent <= 0) throw new IllegalArgumentException("Talent must be > 0, got: " + talent);
        return talent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AttributeXP that)) return false;
        if (!super.equals(o)) return false;
        return xp == that.xp && Double.compare(talent, that.talent) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), xp, talent);
    }

    @Override
    public String toString() {
        return super.toString() + " xp=" + xp + " talent=" + talent;
    }
}
