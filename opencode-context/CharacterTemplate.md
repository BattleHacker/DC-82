# CharacterTemplate

**Description:** Vorlage für `Character`-Instanzen aus `config/characters.xml`. Enthält eine Liste möglicher Rassen (zufällige Auswahl), mehrere `EffectPool`s (je mit eigenem `effectCount`-Bereich) und eine Gewichtung für die zufällige Template-Auswahl via `getRandomTemplate()`. Lazy-Load via `CharacterTemplate.getTemplate(name)`.

**Related keywords:**
- `./Character.md` – Character wird aus CharacterTemplate erstellt
- `./Race.md` – Template referenziert mehrere Rassen
- `./Race.md` (RaceEffect, parseEffectElement) – Pool-Effekte via `Race.parseEffectElement()` erzeugt
- `./AttributeRegistry.md` – Indirekt genutzt über `Race.parseEffectElement()`
- `./ConfigLoader.md` – CharacterTemplate nutzt ConfigLoader zum XML-Parsen
- `./RandomUtil.md` – Zufallsauswahl von Rasse, Effekten und Template

**Related files:**
- `src/main/java/dc82/model/CharacterTemplate.java`
- `config/characters.xml`

**Classes:** `dc82.model.CharacterTemplate`, `dc82.model.CharacterTemplate.EffectPool` (record)

**TODOs:** *(none)*

**Latest changes:**
- `EffectPool` von Class auf Record umgestellt (Issue #4)
- Guards in `getRandomRace()` und `getRandomTemplate()` gegen leere Listen (Issue #3)
- `parseEffectPool()` delegiert jetzt an `Race.parseEffectElement()` (Issue #1)
