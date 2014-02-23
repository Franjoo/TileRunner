package com.tilerunner.gameobjects.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilerunner.input.IGameInput;
import com.tilerunner.input.IGameInputController;

/**
 * Created with IntelliJ IDEA.
 * User: Franjo
 * Date: 19.02.14
 * Time: 13:37
 */
public interface IPlayable extends IGameObject{

    public void update(float delta);

    public void render(SpriteBatch batch);

    public float getX();

    public float getY();

    public float getVx();

    public float getVy();

    public IGameInput getInputController();

}
