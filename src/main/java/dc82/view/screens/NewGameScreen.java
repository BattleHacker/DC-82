package dc82.view.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import dc82.controller.GameController;
import dc82.controller.MenuAction;
import dc82.view.ViewManager;

public class NewGameScreen extends AbstractScreen {

    public NewGameScreen(ViewManager viewManager, GameController controller, Skin skin) {
        super(viewManager, controller, skin);
    }

    @Override
    protected void buildUI() {
        stage.clear();
        var table = new Table();
        table.setFillParent(true);

        table.add(new Label("NEW GAME", skin)).pad(10).row();
        table.add(new Label("Start a new adventure?", skin)).pad(10).row();

        table.add(btn("START", MenuAction.START_GAME)).pad(8).row();
        table.add(btn("BACK", MenuAction.BACK)).pad(4);

        stage.addActor(table);
    }
}
