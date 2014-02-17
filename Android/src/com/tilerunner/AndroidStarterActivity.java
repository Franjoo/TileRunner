package com.tilerunner;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import test.particlesystem.TestEnvironment;

public class AndroidStarterActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;

//        initialize(new PoolingTest(), cfg);
        initialize(new TestEnvironment(), cfg);
    }
}
