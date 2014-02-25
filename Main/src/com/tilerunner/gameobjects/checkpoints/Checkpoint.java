package com.tilerunner.gameobjects.checkpoints;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created with IntelliJ IDEA.
 * User: Franjo
 * Date: 24.02.14
 * Time: 12:40
 */
public class Checkpoint implements ICheckpoint {

    private float x, y;
    private float width, height;
    private Texture bounds;

    public Checkpoint(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        drawBoundsTexture();
    }

    private void drawBoundsTexture() {
        Pixmap p = new Pixmap((int) width, (int) height, Pixmap.Format.RGBA8888);

        p.setColor(1, 1, 1, 1);
        for (int i = 0; i < 4; i++) {
            p.drawRectangle(i, i, (int) width - 2 * i, (int) height - 2 * i);
        }

        bounds = new Texture(p);
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void draw(SpriteBatch batch) {
    }

    @Override
    public void drawBounds(SpriteBatch batch) {
        batch.draw(bounds, x, y);
    }

    @Override
    public boolean isHit(float x, float y) {
        // contains
        return this.x <= x && this.x + this.width >= x && this.y <= y && this.y + this.height >= y;

    }

    public float getPointX(){
        return x + width/2;
    }

    public float getPointY(){
        return y;
    }
}
