# Race

**Description:** Eine spielbare NPC-Rasse (z.B. Human, Elf, Wolf). Definiert eine Liste von `RaceEffect`-Records, die bei der Erstellung einer `Creature` dieser Rasse angewendet werden (CHANGE_VALUE einmalig, MODIFY_VALUE als angehängter Effect). Statische Lazy-Load-Funktion: `getRace(name)` lädt bei erstem Zugriff alle Rassen aus `config/races.xml` und cached sie.

**Related keywords:**
- `./ConfigLoader.md` – Race nutzt ConfigLoader zum XML-Parsen
- `./Creature.md` – Rassen-Effekte werden auf Creature angewendet
- `./EffectTemplate.md` – Effekt-Vorlagen aus effects.xml werden in races.xml referenziert
- `./AttributeRegistry.md` – Race nutzt `expandAttributeNames()` via AttributeRegistry

**Related files:**
- `src/main/java/dc82/model/Race.java`
- `config/races.xml`
- `config/effects.xml`

**Classes:** `dc82.model.Race`, `dc82.model.Race.RaceEffect` (record)

**Static helpers:**
- `parseEffectElement(Element)` – parst ein `<effect>`-XML-Element in `List<RaceEffect>` (nutzt intern `AttributeRegistry.expandAttributeNames()`)

**TODOs:** *(none)*

**Latest changes:**  
- `expandAttributeNames()` in `AttributeRegistry` ausgelagert (Issue #2/#6)
- `parseEffectElement(Element)` als statische Methode extrahiert – zentraler `<effect>`-Parser für Race + CharacterTemplate (Issue #1)
- `loadAll()` verwendet jetzt `parseEffectElement()`
- `RaceEffect`-Record um `maxAmount` (double), `decimalPlaces` (int) und `resolveAmount()` erweitert
- `resolveAmount()`: bei `maxAmount > amount` wird ein Zufallswert gezogen (ganze Zahlen via `nextInt()`, Dezimal via `step * nextInt(steps)`)
- Parsing in `loadAll()` unterstützt Bereichs-Syntax `"3 - 5"` (ganze Zahlen) und `"3.0 - 5.0"` (Nachkommastellen)
- `decimalPlaces()` Helper (package-private) für Nachkommastellen-Ermittlung
- `RaceEffect`-Record initial hinzugefügt (`attributeName`, `type`, `field`, `amount`, `duration`)
- Rassen laden jetzt `<effects>`-Blöcke aus `races.xml`; referenzieren `EffectTemplate` aus `effects.xml`
- `races.xml` verwendet jetzt `name`-Attribut auf `<race>` statt Text-Content
