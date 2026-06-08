# Race

**Description:** Eine spielbare NPC-Rasse (z.B. Human, Elf, Wolf). Statische Lazy-Load-Funktion: `getRace(name)` lädt bei erstem Zugriff alle Rassen aus `config/races.xml` und cached sie.

**Related keywords:**
- `./ConfigLoader.md` – Race nutzt ConfigLoader zum XML-Parsen

**Related files:**
- `src/main/java/dc82/model/Race.java`
- `config/races.xml`

**Classes:** `dc82.model.Race`

**TODOs:** *(none)*

**Latest changes:**  
- Initial erstellt: Lazy-Load aus `config/races.xml`
- Optimiert: `HashMap<String, Race>` statt lineare Liste
