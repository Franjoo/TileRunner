package com.tilerunner.screens.play;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.tilerunner.camera.CameraManager;
import com.tilerunner.gameobjects.player.Player;
import com.tilerunner.gameobjects.world.World;
import com.tilerunner.input.IGameInputController;
import com.tilerunner.input.KeyboardInput;
import com.tilerunner.input.gamepads._X360Gamepad;
import com.tilerunner.ui.PlayUI;
import com.tilerunner.ui.PlayerUI;

/**
 * represents a class which is responsible for the camera_1 of the gameplay and for
 * updating the world
 */
public class PlayController {
    private static final String TAG = PlayController.class.getSimpleName();

    private boolean singleplayer;

    private final World world;
    private final Player p1;
    private Player p2;

    // ui
    private PlayUI playUI;

    private CameraManager cameraManager;

    /**
     * creates an new PlayController
     */
    public PlayController() {


        singleplayer = PlayScreen.PLAYMODE == PlayScreen.SINGLEPLAYER;
        // push test

        //**** SINGLEPLAYER
        if (singleplayer) {
            world = new World(this);
            p1 = new Player(world, 1);

            // input
            IGameInputController input_p1 = null;
            if (Gdx.app.getType() == Application.ApplicationType.Desktop) {

                // xbox 360 controller
                Array<Controller> controllers = Controllers.getControllers();
                try{
                    if (controllers != null)
                        input_p1 = new _X360Gamepad(controllers.get(0));
                    else input_p1 = new KeyboardInput(p1);
                }catch(Exception e){
                    e.printStackTrace();
                }

            }

            p1.setInputController(input_p1);

            cameraManager = new CameraManager(p1, null);

            world.setPlayers(p1);
            world.createEnemies();
            world.createRenderer(PlayScreen.getInstance().getBatch());


            // ui
            playUI = new PlayUI();


        }


        //**** MULTIPLAYER
        else {
            world = new World(this);
            p1 = new Player(world, 1);
            p2 = new Player(world, 2);

            // input
            IGameInputController input_p1 = null;
            IGameInputController input_p2 = null;
            if (Gdx.app.getType() == Application.ApplicationType.Desktop) {

                // xbox 360 controller
                Array<Controller> controllers = Controllers.getControllers();
                if (controllers.get(0) != null) input_p1 = new _X360Gamepad(controllers.get(0));
                if (controllers.get(1) != null) input_p2 = new _X360Gamepad(controllers.get(1));
            }

            p1.setInputController(input_p1);
            p2.setInputController(input_p2);

            cameraManager = new CameraManager(p1, p2);

            world.setPlayers(p1, p2);
            world.createEnemies();
            world.createRenderer(PlayScreen.getInstance().getBatch());
        }


    }

    /**
     * updates the objects used in PlayScreen
     *
     * @param deltaTime time since last frame
     */
    public void update(float deltaTime) {

        if (singleplayer) {
            p1.update(deltaTime);
        } else {
            // update players
            p1.update(deltaTime);
            p2.update(deltaTime);
        }

        // update enemies
        for (int i = 0; i < world.getEnemies().size; i++) {
            world.getEnemies().get(i).update(deltaTime);
        }

        // pool objects
        world.updatePoolObjects(deltaTime);

        // traps
        world.updateTraps(deltaTime);

        // decorations
        world.updateDecorations(deltaTime);

        // update cameraManager
        cameraManager.update(deltaTime);

        // update playUI
        playUI.update(deltaTime);
    }

    /**
     * returns the world in which the game is taking place
     */
    public World getWorld() {
        return world;
    }

    public PlayUI getPlayUI(){
        return playUI;
    }

    public Player getPlayer(int i) {
        if (i == 1) return p1;
        return p2;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

}
