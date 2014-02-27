package com.tilerunner.gameobjects.platforms;

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
 * Time: 16:16
 */
public class Platforms {

    private World world;
    private Array<Platform> platforms;

    // sound
    private Sound sound;

    public Platforms(TiledMap map, World world) {

        // initialize
        platforms = new Array<>();
        for (int i = 0; i < map.getLayers().getCount(); i++) {
            if (map.getLayers().get(i).getObjects().getCount() != 0
                    && map.getLayers().get(i).getProperties().containsKey("platforms")) {  // coin object layer

                MapLayer l = map.getLayers().get(i);
                MapObjects objects = l.getObjects();
                for (int j = 0; j < objects.getCount(); j++) {
                    MapProperties p = objects.get(j).getProperties();

                    String type = p.get("type").toString();

                    if (type.equals("platform")) {
                        float x1 = Integer.parseInt(p.get("x").toString());
                        float y1 = Integer.parseInt(p.get("y").toString());
                        float x2 = x1 + Integer.parseInt(p.get("p").toString().split(" ")[0]) * World.TS;
                        float y2 = y1 + Integer.parseInt(p.get("p").toString().split(" ")[1]) * World.TS;

                        float w = Integer.parseInt(p.get("w").toString()) * World.TS;
                        float h = Integer.parseInt(p.get("h").toString()) * World.TS;
                        float v = Integer.parseInt(p.get("v").toString()) * World.TS; // pixel per second

                        Platform platform = new Platform(x1, y1, x2, y2, w, h,v);
                        platforms.add(platform);

                    }
                }
            }
        }
    }


    public void update(float delta) {
        for (Platform p : platforms) p.update(delta);
    }


    public void render(SpriteBatch batch) {
        batch.begin();
        for (Platform p : platforms) p.draw(batch);
        batch.end();
    }


    public void renderBounds(SpriteBatch batch) {
        batch.begin();
        for (Platform p : platforms) p.drawBounds(batch);
        batch.end();
    }

    public Array<Platform> getPlatforms() {
        return platforms;
    }
}
