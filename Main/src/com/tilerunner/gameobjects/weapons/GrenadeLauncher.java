package com.tilerunner.gameobjects.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.tilerunner.gameobjects.GameObject;
import com.tilerunner.gameobjects.world.World;

/**
 * User: Franjo
 * Date: 27.11.13
 * Time: 16:25
 * Project: TileRunner
 */
public class GrenadeLauncher extends Weapon {

    private boolean isShooting;
    private boolean wasShooting;
    private boolean hasFired;

    private GameObject go;
    private float speed;
    private Array<Grenade> grenades;

    private World world;

    public GrenadeLauncher(World world) {
        this.world = world;


        grenades = new Array<>();
    }


    @Override
    public void shoot(float dirX, float dirY) {
        isShooting = true;
//        if (isShooting) {
//            speed += 0.1f;
//        }

        speed = 3000;
        grenades.add(new Grenade(go.getX(), go.getY(),
                dirX * speed, dirY * speed, world, go));

        wasShooting = isShooting;


        hasFired = !isShooting && wasShooting;
    }

    @Override
    public void applyTo(GameObject go) {
        this.go = go;
    }

    @Override
    public void update(float deltaTime) {
        if (hasFired) {

        }

        for (int i = 0; i < grenades.size; i++) {
            grenades.get(i).update(deltaTime);
            if (grenades.get(i).isExploded) {

            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        for (int i = 0; i < grenades.size; i++) {
            grenades.get(i).render(batch);
        }
    }
}
