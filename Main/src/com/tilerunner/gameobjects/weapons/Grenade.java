package com.tilerunner.gameobjects.weapons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.tilerunner.gameobjects.GameObject;
import com.tilerunner.gameobjects.world.Detector;
import com.tilerunner.gameobjects.world.World;
import com.tilerunner.util.Drawer;

/**
 * User: Franjo
 * Date: 01.12.13
 * Time: 18:06
 * Project: TileRunner
 */
public class Grenade extends ThrowableWeapon {

    private final TextureRegion texture_shiver;
    private final TextureRegion texture_afterglow;
    private Detector detector;
    private Texture texture;

    private float lifeTime = 3;
    private float time;
    public boolean isExploded;

    private float x, y;
    private float vx, vy;
    private World world;


    private Vector2 _v2;
    private GameObject go;

    public Grenade(float x, float y, float vx, float vy, World world, GameObject go) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.world = world;
        this.go = go;

        isExploded = false;

        texture = Drawer.rectangleTexture(6, 6, new Color(1, 1, 1, 1));

        detector = Detector.getInstance();

        _v2 = new Vector2();


        texture_shiver = drawShiverTexture();
        texture_afterglow = drawAfterGlowTexture();

    }


    public void update(float delta) {
        time += delta;

        if (time < lifeTime) {
            float gravity = -100;
            float friction = 0.6f;
            vy += gravity;

            if (detector.isSolid(x + vx * delta, y)) {
                vx *= -1 * friction;
            }

            if (detector.isSolid(x, y + vy * delta)) {
                vy *= -1 * friction;
            }

            x += vx * delta;
            y += vy * delta;
        } else if (!isExploded) {
            explode();
            isExploded = true;
        } else {

        }

    }

    private void explode() {
        final float speed = 120;
        _v2.set(1, 0).scl(vx, vy);

        for (int i = 0; i < 360; i++) {
            _v2.rotate(_v2.angle() + 1);
            world.spawnShiver(x, y,
                    _v2.x + speed, _v2.y + speed,
                    texture_shiver, texture_afterglow);
//            );
        }

//        world.spawnShiver()

    }

    private TextureRegion drawShiverTexture() {
        Pixmap pixmap = new Pixmap(8, 8, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.6f, 0.7f, 0.2f, 1);
        pixmap.fillRectangle(0, 0, 8, 8);
        pixmap.setColor(1f, 1f, 1f, 1);
        pixmap.drawRectangle(1, 1, 8, 8);

        return new TextureRegion(new Texture(pixmap));
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

    public void render(SpriteBatch batch) {
        if (!isExploded) {
            batch.begin();
            batch.draw(texture, x, y);
            batch.end();
        }
    }

    @Override
    public void toss(float xDir, float yDir) {


    }

    public void applyTo(GameObject go) {
        this.go = go;
    }

}
