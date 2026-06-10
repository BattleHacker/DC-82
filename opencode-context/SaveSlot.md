# SaveSlot / Settings

**Description:** Datenmodelle für das Speichern. `SaveSlot` speichert Metadaten (id, displayName, createdAt, updatedAt) plus `GameState` (Party) für einen Spielstand. `Settings` speichert globale Einstellungen (masterVolume, fullscreen).

**Related keywords:**
- `./DC-82.md`
- `./SaveManager.md`
- `./GameState.md` – SaveSlot enthält GameState mit Party

**Related files:**
- `src/main/java/dc82/model/SaveSlot.java`
- `src/main/java/dc82/model/GameState.java`
- `src/main/java/dc82/model/Settings.java`
- `src/main/java/dc82/util/SaveManager.java`

**Classes:** `dc82.model.SaveSlot`, `dc82.model.GameState`, `dc82.model.Settings`

**TODOs:** *(none)*

**Latest changes:**
- GameState-Feld hinzugefügt (party = 3 Character-Instanzen beim Neustart)
- SaveSlot und Settings als POJOs mit public-Feldern für LibGDX Json-Serialisierung angelegt
