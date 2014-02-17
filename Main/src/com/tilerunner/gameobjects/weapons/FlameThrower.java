package com.tilerunner.gameobjects.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.tilerunner.gameobjects.GameObject;
import com.tilerunner.gameobjects.world.World;

/**
 * User: Franjo
 * Date: 19.11.13
 * Time: 14:17
 * Project: TileRunner
 */
public class FlameThrower extends Weapon {

    private final String effectPath = "particleEffects/flameThrower.getPosition";
    private final String particlePath = "particleEffects/pooling/";

    private GameObject go;
    private World world;

    private ParticleEffect particleEffect;

    public FlameThrower(World world) {
        this.world = world;

        init();
    }

    private void init() {
        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal(effectPath), Gdx.files.internal(particlePath));
        particleEffect.start();
    }


    @Override
    public void shoot(float x, float y) {
        if (go != null) {
             Array<ParticleEmitter> e = particleEffect.getEmitters();
            for (int i = 0; i < e.size; i++) {

            }

        }
    }

    @Override
    public void applyTo(GameObject go) {
        this.go = go;
    }

    @Override
    public void render(SpriteBatch batch) {


        batch.begin();
        particleEffect.draw(batch);
        batch.end();


    }

    @Override
    public void update(float deltaTime) {
//        super.update(deltaTime);

//        particleEffect.setPosition(x,y);
//        particleEffect.setPosition(x - Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2 - y);
        particleEffect.setPosition(x - world.mapWidth/2, world.mapHeight/2 - y);
        particleEffect.update(deltaTime);
    }
}
