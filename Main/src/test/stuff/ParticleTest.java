package test.stuff;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 * User: Franjo
 * Date: 19.11.13
 * Time: 14:42
 * Project: TileRunner
 */
public class ParticleTest implements ApplicationListener {

    private FPSLogger logger;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    private float x;
    private float y;
    private float r;

    private ParticleEffect effect;
    private ParticleEmitter emitter;
    private Particle particle;

    private float angle;

    @Override
    public void create() {
        logger = new FPSLogger();
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        x = Gdx.graphics.getWidth() / 2;
        y = Gdx.graphics.getHeight() / 2;

        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("particleEffects/flameThrower.p"), Gdx.files.internal("particleEffects/pooling/"));
        effect.start();

        emitter = new ParticleEmitter();


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

        update(delta);

//        camera.setToOrtho(true);
        batch.setProjectionMatrix(camera.combined);



        batch.begin();
        effect.update(delta);
        effect.draw(batch);
        batch.end();


        logger.log();

    }

    public void update(float deltaTime) {

        float v = 120;

        // keys
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) x -= v * deltaTime;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x += v * deltaTime;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) y += v * deltaTime;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) y -= v * deltaTime;

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) r += 5 * deltaTime;
        else if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) r -= 5 * deltaTime;
        else r = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))


        effect.setPosition(x, y);

        Array<ParticleEmitter> emitters = effect.getEmitters();
        for (int i = 0; i < emitters.size; i++) {
            float hMin = emitters.get(i).getAngle().getHighMin();
            float hMax = emitters.get(i).getAngle().getHighMax();

            emitters.get(i).setContinuous(false);


            if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            }

            emitters.get(i).getAngle().setHighMin(hMin + r);
            emitters.get(i).getAngle().setHighMax(hMax + r);


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
