package com.tilerunner.gameobjects.decorations;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;

/**
 * User: Franjo
 * Date: 04.12.13
 * Time: 19:58
 * Project: TileRunner
 */
public abstract class Decoration {

    protected MapObject mapObject;

    public Decoration(MapObject mapObject) {
        this.mapObject = mapObject;
    }

    public Decoration(){

    }

    public abstract void update(float delta);

    public abstract void draw(SpriteBatch batch);

}
