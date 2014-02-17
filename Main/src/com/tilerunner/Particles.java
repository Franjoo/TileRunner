package com.tilerunner;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/**
 * User: Franjo
 * Date: 29.11.13
 * Time: 13:49
 * Project: TileRunner
 */
public class Particles {

    private Array<Pool> pools;

    public void addPool(Pool pool){
         pools.add(pool);
    }

    public void removePool(Pool pool){
        pools.removeValue(pool,true);
    }

    public void update(float delta){
        for (int i = 0; i < pools.size; i++) {
//            for (int j = 0; j < pools.get(i).; j++) {

//            }
        }
    }

    public void render(SpriteBatch batch){

    }



}
