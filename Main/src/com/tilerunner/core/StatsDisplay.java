package com.tilerunner.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * User: Franjo
 * Date: 18.11.13
 * Time: 23:29
 * Project: TileRunner
 */
public class StatsDisplay {

    private SpriteBatch batch;
    private BitmapFont font;
    private FPSLogger logger;
    private CharSequence str;

    public StatsDisplay() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/arial-15.fnt"), Gdx.files.internal("fonts/arial-15.png"), true);
        font.setColor(1, 0.2f, 0.2f, 1);
        logger = new FPSLogger();
        str = "hello worlcsdfweafdscjkasbfcjbybcds<d";
    }

    public void render() {
        batch.begin();
        font.draw(batch, str, 3200, 3200);
        batch.end();


    }

    public void update(float delta) {
    }

}
