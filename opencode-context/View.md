# View

**Description:** Die View-Schicht basiert auf **LibGDX 1.12.1** mit scene2d-UI (Stage, Table, TextButton, Label, Skin) und einem FitViewport (320×240 virtuelle Pixel). MVC-Architektur: View hat keine Model-Referenz, der Controller vermittelt über MenuAction. Alle Screens haben Zugriff auf `ViewManager` und `GameController`.

**Links:**
- `./DC-82.md`
- `./Architektur-Grundprinzipien.md`

**Related files:**
- `src/main/java/dc82/view/ViewManager.java` — Interface: `showScreen(id)`, `showScreenWithTransition(id)`
- `src/main/java/dc82/view/ViewManagerImpl.java` — Lazy-cached Screens, Delegation an Game.setScreen()
- `src/main/java/dc82/view/ScreenId.java` — Enum: SPLASH, MAIN_MENU, LOAD_GAME, MODS, SETTINGS
- `src/main/java/dc82/view/screens/AbstractScreen.java` — Basis: Stage, Skin (generierte Pixmap-Texturen), animateFadeIn/Out
- `src/main/java/dc82/view/screens/SplashScreen.java` — 0.5s → MainMenuScreen
- `src/main/java/dc82/view/screens/MainMenuScreen.java` — 4 Buttons vertikal zentriert
- `src/main/java/dc82/view/screens/LoadGameScreen.java`
- `src/main/java/dc82/view/screens/ModsScreen.java`
- `src/main/java/dc82/view/screens/SettingsScreen.java`
- `src/main/java/dc82/view/components/PixelButton.java` — TextButton mit padding
- `src/main/java/dc82/view/components/PixelFont.java` — BitmapFont mit integer positions
- `src/main/java/dc82/controller/GameController.java` — Bindeglied zwischen UI und Model
- `src/main/java/dc82/controller/MenuAction.java` — Enum: CONTINUE, NEW_GAME, LOAD, MODS, SETTINGS, BACK
- `src/main/java/dc82/Main.java` — LibGDX Game + LWJGL3-Start

**Classes:** `dc82.view.ViewManager`, `ViewManagerImpl`, `ScreenId`, `AbstractScreen`, `SplashScreen`, `MainMenuScreen`, `LoadGameScreen`, `ModsScreen`, `SettingsScreen`, `PixelButton`, `PixelFont`, `GameController`, `MenuAction`

**TODOs:** 
- PixelFont: Echten Pixel-Font via FreeTypeFontGenerator oder programmatisch generieren (derzeit default BitmapFont)
- Transitions: Fade-Animation zwischen Screens (derzeit nur FadeIn beim Show)
- GameScreen: Spiel-View für Kampf/Map
- Skin: .json-Skin-Datei auslagern für einfachere Anpassung

**Latest changes:**
- Initiale View-Schicht mit LibGDX implementiert (2026-06-08)
- Splash → MainMenu → Sub-Views navigierbar
- Fade-Animation via Actions-API
- Programmatische Skin-Generierung (Pixmap-Texturen für buttons)
