package dc82.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A creature in the game world. Each creature holds a list of {@link Attribute}
 * objects that define its game-mechanic properties.
 */
public class Creature {

    private String name;
    private List<Attribute> attributes;

    public Creature(String name) {
        this.name = name;
        this.attributes = new ArrayList<>();
    }

    public Creature(String name, List<Attribute> attributes) {
        this.name = name;
        this.attributes = new ArrayList<>(attributes);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    /** Looks up an attribute by name. Returns empty if not found. */
    public Optional<Attribute> getAttribute(String name) {
        return attributes.stream()
                .filter(a -> a.getName().equals(name))
                .findFirst();
    }

    /** Removes all attributes with the given name. Returns true if any were removed. */
    public boolean removeAttribute(String name) {
        return attributes.removeIf(a -> a.getName().equals(name));
    }

    @Override
    public String toString() {
        return name + attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Creature creature)) return false;
        return Objects.equals(name, creature.name) && Objects.equals(attributes, creature.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, attributes);
    }
}
