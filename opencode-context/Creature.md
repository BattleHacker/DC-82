# Creature

**Description:** Eine Kreatur in der Spielwelt. Jede Creature hat eine `Race` (Rasse) und eine `List<Attribute>`. Attribute werden **lazy** erzeugt: `getAttribute(name)` legt ein neues `Attribute(name, 0)` an, falls es nicht existiert. Beim Erstellen werden automatisch die Rassen-Effekte (CHANGE_VALUE + MODIFY_VALUE) aus der Race angewendet.

**Related keywords:**
- `./Attribute.md` – Attribute definieren die Eigenschaften einer Creature
- `./Race.md` – Jede Creature hat eine Rasse
- `./Effect.md` – Rassen-Effekte werden auf Attribute angewendet
- `./Status.md` – Creature kann Status halten (via EffectConsumer)
- `./EffectConsumer.md` – Creature implementiert EffectConsumer
- `./EffectTemplate.md` – Effect-Vorlagen für Rassen-Effekte
- `./AttributeRegistry.md` – Creature nutzt `xpTrackingNames()` für lazy Attribute-Erzeugung

**Related files:**
- `src/main/java/dc82/model/Creature.java`
- `src/main/java/dc82/model/EffectConsumer.java`

**Classes:** `dc82.model.Creature`

**TODOs:** *(none)*
- No-Arg-Constructor hinzugefügt (für LibGDX Json-Deserialisierung, ohne applyRaceEffects())

**Latest changes:**
- Race-Feld hinzugefügt (Constructor-Pflicht, plus Getter/Setter)
- `getAttribute(String)` auto-creates jetzt fehlende Attribute (AttributeXP für xpTracking=true)
- Rassen-Effekte werden im Constructor automatisch angewendet (`applyRaceEffects()`)
- `applyEffects(List<RaceEffect>)` als protected Methode extrahiert (für Character und Subklassen)
- `applyRaceEffects()` delegiert jetzt an `applyEffects()`
