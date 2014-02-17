package test.stuff;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * User: Franjo
 * Date: 02.12.13
 * Time: 09:04
 * Project: TileRunner
 */
public class LightingTest implements ApplicationListener {

    static int VIEWPORT_WIDTH = 800;
    static int VIEWPORT_HEIGHT = 480;

    Array<Vector2> points;
    SpriteBatch batch;
    OrthographicCamera camera;
    ShaderProgram program;


    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);


    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
