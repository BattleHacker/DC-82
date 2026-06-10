# Logger

**Description:** Globales Logging-System. Im DEBUG-Modus werden alle Logs in die Konsole geschrieben. Im PROD-Modus in die Datei `game.log` (via Gdx.files.local). Die Log-Datei wird bei jedem Neustart geleert. `debug()`-Aufrufe werden nur im DEBUG-Modus ausgegeben.

**Related keywords:**
- `./DC-82.md`
- `./SaveManager.md`

**Related files:**
- `src/main/java/dc82/util/Logger.java`
- `src/main/java/dc82/Main.java`

**Classes:** `dc82.util.Logger`, `dc82.util.Logger.Mode`

**TODOs:** *(none)*

**Latest changes:**
- Logger mit Mode (DEBUG/PROD), Leveln (DEBUG/INFO/WARN/ERROR), Datei-Logging in game.log
- Main: Logger.init() in create(), Mode via `--debug` CLI-Flag in `sun.java.command`
