package dc82;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import dc82.controller.GameController;
import dc82.util.Logger;
import dc82.util.SaveManager;
import dc82.view.ScreenId;
import dc82.view.ViewManager;
import dc82.view.ViewManagerImpl;

public class Main extends Game {

    @Override
    public void create() {
        Logger.init(logMode());
        Logger.info("Application started");

        SaveManager saveManager = new SaveManager();
        GameController controller = new GameController(saveManager);
        ViewManager viewManager = new ViewManagerImpl(this, controller);
        controller.bind(viewManager);
        viewManager.showScreen(ScreenId.SPLASH);
        Logger.info("SplashScreen shown");
    }

    public static void main(String[] args) {
        var config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("DC-82");
        config.setWindowedMode(1280, 960);
        config.setResizable(false);
        config.useVsync(true);
        config.setForegroundFPS(60);
        new Lwjgl3Application(new Main(), config);
    }

    private static Logger.Mode logMode() {
        for (String arg : System.getProperty("sun.java.command", "").split("\\s+")) {
            if ("--debug".equals(arg)) {
                return Logger.Mode.DEBUG;
            }
        }
        return Logger.Mode.PROD;
    }
}

