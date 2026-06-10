package dc82.view.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dc82.model.Attribute;
import dc82.model.Character;

public class CharacterTile extends Table {

    private static final int ICON_SIZE = 24;

    public CharacterTile(Character character, Skin skin) {
        int hp = getAttrValue(character, "C_HP");
        int maxHp = getAttrValue(character, "C_maxHP");
        int mp = getAttrValue(character, "C_MP");
        int maxMp = getAttrValue(character, "C_maxMP");
        int dp = getAttrValue(character, "C_DP");

        top().pad(4);

        var nameLabel = new Label(character.getName(), skin);
        nameLabel.setFontScale(0.9f);
        add(nameLabel).colspan(3).center().padBottom(4).row();

        var hpLabel = new Label("HP " + hp + "/" + maxHp, skin);
        hpLabel.setFontScale(0.7f);
        var mpLabel = new Label("MP " + mp + "/" + maxMp, skin);
        mpLabel.setFontScale(0.7f);

        var statsTable = new Table();
        statsTable.add(hpLabel).left().row();
        statsTable.add(mpLabel).left();

        add(statsTable).left().padRight(4);

        Container<Table> iconBox = new Container<>();
        iconBox.setBackground(createPlaceholderBg());
        iconBox.size(ICON_SIZE);
        add(iconBox).padRight(4);

        var dpLabel = new Label("DP " + dp, skin);
        dpLabel.setFontScale(0.7f);
        add(dpLabel).right().expandX();
    }

    private static int getAttrValue(Character character, String name) {
        Attribute attr = character.getAttribute(name);
        return attr != null ? attr.getValue() : 0;
    }

    private static TextureRegionDrawable createPlaceholderBg() {
        Pixmap pix = new Pixmap(ICON_SIZE, ICON_SIZE, Pixmap.Format.RGBA8888);
        pix.setColor(0.2f, 0.2f, 0.3f, 1);
        pix.fill();
        pix.setColor(0.4f, 0.4f, 0.5f, 1);
        pix.drawRectangle(0, 0, ICON_SIZE, ICON_SIZE);
        Texture tex = new Texture(pix);
        pix.dispose();
        tex.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        return new TextureRegionDrawable(new TextureRegion(tex));
    }
}
