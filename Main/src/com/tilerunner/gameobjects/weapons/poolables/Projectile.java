package com.tilerunner.gameobjects.weapons.poolables;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Pool;
import com.tilerunner.gameobjects.world.Detector;

/**
 * User: Franjo
 * Date: 29.11.13
 * Time: 13:57
 * Project: TileRunner
 */
public abstract class Projectile implements Pool.Poolable{

    private final Detector detector;

    private boolean active;
    private float x,y;
    private float vX, vY;

    public Projectile() {
        detector = Detector.getInstance();
    }

    public abstract void reset();

    abstract void init();

    abstract void update(float delta);

    abstract void render(SpriteBatch batch);
}
