package com.tilerunner.gameobjects.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tilerunner.gameobjects.world.Detector;

/**
 * User: Franjo
 * Date: 23.11.13
 * Time: 20:51
 * Project: TileRunner
 */
public class Bomb {

    private Texture texture_bomb;
    private TextureRegion texture_shiver;
    private TextureRegion texture_afterglow;

    private Array<Shiver> shivers;

    private float x;
    private float y;

    private final int num_shivers = 50;

    public Bomb(float x, float y) {
        this.x = x;
        this.y = y;

        texture_bomb = drawBombTexture();
        texture_shiver = drawShiverTexture();
        texture_afterglow = drawAfterGlowTexture();

        createShivers();
    }

    private TextureRegion drawAfterGlowTexture() {
        Pixmap pixmap = new Pixmap(500, 8, Pixmap.Format.RGBA8888);
        Color c1 = new Color(0, 0, 1, 0.7f);
        Color c2 = new Color(c1);
        for (int w = 0; w < pixmap.getWidth(); w++) {
            for (int h = 0; h < pixmap.getHeight(); h++) {
                float alpha = c1.a - ((float) w / (float) pixmap.getWidth()) * c1.a;
                c2.set(c1.r, c1.g, c1.b, alpha);
                pixmap.setColor(c2);
                pixmap.drawPixel(w, h);
            }
        }

        return new TextureRegion(new Texture(pixmap));

    }

    private void createShivers() {
        shivers = new Array<>();
        Vector2 dir = new Vector2();
        for (int i = 0; i < num_shivers; i++) {
            dir.set(1, 0);
            dir.setAngle((float) (Math.random() * 360));
            float dirX = dir.x;
            float dirY = dir.y;
//            Shiver s = new Shiver(x, y, dirX, dirY, texture_shiver, texture_afterglow);
//            shivers.add(s);
        }

    }

    private TextureRegion drawShiverTexture() {
        Pixmap pixmap = new Pixmap(8, 8, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.6f, 0.7f, 0.2f, 1);
        pixmap.fillRectangle(0, 0, 8, 8);
        pixmap.setColor(1f, 1f, 1f, 1);
        pixmap.drawRectangle(1, 1, 8, 8);

        return new TextureRegion(new Texture(pixmap));
    }

    private Texture drawBombTexture() {
        Pixmap pixmap = new Pixmap(40, 40, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 1, 0, 1);
        pixmap.fillCircle(20, 20, 20);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.drawCircle(0, 0, pixmap.getWidth() / 2);

        return new Texture(pixmap);
    }

    public void update(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            explode();
        }

        for (int i = 0; i < shivers.size; i++) {
            shivers.get(i).update(deltaTime);
            if (shivers.get(i).hits >= 10) {
                shivers.removeIndex(i);
            }
        }
    }

    private void explode() {
        for (int i = 0; i < shivers.size; i++) {
            shivers.get(i).isActive = true;
        }

    }

    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(texture_bomb,x,y);
        for (int i = 0; i < shivers.size; i++) {
            shivers.get(i).draw(batch);
        }
        batch.end();

    }

}
