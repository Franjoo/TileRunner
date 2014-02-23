package com.tilerunner.gameobjects.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilerunner.input.IGameInput;

/**
 * Created with IntelliJ IDEA.
 * User: Franjo
 * Date: 19.02.14
 * Time: 13:37
 */
public class Runner implements IPlayable {

    // position
    private float x;
    private float y;

    // velocity
    private float vx;
    private float vy;

    public Runner() {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getVx() {
        return vx;
    }

    @Override
    public float getVy() {
        return vy;
    }

    @Override
    public IGameInput getInputController() {
        return null;
    }
}
