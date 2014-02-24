package com.tilerunner.gameobjects.traps;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilerunner.gameobjects.IHitable;

/**
 * User: Franjo
 * Date: 01.12.13
 * Time: 16:01
 * Project: TileRunner
 */
public interface Trap extends IHitable {

    void update(float delta);

    void draw(SpriteBatch batch);

    void drawBounds(SpriteBatch batch);
}
