package com.tilerunner.gameobjects.equipment;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.tilerunner.gameobjects.player.IPlayable;
import com.tilerunner.gameobjects.player.Player;
import com.tilerunner.gameobjects.world.Detector;
import com.tilerunner.input.IGameInput;
import test.particlesystem.TintedParticle;

/**
 * User: Franjo
 * Date: 15.11.13
 * Time: 18:18
 * Project: TileRunner
 */
public class Jetpack extends Equipment {

    private final IPlayable player;
    private final IGameInput input;

    // fire effect
    private final float ppu = 10;
    private Detector detector;
    private Pool<TintedParticle> firePool;
    private Pool<TintedParticle> smokePool;
    private Array<TintedParticle> fireParticles;
    private Array<TintedParticle> smokeParticles;

    private final float vY_MAX = 8f;

    public Jetpack(Player player) {
        this.player = player;
        input = player.getInputController();

        init();
    }

    private void init() {
        aY = 12f;
        aX = 0;

        detector = Detector.getInstance();
        fireParticles = new Array<>();
        smokeParticles = new Array<>();
        firePool = new Pool<TintedParticle>() {
            @Override
            protected TintedParticle newObject() {
                float x = player.getX();
                float y = player.getY();

                float lifeTime = (float) (0.3 + Math.random() * 1.2f);
//                float vX = player.vX;
//                float vY = player.vY;

                TintedParticle tp = new TintedParticle(vX, vY, 1, 1);
                tp.lifeTime = lifeTime;
                tp.setPosition(player.getX(), player.getY());

                // color
                float r = 1;
                float g = (float) (Math.random() * 0.2);
                float b = (float) (Math.random() * 0.2);
                float a = (float) (0.4f + Math.random() * 0.3f);

                tp.setColor(r, g, b, a);

                return tp;
            }
        };
    }


    @Override
    public void update(float deltaTime) {

        IGameInput i = player.getInputController();

//        if (i != null && i.get_trigger_left() != 0) {
        if (i != null && i.isB()) {
            vY += aY * deltaTime;

            // emmit particles
            for (int j = 0; j < ppu; j++) {
                TintedParticle tp = firePool.obtain();


                tp.vX = (float) (-600 + Math.random() * 1200);
                tp.vY = (float) (-1200 - Math.random() * 1200);
//                tp.setPosition(player.getX(), player.getY());

                tp.setBounds(player.getX(), player.getY(), 32,32);
                tp.active = true;
                fireParticles.add(tp);
            }

//            if (player.getVy() > vY_MAX) player.setvYvY_MAX;
        } else vY = 0;

        for (int j = 0; j < fireParticles.size; j++) {
            TintedParticle tp = fireParticles.get(j);
            tp.update(deltaTime);

            if (!tp.active) {
                fireParticles.removeIndex(j);
                firePool.free(tp);
            }


        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        batch.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
        for (int j = 0; j < fireParticles.size; j++) {
            fireParticles.get(j).draw(batch);
        }
        batch.end();
    }
}
