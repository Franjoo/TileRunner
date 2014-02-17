package com.tilerunner.gameobjects.equipment;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * User: Franjo
 * Date: 15.11.13
 * Time: 18:18
 * Project: TileRunner
 */
public abstract class Equipment {

    protected float aX, aY;
    protected float vX, vY;
    protected float frictionX, frictionY;

    public abstract void update(float deltaTime);

    public abstract void render(SpriteBatch batch);


    public float get_aX() {
        return aX;
    }

    public float get_aY() {
        return aY;
    }

    public float getvX() {
        return vX;
    }

    public float getvY() {
        return vY;
    }

    public float get_frictionX() {
        return frictionX;
    }

    public float get_frictionY() {
        return frictionY;
    }
}
