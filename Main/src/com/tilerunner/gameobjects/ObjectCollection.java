package com.tilerunner.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import com.tilerunner.gameobjects.traps.Trap;
import com.tilerunner.gameobjects.world.World;

/**
 * Created with IntelliJ IDEA.
 * User: Franjo
 * Date: 24.02.14
 * Time: 16:17
 */
public class ObjectCollection<T> extends Array<ICollectionObject> {

    protected World world;

    public ObjectCollection(World world) {
        this.world = world;
    }

    public void update(float delta) {
        for (int i = 0; i < size; i++) {
            get(i).update(delta);
        }
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        for (int i = 0; i < size; i++) {
            get(i).draw(batch);
        }
        batch.end();
    }

    public void renderBounds(SpriteBatch batch) {
        batch.begin();
        for (int i = 0; i < size; i++) {
            get(i).drawBounds(batch);
        }
        batch.end();
    }


}
