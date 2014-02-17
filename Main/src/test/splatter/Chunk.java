package test.splatter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

import java.util.Timer;

/**
 * User: Franjo
 * Date: 28.11.13
 * Time: 21:39
 * Project: TileRunner
 */
public class Chunk implements Pool.Poolable {

    private static final float gravity = 80f;

    private float time;
    private float lifeTime;

    public boolean active;

    public float x, y;
    private float vx, vy;

    private float vrotation;
    private float rotation;

    private float scale;

    private TextureRegion sourceRegion;


    public void init(float x, float y, float vx, float vy, TextureRegion sourceRegion) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.sourceRegion = sourceRegion;

        active = true;
        time = 0;
        lifeTime = (float) (0.2f + Math.random() * 1.8f);

        rotation = 0;
        vrotation = (float) (3 + Math.random() * 6);

        scale = 1;

    }

    private void drawOutline() {
        Pixmap pixmap = new Pixmap(sourceRegion.getRegionWidth(), sourceRegion.getRegionHeight(), Pixmap.Format.RGBA8888);
    }

    public void update(float delta) {
        time += delta;

        if (time > lifeTime) {
            active = false;
        } else {
            vy -= gravity * delta;
            x += vx;
            y += vy;

            scale = 1 - time / lifeTime;
            rotation += vrotation;
        }
    }


    @Override
    public void reset() {
        x = y = vx = vy = 0;
    }

    public void draw(SpriteBatch batch) {
        Color c = batch.getColor();
        float a = time / lifeTime;

        batch.setColor(c.r, c.g, c.b, 1 - a);
        batch.draw(sourceRegion, x, y, sourceRegion.getRegionWidth() * scale /2, sourceRegion.getRegionHeight() * scale/2, sourceRegion.getRegionWidth(), sourceRegion.getRegionHeight(), scale, scale, rotation);
        batch.setColor(c.r, c.g, c.b, 1);

    }
}