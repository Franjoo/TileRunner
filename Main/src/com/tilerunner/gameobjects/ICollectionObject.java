package com.tilerunner.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created with IntelliJ IDEA.
 * User: Franjo
 * Date: 24.02.14
 * Time: 16:23
 */
public interface ICollectionObject extends IHitable{

    void update(float delta);

    void draw(SpriteBatch batch);

    void drawBounds(SpriteBatch batch);

}
