package dc82.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import dc82.controller.MenuAction;
import dc82.view.components.PixelButton;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dc82.controller.GameController;
import dc82.view.ViewManager;
import dc82.view.components.PixelFont;

public abstract class AbstractScreen implements Screen {

    protected final ViewManager viewManager;
    protected final GameController controller;
    protected final Skin skin;
    protected final Stage stage;
    protected final Viewport viewport;

    protected static final int VW = 640;
    protected static final int VH = 480;

    public AbstractScreen(ViewManager viewManager, GameController controller, Skin skin) {
        this.viewManager = viewManager;
        this.controller = controller;
        this.skin = skin;
        this.viewport = new FitViewport(VW, VH, new OrthographicCamera());
        this.stage = new Stage(viewport);
    }

    public static Skin createSkin() {
        BitmapFont font = PixelFont.get();
        Skin skin = new Skin();
        skin.add("default-font", font, BitmapFont.class);

        var upPix = makeButtonPixmap(0.15f, 0.15f, 0.15f, 0.4f, 0.4f, 0.4f);
        var overPix = makeButtonPixmap(0.25f, 0.25f, 0.25f, 0.6f, 0.6f, 0.6f);
        var downPix = makeButtonPixmap(0.08f, 0.08f, 0.08f, 0.3f, 0.3f, 0.3f);

        var upTex = new Texture(upPix);
        var overTex = new Texture(overPix);
        var downTex = new Texture(downPix);

        upPix.dispose(); overPix.dispose(); downPix.dispose();

        upTex.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        overTex.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        downTex.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        skin.add("tex-up", upTex);
        skin.add("tex-over", overTex);
        skin.add("tex-down", downTex);

        var upDraw = new TextureRegionDrawable(new TextureRegion(upTex));
        var overDraw = new TextureRegionDrawable(new TextureRegion(overTex));
        var downDraw = new TextureRegionDrawable(new TextureRegion(downTex));

        var btnStyle = new TextButtonStyle(upDraw, downDraw, overDraw, font);
        skin.add("default", btnStyle, TextButtonStyle.class);

        var labelStyle = new LabelStyle(font, Color.WHITE);
        skin.add("default", labelStyle, LabelStyle.class);

        var scrollPaneStyle = new ScrollPaneStyle();
        skin.add("default", scrollPaneStyle, ScrollPaneStyle.class);

        var windowStyle = new WindowStyle(font, Color.WHITE, null);
        skin.add("default", windowStyle, WindowStyle.class);

        Pixmap delPix = new Pixmap(4, 4, Pixmap.Format.RGBA8888);
        delPix.setColor(0.55f, 0.08f, 0.08f, 1);
        delPix.fill();
        delPix.setColor(0.75f, 0.15f, 0.15f, 1);
        delPix.drawRectangle(0, 0, 4, 4);
        Texture delTex = new Texture(delPix);
        delPix.dispose();
        delTex.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        skin.add("delete-btn-bg", new TextureRegionDrawable(new TextureRegion(delTex)));

        return skin;
    }

    public static Pixmap makeButtonPixmap(float r, float g, float b,
                                          float br, float bg, float bb) {
        Pixmap p = new Pixmap(4, 4, Pixmap.Format.RGBA8888);
        p.setColor(r, g, b, 1);
        p.fill();
        p.setColor(br, bg, bb, 1);
        p.drawRectangle(0, 0, 4, 4);
        return p;
    }

    protected abstract void buildUI();

    protected PixelButton btn(String text, MenuAction action) {
        var b = new PixelButton(text, skin);
        b.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.onMenuAction(action);
            }
        });
        return b;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void show() {
        buildUI();
        Gdx.input.setInputProcessor(stage);
        animateFadeIn(0.25f);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    protected void animateFadeIn(float duration) {
        stage.getRoot().getColor().a = 0;
        stage.getRoot().addAction(Actions.fadeIn(duration));
    }

}
