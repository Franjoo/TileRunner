package com.tilerunner.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * User: Franjo
 * Date: 04.12.13
 * Time: 22:36
 * Project: TileRunner
 */
public class PlayUI {

    private SpriteBatch batch;
    private OrthographicCamera camera;

    private BitmapFont font;

    private TimeDisplay timeDisplay;

    public PlayUI() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(true);


        timeDisplay = new TimeDisplay();
    }


    public void update(float delta) {
          timeDisplay.update(delta);

    }


    public void render() {
        batch.setProjectionMatrix(camera.combined);
        camera.update();
//        // background color
//        Color c = Color.valueOf("000000");
//        Gdx.gl.glClearColor(c.r, c.g, c.b, c.a);
//        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        timeDisplay.render(batch);
    }
}
