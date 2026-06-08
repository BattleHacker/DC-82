package dc82;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import dc82.controller.GameController;
import dc82.view.ScreenId;
import dc82.view.ViewManager;
import dc82.view.ViewManagerImpl;

public class Main extends Game {

    @Override
    public void create() {
        GameController controller = new GameController();
        ViewManager viewManager = new ViewManagerImpl(this, controller);
        controller.bind(viewManager);
        viewManager.showScreen(ScreenId.SPLASH);
    }

    public static void main(String[] args) {
        var config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("DC-82");
        config.setWindowedMode(640, 480);
        config.setResizable(false);
        config.useVsync(true);
        config.setForegroundFPS(60);
        new Lwjgl3Application(new Main(), config);
    }
}
