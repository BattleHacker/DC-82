# SaveManager

**Description:** Utility zum Speichern und Laden von Spielständen und globalen Einstellungen. Nutzt LibGDX `Json` (Gdx.files.local) zur Serialisierung. Speichert `SaveSlot`-Metadaten in `saves/index.json` und die Settings in `settings.json`.

**Related keywords:**
- `./DC-82.md`
- `./SaveSlot.md`
- `./GameState.md`
- `./ConfigLoader.md`
- `./Race.md` – Race hat benutzerdefinierten Json-Serializer (nur Name wird gespeichert)

**Related files:**
- `src/main/java/dc82/util/SaveManager.java`
- `src/main/java/dc82/model/SaveSlot.java`
- `src/main/java/dc82/model/Settings.java`
- `src/main/java/dc82/view/screens/LoadGameScreen.java`
- `src/main/java/dc82/view/screens/MainMenuScreen.java`
- `src/main/java/dc82/controller/GameController.java`
- `src/main/java/dc82/Main.java`

**Classes:** `dc82.util.SaveManager`, `dc82.model.SaveSlot`, `dc82.model.Settings`

**TODOs:** *(none)*

**Latest changes:**
- SaveManager mit CRUD-Operationen implementiert (getSaveSlots, saveSlot, deleteSlot, getLatestSlot, hasSaves)
- loadSettings / saveSettings für globale Einstellungen
- Index wird als JSON-Array in saves/index.json gespeichert
- Sortierung nach updatedAt descending (neuste oben)
- Verbindung via GameController an die Screens
