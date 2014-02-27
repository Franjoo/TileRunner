package com.tilerunner.gameobjects.crates;

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
 * Time: 23:32
 */
public class Crates {

    private World world;
    private Array<Crate> boxes;

    // sound
    private Sound sound;

    public Crates(TiledMap map, World world) {

        // initialize
        boxes = new Array<>();
        for (int i = 0; i < map.getLayers().getCount(); i++) {
            if (map.getLayers().get(i).getObjects().getCount() != 0
                    && map.getLayers().get(i).getProperties().containsKey("crates")) {  // coin object layer

                MapLayer l = map.getLayers().get(i);
                MapObjects objects = l.getObjects();
                for (int j = 0; j < objects.getCount(); j++) {
                    MapProperties p = objects.get(j).getProperties();

                    String type = p.get("type").toString();

                    if (type.equals("crate")) {
                        float x = Float.parseFloat(p.get("x").toString());
                        float y = Float.parseFloat(p.get("y").toString());
                        float w = Integer.parseInt(p.get("w").toString()) * World.TS;
                        float h = Integer.parseInt(p.get("h").toString()) * World.TS;

                        Crate crate = new Crate(x, y, w, h);
                        boxes.add(crate);
                    }
                }
            }
        }
    }


    public void update(float delta) {
        for (Crate b : boxes) b.update(delta);
    }


    public void render(SpriteBatch batch) {
        batch.begin();
        for (Crate b : boxes) b.draw(batch);
        batch.end();
    }


    public void renderBounds(SpriteBatch batch) {
        batch.begin();
        for (Crate b : boxes) b.drawBounds(batch);
        batch.end();
    }

    public Array<Crate> getBoxes() {
        return boxes;
    }
}
