package com.tilerunner.gameobjects.traps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.esotericsoftware.spine.SkeletonBounds;
import com.tilerunner.gameobjects.collectibles.Coin;
import com.tilerunner.gameobjects.player.Player;
import com.tilerunner.gameobjects.world.World;

/**
 * Created with IntelliJ IDEA.
 * User: Franjo
 * Date: 23.02.14
 * Time: 23:22
 */
public class Traps {

    private World world;
    private Array<Trap> traps;

    // sound
    private Sound sound;

    public Traps(TiledMap map, World world) {
        this.world = world;

        // find traps
        traps = new Array<>();
        for (int i = 0; i < map.getLayers().getCount(); i++) {
            if (map.getLayers().get(i).getObjects().getCount() != 0
                    && map.getLayers().get(i).getProperties().containsKey("traps")) {  // coin object layer

                MapLayer l = map.getLayers().get(i);
                MapObjects objects = l.getObjects();
                for (int j = 0; j < objects.getCount(); j++) {
                    MapProperties p = objects.get(j).getProperties();

                    String type = p.get("type").toString();

                    if (type.equals("saw")) {
                        float x = Integer.parseInt(p.get("x").toString());
                        float y = Integer.parseInt(p.get("y").toString());
                        float w = Integer.parseInt(p.get("w").toString()) * World.TILESIZE;
                        float h = Integer.parseInt(p.get("h").toString()) * World.TILESIZE;
//                        float vr = Float.parseFloat(p.get("vr").toString());

                        Saw saw = new Saw(x, y, w, h, 180, new TextureRegion(new Texture(Gdx.files.internal("saw.png"))));
                        traps.add(saw);


                    }
                }
            }
        }


    }

    public void update(float delta) {
        for (Trap trap : traps) trap.update(delta);
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        for (Trap trap : traps) {
            trap.draw(batch);
        }
        batch.end();
    }

    public void renderBounds(SpriteBatch batch) {
        batch.begin();
        for (Trap trap : traps) trap.drawBounds(batch);
        batch.end();


    }

    public Array<Trap> getTraps() {
        return traps;
    }
}
