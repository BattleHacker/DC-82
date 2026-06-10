# MapState

**Description:** Zentraler Zustand des Milestone-Graphen. Enthält alle Milestones (Map<HexCoord,Milestone>), alle Pfade (List<Path>) und die aktuelle Spielerposition (current). Wird serialisiert und im SaveSlot gespeichert.

**Related keywords:**
- `./DC-82.md`
- `./HexCoord.md`
- `./Path.md`
- `./Milestone.md`
- `./MapGenerator.md`
- `./SaveSlot.md`

**Related files:**
- `src/main/java/dc82/model/MapState.java`
- `src/main/java/dc82/controller/GameController.java`
- `src/main/java/dc82/util/MapGenerator.java`

**Classes:** `dc82.model.MapState`

**TODOs:** *(none)*

**Latest changes:**
- MapState mit milestones-Map, allPaths-Liste, current-HexCoord, setCurrent(), createStartingState()
