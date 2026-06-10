package dc82.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class Logger {

    public enum Mode {
        DEBUG,
        PROD
    }

    private static Mode mode = Mode.PROD;
    private static FileHandle logFile;
    private static boolean initialized = false;
    private static final SimpleDateFormat DATE_FMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Logger() {}

    public static void init(Mode mode) {
        Logger.mode = mode;
        logFile = Gdx.files.local("game.log");
        logFile.writeString("", false);
        initialized = true;
        log(Level.INFO, "Logger initialized in " + mode + " mode");
    }

    public static void info(String message) {
        log(Level.INFO, message);
    }

    public static void warn(String message) {
        log(Level.WARN, message);
    }

    public static void error(String message) {
        log(Level.ERROR, message);
    }

    public static void debug(String message) {
        if (mode == Mode.DEBUG) {
            log(Level.DEBUG, message);
        }
    }

    private static void log(Level level, String message) {
        String entry = "[" + level.label + "] " + timestamp() + " - " + message;
        if (!initialized || mode == Mode.DEBUG) {
            if (level == Level.ERROR || level == Level.WARN) {
                System.err.println(entry);
            } else {
                System.out.println(entry);
            }
        } else {
            logFile.writeString(entry + "\n", true);
        }
    }

    private static String timestamp() {
        return DATE_FMT.format(new Date());
    }

    private enum Level {
        DEBUG("DEBUG"),
        INFO("INFO"),
        WARN("WARN"),
        ERROR("ERROR");

        final String label;

        Level(String label) {
            this.label = label;
        }
    }
}
