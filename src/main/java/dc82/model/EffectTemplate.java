package dc82.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import dc82.util.ConfigLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class EffectTemplate {

    private static final Map<String, EffectTemplate> templates = new HashMap<>();

    private final String name;
    private final Effect.Type type;
    private final Effect.Field field;
    private final String icon;
    private final int defaultDuration;

    public EffectTemplate(String name, Effect.Type type, Effect.Field field, String icon, int defaultDuration) {
        this.name = name;
        this.type = type;
        this.field = field;
        this.icon = icon;
        this.defaultDuration = defaultDuration;
    }

    public String getName() {
        return name;
    }

    public Effect.Type getType() {
        return type;
    }

    public Effect.Field getField() {
        return field;
    }

    public String getIcon() {
        return icon;
    }

    public int getDefaultDuration() {
        return defaultDuration;
    }

    public static EffectTemplate getTemplate(String name) {
        synchronized (templates) {
            if (templates.isEmpty()) {
                loadAll();
            }
            EffectTemplate t = templates.get(name);
            if (t == null) {
                throw new IllegalArgumentException("Unknown effect template: " + name);
            }
            return t;
        }
    }

    private static void loadAll() {
        try {
            Document doc = ConfigLoader.loadXml("effects.xml");
            var nodeList = doc.getDocumentElement().getElementsByTagName("effect");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element elem = (Element) nodeList.item(i);
                String name = elem.getAttribute("name");
                String typeStr = elem.getAttribute("type");
                String fieldStr = elem.getAttribute("field");
                String icon = elem.getAttribute("icon");
                String durStr = elem.getAttribute("duration");

                Effect.Type type = Effect.Type.valueOf(typeStr);
                Effect.Field field = Effect.Field.valueOf(fieldStr);
                int defaultDuration = durStr.isEmpty() ? 0 : Integer.parseInt(durStr);

                templates.put(name, new EffectTemplate(name, type, field, icon, defaultDuration));
            }
        } catch (SAXException | IOException e) {
            throw new RuntimeException("Failed to load effects.xml", e);
        }
    }
}
