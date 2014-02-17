package test.particlesystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/**
 * User: Franjo
 * Date: 10.12.13
 * Time: 20:03
 * Project: TileRunner
 */
public class Explosion {

    private Pool<TintedParticle> pool;
    private Array<TintedParticle> particles;

    private float time;
    private float duration = 1;
    private float pps = 200;

    public Explosion() {

        particles = new Array<>();

        pool = new Pool<TintedParticle>() {
            @Override
            protected TintedParticle newObject() {
                return new TintedParticle();
            }
        };
    }

    public void explode(float x, float y) {

//        if (time >= duration) {
        for (int i = 0; i < Gdx.graphics.getDeltaTime() * pps; i++) {
            TintedParticle tp = pool.obtain();
//            TintedParticle tp = new TintedParticle();
            tp.setPosition(x, y);
            tp.setSize((float) (10 + Math.random() + 30), (float) (10 + Math.random() + 30));

            float vX = (float) (-100 + Math.random() * 200);
            float vY = (float) (-100 + Math.random() * 200);

            float degrees = (float) (Math.random() * 360);
            float rad = degrees * MathUtils.degreesToRadians;
            float cos = (float) Math.cos(rad);
            float sin = (float) Math.sin(rad);

            float newX = vX * cos - vY * sin;
            float newY = vX * sin + vY * cos;


            tp.init(newX, newY, 0, 0);
            particles.add(tp);

        }

    }

    public void reset() {
        pool.freeAll(particles);
        particles.clear();
        time = 0;
    }

    public void update(float delta) {

        for (int i = 0; i < particles.size; i++) {
            TintedParticle tp = particles.get(i);
            tp.update(delta);
            if (!tp.active) {
                particles.removeIndex(i);
                pool.free(tp);
            }
        }

        time += delta;
        if (time >= 0.5f) {
            System.out.println("fps:" + Gdx.graphics.getFramesPerSecond() + "active:" + particles.size + " free:" + pool.getFree() + " peak:" + pool.peak);
            time = 0;
        }
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        batch.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
//        batch.setBlendFunction(GL10.GL_ONE,GL10.GL_ONE_MINUS_SRC_ALPHA);

        for (int i = 0; i < particles.size; i++) {
            particles.get(i).draw(batch);
        }

//        batch.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        batch.end();

    }


}
