package com.tilerunner.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tilerunner.gameobjects.player.IPlayable;

public class CameraHelper {
    public static final String TAG = CameraHelper.class.getSimpleName();

    private static final float MAX_ZOOM_IN = 0.25f;
    private static final float MAX_ZOOM_OUT = 4.0f;

    private float aX = 0.07f;
    private float aY = 0.07f;

//    private Vector2 position;

    private float x;
    private float y;

    private float zoom;
    private IPlayable target;
    private IPlayable[] targets;

    public float deltaX;
    public float deltaY;
    public float oldX;
    public float oldY;

    private float targetDeltaX;
    private float targetDeltaY;

    private Vector2 distance;

    public CameraHelper() {

//        position = new Vector2(0, 0);
        zoom = 1f;

        targets = new IPlayable[2];
    }


    public void update(float deltaTime) {

        handleDebugControlls();

//        if (targets[0] != null | targets[1] != null) {
//            if (targets[0] != null) target = targets[0];
//            else target = targets[1];
//
//
//
//
//        }


        if (targets[1] == null) {
            target = targets[0];

            float qX = target.getX();
            float qY = target.getY();

            deltaX = qX - x;
            deltaY = qY - y;

            x += deltaX * aX;
            y += deltaY * aY;


        } else if (targets[0] != null && targets[1] != null) {
            x = targets[0].getX() + (targets[1].getX() - targets[0].getX()) / 2;
            y = targets[0].getY() + (targets[1].getY() - targets[0].getY()) / 2;

            float distanceX = targets[1].getX() - targets[0].getX();
            float distanceY = targets[1].getY() - targets[0].getY();

            distance.set(distanceX, distanceY);

            zoom = 1 + distance.len() / 1300;


//            setZoom(zoom);
            if (zoom > MAX_ZOOM_OUT) zoom = MAX_ZOOM_OUT;
            if (zoom < MAX_ZOOM_OUT/2) zoom = MAX_ZOOM_OUT/2;


        }


    }

    private void handleDebugControlls() {
        // Zoom in
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_1)) {
            addZoom(-0.006f);
            if (getZoom() < MAX_ZOOM_IN) setZoom(MAX_ZOOM_IN);
            System.out.println("Zoom: " + getZoom());
        }

        // Zoom out
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_2)) {
            addZoom(0.006f);
            if (getZoom() > MAX_ZOOM_OUT) setZoom(MAX_ZOOM_OUT);
            System.out.println("Zoom: " + getZoom());
        }

        // set Zoom 1
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_3)) {
            setZoom(1);
            System.out.println("Zoom: " + getZoom());
        }
    }


    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
//        position.set(x, y);
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

//    public Vector2 getPosition() {
//        return position;
//    }

    public void addZoom(float amount) {
        setZoom(zoom + amount);
    }

    public void setZoom(float zoom) {
        this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
    }

    public float getZoom() {
        return zoom;
    }

    public void setTarget(IPlayable target) {
//        this.target = target;
        targets[0] = target;
        targets[1] = null;

        x = target.getX();
        y = target.getY();
    }

    public void setTargets(IPlayable t1, IPlayable t2) {
        targets[0] = t1;
        targets[1] = t2;
        distance = new Vector2(t2.getX() - t1.getX(), t2.getY() - t1.getY());
    }


    public IPlayable getTarget() {
        return target;
    }

    public boolean hasTarget(Sprite target) {
        return hasTarget() && this.target.equals(target);
    }

    public boolean hasTarget() {
        return (targets[0] != null || targets[0] != null);
    }


    public void applyTo(OrthographicCamera camera) {

        camera.position.set(x,y,0);

        camera.zoom = zoom;
        camera.update();
    }

}
