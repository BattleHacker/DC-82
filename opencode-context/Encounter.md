# Encounter

**Description:** Eine einzelne Begegnung mit drei Zustaenden: BEFORE_COMBAT, IN_COMBAT, AFTER_COMBAT. Bei IN_COMBAT laeuft ein Kampf in Runden ab (Phasen 1-7). Werden keine feindlichen NPCs generiert, wechselt der Zustand sofort zu AFTER_COMBAT. Beim Verschieben zwischen Milestones muss eine bestimmte Anzahl aufeinanderfolgender Encounters bestanden werden.

**Related keywords:**
- `./Milestone.md` – Jeder Milestone ist ein Encounter
- `./DC-82.md` – Hauptprojekt

**Related files:** *(none yet)*

**Classes:** *(none yet)*

**TODOs:**
- Encounter-Klasse definieren (Zustand, Gegner-Liste, aktuelle Runde)
- Kampfrunden-Logik mit Phasen 1-7 implementieren
- NPC- und Spieler-Wuerfelmechanik definieren

**Latest changes:**
- Konzept notiert
