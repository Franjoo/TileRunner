package com.tilerunner.gameobjects.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.tilerunner.gameobjects.world.Detector;
import com.tilerunner.gameobjects.world.World;

/**
 * User: Franjo
 * Date: 23.11.13
 * Time: 21:01
 * Project: TileRunner
 */
public class Shiver implements Pool.Poolable {

    private final World world;
    Detector detector;

    public int hits;
    //    private float speed;
    public boolean isActive;
    private float x;
    private float y;
    private float vx;
    private float vy;
    private Vector2 direction;
    private TextureRegion texture_shiver;
    private TextureRegion texture_afterglow;

//    public Shiver(float x, float y, float vx, float vy, TextureRegion shiver, TextureRegion afterglow) {
//        this.x = x;
//        this.y = y;
//        this.vx = vx;
//        this.vy = vy;
//
//        direction = new Vector2(vx, vy);
//
//        texture_shiver = shiver;
//        texture_afterglow = afterglow;
//
//        direction = new Vector2(vx, vy);
//        speed = 150;
//
//        detector = Detector.getInstance();
//    }

    public Shiver(World world) {
        this.world = world;

        detector = Detector.getInstance();
    }

    public void update(float delta) {
//        if (isActive) {

        float gravity = -100;
        vy += gravity;

        if (detector.isSolid(x + vx * delta, y)) {

            if (detector.isDestructible(x + vx * delta, y)) {
                float x = this.x + vx * delta;
                float y = this.y;

//                System.out.println("spawn chunk");

                world.spawnChunk(x, y,
                        (float) (vx * (10 + Math.random() * 3)),
                        (float) (vy * (10 + Math.random() * 3)),
                        (float) (0.4f + Math.random() * 0.6f),
                        (float) (0.0f + Math.random() * 0.3f),
                        null);

//                world.spawnChunk(x, y, 120, 120, 1, 0.5f, null);
            }


            vx *= -1;
        }

        if (detector.isSolid(x, y + vy * delta)) {
            vy *= -1;
        }

        x += vx * delta;
        y += vy * delta;


//            if (detector.isSolid(x + vx * deltaTime, y)) {
//                vx *= -1;
//                hits++;
//            } else x += vx *  deltaTime;
//
//            if (detector.isSolid(x, y + vy *  deltaTime)) {
//                vy *= -1;
//                hits++;
//            } else y += vy *  deltaTime;


//            direction.set(vx, vy);

//        }
    }

    public void draw(SpriteBatch batch) {
//        if (isActive) {
//            batch.draw(texture_afterglow, x, y, 0, 0, texture_afterglow.getRegionWidth(), texture_afterglow.getRegionHeight(), 1, 1, direction.angle() - 180);
        batch.draw(texture_shiver, x, y);

//        }
    }

    @Override
    public void reset() {
        x = y = vx = vy = 0;
    }

    public void init(float x, float y, float vx, float vy, TextureRegion shiver, TextureRegion afterglow) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;

        texture_shiver = shiver;
        texture_afterglow = afterglow;

        isActive = true;
    }

}
