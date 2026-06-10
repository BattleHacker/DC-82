package dc82.view.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import dc82.controller.GameController;
import dc82.controller.MenuAction;
import dc82.view.ViewManager;
import dc82.view.components.PixelButton;

public class SettingsScreen extends AbstractScreen {

    public SettingsScreen(ViewManager viewManager, GameController controller, Skin skin) {
        super(viewManager, controller, skin);
    }

    @Override
    protected void buildUI() {
        stage.clear();
        var table = new Table();
        table.setFillParent(true);

        table.add(new Label("SETTINGS", skin)).pad(10).row();
        table.add(new Label("--- coming soon ---", skin)).pad(20).row();

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
}
