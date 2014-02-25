package com.tilerunner.gameobjects.checkpoints;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilerunner.gameobjects.IHitable;

/**
 * Created with IntelliJ IDEA.
 * User: Franjo
 * Date: 24.02.14
 * Time: 12:34
 */
public interface ICheckpoint extends IHitable {

    void update(float delta);

    void draw(SpriteBatch batch);

    void drawBounds(SpriteBatch batch);
}
