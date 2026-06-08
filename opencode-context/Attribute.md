# Attribute

**Description:** Generischer Attribut-Wert mit `name` (String), `baseValue` (int), `minValue`/`maxValue` Limits und einer Liste von MODIFY_VALUE-Effekten. `getValue()` verarbeitet die Modify-Effekte sequentiell (top-to-bottom) und clamp nach jedem Schritt in die Limits. Implementiert `EffectConsumer` für Status.

**Related keywords:**
- `./Creature.md` – Creatures halten eine Liste von Attributen
- `./Effect.md` – Attribute speichern MODIFY_VALUE-Effekte
- `./Status.md` – Attribute können Status halten (via EffectConsumer)

**Related files:**
- `src/main/java/dc82/model/Attribute.java`
- `src/main/java/dc82/model/AttributeXP.java`
- `src/main/java/dc82/model/EffectConsumer.java`

**Classes:** `dc82.model.Attribute`, `dc82.model.AttributeXP`

**TODOs:** *(none)*

**Latest changes:**
- Neues Feld `xpTracking` (boolean), default `true`
- `AttributeXP` als Subklasse mit XP-Tracking, Level-Progression und talent-Multiplikator
- Feld `value` zu `baseValue` umbenannt
- `minValue`/`maxValue` Limits hinzugefügt (Attribute: 0/MAX_VALUE, AttributeXP: 1/MAX_LEVEL)
- `getValue()` verarbeitet Modify-Effekte jetzt sequentiell mit Clamping
- `setValue()` clamp in Limits
- `modifyEffects`-Liste mit `addModifyEffect(index, Effect)` zum Einfügen an Position
- Implementiert `EffectConsumer` (Status-Liste)
