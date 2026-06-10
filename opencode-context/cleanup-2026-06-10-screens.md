# Screens & Navigation Cleanup (2026-06-10)

Issue-Liste basierend auf der Analyse aller Screen-Klassen und der Navigationslogik.

## HIGH

- [x] #1 — 4× duplizierten `btn()`-Helper aus EncounterScreen/MainMenuScreen/NewGameScreen/MapScreen nach AbstractScreen extrahieren
- [x] #2 — Static `skin`-Lifecycle: `AbstractScreen.dispose()` darf shared static Skin nicht zerstören → Skin-Init nach `Main.create()` verschoben, non-static `Skin` per Konstruktor an Screens übergeben
- [x] #3 — `LoadGameScreen.deleteDrawable` static Texture wird nie disposed → Pixmap/Texture-Erzeugung in `AbstractScreen.createSkin()` verschoben, Drawable als `delete-btn-bg` im Skin, statisches Feld entfernt (GPU-Leak)

## MEDIUM

- [x] #4 — `show()`-Rebuild-Pattern vereinheitlichen → `buildUI()` aus Konstruktor entfernt, ruft jetzt `AbstractScreen.show()` bei jedem show()-Aufruf; 3 redundante `show()`-Overrides entfernt
- [x] #5 — `showScreenWithTransition()` ist No-Op → aus Interface entfernt, alle Caller auf showScreen() umgestellt
- [x] #6 — `EncounterScreen.travelPanel()` baut gesamte UI bei Reise-Klick neu → travelPanel als Feld, refreshTravelPanel() aktualisiert nur die Reise-Buttons, buildUI() nach travel ersetzt
- [x] #7 — `MapScreen.render()` iteriert Milestones/Paths 3× pro Frame → Listen 1× pro Frame in render() fetchen, per Parameter an drawPaths/drawMilestones/drawNames übergeben
- [x] #8 — `GameController.loadGame()` NPE wenn `bind()` noch nicht aufgerufen → null-Check vor viewManager-Zugriff
- [x] #9 — `LoadGameScreen.showDeleteDialog()` unsafe `(Boolean) object`-Cast → instanceof-Prüfung
- [x] #10 — `MapScreen.drawPaths()` NPE wenn `getCurrentHex()` null ist → null-Check in connects()-Aufruf
- [x] #11 — `LoadGameScreen.formatDate()` erzeugt neues `SimpleDateFormat` pro Aufruf → static final field
- [x] #12 — `ViewManagerImpl.showScreen(null)` → schwer lesbarer Crash → null-Check + Warn-Log

## LOW

- [x] #13 — Unused `AbstractScreen.animateFadeOut()` → entfernt
- [x] #14 — `PixelFont`-BitmapFont wird nie disposed → PixelFont.dispose() + in Main.dispose() aufgerufen
- [x] #15 — `MapScreen.render()` überschreibt ohne `super.render()` → akzeptiert (eigener glClear + ShapeRenderer vor stage.draw)
- [x] #16 — `MapScreen.hide()` räumt `InputMultiplexer` nicht auf → hide() setzt inputProcessor auf null
