package com.tilerunner.screens.play;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.utils.Array;
import com.tilerunner.camera.CameraManager;
import com.tilerunner.gameobjects.player.Player;
import com.tilerunner.gameobjects.world.World;
import com.tilerunner.input.IGameInput;
import com.tilerunner.input.KeyboardInput;
import com.tilerunner.input.gamepads.X360Gamepad;
import com.tilerunner.ui.PlayUI;

/**
 * represents a class which is responsible for the camera_1 of the gameplay and for
 * updating the world
 */
public class PlayController {
    private static final String TAG = PlayController.class.getSimpleName();

    private boolean singleplayer;

    private World world;
    private Player p1;
    private Player p2;

    // ui
    private PlayUI playUI;

    private CameraManager cameraManager;

    /**
     * creates an new PlayController
     */
    public PlayController() {
        init();
    }

    private void init() {


        //**** SINGLEPLAYER
        if (PlayScreen.PLAYMODE == PlayScreen.SINGLEPLAYER) {
            world = new World(this);
            p1 = new Player(world, 1);

            IGameInput input = null;
            if (Gdx.app.getType() == Application.ApplicationType.Desktop) {

                // xbox 360 controller
                Array<Controller> controllers = Controllers.getControllers();
                if (controllers.size == 0) input = new KeyboardInput();

                    // keyboard
                else input = new X360Gamepad(controllers.get(0));
            }


            p1.setInputController(input);

            cameraManager = new CameraManager(p1, null);

            world.setPlayers(p1);

            world.createEnemies();
            world.createRenderer(PlayScreen.getInstance().getBatch());
            world.setCamera(cameraManager.getCamera_shared());


            // ui
            playUI = new PlayUI();


            world.getRenderer().setView(cameraManager.getCamera_shared());


        }


        //**** MULTIPLAYER
        else {
            world = new World(this);
            p1 = new Player(world, 1);
            p2 = new Player(world, 2);

            // input
            IGameInput input_p1 = null;
            IGameInput input_p2 = null;
            if (Gdx.app.getType() == Application.ApplicationType.Desktop) {

                // xbox 360 controller
                Array<Controller> controllers = Controllers.getControllers();
                if (controllers.size >= 1) input_p1 = new X360Gamepad(controllers.get(0));
                else {
                    input_p1 = new KeyboardInput(Input.Keys.NUMPAD_1,  // left
                            Input.Keys.NUMPAD_3, // right
                            Input.Keys.NUMPAD_5, // up
                            Input.Keys.NUMPAD_2, // down
                            Input.Keys.SEMICOLON, // a
                            Input.Keys.APOSTROPHE, // b
                            Input.Keys.SHIFT_RIGHT); // x
                }

                if (controllers.size >= 2) input_p2 = new X360Gamepad(controllers.get(1));
                else {
                    input_p2 = new KeyboardInput(Input.Keys.F,  // left
                            Input.Keys.H, // right
                            Input.Keys.T, // up
                            Input.Keys.G, // down
                            Input.Keys.A, // a
                            Input.Keys.S, // b
                            Input.Keys.D); // x
                }
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
     * @param delta time since last frame
     */
    public void update(float delta) {

        if (PlayScreen.PLAYMODE == PlayScreen.SINGLEPLAYER) {
            p1.getInputController().poll();
            p1.update(delta);

        } else {
            p1.getInputController().poll();
            p2.getInputController().poll();

            // update players
            p1.update(delta);
            p2.update(delta);
        }

        // update enemies
        for (int i = 0; i < world.getEnemies().size; i++) {
            world.getEnemies().get(i).update(delta);
        }

        // pool objects
        world.updatePoolObjects(delta);

        // checkpoints
        world.checkpoints().update(delta);

        // traps
        world.traps().update(delta);

        // decorations
        world.updateDecorations(delta);

        // coins
        world.coins().update(delta);

        // update cameraManager
        cameraManager.update(delta);

        // update playUI
//        playUI.update(deltaTime);

        // DEBUGGING
        if (Gdx.input.isKeyPressed(Input.Keys.F7)) {
            init();
        }
    }


    /**
     * returns the world in which the game is taking place
     */
    public World getWorld() {
        return world;
    }

    public PlayUI getPlayUI() {
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
