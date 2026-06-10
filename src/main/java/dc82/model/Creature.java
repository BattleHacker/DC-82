package dc82.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A creature in the game world. Each creature has a {@link Race} and holds
 * a list of {@link Attribute} objects that define its game-mechanic properties.
 * Attributes are created lazily on first access via {@link #getAttribute(String)}.
 * Race-specific effects are applied automatically in the constructor.
 */
public class Creature implements EffectConsumer {

    private String name;
    private Race race;
    private List<Attribute> attributes;
    private List<Status> statuses;

    // No-arg constructor for LibGDX Json deserialization only
    public Creature() {
        this.attributes = new ArrayList<>();
        this.statuses = new ArrayList<>();
    }

    public Creature(String name, Race race) {
        this(name, race, new ArrayList<>());
    }

    public Creature(String name, Race race, List<Attribute> attributes) {
        this.name = name;
        this.race = race;
        this.attributes = new ArrayList<>(attributes);
        this.statuses = new ArrayList<>();
        applyRaceEffects();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = new ArrayList<>(attributes);
    }

    /** Adds a single attribute to this creature. */
    public void addAttribute(Attribute attribute) {
        this.attributes.add(attribute);
    }

    /**
     * Returns the attribute with the given name.
     * If no such attribute exists, it is created automatically with the correct
     * subclass ({@link AttributeXP} for XP-tracking attributes, plain
     * {@link Attribute} otherwise) and added to the list.
     */
    public Attribute getAttribute(String name) {
        return attributes.stream()
                .filter(a -> a.getName().equals(name))
                .findFirst()
                .orElseGet(() -> {
                    Attribute attr = AttributeRegistry.xpTrackingNames().contains(name)
                            ? new AttributeXP(name, 0)
                            : new Attribute(name, 0);
                    attributes.add(attr);
                    return attr;
                });
    }

    /** Removes all attributes with the given name. Returns true if any were removed. */
    public boolean removeAttribute(String name) {
        return attributes.removeIf(a -> a.getName().equals(name));
    }

    // -- Race / template effects ---------------------------------------

    private void applyRaceEffects() {
        if (race == null) return;
        applyEffects(race.getRaceEffects());
    }

    /** Applies a list of race effects (CHANGE_VALUE directly, MODIFY_VALUE as modifiers). */
    protected void applyEffects(List<Race.RaceEffect> effects) {
        for (var re : effects) {
            Attribute attr = getAttribute(re.attributeName());
            double resolved = re.resolveAmount();
            if (re.type() == Effect.Type.CHANGE_VALUE) {
                attr.applyChange(re.field(), resolved);
            } else {
                attr.addModifyEffect(new Effect(Effect.Type.MODIFY_VALUE, re.field(), resolved, re.duration()));
            }
        }
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

    // -- Object overrides ---------------------------------------------

    @Override
    public String toString() {
        return race + " " + name + attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Creature creature)) return false;
        return Objects.equals(name, creature.name)
                && Objects.equals(race, creature.race)
                && Objects.equals(attributes, creature.attributes)
                && Objects.equals(statuses, creature.statuses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, race, attributes, statuses);
    }
}
