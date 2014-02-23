package com.tilerunner;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import test.*;
import test.b2dlight.B2DLightTest;
import test.light.LightTest;
//import test.pooling.PoolingTest;
//import test.pooling.TestEnvironment;
import test.particlesystem.TestEnvironment;
import test.rope.RopeTest;
import test.splatter.SplatterTest;

/**
 * User: Franjo
 * Date: 12.11.13
 * Time: 18:32
 * Project: TileRunner
 */
public class TestStarter {

    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 480;

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.useGL20 = true;
        cfg.vSyncEnabled = false;
//        cfg.foregroundFPS = 5000;
        cfg.width = FRAME_WIDTH;
        cfg.height = FRAME_HEIGHT;

//        new LwjglApplication(new LightTest(), cfg);
//        new LwjglApplication(new B2DLightTest(), cfg);
//        new LwjglApplication(new ShaderTest(), cfg);
        new LwjglApplication(new RopeTest(), cfg);
//        new LwjglApplication(new SplatterTest(), cfg);
//        new LwjglApplication(new ControllerTest(), cfg);
//        new LwjglApplication(new PoolingTest(), cfg);
//        new LwjglApplication(new TestEnvironment(), cfg);




//        new LwjglApplication(new ParticleTest(), cfg);
//        new LwjglApplication(new ShaderCanvas(), cfg);
    }
}