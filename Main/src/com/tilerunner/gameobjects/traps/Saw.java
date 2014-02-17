package com.tilerunner.gameobjects.traps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

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
    }


    @Override
    public boolean isHit(float x, float y) {
        _dist.set(x - this.x, y - this.y);
        return _dist.len() <= w / 2;
    }

    @Override
    public void update(float delta) {
        r += vr * delta;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(region, x + w / 2, y + h / 2,
                region.getRegionWidth() / 2, region.getRegionHeight() / 2,
                region.getRegionWidth(), region.getRegionHeight(),
                scaleX, scaleY
                , r);
        batch.end();

    }
}
