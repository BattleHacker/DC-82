package dc82.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import dc82.controller.GameController;
import dc82.controller.MenuAction;
import dc82.model.HexCoord;
import dc82.model.Milestone;
import dc82.model.Path;
import dc82.view.ViewManager;
import dc82.view.components.PixelButton;
import dc82.view.components.PixelFont;

import java.util.Collection;

public class MapScreen extends AbstractScreen {

    private static final float HEX_SIZE = 28f;
    private static final float MIN_ZOOM = 1f;
    private static final float MAX_ZOOM = 5f;
    private static final float LINE_WIDTH_SCREEN_PX = 5f;
    private static final float RADIUS_SCREEN_PX = 12f;

    private static final Color GREEN_CURRENT = new Color(0.2f, 0.8f, 0.2f, 1);
    private static final Color YELLOW_UNVISITED = new Color(0.9f, 0.8f, 0.1f, 1);
    private static final Color GRAY_VISITED = new Color(0.4f, 0.4f, 0.4f, 1);
    private static final Color BROWN_PATH = new Color(0.5f, 0.3f, 0.1f, 1);
    private static final Color GRAY_PATH = new Color(0.3f, 0.3f, 0.3f, 1);

    private final OrthographicCamera mapCamera;
    private final ShapeRenderer shapeRenderer;
    private final InputAdapter dragProcessor;
    private float zoom = 1f;
    private boolean isDragging;
    private float lastTouchX, lastTouchY;
    private PixelButton zoomInBtn, zoomOutBtn;
    private final GlyphLayout nameLayout = new GlyphLayout();

    public MapScreen(ViewManager viewManager, GameController controller, Skin skin) {
        super(viewManager, controller, skin);
        mapCamera = new OrthographicCamera(VW, VH);
        shapeRenderer = new ShapeRenderer();
        dragProcessor = createDragProcessor();
    }

    private static boolean isDescendantOfPixelButton(Actor actor) {
        while (actor != null) {
            if (actor instanceof PixelButton) return true;
            actor = actor.getParent();
        }
        return false;
    }

