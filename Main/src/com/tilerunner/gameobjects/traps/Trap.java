package com.tilerunner.gameobjects.traps;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * User: Franjo
 * Date: 01.12.13
 * Time: 16:01
 * Project: TileRunner
 */
public interface Trap {


    public boolean isHit(float x, float y);

    public void update(float delta);

    public void render(SpriteBatch batch);

}
