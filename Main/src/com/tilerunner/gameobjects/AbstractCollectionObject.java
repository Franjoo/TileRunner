package com.tilerunner.gameobjects;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created with IntelliJ IDEA.
 * User: Franjo
 * Date: 24.02.14
 * Time: 18:01
 */
public abstract class AbstractCollectionObject implements ICollectionObject {

    private Texture bounds;
    protected float x, y;
    protected float width, height;

    public AbstractCollectionObject(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        drawBoundsTexture();
    }

    protected void drawBoundsTexture() {
        Pixmap p = new Pixmap((int) width, (int) height, Pixmap.Format.RGBA8888);

        p.setColor(0, 0, 1, 1);
        for (int i = 0; i < 4; i++) {
            p.drawRectangle(i, i, (int) width - 2 * i, (int) height - 2 * i);
        }

        bounds = new Texture(p);
    }


    public abstract void update(float delta);



    public abstract void draw(SpriteBatch batch);


    @Override
    public void drawBounds(SpriteBatch batch) {
        batch.draw(bounds,x,y);
    }

    @Override
    public boolean isHit(float x, float y) {
        // contains
        return this.x <= x && this.x + this.width >= x && this.y <= y && this.y + this.height >= y;
    }

    public boolean isHit(float x, float y, float width, float height){
        // overlaps
        return this.x < x + width && this.x + this.width > x && this.y < y + height && this.y + this.height > y;
    }
}
