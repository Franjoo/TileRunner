package test.rope;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * User: Franjo
 * Date: 26.11.13
 * Time: 19:40
 * Project: TileRunner
 */
public class RopeTest implements ApplicationListener {

    private SpriteBatch batch;
    private OrthographicCamera camera;

    private Texture bounds;

    private Player player;


    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 1);
        camera.zoom = 1.05f;

        player = new Player();

        drawBounds();
    }

    private void drawBounds() {
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.drawRectangle(1, 1, Gdx.graphics.getWidth() - 2, Gdx.graphics.getHeight() - 2);
        pixmap.drawRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
        bounds = new Texture(pixmap);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {

        float delta = Gdx.graphics.getDeltaTime();

        // background color
        Color c = Color.valueOf("000000");
        Gdx.gl.glClearColor(c.r, c.g, c.b, c.a);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        camera.update();

        batch.begin();
        batch.draw(bounds, 0, 0);
        batch.end();

        player.update(delta);
        player.render(batch);

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


