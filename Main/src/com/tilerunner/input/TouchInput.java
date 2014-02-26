package com.tilerunner.input;

import com.badlogic.gdx.Gdx;

/**
 * Created with IntelliJ IDEA.
 * User: Franjo
 * Date: 25.02.14
 * Time: 17:34
 */
public class TouchInput implements IGameInput {

    @Override
    public float stickX() {
        if (Gdx.input.getX() > 0 && Gdx.input.getX() < 130) return -1;
        if (Gdx.input.getX() > 130 && Gdx.input.getX() < 260) return 1;
        return 0;
    }

    @Override
    public float stickY() {
        return 0;
    }

    @Override
    public boolean isA() {
        return Gdx.input.getX() > 500;
    }

    @Override
    public boolean isB() {
        return false;
    }

    @Override
    public boolean isX() {
        return false;
    }

    @Override
    public void poll() {
    }
}
