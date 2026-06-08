package dc82.view.components;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class PixelButton extends TextButton {

    public PixelButton(String text, Skin skin) {
        super(text, skin);
        pad(5, 12, 5, 12);
    }
}
