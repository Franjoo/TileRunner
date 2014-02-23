package com.tilerunner.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.tilerunner.gameobjects.player.IPlayable;
import com.tilerunner.gameobjects.player.Player;

/**
 * User: Franjo
 * Date: 13.11.13
 * Time: 18:14
 * Project: TileRunner
 */
public class CameraManager {

    // bools
    private boolean singleplayer;
    private boolean useShared;

    // player
    private IPlayable p1;
    private IPlayable p2;
    private Vector2 distance;

    // cameras
    private OrthographicCamera camera_p1;
    private OrthographicCamera camera_p2;
    private OrthographicCamera camera_shared;

    // cameraHelper
    private CameraHelper helper_p1;
    private CameraHelper helper_p2;
    private CameraHelper helper_shared;

    public CameraManager(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;

        distance = new Vector2();

        if (p2 == null) singleplayer = true;

        init();
    }

    private void init() {
        if (singleplayer) {
            // camera
//            camera_p1 = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            camera_shared = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            // cameraHelper
//            helper_p1 = new CameraHelper();
            helper_shared = new CameraHelper();
            helper_shared.applyTo(camera_shared);
            helper_shared.setTarget(p1);
        } else {
            // cameras
            camera_p1 = new OrthographicCamera(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
            camera_p2 = new OrthographicCamera(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
            camera_shared = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            // cameraHelper
            helper_p1 = new CameraHelper();
            helper_p2 = new CameraHelper();
            helper_shared = new CameraHelper();

            // apply to cameras
            helper_p1.applyTo(camera_p1);
            helper_p2.applyTo(camera_p2);
            helper_shared.applyTo(camera_shared);

            // set targets
            helper_p1.setTarget(p1);
            helper_p2.setTarget(p2);
            helper_shared.setTargets(p1, p2);


        }

    }

    public void update(float deltaTime) {
        //**** SINGLEPLAYER
        if (singleplayer) {
            useShared = true;

            // update helper
            helper_shared.update(deltaTime);

            // apply to cameras
            helper_shared.applyTo(camera_shared);
        }

        //**** MULTIPLAYER
        else {


            // calculate distance
            distance.set(p2.getX() - p1.getX(), p2.getY() - p1.getY());
//        System.out.println("distance: " + distance.len());
//        if (distance.len() <= 500) {
//            useShared = true;
//            helper_shared.update(deltaTime);
//            helper_shared.applyTo(camera_shared);
//        } else {
//            useShared = false;

//        if (distance.len() <= Gdx.graphics.getHeight() * camera_shared.zoom) useShared = true;
//        else useShared = false;


            useShared = true;

            // update helper
            helper_p1.update(deltaTime);
            helper_p2.update(deltaTime);
            helper_shared.update(deltaTime);

            // apply to cameras
            helper_p1.applyTo(camera_p1);
            helper_p2.applyTo(camera_p2);
            helper_shared.applyTo(camera_shared);

        }

    }


    //*** GETTER

    public OrthographicCamera getCamera_p1() {
        return camera_p1;
    }

    public OrthographicCamera getCamera_p2() {
        return camera_p2;
    }

    public OrthographicCamera getCamera_shared() {
        return camera_shared;
    }

    public boolean useShared() {
        return useShared;
    }
}
