# MapGenerator

**Description:** Prozedurale Generierung des Milestone-Graphen. Bei Betreten eines neuen Milestones werden 1–5 Nachbarn generiert (erster MS immer 4). Expansions-Regel: Phase 1 wählt zufällig innerhalb ±20° der Expansionsrichtung (weg von (0,0)); Phase 2 füllt Rest aus dem Pool. 30°-Mindestwinkel zwischen Nachbarn. Keine Crossing-Pfade, keine Milestones auf Pfadlinien. Dead-End-Prävention: voriger MS muss ≥2 unbesuchte Nachbarn haben.

**Related keywords:**
- `./DC-82.md`
- `./HexCoord.md`
- `./Path.md`
- `./Milestone.md`
- `./MapState.md`

**Related files:**
- `src/main/java/dc82/util/MapGenerator.java`
- `src/main/java/dc82/controller/GameController.java`

**Classes:** `dc82.util.MapGenerator`

**TODOs:**
- (none — dead-end prevention implemented via deterministic check)

**Latest changes:**
- 2-Phasen-Generierung: Expansion (±20° Richtung weg von (0,0)) + Füllen aus Pool
- Erster MS (0,0) immer target=4 (4 Nachbarn)
- 30°-Regel, no-crossing, isOnAnyPathLine
- Dead-End-Prävention: voriger MS muss ≥2 unbesuchte Nachbarn haben
- max. 2 bestehende unbesuchte MS, min. 1 neuer MS pro Durchlauf
