package com.tilerunner.gameobjects.collectibles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import com.tilerunner.gameobjects.player.Player;
import com.tilerunner.gameobjects.world.World;

/**
 * Created with IntelliJ IDEA.
 * User: Franjo
 * Date: 21.02.14
 * Time: 12:43
 */
public class Coins {

    private World world;
    private Array<Coin> coins;

    // sound
    private Sound sound;

    public Coins(TiledMap map, World world) {
        this.world = world;

        // coin color atlases
        final TextureAtlas atlas_white = new TextureAtlas(Gdx.files.internal("coins/coins_white.txt"));
        final TextureAtlas atlas_yellow = new TextureAtlas(Gdx.files.internal("coins/coins_yellow.txt"));
        final TextureAtlas atlas_red = new TextureAtlas(Gdx.files.internal("coins/coins_red.txt"));
        final TextureAtlas atlas_blue = new TextureAtlas(Gdx.files.internal("coins/coins_blue.txt"));

        // animations
        final Animation animation_white = new Animation(0.08f, atlas_white.getRegions(),Animation.LOOP);
        final Animation animation_yellow = new Animation(0.1f, atlas_yellow.getRegions(),Animation.LOOP);
        final Animation animation_red = new Animation(0.12f, atlas_red.getRegions(),Animation.LOOP);
        final Animation animation_blue = new Animation(0.15f, atlas_blue.getRegions(),Animation.LOOP);

        // create sounds
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/coin.wav"));

        // find coins
        coins = new Array<>();
        for (int i = 0; i < map.getLayers().getCount(); i++) {
            if (map.getLayers().get(i).getObjects().getCount() != 0
                    && map.getLayers().get(i).getProperties().containsKey("coins")) {  // coin object layer

                MapLayer l = map.getLayers().get(i);
                MapObjects objects = l.getObjects();
                for (int j = 0; j < objects.getCount(); j++) {
                    MapProperties p = objects.get(j).getProperties();

                    String type = p.get("type").toString();
                    float x = Float.parseFloat(p.get("x").toString());
                    float y = Float.parseFloat(p.get("y").toString());

                    if (type.equals("white")) coins.add(new Coin(x, y, world, animation_white,131,0.95f));
                    else if (type.equals("yellow")) coins.add(new Coin(x, y, world, animation_yellow,131,0.95f));
                    else if (type.equals("red")) coins.add(new Coin(x, y, world, animation_red,128,0.95f));
                    else if (type.equals("blue")) coins.add(new Coin(x, y, world, animation_blue,125,0.94f));


                }


            }
        }


    }

    public void update(float delta) {
        for (int i = 0; i < coins.size; i++) {
            // update
            Coin coin = coins.get(i);
            coin.update(delta);

            // collision
            for (int j = 0; j < world.getPlayers().size; j++) {
                Player p = world.getPlayers().get(j);
                if (p.getSkeletonBounds().aabbContainsPoint(coin.getX(), coin.getY())) {
                    sound.play();
                    coins.removeValue(coin,true);
                }
            }
        }
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        for (Coin coin : coins) coin.draw(batch);
        batch.end();
    }

}
