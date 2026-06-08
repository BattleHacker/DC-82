package dc82.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import dc82.util.ConfigLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * A playable or NPC race (e.g. Human, Elf, Wolf).
 * Races are loaded lazily from {@code config/races.xml} on first access.
 */
public class Race {

    private static final Map<String, Race> races = new HashMap<>();

    private final String name;

    public Race(String name) {
        this.name = name;
    }

    /** Returns the race name, e.g. "Human". */
    public String getName() {
        return name;
    }

    /**
     * Looks up a race by name. Loads all races from config/races.xml
     * on the first call if the internal map is empty.
     *
     * @param name the race name (case-sensitive)
     * @return the matching Race
     * @throws IllegalArgumentException if the race is not found
     */
    public static Race getRace(String name) {
        Race race = races.get(name);
        if (race != null) return race;
        if (races.isEmpty()) {
            loadAll();
            race = races.get(name);
            if (race != null) return race;
        }
        throw new IllegalArgumentException("Unknown race: " + name);
    }

    private static void loadAll() {
        try {
            Document doc = ConfigLoader.loadXml("races.xml");
            var nodeList = doc.getDocumentElement().getElementsByTagName("race");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element elem = (Element) nodeList.item(i);
                String name = elem.getTextContent().trim();
                races.put(name, new Race(name));
            }
        } catch (SAXException | IOException e) {
            throw new RuntimeException("Failed to load races.xml", e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Race race)) return false;
        return Objects.equals(name, race.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
