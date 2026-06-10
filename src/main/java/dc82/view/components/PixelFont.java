package dc82.view.components;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class PixelFont {

    private static BitmapFont font;

    public static BitmapFont get() {
        if (font == null) {
            font = new BitmapFont();
            font.setUseIntegerPositions(true);
        }
        return font;
    }

    public static void dispose() {
        if (font != null) {
            font.dispose();
            font = null;
        }
    }
}
