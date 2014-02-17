package test.particlesystem;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * User: Franjo
 * Date: 10.12.13
 * Time: 20:02
 * Project: TileRunner
 */
public class TestEnvironment implements ApplicationListener {

    SpriteBatch batch;
    OrthographicCamera camera;
    InputAdapter input;

    Explosion explosion;

    FPSLogger logger;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(true);

        explosion = new Explosion();

        logger = new FPSLogger();

        input = new InputAdapter() {
//            @Override
//            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//                explosion.explode(screenX, screenY);
//                return true;
//            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                explosion.explode(screenX, screenY);
                return true;
            }
        };

        Gdx.input.setInputProcessor(input);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        camera.update();

        explosion.update(Gdx.graphics.getDeltaTime());
        explosion.render(batch);


        logger.log();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
