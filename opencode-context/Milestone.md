# Milestone

**Description:** Ein Point of Interest auf der Karte (z.B. Stadt, Bosskampf). Der Spieler bewegt sich von Milestone zu Milestone. Jeder Milestone ist datentechnisch ein Encounter, wird jedoch mit anderen Parametern generiert. Beim Verschieben zwischen Milestones muss eine bestimmte Anzahl generierter Encounters bestanden werden.

**Related keywords:**
- `./Encounter.md` – Jeder Milestone ist selbst ein Encounter
- `./RandomUtil.md` – Milestone nutzt RandomUtil.randomName()
- `./DC-82.md` – Hauptprojekt

**Related files:**
- `src/main/java/dc82/model/Milestone.java`
- `src/main/java/dc82/model/HexCoord.java`
- `src/main/java/dc82/model/Path.java`
- `src/main/java/dc82/model/MapState.java`
- `src/main/java/dc82/util/MapGenerator.java`

**Classes:** `dc82.model.Milestone`, `dc82.model.HexCoord`, `dc82.model.Path`, `dc82.model.MapState`, `dc82.util.MapGenerator`

**TODOs:**
- Encounter-Parameter pro Milestone
- Auswahl-Mechanik für nächsten Milestone
- Milestone-Typen (Stadt, Dungeon, Boss, ...)

**Latest changes:**
- Milestone-Klasse implementiert (hex, paths, isCurrent, displayName)
- HexCoord als axiales Flat-top-Koordinatensystem (distanceTo, angleDegTo, lineTo, hexesInRange)
- Path als Kante zwischen zwei HexCoords
- MapState zentraler Graph mit milestones, allPaths, current
- MapGenerator: 1–6 Nachbarn, 160-200°-Expansion vom Zentrum weg, 20°-Mindestwinkel, keine Crossing-Pfade, keine Pfade durch existierende Milestones
- `randomName()` delegiert jetzt an `RandomUtil.randomName(5, 12)`; eigene `C`/`V`/`RAND`-Felder entfernt
