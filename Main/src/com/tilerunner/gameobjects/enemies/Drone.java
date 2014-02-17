package com.tilerunner.gameobjects.enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tilerunner.gameobjects.player.Player;
import com.tilerunner.gameobjects.weapons.MachineGun;
import com.tilerunner.gameobjects.weapons.Weapon;
import com.tilerunner.gameobjects.world.Detector;
import com.tilerunner.gameobjects.world.World;
import com.tilerunner.pathfinding.Path;
import com.tilerunner.pathfinding.Pathfinder;
import com.tilerunner.util.Drawer;

/**
 * User: Franjo
 * Date: 05.12.13
 * Time: 11:05
 * Project: TileRunner
 */
public class Drone implements IEnemy {

    // appearance
    private Texture tex_drone;

    // movement
    private float x, y;
    private float width, height;
    private float vMax;
    private Vector2 direction;

    private Weapon weapon;
    private Detector detector;
    private Pathfinder pathfinder;
    private World world;

    private Array<Player> players;

    public Drone(float x, float y, World world) {
        this.x = x;
        this.y = y;
        this.world = world;

        weapon = new MachineGun(world);
        detector = Detector.getInstance();
        pathfinder = Pathfinder.getInstance();
        players = world.getPlayers();


        tex_drone = Drawer.rectangleTexture(50, 50, new Color(1, 1, 1, 1));

        // test pathfinder
        Player p = players.get(0);
        pathfinder.findPath((int) x / 64, (int) y / 64, (int) p.getX() / 64, (int) p.getY() / 64);

    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void update(float delta) {
//        Player p = players.get(0);
//        Path path = pathfinder.findPath((int) x / 64, (int) y / 64, (int) p.getX() / 64, (int) p.getY() / 64);
//
//        Vector2 next = path.getNext(x, y, 120 * delta);
//        x = next.x;
//        y = next.y;

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(tex_drone, x, y);
        batch.end();
    }


}
