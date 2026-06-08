# Status

**Description:** Ein Status (z.B. poisoned, stunned, burning) der an einen EffectConsumer (Creature, Attribute oder Item) angehängt werden kann. Status werden von der Game-Logik interpretiert, um alternatives Verhalten zu steuern.

**Related keywords:**
- `./EffectConsumer.md` – Status werden an EffectConsumer gehängt
- `./Effect.md` – Effects können Status verhängen
- `./Creature.md` – Kreaturen können Status halten
- `./Attribute.md` – Attribute können Status halten

**Related files:**
- `src/main/java/dc82/model/Status.java`

**Classes:** `dc82.model.Status`

**TODOs:**
- Mechanik ausarbeiten: Wie modifiziert ein Status die Game-Logik? (z.B. overrideTrigger fuer TAUNTED, skipTrigger fuer STUNNED)
- Status koennte eine Map von TriggerPhase -> Modifikationen halten

**Latest changes:**
- Neu erstellt
- Konzept notiert: Status modifiziert Ausloeser-Phase von Effekten in Kampfrunden
