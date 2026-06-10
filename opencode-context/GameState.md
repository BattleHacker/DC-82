# GameState

**Description:** Speichert den vollständigen Spielzustand für einen Spielstand. Enthält aktuell die Party (`List<Character>`). Zukünftig erweiterbar um MapState, EncounterState etc. Wird als Teil von `SaveSlot` via LibGDX Json serialisiert.

**Related keywords:**
- `./SaveSlot.md` – GameState ist Feld in SaveSlot
- `./Character.md` – Party besteht aus Character-Instanzen
- `./SaveManager.md` – Serialisiert/Deserialisiert GameState

**Related files:**
- `src/main/java/dc82/model/GameState.java`
- `src/main/java/dc82/model/SaveSlot.java`

**Classes:** `dc82.model.GameState`

**TODOs:** *(none)*

**Latest changes:**
- Initial erstellt: party-Feld für 3 Normal-Charaktere
