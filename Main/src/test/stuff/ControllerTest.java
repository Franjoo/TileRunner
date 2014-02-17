package test.stuff;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.tilerunner.input.gamepads.X360Gamepad;

/**
 * User: Franjo
 * Date: 30.11.13
 * Time: 19:55
 * Project: TileRunner
 */
public class ControllerTest implements ApplicationListener {

    X360Gamepad gamepad;

    @Override
    public void create() {

        gamepad = new X360Gamepad(Controllers.getControllers().get(0));
    }

    @Override
    public void resize(int width, int height) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render() {
        gamepad.poll();

    }

    @Override
    public void pause() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resume() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void dispose() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
