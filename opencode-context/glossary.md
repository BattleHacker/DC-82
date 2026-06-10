# Project Glossary

This file is loaded on every startup (Rule #1). Use it to find the right context file.

| Keyword | Context File | Description |
|---------|-------------|-------------|
| DC-82   | ./DC-82.md  | Main project description |
| Architektur Grundprinzipien | ./Architektur-Grundprinzipien.md | MVC-Design, generische Datenstruktur, XML-Content, erweiterbares Model, konfigurierbare Parameter |
| Attribute | ./Attribute.md | Generischer Attribut-Wert (name + value) |
| Creature | ./Creature.md | Kreatur mit Liste von Attributen |
| Race | ./Race.md | Rasse (e.g. Human, Elf), Lazy-Load aus XML |
| ConfigLoader | ./ConfigLoader.md | Utility zum Lazy-Laden von XML-Configs |
| Effect | ./Effect.md | Ein Effekt ändert Werte dauerhaft (CHANGE_VALUE) oder temporär (MODIFY_VALUE) |
| Status | ./Status.md | Ein Status (z.B. poisoned) an EffectConsumer (Creature/Attribute/Item) |
| EffectConsumer | ./EffectConsumer.md | Interface für empfänger von Status-Effekten |
| Milestone | ./Milestone.md | Point of Interest auf der Karte (z.B. Stadt, Boss) |
| Encounter | ./Encounter.md | Einzelne Begegnung mit 3 Zustaenden (before/in/after combat) |
| View | ./View.md | LibGDX-UI-Layer: Screens, Components, ViewManager, MVC-Architektur |
| SaveSlot | ./SaveSlot.md | Datenmodell für Speicherstände (SaveSlot/Settings) |
| SaveManager | ./SaveManager.md | Utility zum Speichern/Laden von Saves und Settings |
| Logger | ./Logger.md | Globales Logging (Konsole im Debug-Modus, Datei im Prod-Modus) |
| HexCoord | ./HexCoord.md | Axiale Flat-top-Hex-Koordinaten (q,r) mit Mathe-Utilities |
| Path | ./Path.md | Bidirektionale Verbindung zwischen zwei HexCoords |
| MapState | ./MapState.md | Zentraler Zustand des Milestone-Graphen |
| MapGenerator | ./MapGenerator.md | Prozedurale Generierung des Milestone-Graphen |

---

**To add a keyword:** Edit this table and create the corresponding `.md` file in this folder.
Only the user can define new keywords, or you may suggest them for approval (Rule #2).
