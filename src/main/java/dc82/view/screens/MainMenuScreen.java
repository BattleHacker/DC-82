package dc82.view.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import dc82.controller.GameController;
import dc82.controller.MenuAction;
import dc82.view.ViewManager;

public class MainMenuScreen extends AbstractScreen {

    public MainMenuScreen(ViewManager viewManager, GameController controller, Skin skin) {
        super(viewManager, controller, skin);
    }

    @Override
    protected void buildUI() {
        stage.clear();
        var table = new Table();
        table.setFillParent(true);

        boolean hasSaves = controller.getSaveManager().hasSaves();

        if (hasSaves) {
            table.add(btn("CONTINUE...", MenuAction.CONTINUE)).pad(4).row();
        }
        table.add(btn("NEW GAME", MenuAction.NEW_GAME)).pad(4).row();
        if (hasSaves) {
            table.add(btn("LOAD GAME", MenuAction.LOAD)).pad(4).row();
        }
        table.add(btn("MODS", MenuAction.MODS)).pad(4).row();
        table.add(btn("SETTINGS", MenuAction.SETTINGS)).pad(4).row();
        table.add(btn("EXIT", MenuAction.QUIT)).pad(4).row();

        stage.addActor(table);
    }

}
