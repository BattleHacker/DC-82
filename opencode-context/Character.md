# Character

**Description:** Ein spezialisierter `Creature`-Subtyp für Spieler- und NPC-Charaktere. Wird aus einem `CharacterTemplate` erstellt. Der Name wird automatisch generiert (2–4 Silben via `RandomUtil.randomName()`). Die Rassen-Effekte + zufällig aus den `EffectPool`s gezogene Effekte werden beim Erstellen angewendet.

**Related keywords:**
- `./Creature.md` – Character erweitert Creature
- `./CharacterTemplate.md` – Character wird aus CharacterTemplate erstellt (mit zufälliger Rasse + zufälligen Effekten)
- `./Race.md` – Das Template referenziert mehrere Rassen
- `./RandomUtil.md` – Name wird per RandomUtil generiert

**Related files:**
- `src/main/java/dc82/model/Character.java`

**Classes:** `dc82.model.Character`

**TODOs:** *(none)*

**Latest changes:**
- Konstruktor nutzt jetzt `template.getRandomRace()` + `template.getRandomEffects()`
