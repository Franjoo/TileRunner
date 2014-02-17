package com.tilerunner.gameobjects.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilerunner.gameobjects.GameObject;
import com.tilerunner.gameobjects.world.World;

/**
 * User: Franjo
 * Date: 13.11.13
 * Time: 16:45
 * Project: TileRunner
 */
public class MachineGun extends Weapon {

    private Texture texture;
    private Sound sound;
    private GameObject go;
    private World world;

    private final float bulletSpeed = 2000;
    private final float variance = 0.04f;
    private final int firerate = 9;
    private float lastBulletTime;
    private float time;

    public MachineGun(World world) {
        this.world = world;
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.wav"));
    }


    public void shoot(float dirX, float dirY) {
        if (go != null && time - lastBulletTime > (float) (1) / firerate) {
            sound.play();
            world.spawnBullet(go.getX() + go.getWidth() / 2, go.getY() + go.getHeight() / 2, dirX, dirY, variance, bulletSpeed);
            lastBulletTime = time;
        }
    }

    @Override
    public void applyTo(GameObject go) {
        this.go = go;
    }

    @Override
    public void render(SpriteBatch batch) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(float deltaTime) {
//        super.update(deltaTime);
//        x = go.getX();
//        y = go.getY();

        time += Gdx.graphics.getDeltaTime();
    }
}
