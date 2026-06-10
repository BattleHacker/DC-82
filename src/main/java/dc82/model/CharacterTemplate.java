package dc82.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dc82.util.ConfigLoader;
import dc82.util.RandomUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class CharacterTemplate {

    private static final Map<String, CharacterTemplate> templates = new HashMap<>();

    private final String name;
    private final List<Race> races;
    private final List<EffectPool> effectPools;
    private final double weight;

    public CharacterTemplate(String name, List<Race> races, List<EffectPool> effectPools, double weight) {
        this.name = name;
        this.races = List.copyOf(races);
        this.effectPools = List.copyOf(effectPools);
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public List<Race> getRaces() {
        return races;
    }

    public List<EffectPool> getEffectPools() {
        return effectPools;
    }

    public double getWeight() {
        return weight;
    }

    public Race getRandomRace() {
        if (races.isEmpty()) {
            throw new IllegalStateException("Template '" + name + "' has no races");
        }
        return races.get(RandomUtil.get().nextInt(races.size()));
    }

    public List<Race.RaceEffect> getRandomEffects() {
        List<Race.RaceEffect> all = new ArrayList<>();
        for (EffectPool pool : effectPools) {
            all.addAll(pool.pickRandom());
        }
        return all;
    }

    public static CharacterTemplate getTemplate(String name) {
        synchronized (templates) {
            if (templates.isEmpty()) {
                loadAll();
            }
            CharacterTemplate t = templates.get(name);
            if (t == null) {
                throw new IllegalArgumentException("Unknown character template: " + name);
            }
            return t;
        }
    }

    public static CharacterTemplate getRandomTemplate() {
        synchronized (templates) {
            if (templates.isEmpty()) loadAll();
            if (templates.isEmpty()) {
                throw new IllegalStateException("No character templates loaded");
            }
            double total = templates.values().stream()
                    .mapToDouble(t -> t.weight)
                    .sum();
            if (total <= 0) {
                return templates.values().iterator().next();
            }
            double r = RandomUtil.get().nextDouble() * total;
            double cumulative = 0;
            for (CharacterTemplate t : templates.values()) {
                cumulative += t.weight;
                if (r <= cumulative) return t;
            }
            return templates.values().iterator().next();
        }
    }

    public record EffectPool(List<Race.RaceEffect> effects, int minCount, int maxCount) {
        public EffectPool {
            effects = List.copyOf(effects);
        }

        public List<Race.RaceEffect> pickRandom() {
            if (effects.isEmpty()) return List.of();
            int count = minCount + RandomUtil.get().nextInt(maxCount - minCount + 1);
            count = Math.min(count, effects.size());
            List<Race.RaceEffect> shuffled = new ArrayList<>(effects);
            Collections.shuffle(shuffled, RandomUtil.get());
            return List.copyOf(shuffled.subList(0, count));
        }
    }

    private static void loadAll() {
        try {
            Document doc = ConfigLoader.loadXml("characters.xml");
            var nodeList = doc.getDocumentElement().getElementsByTagName("character");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element elem = (Element) nodeList.item(i);
                String name = elem.getAttribute("name").trim();
                String weightStr = elem.getAttribute("weight");
                double weight = weightStr.isEmpty() ? 1.0 : Double.parseDouble(weightStr);

                String raceText = elem.getElementsByTagName("race").item(0).getTextContent().trim();
                List<Race> races = Arrays.stream(raceText.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(Race::getRace)
                        .collect(Collectors.toList());

                List<EffectPool> pools = new ArrayList<>();
                var poolNodes = elem.getElementsByTagName("effectPool");
                for (int j = 0; j < poolNodes.getLength(); j++) {
                    Element poolElem = (Element) poolNodes.item(j);
                    pools.add(parseEffectPool(poolElem));
                }

                templates.put(name, new CharacterTemplate(name, races, pools, weight));
            }
        } catch (SAXException | IOException e) {
            throw new RuntimeException("Failed to load characters.xml", e);
        }
    }

    private static EffectPool parseEffectPool(Element poolElem) {
        String countStr = poolElem.getAttribute("effectCount");
        int[] range = parseCountRange(countStr);

        List<Race.RaceEffect> effects = new ArrayList<>();
        var effectNodes = poolElem.getElementsByTagName("effect");
        for (int k = 0; k < effectNodes.getLength(); k++) {
            effects.addAll(Race.parseEffectElement((Element) effectNodes.item(k)));
        }

        return new EffectPool(effects, range[0], range[1]);
    }

    private static int[] parseCountRange(String s) {
        if (s.contains(" - ")) {
            String[] parts = s.split(" - ");
            return new int[]{Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim())};
        }
        int v = Integer.parseInt(s.trim());
        return new int[]{v, v};
    }
}