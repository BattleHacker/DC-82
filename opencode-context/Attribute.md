# Attribute

**Description:** Generischer Attribut-Wert mit `name` (String), `baseValue` (int), `minValue`/`maxValue` Limits und einer Liste von MODIFY_VALUE-Effekten auf `field=VALUE`. `getValue()` verarbeitet nur VALUE-MODIFY_VALUE-Effekte sequentiell (top-to-bottom) und clamp nach jedem Schritt in die Limits. `applyChange(Field, double)` ändert den Basiswert (VALUE), XP oder TALENT via Subklasse. Implementiert `EffectConsumer` für Status.

**Hinweis:** `B_CON` steht für **CONCENTRATION** (Konzentration) und beschreibt die magische Stärke einer Kreatur, NICHT Constitution (Konstitution) wie in üblichen RPG-Systemen.

**Related keywords:**
- `./Creature.md` – Creatures halten eine Liste von Attributen
- `./Effect.md` – Attribute speichern MODIFY_VALUE-Effekte
- `./Status.md` – Attribute können Status halten (via EffectConsumer)
- `./RandomUtil.md` – Talent-Bonus nutzt in Rassen/Charakteren Zufallswerte

**Related files:**
- `src/main/java/dc82/model/Attribute.java`
- `src/main/java/dc82/model/AttributeXP.java`
- `src/main/java/dc82/model/EffectConsumer.java`
- `config/attributes.xml`

**Classes:** `dc82.model.Attribute`, `dc82.model.AttributeXP`

**TODOs:** *(none)*

**Latest changes:**
- `getValue()` filtert jetzt nur MODIFY_VALUE-Effekte mit `field=VALUE` (ignoriert TALENT/XP-Modifier)
- `applyChange(Effect.Field, double)` — Parameter von int auf double geändert (TALENT-Unterstützung)
- `config/attributes.xml` erweitert: P_-Attribute für Waffen (BAREHANDED, SHORTBLADE, …), Verteidigung, Magie (9 Schulen), Charisma (BARBING, CONVINCE, INTIMIDATE), Intelligenz (IDENT, STRATEGY, …), Stealth (SNEAK, ESCAPE), S_LONGSWORD
- No-arg-Konstruktoren in `Attribute` und `AttributeXP` ergänzt (fix für LibGDX Json-Deserialisierung beim Laden von saves/index.json)
