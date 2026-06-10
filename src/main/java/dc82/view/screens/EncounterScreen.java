package dc82.view.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import dc82.controller.GameController;
import dc82.controller.MenuAction;
import dc82.model.Milestone;
import dc82.model.Path;
import dc82.view.ViewManager;
import dc82.view.components.PixelButton;

public class EncounterScreen extends AbstractScreen {

    public EncounterScreen(ViewManager viewManager, GameController controller) {
        super(viewManager, controller);
    }

    @Override
    protected void buildUI() {
        stage.clear();
        var root = new Table();
        root.setFillParent(true);

        root.add(btn("MAP", MenuAction.MAP)).left().pad(4).padLeft(8).row();

        var body = new Table();
        body.add(placeholderPanel("LEFT", "--- left placeholder ---")).expand().fill().pad(4);
        body.add(placeholderPanel("CENTER", "--- center placeholder ---")).expand().fill().pad(4);
        body.add(travelPanel()).expand().fill().pad(4);

        root.add(body).expand().fill().row();

        stage.addActor(root);
    }

    private Table placeholderPanel(String title, String text) {
        var t = new Table();
        t.add(new Label(title, skin)).pad(4).row();
        t.add(new Label(text, skin)).pad(4);
        return t;
    }

    private Table travelPanel() {
        var t = new Table();
        t.add(new Label("travel to", skin)).pad(4).row();

        var mc = controller.getMapController();
        Milestone current = mc.getCurrentMilestone();
        if (current != null) {
            for (Path p : current.paths) {
                var targetHex = p.other(current.hex);
                Milestone target = mc.getMilestoneAt(targetHex);
                String name = target != null ? target.displayName : "???";
                var btn = new PixelButton(name, skin);
                btn.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        mc.travelTo(targetHex);
                        buildUI();
                    }
                });
                t.add(btn).fillX().pad(2).row();
            }
        }

        return t;
    }

    @Override
    public void show() {
        buildUI();
        super.show();
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
