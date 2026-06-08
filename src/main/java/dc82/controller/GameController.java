package dc82.controller;

import dc82.view.ScreenId;
import dc82.view.ViewManager;

public class GameController {

    private ViewManager viewManager;

    public GameController() {}

    public void bind(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public void onMenuAction(MenuAction action) {
        switch (action) {
            case CONTINUE, NEW_GAME -> viewManager.showScreenWithTransition(ScreenId.SPLASH);
            case LOAD -> viewManager.showScreenWithTransition(ScreenId.LOAD_GAME);
            case MODS -> viewManager.showScreenWithTransition(ScreenId.MODS);
            case SETTINGS -> viewManager.showScreenWithTransition(ScreenId.SETTINGS);
            case BACK -> viewManager.showScreenWithTransition(ScreenId.MAIN_MENU);
        }
    }
}
