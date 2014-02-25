package com.tilerunner.gameobjects.platforms;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created with IntelliJ IDEA.
 * User: Franjo
 * Date: 24.02.14
 * Time: 16:15
 */
public interface IPlatform {

    void update(float delta);

    void draw(SpriteBatch batch);

    void drawBounds(SpriteBatch batch);

}
