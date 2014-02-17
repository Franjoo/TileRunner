package com.tilerunner.gameobjects.enemies;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tilerunner.gameobjects.GameObject;
import com.tilerunner.gameobjects.player.Player;
import com.tilerunner.gameobjects.world.World;
import com.tilerunner.gameobjects.weapons.Weapon;

/**
 * User: Franjo
 * Date: 17.11.13
 * Time: 16:18
 * Project: TileRunner
 */
public class Enemy extends GameObject {

    private Texture t_shape;

    private World world;
    private Weapon weapon;
    private float range;
    private Array<Player> players;

    private Vector2 helperV2;

    public Enemy(World world, float x, float y, Weapon weapon, float range) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.weapon = weapon;
        this.range = range;

        this.range = 1300;

        weapon.applyTo(this);

        drawEnemieShape();

        helperV2 = new Vector2();

    }

    private void drawEnemieShape() {
        Pixmap pixmap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 0, 0, 1);
        pixmap.fillRectangle((int) x, (int) y, (int) x,(int) y);

        t_shape = new Texture(pixmap);

    }

    public void setPlayers(Array<Player> players) {
        this.players = players;
    }


    @Override
    public void update(float delta) {

        weapon.update(delta);


        float d_min = Integer.MAX_VALUE;
        int nearest = -1;

        for (int i = 0; i < players.size; i++) {
            helperV2.set(players.get(i).getX() - x, players.get(i).getY() - y);
            if (helperV2.len() < d_min && helperV2.len() <= range) {
                d_min = helperV2.len();
                nearest = i;
            }
        }

        if (nearest != -1) {
            helperV2.set(players.get(nearest).getX() - x, players.get(nearest).getY() - y);
            helperV2.nor();
            weapon.shoot(helperV2.x, helperV2.y);
        }
    }

    @Override
    public void render(SpriteBatch batch) {

        batch.begin();
        batch.draw(t_shape, 0, 0);
        batch.end();

        weapon.render(batch);
    }
}
