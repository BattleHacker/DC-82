package dc82.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import dc82.util.ConfigLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class AttributeRegistry {

    private static final List<String> ALL_NAMES = loadAll();
    private static final Set<String> XP_NAMES = loadXpTracking();
    private static final Set<String> CATEGORIES = Set.of("C", "B", "P", "S");

    private static List<String> loadAll() {
        try {
            Document doc = ConfigLoader.loadXml("attributes.xml");
            var nodeList = doc.getDocumentElement().getElementsByTagName("attribute");
            List<String> names = new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                names.add(((Element) nodeList.item(i)).getAttribute("name").trim());
            }
            return names;
        } catch (SAXException | IOException e) {
            return List.of();
        }
    }

    private static Set<String> loadXpTracking() {
        try {
            Document doc = ConfigLoader.loadXml("attributes.xml");
            var nodeList = doc.getDocumentElement().getElementsByTagName("attribute");
            Set<String> set = new HashSet<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element elem = (Element) nodeList.item(i);
                String name = elem.getAttribute("name");
                String xpStr = elem.getAttribute("xpTracking");
                if (xpStr.isEmpty() || Boolean.parseBoolean(xpStr)) {
                    set.add(name);
                }
            }
            return set;
        } catch (SAXException | IOException e) {
            return Set.of();
        }
    }

    public static List<String> allNames() {
        return ALL_NAMES;
    }

    public static Set<String> xpTrackingNames() {
        return XP_NAMES;
    }

    public static List<String> namesByPrefix(String prefix) {
        return ALL_NAMES.stream()
                .filter(n -> n.startsWith(prefix))
                .collect(Collectors.toList());
    }

    public static List<String> expandAttributeNames(String spec) {
        if (spec == null || spec.isBlank()) return List.of();
        String s = spec.trim();
        if (s.equals("*")) return List.copyOf(ALL_NAMES);
        if (s.length() == 1 && CATEGORIES.contains(s)) {
            return namesByPrefix(s + "_");
        }
        if (s.contains(",")) {
            return Arrays.stream(s.split(","))
                    .map(String::trim)
                    .filter(p -> !p.isEmpty())
                    .flatMap(part -> expandAttributeNames(part).stream())
                    .collect(Collectors.toList());
        }
        return List.of(s);
    }
}