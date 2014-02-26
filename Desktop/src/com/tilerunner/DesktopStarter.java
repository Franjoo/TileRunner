package com.tilerunner;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tilerunner.core.Main;

/**
 * Desktop Starter Class
 */
public class DesktopStarter {

    private static final String VERSION = "v0.001";

    private static final int FRAME_WIDTH = 1280;
    private static final int FRAME_HEIGHT = 720;

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Tile Runner " + VERSION;
        cfg.useGL20 = true;
        cfg.width = FRAME_WIDTH;
        cfg.height = FRAME_HEIGHT;
        cfg.vSyncEnabled = true;




        new LwjglApplication(new Main(), cfg);
    }
}