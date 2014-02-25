package com.tilerunner.gameobjects.boxes;

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
public class Box extends AbstractCollectionObject {

    private float friction = 0.4f;
    private Detector detector;
    private boolean falls = false;
    private float vX, vY;


    public Box(float x, float y, float width, float height) {
        super(x, y, width, height);

        detector = Detector.getInstance();

        vX = 0;
    }


    @Override
    public void update(float delta) {

        falls = true;
        final int n = (int) (width / World.TILESIZE);
        for (int i = 0; i < n; i++) {
            if ((!detector.isSolid(this.x + i * World.TILESIZE, y - C.EPSILON)
                    && !detector.isStep(this.x + i * World.TILESIZE, y - C.EPSILON))) {

                System.out.println("falls");
            } else {

                y = (int) ((y - vY) / World.TILESIZE) * World.TILESIZE;
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
    }

    @Override
    public boolean isHit(float x, float y) {
        return super.isHit(x, y);
    }

    public float push(float v) {
        float diff = v * friction;

        final int n = (int) (height / World.TILESIZE);
        for (int i = 0; i < n; i++) {

            // right push
            if (v > 0 && (detector.isSolid(this.x + width + diff, y + i * World.TILESIZE)
                    || detector.isStep(this.x + width + diff, y + i * World.TILESIZE)
                    || detector.isStep(this.x + width + diff, y - C.EPSILON))) {

                x = (int) ((x + diff) / World.TILESIZE) * World.TILESIZE;

                return v;
            }

            // left push
            else if (v < 0 && (detector.isSolid(this.x + diff, y + i * World.TILESIZE)
                    || detector.isStep(this.x + diff, y + i * World.TILESIZE)
                    || detector.isStep(this.x + diff, y - C.EPSILON))) {

                x = (int) ((x - diff) / World.TILESIZE) * World.TILESIZE;

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
