package test.splatter;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/**
 * User: Franjo
 * Date: 28.11.13
 * Time: 21:12
 * Project: TileRunner
 */
public class SplatterTest implements ApplicationListener {

    FPSLogger logger;


    private SpriteBatch batch;
    private OrthographicCamera camera;

    private TextureRegion tileRegion;

    private Array<Chunk> activeChunks;
    private Pool<Chunk> chunkPool;

    private Vector2 direction;
    private Vector2 _helperVector;

    private int cpsMAX;


    @Override
    public void create() {
        logger =new FPSLogger();

        batch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth() * 10, Gdx.graphics.getHeight() * 10);

        direction = new Vector2(1, 0);
        _helperVector = new Vector2();

        tileRegion = new TextureRegion(new Texture(Gdx.files.internal("test/tile.png")));

        activeChunks = new Array<>();

        chunkPool = new Pool<Chunk>() {
            @Override
            protected Chunk newObject() {
                return new Chunk();
            }
        };


    }


    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        // background color
        Color c = Color.valueOf("FFFFFF");
        Gdx.gl.glClearColor(c.r, c.g, c.b, c.a);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        camera.update();

        updatePool();
        updateDirection();

        spawnChunkk();


        batch.begin();


        batch.draw(tileRegion, 0, 0);


        int len = activeChunks.size;

        for (int i = len; --i >= 0; ) {

            Chunk chunk = activeChunks.get(i);
            chunk.update(Gdx.graphics.getDeltaTime());
//
            if (chunk.y < -1000) {
                chunk.active = false;
            }

            if (!chunk.active) {
                activeChunks.removeIndex(i);
                chunkPool.free(chunk);
            }else{
//                System.out.println("draw");
                chunk.draw(batch);
            }


        }
        batch.end();


        renderChunks(batch);


//        System.out.println("num chunks: " + activeChunks.size);


        logger.log();
    }

    private void updatePool() {
//        if (!b.alive) {
//            activeBullets.removeIndex(i);
//            bulletPool.free(b);
//        }

    }

    private void updateDirection() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            direction.setAngle(direction.angle() - 1f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            direction.setAngle(direction.angle() + 1f);
        }

//        System.out.println(direction);

    }

    public void renderChunks(final SpriteBatch batch) {
        batch.begin();
        for (int i = 0; i < activeChunks.size; i++) {
            activeChunks.get(i).draw(batch);
        }
        batch.end();

    }

    private void spawnChunkk() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {

            float x = 0;
            float y = 0;
            float vx = (float) (direction.x * (40 + Math.random() * 10));
            float vy = (float) (direction.y * (40 + Math.random() * 10));

            Chunk c = chunkPool.obtain();
            c.init(x, y, vx, vy, tileRegion);
            activeChunks.add(c);
        }

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


