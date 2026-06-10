package dc82.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import dc82.controller.GameController;
import dc82.view.screens.*;

import java.util.HashMap;
import java.util.Map;

public class ViewManagerImpl implements ViewManager {

    private final Game game;
    private final GameController controller;
    private final Skin skin;
    private final Map<ScreenId, AbstractScreen> screens = new HashMap<>();
    private AbstractScreen currentScreen;

    public ViewManagerImpl(Game game, GameController controller, Skin skin) {
        this.game = game;
        this.controller = controller;
        this.skin = skin;
    }

    @Override
    public void showScreen(ScreenId id) {
        if (id == null) {
            dc82.util.Logger.warn("ViewManagerImpl.showScreen(null) called");
            return;
        }
        AbstractScreen screen = getOrCreate(id);
        if (currentScreen != null && currentScreen != screen) {
            currentScreen.hide();
        }
        currentScreen = screen;
        game.setScreen(screen);
    }

    private AbstractScreen getOrCreate(ScreenId id) {
        return screens.computeIfAbsent(id, this::createScreen);
    }

    private AbstractScreen createScreen(ScreenId id) {
        return switch (id) {
            case SPLASH -> new SplashScreen(this, controller, skin);
            case MAIN_MENU -> new MainMenuScreen(this, controller, skin);
            case NEW_GAME -> new NewGameScreen(this, controller, skin);
            case LOAD_GAME -> new LoadGameScreen(this, controller, skin);
            case MODS -> new ModsScreen(this, controller, skin);
            case SETTINGS -> new SettingsScreen(this, controller, skin);
            case ENCOUNTER -> new EncounterScreen(this, controller, skin);
            case MAP -> new MapScreen(this, controller, skin);
            default -> throw new IllegalArgumentException("Unknown screen: " + id);
        };
    }
}
