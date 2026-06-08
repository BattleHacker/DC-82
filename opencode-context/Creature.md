# Creature

**Description:** Eine Kreatur in der Spielwelt. Hält eine `List<Attribute>` und erlaubt das Hinzufügen, Suchen und Entfernen von Attributen. Implementiert `EffectConsumer` und kann `Status`-Objekte halten.

**Related keywords:**
- `./Attribute.md` – Attribute definieren die Eigenschaften einer Creature
- `./Status.md` – Creature kann Status halten (via EffectConsumer)
- `./EffectConsumer.md` – Creature implementiert EffectConsumer

**Related files:**
- `src/main/java/dc82/model/Creature.java`
- `src/main/java/dc82/model/EffectConsumer.java`

**Classes:** `dc82.model.Creature`

**TODOs:** *(none)*

**Latest changes:**
- Methoden-Typos korrigiert (`getAttributees` → `getAttributes` etc.)
- Implementiert `EffectConsumer` mit Status-Liste
