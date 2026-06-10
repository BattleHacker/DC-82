# EffectTemplate

**Description:** Eine vordefinierte Effekt-Vorlage aus `config/effects.xml`. Enthält `name`, `type` (CHANGE_VALUE/MODIFY_VALUE), `field` (VALUE/XP/TALENT), `icon` (placeholder) und optional `defaultDuration`. Wird von `Race.loadAll()` referenziert, um Rassen-Effekte zu erzeugen.

**Related keywords:**
- `./Race.md` – Race nutzt EffectTemplate zum Erzeugen von RaceEffect-Einträgen
- `./Effect.md` – EffectTemplate erzeugt Effect-Instanzen
- `./ConfigLoader.md` – EffectTemplate nutzt ConfigLoader zum XML-Parsen

**Related files:**
- `src/main/java/dc82/model/EffectTemplate.java`
- `config/effects.xml`

**Classes:** `dc82.model.EffectTemplate`

**TODOs:** *(none)*

**Latest changes:**
- Initial erstellt: Lazy-Load aus `config/effects.xml`
- Drei Vorlagen: `ATTRIBUTE_BONUS` (MODIFY_VALUE/VALUE), `XP_GAIN` (CHANGE_VALUE/XP), `TALENT_BONUS` (CHANGE_VALUE/TALENT)