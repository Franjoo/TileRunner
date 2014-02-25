package com.tilerunner.gameobjects.checkpoints;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import com.tilerunner.gameobjects.world.World;

/**
 * Created with IntelliJ IDEA.
 * User: Franjo
 * Date: 24.02.14
 * Time: 12:36
 */
public class Checkpoints {

    private World world;
    private Array<Checkpoint> checkpoints;

    // sound
    private Sound sound;

    public Checkpoints(TiledMap map, World world) {
        this.world = world;

        // find traps
        checkpoints = new Array<>();
        for (int i = 0; i < map.getLayers().getCount(); i++) {
            if (map.getLayers().get(i).getObjects().getCount() != 0
                    && map.getLayers().get(i).getProperties().containsKey("checkpoints")) {  // checkpoints object layer

                MapLayer l = map.getLayers().get(i);
                MapObjects objects = l.getObjects();
                for (int j = 0; j < objects.getCount(); j++) {
                    MapProperties p = objects.get(j).getProperties();

                    String type = p.get("type").toString();

                    if (type.equals("checkpoint")) {
                        float x = Integer.parseInt(p.get("x").toString());
                        float y = Integer.parseInt(p.get("y").toString());
                        float w = Integer.parseInt(p.get("w").toString()) * World.TILESIZE;
                        float h = Integer.parseInt(p.get("h").toString()) * World.TILESIZE;

                        Checkpoint c = new Checkpoint(x, y, w, h);
                        checkpoints.add(c);

                    }
                }
            }
        }
    }


    public void update(float delta) {
        for (Checkpoint c : checkpoints) c.update(delta);
    }


    public void render(SpriteBatch batch) {
        batch.begin();
        for (Checkpoint c : checkpoints) c.draw(batch);
        batch.end();
    }


    public void renderBounds(SpriteBatch batch) {
        batch.begin();
        for (Checkpoint c : checkpoints) c.drawBounds(batch);
        batch.end();
    }

    public Array<Checkpoint> getCheckpoints() {
        return checkpoints;
    }
}
