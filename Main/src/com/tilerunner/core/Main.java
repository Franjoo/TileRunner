package com.tilerunner.core;

/**
 * User: Franjo
 * Date: 11.11.13
 * Time: 13:04
 * Project: TileRunner
 */

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;


/**
 * Main class which provides the render() method to realize the game loop.
 * Through the implementation of the ApplicationListener Interface, this class
 * follows the standard Android activity life-cycle.
 */
public class Main implements ApplicationListener {
    private static final String TAG = Main.class.getSimpleName();

    // debug bools
    private static final boolean FPS_LOGGING = true;

    // statsdisplay
    private StatsDisplay stats;
    private GameController gameController;
    private FPSLogger fpsLogger;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        gameController = new GameController();
        fpsLogger = new FPSLogger();
        stats = new StatsDisplay();
    }

    @Override
    public void dispose() {
        gameController.dispose();
    }

    @Override
    public void render() {

        // background color
        Color c = Color.valueOf("000000");
        Gdx.gl.glClearColor(c.r, c.g, c.b, c.a);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        // update
        gameController.update(Gdx.graphics.getDeltaTime());

        // stats display
//        stats.render();

        // log fps
        if (FPS_LOGGING) fpsLogger.log();
    }

    @Override
    public void resize(int width, int height) {
        gameController.resize(width, height);
    }

    @Override
    public void pause() {
        gameController.pause();
    }

    @Override
    public void resume() {
        gameController.resume();
    }

}
