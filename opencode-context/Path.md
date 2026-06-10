# Path

**Description:** Beschreibt eine bidirektionale Verbindung zwischen zwei HexCoords (a, b). Wird zentral in MapState.allPaths verwaltet. Jedes Milestone referenziert seine ausgehenden Pfade in `paths`.

**Related keywords:**
- `./DC-82.md`
- `./HexCoord.md`
- `./Milestone.md`
- `./MapState.md`

**Related files:**
- `src/main/java/dc82/model/Path.java`

**Classes:** `dc82.model.Path`

**TODOs:** *(none)*

**Latest changes:**
- Path-Klasse mit HexCoord a, b, distance, connects(), other()
