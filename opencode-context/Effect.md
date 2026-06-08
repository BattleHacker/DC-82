# Effect

**Description:** Ein Effekt ändert Werte dauerhaft (CHANGE_VALUE) oder temporär (MODIFY_VALUE). CHANGE_VALUE wirkt auf Attribute (VALUE, XP, TALENT) mit Limits (min/max). MODIFY_VALUE wird in der modifyEffects-Liste des Attributs gespeichert und sequentiell von oben nach unten abgearbeitet – nach jedem Schritt wird das intermediaere Ergebnis in die Limits geclampt. Ein Effect kann auch Status-Objekte verhängen.

**Related keywords:**
- `./Attribute.md` – Attribute empfangen und speichern Effects (min/max, modifyEffects)
- `./Status.md` – Effects können Status verhängen
- `./EffectConsumer.md` – Schnittstelle für Status-Empfänger

**Related files:**
- `src/main/java/dc82/model/Effect.java`

**Classes:** `dc82.model.Effect` (inkl. Enums `Type`, `Field`)

**TODOs:**
- `DurationUnit`-Enum (`ROUNDS`, `ENCOUNTERS`) und `durationUnit`-Feld einführen
- `TriggerPhase`-Enum (`START_OF_ROUND`, `NPC_ROLL`, `PLAYER_ROLL`, `PLAYER_ASSIGN`, `STANDARD_NPC`, `END_OF_ROUND`) und `triggerPhase`-Feld einführen
- Status kann `triggerPhase` eines Effekts überschreiben (Mechanik ausarbeiten)

**Latest changes:**
- Neu erstellt
- CHANGE_VALUE beachtet Limits (minValue/maxValue) des Attributs
- MODIFY_VALUE wird sequentiell mit Clamping nach jedem Schritt verarbeitet
- DurationUnit (Runden/Encounter) und TriggerPhase (Kampfrunden-Phasen 1-6) als Konzept notiert
