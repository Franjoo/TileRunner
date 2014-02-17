package com.tilerunner.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.tilerunner.gameobjects.player.Player;

/**
 * User: Franjo
 * Date: 26.10.13
 * Time: 10:47
 * Project: Main
 */
public class KeyboardInput extends InputAdapter implements IGameInputController {

    private Player player;
    private OrthographicCamera camera;
    private Vector2 vec_stick_rightX;
    private Vector2 vec_stick_rightY;

    public KeyboardInput(Player player) {
        this.player = player;
//        this.camera = camera;

        vec_stick_rightX = new Vector2();
        vec_stick_rightY = new Vector2();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public float get_left_stickX() {
        if (Gdx.input.isKeyPressed(Keys.LEFT) && Gdx.input.isKeyPressed(Keys.RIGHT)) return 0;
        else if (Gdx.input.isKeyPressed(Keys.LEFT)) return -1;
        else if (Gdx.input.isKeyPressed(Keys.RIGHT)) return 1;
        return 0;
    }

    @Override
    public float get_left_stickY() {
        if (Gdx.input.isKeyPressed(Keys.UP) && Gdx.input.isKeyPressed(Keys.DOWN)) return 0;
        else if (Gdx.input.isKeyPressed(Keys.UP)) return 1;
        else if (Gdx.input.isKeyPressed(Keys.DOWN)) return -1;
        return 0;
    }

    @Override
    public float get_right_stickX() {

        float relMouseX = Gdx.input.getX() + player.getY();

//        System.out.println("Camera: " + camera.position.x + "  " + player.getX());

//        System.out.println(relMouseX + "   " + player.x);

        if (relMouseX - player.getX() < player.getX()) return -1;
        else if (relMouseX > player.getX()) return 1;
        return 0;
    }

    @Override
    public float get_right_stickY() {

        float relMouseY = Gdx.input.getY() + player.getY();

        if (relMouseY < player.getY()) return -1;
        else if (relMouseY > player.getY()) return 1;
        return 0;
    }

    @Override
    public boolean get_isA() {
        return Gdx.input.isKeyPressed(Keys.Y);
    }

    @Override
    public boolean get_isB() {
        return Gdx.input.isKeyPressed(Keys.X);

    }

    @Override
    public float get_trigger_left() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public float get_trigger_right() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean get_isY() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean get_isX() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean is_DPAD_UP() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean is_DPAD_DOWN() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean is_DPAD_LEFT() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean is_DPAD_RIGHT() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean is_L1_pressed() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean is_R1_pressed() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean is_BACK_pressed() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean is_START_pressed() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean is_L3_pressed() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean is_R3_pressed() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
