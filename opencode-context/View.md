# View

**Description:** Die View-Schicht basiert auf **LibGDX 1.12.1** mit scene2d-UI (Stage, Table, TextButton, Label, Skin) und einem FitViewport (640×480 virtuelle Pixel). MVC-Architektur: View hat keine Model-Referenz, der Controller vermittelt über MenuAction. Alle Screens haben Zugriff auf `ViewManager` und `GameController`.

**Links:**
- `./DC-82.md`
- `./Architektur-Grundprinzipien.md`

**Related files:** Siehe `./DC-82.md` »Related files« für vollständige Liste der View-Klassen.

**Classes:** Siehe `./DC-82.md` »Classes«.

**TODOs:** 
- PixelFont: Echten Pixel-Font via FreeTypeFontGenerator oder programmatisch generieren (derzeit default BitmapFont)
- Transitions: Fade-Animation zwischen Screens (derzeit nur FadeIn beim Show)
- EncounterScreen: Kampf- und Erkundungs-Mechaniken
- MapScreen: Scrollrad-Zoom-Unterstützung
- Skin: .json-Skin-Datei auslagern für einfachere Anpassung

**Latest changes:** Siehe `./DC-82.md` »Latest changes« für vollständige Historie.
