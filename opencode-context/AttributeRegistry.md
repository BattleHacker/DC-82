# AttributeRegistry

**Description:** Zentrale Registry aller Attribut-Namen aus `config/attributes.xml`. Wird von `Creature`, `Race` und `CharacterTemplate` genutzt. Bietet Expansion von Attribut-Shorthands (`*`, `C`, `B`, `P`, `S`, Komma-Listen) sowie XP-Tracking-Informationen.

**Related keywords:**
- `./Race.md` – Race nutzt `expandAttributeNames()` + `parseEffectElement()`
- `./Creature.md` – Creature nutzt `xpTrackingNames()` für lazy Attribute-Erzeugung
- `./ConfigLoader.md` – Lazy-Load der attributes.xml
- `./CharacterTemplate.md` – Charakter-Template nutzt indirekt über `Race.parseEffectElement()`

**Related files:**
- `src/main/java/dc82/model/AttributeRegistry.java`
- `config/attributes.xml`

**Classes:** `dc82.model.AttributeRegistry`

**TODOs:** *(none)*

**Latest changes:**
- Initial erstellt: zentrale Attribut-Verwaltung, expandAttributeNames() aus Race übernommen, xpTrackingNames() aus Creature übernommen
