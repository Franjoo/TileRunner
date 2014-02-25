package com.tilerunner.gameobjects.boxes;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import com.tilerunner.gameobjects.platforms.Platform;
import com.tilerunner.gameobjects.world.World;

/**
 * Created with IntelliJ IDEA.
 * User: Franjo
 * Date: 24.02.14
 * Time: 23:32
 */
public class Boxes {

    private World world;
    private Array<Box> boxes;

    // sound
    private Sound sound;

    public Boxes(TiledMap map, World world) {

        // initialize
        boxes = new Array<>();
        for (int i = 0; i < map.getLayers().getCount(); i++) {
            if (map.getLayers().get(i).getObjects().getCount() != 0
                    && map.getLayers().get(i).getProperties().containsKey("boxes")) {  // coin object layer

                MapLayer l = map.getLayers().get(i);
                MapObjects objects = l.getObjects();
                for (int j = 0; j < objects.getCount(); j++) {
                    MapProperties p = objects.get(j).getProperties();

                    String type = p.get("type").toString();

                    if (type.equals("box")) {
                        float x = Float.parseFloat(p.get("x").toString());
                        float y = Float.parseFloat(p.get("y").toString());
                        float w = Integer.parseInt(p.get("w").toString()) * World.TILESIZE;
                        float h = Integer.parseInt(p.get("h").toString()) * World.TILESIZE;

                        Box box = new Box(x, y, w, h);
                        boxes.add(box);
                    }
                }
            }
        }
    }


    public void update(float delta) {
        for (Box b : boxes) b.update(delta);
    }


    public void render(SpriteBatch batch) {
        batch.begin();
        for (Box b : boxes) b.draw(batch);
        batch.end();
    }


    public void renderBounds(SpriteBatch batch) {
        batch.begin();
        for (Box b : boxes) b.drawBounds(batch);
        batch.end();
    }

    public Array<Box> getBoxes() {
        return boxes;
    }
}
