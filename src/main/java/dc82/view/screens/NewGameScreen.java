package dc82.view.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import dc82.controller.GameController;
import dc82.controller.MenuAction;
import dc82.view.ViewManager;
import dc82.view.components.PixelButton;

public class NewGameScreen extends AbstractScreen {

    public NewGameScreen(ViewManager viewManager, GameController controller) {
        super(viewManager, controller);
    }

    @Override
    protected void buildUI() {
        stage.clear();
        var table = new Table();
        table.setFillParent(true);

        table.add(new Label("NEW GAME", skin)).pad(10).row();
        table.add(new Label("Start a new adventure?", skin)).pad(10).row();

        table.add(btn("START", MenuAction.START_GAME)).pad(8).row();

        var backBtn = new PixelButton("BACK", skin);
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.onMenuAction(MenuAction.BACK);
            }
        });
        table.add(backBtn).pad(4);

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
