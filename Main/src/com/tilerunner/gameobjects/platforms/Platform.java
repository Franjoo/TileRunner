package com.tilerunner.gameobjects.platforms;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.tilerunner.gameobjects.AbstractCollectionObject;
import com.tilerunner.gameobjects.ICollectionObject;

/**
 * Created with IntelliJ IDEA.
 * User: Franjo
 * Date: 24.02.14
 * Time: 17:55
 */
public class Platform extends AbstractCollectionObject {

    private final float x1, x2;
    private final float y1, y2;

    private float dirX, dirY;

    // movement
    private float v;
    private float time, elapsed;

    public Platform(float x1, float y1, float x2, float y2, float width, float height, float v) {
        super(x1, y1, width, height);

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

        // distance
        float distX = x2 - x1;
        float distY = y2 - y1;
        float dist = (float) Math.sqrt(distX * distX + distY * distY);

        // normalize
        dirX = distX / dist;
        dirY = distY / dist;

        time = dist / v;
        this.v = dist / time;

        x = x1;
        y = y1;
    }

    @Override
    public void update(float delta) {
        // moving
        elapsed += delta;
        if (elapsed >= time) {
            dirX *= -1;
            dirY *= -1;
            elapsed = 0;
        } else {
            x += dirX * v * delta;
            y += dirY * v * delta;
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
    }

    public boolean isHit(float x, float y) {
        // contains
        return this.x <= x && this.x + this.width >= x && this.y <= y && this.y + this.height >= y;
    }

    public boolean isHit(Rectangle r){
        // overlaps
        return x < r.x + r.width && x + width > r.x && y < r.y + r.height && y + height > r.y;
    }

    public boolean isHit(float x, float y, float width, float height){
        // overlaps
        return this.x < x + width && this.x + this.width > x && this.y < y + height && this.y + this.height > y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getVx() {
        return dirX * v;
    }

    public float getVy() {
        return dirY * v;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

}
