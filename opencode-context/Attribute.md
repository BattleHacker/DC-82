# Attribute

**Description:** Generischer Attribut-Wert mit `name` (String) und `value` (int). Repräsentiert jede Art von spielmechanisch relevantem Wert (HP, Stärke, Waffenfertigkeit etc.).

**Related keywords:**
- `./Creature.md` – Creatures halten eine Liste von Attributen

**Related files:**
- `src/main/java/dc82/model/Attribute.java`

**Classes:** `dc82.model.Attribute`, `dc82.model.AttributeXP`

**TODOs:** *(none)*

**Latest changes:**
- Neues Feld `xpTracking` (boolean), default `true`
- `AttributeXP` als Subklasse mit XP-Tracking, Level-Progression und talent-Multiplikator
