package com.tilerunner.screens.play;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilerunner.screens.AbstractScreen;

/**
 * class that represents the screen which is used for game play
 */
public class PlayScreen extends AbstractScreen {
    private static final String TAG = PlayScreen.class.getSimpleName();

    // instance
    private static PlayScreen instance = null;

    // play state constants
    public static final String SINGLEPLAYER = "singleplayer";
    public static final String MULTIPLAYER = "multiplayer";
//    public static final String PLAYMODE = SINGLEPLAYER;
    public static final String PLAYMODE = MULTIPLAYER;

    private PlayController playController;
    private PlayRenderer playRenderer;

    private SpriteBatch batch;

    /**
     * creates a new PlayScreen.
     * PlayScreen provides static access to the SpriteBatch which
     * is used for rendering
     */
    public PlayScreen() {

        batch = new SpriteBatch();
        instance = this;

        init();
    }

    /**
     * initializes PlayScreen
     */
    private void init() {
        playController = new PlayController();
        playRenderer = new PlayRenderer(playController);
    }

    /**
     * returns the PlayController which is used in the PlayScreen
     */
    public PlayController getPlayController() {
        return playController;
    }

    /**
     * returns the PlayRenderer which is used in the PlayScreen
     */
    public PlayRenderer getPlayRenderer() {
        return playRenderer;
    }


    public SpriteBatch getBatch() {
        return batch;
    }


    @Override
    public void update(float deltaTime) {
        playController.update(deltaTime);
    }

    @Override
    public void render(float deltaTime) {
        playRenderer.render(deltaTime);
    }

    @Override
    public void resize(int width, int height) {
        playRenderer.resize(width, height);
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    public static PlayScreen getInstance() {
        return instance;
    }

}
