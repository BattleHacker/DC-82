package dc82.view.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import dc82.controller.GameController;
import dc82.view.ScreenId;
import dc82.view.ViewManager;

public class SplashScreen extends AbstractScreen {

    private float elapsed;
    private boolean done;

    public SplashScreen(ViewManager viewManager, GameController controller) {
        super(viewManager, controller);
    }

    @Override
    protected void buildUI() {
        var table = new Table();
        table.setFillParent(true);
        var label = new Label("DC-82", skin);
        label.setFontScale(2f);
        table.add(label);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (done) return;
        elapsed += delta;
        if (elapsed >= 0.5f) {
            done = true;
            viewManager.showScreenWithTransition(ScreenId.MAIN_MENU);
        }
    }

    @Override
    public void show() {
        super.show();
        elapsed = 0;
        done = false;
    }
}
