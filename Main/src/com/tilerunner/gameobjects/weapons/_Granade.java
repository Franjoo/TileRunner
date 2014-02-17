package com.tilerunner.gameobjects.weapons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.tilerunner.core.C;
import com.tilerunner.gameobjects.GameObject;
import com.tilerunner.gameobjects.world.Detector;
import com.tilerunner.util.Drawer;

/**
 * User: Franjo
 * Date: 19.11.13
 * Time: 11:57
 * Project: TileRunner
 */
public class _Granade extends ThrowableWeapon implements Pool.Poolable {

    private GameObject go;
    private final Detector detector;
    private static final Texture text_granade = Drawer.rectangleTexture(30, 30, new Color(1, 1, 1, 1));
    private static final Texture text_particle = Drawer.rectangleTexture(4, 4, new Color(1, 0, 0, 1));

    private Array<Vector2> particles;

    public boolean isExploded;
    public final float lifeTime = 3;
    public float time;
//    private float x, y;
//    private float dirX, dirY;
//    private float bulletSpeed;
//    private Texture texture;


    public _Granade(float x, float y, float vX, float vY) {
        this.x = x;
        this.y = y;
        this.vX = vX;
        this.vY = vY;

        // detector
        detector = Detector.getInstance();

        isExploded = false;

        particles = new Array<>();
//        // textures
//        text_granade = Drawer.rectangleTexture(30, 30, new Color(1, 1, 1, 1));
//        text_particle = Drawer.rectangleTexture(4, 4, new Color(1, 0, 0, 1));
    }



    @Override
    public void update(float deltaTime) {
        if (!isExploded) {
            vY += C.GRAVITY * deltaTime;

            x += vX;
            y += vY;

            if (detector.isSolid(x, y)) {
                isExploded = true;
                explode();
            }

        } else {

        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(text_granade,x,y);
        batch.end();
    }

    @Override
    public void toss(float xDir, float yDir) {

    }

    @Override
    public void applyTo(GameObject go) {
        this.go = go;
    }

    public void explode() {
        System.out.println("explode");
    }


    @Override
    public void reset() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
