package dc82.view;

import com.badlogic.gdx.Game;
import dc82.controller.GameController;
import dc82.view.screens.*;

import java.util.HashMap;
import java.util.Map;

public class ViewManagerImpl implements ViewManager {

    private final Game game;
    private final GameController controller;
    private final Map<ScreenId, AbstractScreen> screens = new HashMap<>();
    private AbstractScreen currentScreen;

    public ViewManagerImpl(Game game, GameController controller) {
        this.game = game;
        this.controller = controller;
    }

    @Override
    public void showScreen(ScreenId id) {
        AbstractScreen screen = getOrCreate(id);
        if (currentScreen != null && currentScreen != screen) {
            currentScreen.hide();
        }
        currentScreen = screen;
        game.setScreen(screen);
    }

    @Override
    public void showScreenWithTransition(ScreenId id) {
        showScreen(id);
    }

    private AbstractScreen getOrCreate(ScreenId id) {
        return screens.computeIfAbsent(id, this::createScreen);
    }

    private AbstractScreen createScreen(ScreenId id) {
        return switch (id) {
            case SPLASH -> new SplashScreen(this, controller);
            case MAIN_MENU -> new MainMenuScreen(this, controller);
            case LOAD_GAME -> new LoadGameScreen(this, controller);
            case MODS -> new ModsScreen(this, controller);
            case SETTINGS -> new SettingsScreen(this, controller);
        };
    }
}
