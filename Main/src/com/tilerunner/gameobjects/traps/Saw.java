package com.tilerunner.gameobjects.traps;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.tilerunner.gameobjects.world.World;

/**
 * User: Franjo
 * Date: 01.12.13
 * Time: 16:00
 * Project: TileRunner
 */
public class Saw implements Trap {

    private float x, y;
    private float w, h;
    private float vr;
    private float r;
    private float scaleX, scaleY;

    private TextureRegion region;
    private Texture bounds;

    private Vector2 _dist;

    public Saw(float x, float y, float w, float h, float vr, TextureRegion region) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.vr = vr;
        this.region = region;

        scaleX = w / region.getRegionWidth();
        scaleY = h / region.getRegionHeight();

        _dist = new Vector2();

        drawBoundsTexture();
    }

    private void drawBoundsTexture() {
        Pixmap p = new Pixmap((int) w, (int) h, Pixmap.Format.RGBA8888);

        p.setColor(0, 0, 1, 1);
        for (int i = 0; i < 4; i++) {
            p.drawRectangle(i, i, (int) w - 2 * i, (int) h - 2 * i);
        }

        p.setColor(0, 1, 0, 1);
        for (int i = 0; i < 4; i++) {
            p.drawCircle((int) w / 2, (int) h / 2, (int) w / 2 - i);
        }


        bounds = new Texture(p);
    }


    @Override
    public boolean isHit(float x, float y) {
        _dist.set(x - (this.x + w / 2), y - (this.y + h / 2));
        return _dist.len() <= w / 2;
    }

    @Override
    public void update(float delta) {
        r += vr * delta;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(region, x, y,
                w  / 2, h / 2f,
                w, h,
                1, 1
                , r);
    }

    @Override
    public void drawBounds(SpriteBatch batch) {
        batch.draw(bounds, x, y);
    }

    public float getCenterX() {
        return x + w / 2;
    }

    public float getCenterY() {
        return y + h / 2;
    }


}
