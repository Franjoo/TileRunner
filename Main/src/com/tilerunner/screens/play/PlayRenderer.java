package com.tilerunner.screens.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;

/**
 * class which is responsible for rendering the PlayScreen
 */
public class PlayRenderer {
    private static final String TAG = PlayRenderer.class.getSimpleName();

    private boolean singleplayer;

    private PlayController playController;
    private SpriteBatch batch;

    private Texture border_left;
    private Texture border_right;
    private Texture tSplit;

    private Color _color;

    private Vector3 vector3 = new Vector3();

    /**
     * creates an new PlayRenderer
     *
     * @param playController PlayController that is used
     */
    public PlayRenderer(PlayController playController) {
        this.playController = playController;

        singleplayer = PlayScreen.PLAYMODE == PlayScreen.SINGLEPLAYER;

        batch = PlayScreen.getInstance().getBatch();
        batch.setProjectionMatrix(playController.getCameraManager().getCamera_shared().combined);

        drawBorderTextures();

        _color = new Color();
    }

    private void drawBorderTextures() {

        float w = 600;
        float h = 200;

        Pixmap p = new Pixmap((int) (w), (int) (h), Pixmap.Format.RGBA8888);
        p.setColor(1, 1, 1, 1);
        p.fillRectangle(0, 0, (int) w, (int) h);
//        getPosition.drawRectangle(getPosition.getWidth(), 0, getPosition.getWidth(), getPosition.getHeight());

        tSplit = new Texture((int) w, (int) h, Pixmap.Format.RGBA8888);
        tSplit.draw(p, 0, 0);
//        tSplit.draw(getPosition, Gdx.graphics.getWidth(), 0);
    }

    /**
     * renders the objects of the playScreen
     *
     * @param deltaTime
     */
    public void render(float deltaTime) {

        batch.setColor(1, 1, 1, 1);


        int vw = 1280;
        int vh = 600;

        // traps
        playController.getWorld().renderTraps(batch);


//        batch = PlayScreen.getInstance().getBatch();
//        Gdx.gl.glViewport(0, 0, vw, vh);
//        batch.setProjectionMatrix(playController.getCameraManager().getCamera_shared().combined);


        // backgrounds
        batch.setColor(0.3f, 0.3f, 0.3f, batch.getColor().a);
        playController.getWorld().renderGhostLayer(batch);


//        batch.setColor(1,1,1,1);
        batch.setColor(0.3f, 0.3f, 0.3f, batch.getColor().a);
        playController.getWorld().renderBackground();


        batch.setColor(1, 1, 1, 1);

        // gameLayer
        playController.getWorld().renderGameLayer(batch);

        // shader layer
//        playController.getWorld().renderShaderLayers(batch);

        // enemies
        for (int i = 0; i < playController.getWorld().getEnemies().size; i++) {
            playController.getWorld().getEnemies().get(i).render(batch);
        }

        // players
        playController.getPlayer(1).render(batch);
        if (!singleplayer) playController.getPlayer(2).render(batch);


        // pool objects
        playController.getWorld().renderPoolObjects(batch);

        // decorations
        playController.getWorld().renderDecorations(batch);

        // coins
        playController.getWorld().getCoins().render(batch);


//        playController.getPlayUI().render();

//        batch.flush();
//
        // foreground
//        _color.set(batch.getColor());
//        batch.begin();
//        batch.setColor(0.2f,0.2f,0.2f,1);
//        playController.getWorld().renderForeground();
//        batch.flush();
//        batch.end();

    }

    /**
     * resizes the viewport of the camera which is used.
     *
     * @param width  new width of the viewport in pixels
     * @param height new height of the viewport in pixels
     */
    public void resize(int width, int height) {
//        playController.getCameraManager().getCamera_shared().viewportWidth = (C.VIEWPORT_HEIGHT / height) * width;
//        playController.getCameraManager().getCamera_shared().update();
    }
}