    private InputAdapter createDragProcessor() {
        return new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector2 stageCoords = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
                Actor hit = stage.hit(stageCoords.x, stageCoords.y, true);
                if (hit == null || !isDescendantOfPixelButton(hit)) {
                    lastTouchX = screenX;
                    lastTouchY = screenY;
                    isDragging = true;
                    return true;
                }
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (isDragging) {
                    float dx = screenX - lastTouchX;
                    float dy = screenY - lastTouchY;
                    float pixelScale = VW / (float) Gdx.graphics.getWidth();
                    float worldScale = mapCamera.zoom * pixelScale;
                    mapCamera.position.x -= dx * worldScale;
                    mapCamera.position.y += dy * worldScale;
                    applyBounds();
                    lastTouchX = screenX;
                    lastTouchY = screenY;
                    return true;
                }
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                isDragging = false;
                return false;
            }
        };
    }

    @Override
    protected void buildUI() {
        var root = new Table();
        root.setFillParent(true);

        root.add(btn("BACK", MenuAction.BACK_TO_ENCOUNTER)).left().pad(4).padLeft(8).row();

        root.add().expand().row();

        var zoomTable = new Table();
        zoomInBtn = new PixelButton("+", skin);
        zoomInBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                zoom = Math.max(MIN_ZOOM, zoom / 1.5f);
                mapCamera.zoom = zoom;
                updateZoomButtons();
            }
        });

        zoomOutBtn = new PixelButton("-", skin);
        zoomOutBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                zoom = Math.min(MAX_ZOOM, zoom * 1.5f);
                mapCamera.zoom = zoom;
                updateZoomButtons();
            }
        });

        zoomTable.add(zoomInBtn).pad(2).row();
        zoomTable.add(zoomOutBtn).pad(2);

        root.add(zoomTable).left().pad(4).padLeft(8);

        stage.addActor(root);
    }

    private void updateZoomButtons() {
        zoomInBtn.setDisabled(zoom <= MIN_ZOOM + 0.01f);
        zoomOutBtn.setDisabled(zoom >= MAX_ZOOM - 0.01f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapCamera.update();
        shapeRenderer.setProjectionMatrix(mapCamera.combined);

        var mc = controller.getMapController();
        if (mc.hasMapState()) {
            Collection<Milestone> milestones = mc.getAllMilestones();
            Collection<Path> paths = mc.getAllPaths();
            HexCoord currentHex = mc.getCurrentHex();

            drawPaths(paths, currentHex);
            drawMilestones(milestones);
            drawNames(milestones);
        }

        stage.act(delta);
        stage.draw();
    }

    private void drawPaths(Collection<Path> allPaths, HexCoord currentHex) {
        if (allPaths.isEmpty()) return;

        float pixelScale = VW / (float) Gdx.graphics.getWidth();
        float lineWidth = LINE_WIDTH_SCREEN_PX * pixelScale * mapCamera.zoom;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Path p : allPaths) {
            boolean fromCurrent = currentHex != null && p.connects(currentHex);
            shapeRenderer.setColor(fromCurrent ? BROWN_PATH : GRAY_PATH);

            float x1 = p.a.pixelX() * HEX_SIZE;
            float y1 = p.a.pixelY() * HEX_SIZE;
            float x2 = p.b.pixelX() * HEX_SIZE;
            float y2 = p.b.pixelY() * HEX_SIZE;

            shapeRenderer.rectLine(x1, y1, x2, y2, lineWidth);
        }
        shapeRenderer.end();
    }

    private void drawMilestones(Collection<Milestone> milestones) {
        if (milestones.isEmpty()) return;

        float pixelScale = VW / (float) Gdx.graphics.getWidth();
        float radius = RADIUS_SCREEN_PX * pixelScale * mapCamera.zoom;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Milestone m : milestones) {
            if (m.isCurrent) {
                shapeRenderer.setColor(GREEN_CURRENT);
            } else if (!m.visited) {
                shapeRenderer.setColor(YELLOW_UNVISITED);
            } else {
                shapeRenderer.setColor(GRAY_VISITED);
            }

            float x = m.hex.pixelX() * HEX_SIZE;
            float y = m.hex.pixelY() * HEX_SIZE;
            shapeRenderer.circle(x, y, radius);
        }
        shapeRenderer.end();
    }

    private void drawNames(Collection<Milestone> milestones) {
        if (mapCamera.zoom >= 2f) return;

        if (milestones.isEmpty()) return;

        BitmapFont font = PixelFont.get();
        float pixelScale = VW / (float) Gdx.graphics.getWidth();
        float scale = 0.8f * pixelScale * mapCamera.zoom;

        float origScaleX = font.getData().scaleX;
        float origScaleY = font.getData().scaleY;
        font.getData().setScale(scale);

        stage.getBatch().begin();
        stage.getBatch().setProjectionMatrix(mapCamera.combined);

        for (Milestone m : milestones) {
            float x = m.hex.pixelX() * HEX_SIZE;
            float y = m.hex.pixelY() * HEX_SIZE;

            nameLayout.setText(font, m.displayName);
            float textX = x - nameLayout.width / 2f;
            float textY = y + RADIUS_SCREEN_PX * pixelScale * mapCamera.zoom + 2f;

            font.draw(stage.getBatch(), m.displayName, textX, textY);
        }

        stage.getBatch().end();

        font.getData().setScale(origScaleX, origScaleY);
    }

    @Override
    public void show() {
        zoom = 1f;
        mapCamera.zoom = zoom;
        centerOnCurrent();
        mapCamera.update();

        super.show();

        updateZoomButtons();
        InputMultiplexer mux = new InputMultiplexer();
        mux.addProcessor(dragProcessor);
        mux.addProcessor(stage);
        Gdx.input.setInputProcessor(mux);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    private void centerOnCurrent() {
        var mc = controller.getMapController();
        HexCoord current = mc.getCurrentHex();
        if (current != null) {
            mapCamera.position.set(
                current.pixelX() * HEX_SIZE,
                current.pixelY() * HEX_SIZE,
                0
            );
        }
        applyBounds();
    }

    private void applyBounds() {
        var mc = controller.getMapController();
        if (!mc.hasMapState()) return;
        var milestones = mc.getAllMilestones();
        if (milestones.isEmpty()) return;

        float leftmost = Float.MAX_VALUE;
        float rightmost = -Float.MAX_VALUE;
        float bottommost = Float.MAX_VALUE;
        float topmost = -Float.MAX_VALUE;

        for (Milestone m : milestones) {
            float x = m.hex.pixelX() * HEX_SIZE;
            float y = m.hex.pixelY() * HEX_SIZE;
            if (x < leftmost) leftmost = x;
            if (x > rightmost) rightmost = x;
            if (y < bottommost) bottommost = y;
            if (y > topmost) topmost = y;
        }

        mapCamera.position.x = Math.max(leftmost, Math.min(rightmost, mapCamera.position.x));
        mapCamera.position.y = Math.max(bottommost, Math.min(topmost, mapCamera.position.y));
    }

    @Override
    public void dispose() {
        super.dispose();
        shapeRenderer.dispose();
    }

}
