package dc82.view.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import dc82.controller.GameController;
import dc82.controller.MenuAction;
import dc82.view.ViewManager;
import dc82.view.components.PixelButton;

public class MainMenuScreen extends AbstractScreen {

    public MainMenuScreen(ViewManager viewManager, GameController controller) {
        super(viewManager, controller);
    }

    @Override
    protected void buildUI() {
        var table = new Table();
        table.setFillParent(true);

        table.add(btn("CONTINUE / NEW", MenuAction.CONTINUE)).pad(4).row();
        table.add(btn("LOAD GAME", MenuAction.LOAD)).pad(4).row();
        table.add(btn("MODS", MenuAction.MODS)).pad(4).row();
        table.add(btn("SETTINGS", MenuAction.SETTINGS)).pad(4).row();

        stage.addActor(table);
    }

    private PixelButton btn(String text, MenuAction action) {
        var b = new PixelButton(text, skin);
        b.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.onMenuAction(action);
            }
        });
        return b;
    }
}
