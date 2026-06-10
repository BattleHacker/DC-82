# RandomUtil

**Description:** Zentrale Zufalls-Quelle für das gesamte Spiel. Stellt einen gemeinsamen `Random`-Instanz via `RandomUtil.get()` bereit und eine `randomName(minSyllables, maxSyllables)`-Methode zur Generierung von Fantasy-Namen (Konsonant-Vokal-Silben). Wird von `Milestone`, `Character` und `Race.RaceEffect.resolveAmount()` genutzt.

**Related keywords:**
- `./Milestone.md` – Milestone nutzt RandomUtil.randomName(5, 12)
- `./Character.md` – Character nutzt RandomUtil.randomName(2, 4)
- `./Race.md` – RaceEffect.resolveAmount() nutzt RandomUtil.get()

**Related files:**
- `src/main/java/dc82/util/RandomUtil.java`

**Classes:** `dc82.util.RandomUtil`

**TODOs:** *(none)*

**Latest changes:**
- Initial erstellt: zentrale Random-Instanz + randomName()
- Namenslisten aus Milestone übernommen (C + V Arrays)
