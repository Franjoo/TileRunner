package com.tilerunner.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tilerunner.util.Drawer;

/**
 * User: Franjo
 * Date: 04.12.13
 * Time: 22:53
 * Project: TileRunner
 */
public class TimeDisplay {


    private float x, y;
    private Texture texture_background;

    // font
    private BitmapFont font;

    // currentTime
    private double currentTime;
    private int seconds;
    private int minutes;
    private String time;
    private StringBuilder timeBuilder;

    public TimeDisplay() {
//        texture_background = Drawer.rectangleTexture(100, 50, new Color(0.2f, 0.2f, 0.2f, 1));
        drawTimeBackground();

        x = Gdx.graphics.getWidth() / 2 - texture_background.getWidth() / 2;
        y = 5;

        // font
//        font = new BitmapFont(Gdx.files.internal("fonts/C64_white.fnt"), Gdx.files.internal("fonts/C64_0.png"), true);
        font = new BitmapFont(Gdx.files.internal("fonts/arial-15.fnt"), Gdx.files.internal("fonts/arial-15.png"), true);
//        font = new BitmapFont(Gdx.files.internal("fonts/C64_black.fnt"), true);
        font.setColor(0.8f, 0.8f, 0.8f, 1);
        font.setScale(1.2f);


        time = new String();
        timeBuilder = new StringBuilder();
    }

    private void drawTimeBackground() {
        Pixmap p = new Pixmap(70, 30, Pixmap.Format.RGBA8888);
        p.setColor(0.2f, 0.2f, 0.2f, 0.4f);
        p.fillRectangle(0, 0, p.getWidth(), p.getHeight());
        p.setColor(1, 1, 1, 1);
        p.drawRectangle(0, 0, p.getWidth(), p.getHeight());

        texture_background = new Texture(p);
    }


    public void update(float delta) {
        currentTime += delta;
        seconds = (int) (currentTime) % 60;
        minutes = (int) (currentTime) / 60;

        // build time
        timeBuilder.delete(0, timeBuilder.length());
        if (minutes < 10) timeBuilder.append("0");
        timeBuilder.append(minutes);
        timeBuilder.append(" : ");
        if (seconds < 10) timeBuilder.append("0");
        timeBuilder.append(seconds);

        // set time string
        time = timeBuilder.toString();

    }

    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(texture_background, x, y);
        font.draw(batch, time, x + texture_background.getWidth() / 2 - font.getBounds(time).width / 2, y + texture_background.getHeight() / 2 - font.getBounds(time).height / 2);
        batch.end();

//        batch.begin();
//        font.draw(batch, "dddccsdcscs", camera.viewportWidth / 2, 0);
//        batch.end();

    }

}
