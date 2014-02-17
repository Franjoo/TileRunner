package com.tilerunner.gameobjects.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * User: Franjo
 * Date: 06.12.13
 * Time: 14:00
 * Project: TileRunner
 */
public interface IEnemy {

    public void update(float delta);

    public void render(SpriteBatch batch);

}
