package com.tilerunner.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * User: Franjo
 * Date: 11.11.13
 * Time: 13:31
 * Project: TileRunner
 */
public abstract class GameObject {

    /* position */
    protected float x, y;

    /* dimension */
    protected float width, height;

    /* velocity */
    public float vX, vY;

    /* acceleration */
    protected float aX, aY;

    /* friction */
    protected float frictionX, frictionY;

    /* limits velocity */
    protected float vX_Min;
    protected float vX_Max;
    protected float vY_Min;
    protected float vY_Max;

    /* limits acceleration */
    protected float aX_Max;
    protected float aY_Max;
    protected float aX_Min;
    protected float aY_Min;

    public GameObject() {
    }

    public abstract void update(float deltaTime);

    public abstract void render(SpriteBatch batch);

    public float getvX_Min() {
        return vX_Min;
    }

    public float getvX_Max() {
        return vX_Max;
    }

    public float getvY_Min() {
        return vY_Min;
    }

    public float getvY_Max() {
        return vY_Max;
    }

    public float getaX_Max() {
        return aX_Max;
    }

    public float getaY_Max() {
        return aY_Max;
    }

    public float getaX_Min() {
        return aX_Min;
    }

    public float getaY_Min() {
        return aY_Min;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float get_vX() {
        return vX;
    }

    public float get_vY() {
        return vY;
    }

    public float get_aX() {
        return aX;
    }

    public float get_aY() {
        return aY;
    }

    public float get_frictionX() {
        return frictionX;
    }

    public float get_frictionY() {
        return frictionY;
    }

    protected void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    protected void setDimension(float width, float height) {
        this.width = width;
        this.height = height;
    }

    protected void setVelocity(float vX, float vY) {
        this.vX = vX;
        this.vY = vY;
    }

    protected void setAcceleration(float aX, float aY) {
        this.aX = aX;
        this.aY = aY;
    }

    protected void setFriction(float frictionX, float frictionY) {
        this.frictionX = frictionX;
        this.frictionY = frictionY;
    }


}
