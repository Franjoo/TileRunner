package com.tilerunner.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;

/**
 * Created with IntelliJ IDEA.
 * User: Franjo
 * Date: 23.02.14
 * Time: 19:22
 */
public class KeyboardInput implements IGameInput {

    public final int LEFT;
    public final int RIGHT;
    public final int UP;
    public final int DOWN;
    public final int A;
    public final int B;
    public final int X;


    public KeyboardInput() {
        LEFT = Keys.LEFT;
        RIGHT = Keys.RIGHT;
        UP = Keys.UP;
        DOWN = Keys.DOWN;

        A = Keys.A;
        B = Keys.B;
        X = Keys.X;
    }

    public KeyboardInput(int LEFT, int RIGHT, int UP, int DOWN, int A, int B, int X) {
        this.LEFT = LEFT;
        this.RIGHT = RIGHT;
        this.UP = UP;
        this.DOWN = DOWN;

        this.A = A;
        this.B = B;
        this.X = X;
    }


    @Override
    public float stickX() {
        if (Gdx.input.isKeyPressed(LEFT) && Gdx.input.isKeyPressed(RIGHT)) return 0;
        else if (Gdx.input.isKeyPressed(LEFT)) return -1;
        else if (Gdx.input.isKeyPressed(RIGHT)) return 1;
        return 0;
    }

    @Override
    public float stickY() {
        if (Gdx.input.isKeyPressed(UP) && Gdx.input.isKeyPressed(DOWN)) return 0;
        else if (Gdx.input.isKeyPressed(UP)) return -1;
        else if (Gdx.input.isKeyPressed(DOWN)) return 1;
        return 0;
    }

    @Override
    public boolean isA() {
        return Gdx.input.isKeyPressed(A);
    }

    @Override
    public boolean isB() {
        return Gdx.input.isKeyPressed(B);
    }

    @Override
    public boolean isX() {
        return Gdx.input.isKeyPressed(X);
    }

    @Override
    public void poll() {
    }
}
