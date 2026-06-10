package dc82.view.screens;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dc82.controller.GameController;
import dc82.controller.MenuAction;
import dc82.model.SaveSlot;
import dc82.util.SaveManager;
import dc82.view.ViewManager;
import dc82.view.components.PixelButton;

import java.util.List;

public class LoadGameScreen extends AbstractScreen {

    public LoadGameScreen(ViewManager viewManager, GameController controller) {
        super(viewManager, controller);
    }

    @Override
    protected void buildUI() {
        stage.clear();
        var contentTable = new Table();
        var scrollPane = new ScrollPane(contentTable, skin);
        scrollPane.setFillParent(true);
        scrollPane.setForceScroll(false, true);
        stage.addActor(scrollPane);

        contentTable.add(new Label("LOAD GAME", skin)).pad(10).colspan(2).row();

        SaveManager sm = controller.getSaveManager();
        List<SaveSlot> slots = sm.getSaveSlots();

        if (slots.isEmpty()) {
            contentTable.add(new Label("--- no saves yet ---", skin)).pad(20).colspan(2).row();
        } else {
            for (SaveSlot slot : slots) {
                addSlotRow(contentTable, slot);
            }
        }

        var backBtn = new PixelButton("BACK", skin);
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.onMenuAction(MenuAction.BACK);
            }
        });
        contentTable.add(backBtn).pad(4).colspan(2);
    }

    private void addSlotRow(Table contentTable, SaveSlot slot) {
        var nameLabel = new Label(slot.displayName, skin);
        var dateLabel = new Label(formatDate(slot.updatedAt), skin);
        dateLabel.setFontScale(0.7f);

        var left = new Table();
        left.add(nameLabel).left().pad(4, 8, 1, 8).row();
        left.add(dateLabel).left().pad(1, 8, 4, 8);

        var delBtn = createDeleteButton(slot);

        contentTable.add(left).fillX().padTop(4).padBottom(4).padRight(0);
        contentTable.add(delBtn).size(36).padTop(4).padBottom(4).padLeft(0);
        contentTable.row();
        contentTable.add().colspan(2).height(1).fillX()
            .padLeft(8).padRight(8).row();
    }

    private static TextureRegionDrawable deleteDrawable;

    private static TextureRegionDrawable getDeleteDrawable(Skin skin) {
        if (deleteDrawable == null) {
            Pixmap pix = new Pixmap(4, 4, Pixmap.Format.RGBA8888);
            pix.setColor(0.55f, 0.08f, 0.08f, 1);
            pix.fill();
            pix.setColor(0.75f, 0.15f, 0.15f, 1);
            pix.drawRectangle(0, 0, 4, 4);
            Texture tex = new Texture(pix);
            pix.dispose();
            tex.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
            deleteDrawable = new TextureRegionDrawable(new TextureRegion(tex));
        }
        return deleteDrawable;
    }

    private Button createDeleteButton(SaveSlot slot) {
        TextureRegionDrawable drawable = getDeleteDrawable(skin);

        TextButton.TextButtonStyle delStyle = new TextButton.TextButtonStyle(
            drawable, drawable, drawable,
            skin.getFont("default-font")
        );

        var delBtn = new TextButton("X", delStyle);
        delBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showDeleteDialog(slot);
            }
        });
        return delBtn;
    }

    private void showDeleteDialog(SaveSlot slot) {
        Dialog dialog = new Dialog("", skin) {
            @Override
            protected void result(Object object) {
                if ((Boolean) object) {
                    controller.getSaveManager().deleteSlot(slot.id);
                    buildUI();
                }
            }
        };
        dialog.text("Delete " + slot.displayName + "?");
        dialog.button("YES", true);
        dialog.button("NO", false);
        dialog.show(stage);
        dialog.setPosition(
            Math.round((stage.getWidth() - dialog.getWidth()) / 2f),
            Math.round((stage.getHeight() - dialog.getHeight()) / 2f)
        );
    }

    @Override
    public void show() {
        buildUI();
        super.show();
    }

    private static String formatDate(long millis) {
        var fmt = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
        return fmt.format(new java.util.Date(millis));
    }
}
