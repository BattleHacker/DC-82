package dc82.util;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Lazy-loads XML config files from the {@code config/} directory
 * relative to the program's working directory. Keeps parsed documents
 * in a cache for repeated access.
 */
public class ConfigLoader {

    private static final Path configDir = Path.of("config").toAbsolutePath();
    private static final Map<String, Document> cache = new ConcurrentHashMap<>();

    /** Returns the resolved config directory path. */
    public static Path getConfigDir() {
        return configDir;
    }

    /**
     * Loads an XML file by name (e.g. "creatures.xml") from the config directory.
     * The result is cached so the file is only parsed once.
     *
     * @param filename the XML file name (relative to config/)
     * @return parsed XML Document
     * @throws IOException  if the file cannot be read
     * @throws SAXException if parsing fails
     */
    public static Document loadXml(String filename) throws IOException, SAXException {
        Document doc = cache.get(filename);
        if (doc != null) {
            return doc;
        }
        try {
            var factory = DocumentBuilderFactory.newInstance();
            var builder = factory.newDocumentBuilder();
            doc = builder.parse(configDir.resolve(filename).toFile());
            cache.put(filename, doc);
            return doc;
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("XML parser setup failed", e);
        }
    }

    /** Clears the internal cache. Useful for tests or config reload. */
    public static void clearCache() {
        cache.clear();
    }
}
