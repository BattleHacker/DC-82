package dc82.controller;

import dc82.util.SaveManager;
import dc82.view.ScreenId;
import dc82.view.ViewManager;

public class GameController {

    private final SaveManager saveManager;
    private final MapController mapController;
    private ViewManager viewManager;

    public GameController(SaveManager saveManager) {
        this.saveManager = saveManager;
        this.mapController = new MapController();
    }

    public void bind(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public SaveManager getSaveManager() {
        return saveManager;
    }

    public MapController getMapController() {
        return mapController;
    }

    public void startNewGame() {
        mapController.startNewMap();
    }

    public void onMenuAction(MenuAction action) {
        switch (action) {
            case CONTINUE -> viewManager.showScreenWithTransition(ScreenId.SPLASH);
            case NEW_GAME -> viewManager.showScreenWithTransition(ScreenId.NEW_GAME);
            case START_GAME -> {
                startNewGame();
                viewManager.showScreenWithTransition(ScreenId.ENCOUNTER);
            }
            case LOAD -> viewManager.showScreenWithTransition(ScreenId.LOAD_GAME);
            case MODS -> viewManager.showScreenWithTransition(ScreenId.MODS);
            case SETTINGS -> viewManager.showScreenWithTransition(ScreenId.SETTINGS);
            case MAP -> viewManager.showScreenWithTransition(ScreenId.MAP);
            case BACK_TO_ENCOUNTER -> viewManager.showScreenWithTransition(ScreenId.ENCOUNTER);
            case BACK -> viewManager.showScreenWithTransition(ScreenId.MAIN_MENU);
            default -> dc82.util.Logger.warn("Unhandled menu action: " + action);
        }
    }
}
