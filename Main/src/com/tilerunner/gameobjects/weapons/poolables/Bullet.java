package com.tilerunner.gameobjects.weapons.poolables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Pool;
import com.tilerunner.gameobjects.world.World;

/**
 * User: Franjo
 * Date: 17.11.13
 * Time: 21:33
 * Project: TileRunner
 */
public class Bullet implements Pool.Poolable {
    public static final String TAG = Bullet.class.getSimpleName();
    private static final Texture TEXTURE_BULLET = new Texture(Gdx.files.internal("bullet.png"));

    public boolean alive;
    private float x, y;
    public float dirX, dirY;
    public float bulletSpeed;
    private Texture texture;

    /**
     * Bullet constructor. Just initialize variables.
     */

    public Bullet() {
        texture = TEXTURE_BULLET;
        alive = false;
    }

    /**
     * Initialize the bullet. Call this method after getting a bullet from the pool.
     */
    public void init(float x, float y, float dirX, float dirY, float variance, float bulletSpeed) {

        // set alive
        alive = true;

        // init attributes
        this.x = x;
        this.y = y;
        this.dirX = dirX;
        this.dirY = dirY;
        this.bulletSpeed = bulletSpeed;

        // apply variance
        this.dirX += -variance + Math.random() * variance;
        this.dirY += -variance + Math.random() * variance;

    }

    /**
     * Callback method when the object is freed. It is automatically called by Pool.free()
     * Must reset every meaningful field of this bullet.
     */
    public void reset() {
        x = 0;
        y = 0;
        dirX = 0;
        dirY = 0;
        alive = false;
    }

    /**
     * Method called each frame, which updates the bullet.
     */
    public void update(float delta) {
        x += dirX * bulletSpeed * delta;
        y += dirY * bulletSpeed * delta;
    }

    public void draw(SpriteBatch batch){
        batch.draw(texture, x, y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
