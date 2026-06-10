package dc82.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import dc82.util.ConfigLoader;
import dc82.util.RandomUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * A playable or NPC race (e.g. Human, Elf, Wolf).
 * Races are loaded lazily from {@code config/races.xml} on first access.
 * Each race may define a list of {@link RaceEffect}s that are applied
 * to a {@link Creature} upon creation.
 */
public class Race {

    /**
     * An effect to be applied to a creature of this race at creation time.
     * Supports both fixed and ranged amounts. When {@code maxAmount > amount},
     * a random value in [amount, maxAmount] is resolved at apply-time.
     *
     * @param attributeName the target attribute name (e.g. "B_STR")
     * @param type          CHANGE_VALUE or MODIFY_VALUE
     * @param field         VALUE, XP, or TALENT
     * @param amount        minimum / fixed value
     * @param maxAmount     upper bound (== amount for fixed values)
     * @param decimalPlaces number of decimal places (0 = integers)
     * @param duration      duration in turns (0 = permanent, only for MODIFY_VALUE)
     */
    public record RaceEffect(
            String attributeName,
            Effect.Type type,
            Effect.Field field,
            double amount,
            double maxAmount,
            int decimalPlaces,
            int duration
    ) {
        public double resolveAmount() {
            if (maxAmount <= amount) return amount;
            if (decimalPlaces == 0) {
                return amount + RandomUtil.get().nextInt((int) (maxAmount - amount) + 1);
            }
            double step = Math.pow(10, -decimalPlaces);
            int steps = (int) Math.round((maxAmount - amount) / step);
            return amount + RandomUtil.get().nextInt(steps + 1) * step;
        }
    }

    private static final Map<String, Race> races = new HashMap<>();

    private final String name;
    private final List<RaceEffect> raceEffects;

    public Race(String name) {
        this(name, List.of());
    }

    public Race(String name, List<RaceEffect> raceEffects) {
        this.name = name;
        this.raceEffects = List.copyOf(raceEffects);
    }

    /** Returns the race name, e.g. "Human". */
    public String getName() {
        return name;
    }

    /** Returns an unmodifiable list of effects applied at creature creation. */
    public List<RaceEffect> getRaceEffects() {
        return raceEffects;
    }

    /** Parses a single &lt;effect&gt; XML element into one or more RaceEffect entries. */
    public static List<RaceEffect> parseEffectElement(Element elem) {
        String ref = elem.getAttribute("ref");
        String attrSpec = elem.getAttribute("attribute");
        List<String> attrNames = AttributeRegistry.expandAttributeNames(attrSpec);

        String amountStr = elem.getAttribute("amount");
        double amt, maxAmt;
        int dec;
        if (amountStr.contains(" - ")) {
            String[] parts = amountStr.split(" - ");
            String minStr = parts[0].trim();
            String maxStr = parts[1].trim();
            amt = Double.parseDouble(minStr);
            maxAmt = Double.parseDouble(maxStr);
            dec = Math.max(decimalPlaces(minStr), decimalPlaces(maxStr));
        } else {
            amt = maxAmt = Double.parseDouble(amountStr);
            dec = decimalPlaces(amountStr);
        }

        EffectTemplate template = EffectTemplate.getTemplate(ref);
        String durStr = elem.getAttribute("duration");
        int duration = durStr.isEmpty() ? template.getDefaultDuration() : Integer.parseInt(durStr);

        List<RaceEffect> result = new ArrayList<>();
        for (String attrName : attrNames) {
            result.add(new RaceEffect(attrName, template.getType(), template.getField(), amt, maxAmt, dec, duration));
        }
        return result;
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
        synchronized (races) {
            Race race = races.get(name);
            if (race != null) return race;
            if (races.isEmpty()) {
                loadAll();
                race = races.get(name);
                if (race != null) return race;
            }
        }
        throw new IllegalArgumentException("Unknown race: " + name);
    }

    private static void loadAll() {
        try {
            Document doc = ConfigLoader.loadXml("races.xml");
            var nodeList = doc.getDocumentElement().getElementsByTagName("race");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element elem = (Element) nodeList.item(i);
                String name = elem.getAttribute("name").trim();

                List<RaceEffect> effects = new ArrayList<>();
                var effectNodes = elem.getElementsByTagName("effect");
                for (int j = 0; j < effectNodes.getLength(); j++) {
                    effects.addAll(parseEffectElement((Element) effectNodes.item(j)));
                }

                races.put(name, new Race(name, effects));
            }
        } catch (SAXException | IOException e) {
            throw new RuntimeException("Failed to load races.xml", e);
        }
    }

    static int decimalPlaces(String s) {
        int dot = s.indexOf('.');
        return dot < 0 ? 0 : s.substring(dot + 1).length();
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
