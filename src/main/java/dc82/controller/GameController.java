package dc82.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import dc82.model.Character;
import dc82.model.CharacterTemplate;
import dc82.model.SaveSlot;
import dc82.util.SaveManager;
import dc82.view.ScreenId;
import dc82.view.ViewManager;

public class GameController {

    private final SaveManager saveManager;
    private final MapController mapController;
    private ViewManager viewManager;
    private SaveSlot currentSlot;

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

    public SaveSlot getCurrentSlot() {
        return currentSlot;
    }

    public void startNewGame() {
        CharacterTemplate normal = CharacterTemplate.getTemplate("Normal");
        List<Character> party = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            party.add(new Character(normal));
        }

        String id = UUID.randomUUID().toString();
        String displayName = "New Game - " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        SaveSlot slot = new SaveSlot(id, displayName);
        slot.gameState.party = party;
        saveManager.saveSlot(slot);

        currentSlot = slot;
        mapController.startNewMap();
    }

    public void loadGame(SaveSlot slot) {
        currentSlot = slot;
        mapController.startNewMap();
        if (viewManager == null) {
            dc82.util.Logger.warn("GameController.loadGame() called before bind()");
            return;
        }
        viewManager.showScreen(ScreenId.ENCOUNTER);
    }

    public void onMenuAction(MenuAction action) {
        switch (action) {
            case CONTINUE -> {
                SaveSlot latest = saveManager.getLatestSlot();
                if (latest != null) {
                    loadGame(latest);
                }
            }
            case NEW_GAME -> viewManager.showScreen(ScreenId.NEW_GAME);
            case START_GAME -> {
                startNewGame();
                viewManager.showScreen(ScreenId.ENCOUNTER);
            }
            case LOAD -> viewManager.showScreen(ScreenId.LOAD_GAME);
            case MODS -> viewManager.showScreen(ScreenId.MODS);
            case SETTINGS -> viewManager.showScreen(ScreenId.SETTINGS);
            case MAP -> viewManager.showScreen(ScreenId.MAP);
            case BACK_TO_ENCOUNTER -> viewManager.showScreen(ScreenId.ENCOUNTER);
            case BACK -> viewManager.showScreen(ScreenId.MAIN_MENU);
            default -> dc82.util.Logger.warn("Unhandled menu action: " + action);
        }
    }
}
