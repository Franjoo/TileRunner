package com.tilerunner.gameobjects.crates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilerunner.core.C;
import com.tilerunner.gameobjects.AbstractCollectionObject;
import com.tilerunner.gameobjects.world.Detector;
import com.tilerunner.gameobjects.world.World;

/**
 * Created with IntelliJ IDEA.
 * User: Franjo
 * Date: 24.02.14
 * Time: 23:28
 */
public class Crate extends AbstractCollectionObject {

    private float friction = 0.4f;
    private Detector detector;
    private boolean falls = false;
    private float vX, vY;
    private Texture tex_crate;


    public Crate(float x, float y, float width, float height) {
        super(x, y, width, height);

        detector = Detector.getInstance();

        vX = 0;

        tex_crate = new Texture(Gdx.files.internal("crate.png"));
    }


    @Override
    public void update(float delta) {

        falls = true;
        final int n = (int) (width / World.TS);
        for (int i = 0; i < n; i++) {
            if ((!detector.isSolid(this.x + i * World.TS, y - C.EPSILON)
                    && !detector.isStep(this.x + i * World.TS, y - C.EPSILON))) {

                System.out.println("falls");
            } else {

                y = (int) ((y - vY) / World.TS) * World.TS;
                falls = false;
                vY = 0;
                break;
            }
        }

        if (falls) {
            vY += C.GRAVITY * delta;
//            vX += 5 * delta;

            y += vY;
            x += vX;

        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(tex_crate, x, y, width, height);
    }

    @Override
    public boolean isHit(float x, float y) {
        return super.isHit(x, y);
    }

    public float push(float v) {
        float diff = v * friction;

        final int n = (int) (height / World.TS);
        for (int i = 0; i < n; i++) {

            // right push
            if (v > 0 && (detector.isSolid(this.x + width + diff, y + i * World.TS)
                    || detector.isStep(this.x + width + diff, y + i * World.TS)
                    || detector.isStep(this.x + width + diff, y - C.EPSILON))) {

                x = (int) ((x + diff) / World.TS) * World.TS;

                return v;
            }

            // left push
            else if (v < 0 && (detector.isSolid(this.x + diff, y + i * World.TS)
                    || detector.isStep(this.x + diff, y + i * World.TS)
                    || detector.isStep(this.x + diff, y - C.EPSILON))) {

                x = (int) ((x - diff) / World.TS) * World.TS;

                return v;
            }
        }

        this.x += v * friction;
        return v * (1 - friction);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
