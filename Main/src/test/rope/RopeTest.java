package test.rope;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created with IntelliJ IDEA.
 * User: Franjo
 * Date: 17.02.14
 * Time: 22:27
 */
public class RopeTest extends ApplicationAdapter {

    SpriteBatch batch;
    OrthographicCamera camera;

    // b2d
    World world;
    static final float WORLD_TO_BOX = 0.01f;
    static final float BOX_TO_WORLD = 100f;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(true);

        // b2d world
        world = new World(new Vector2(0, -10), true);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        camera.update();

    }
}
