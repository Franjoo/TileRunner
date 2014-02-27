package com.tilerunner.gameobjects.collectibles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tilerunner.gameobjects.player.Player;
import com.tilerunner.gameobjects.world.World;

/**
 * Created with IntelliJ IDEA.
 * User: Franjo
 * Date: 21.02.14
 * Time: 12:47
 */
public class Coin {

    // movement
    private final float friction;
    private final float acceleration;
    private float vx, vy;
    private Player target;

    // animation
    private float time;

    // constructor params
    private float x, y;
    private World world;
    private Animation animation;

    public Coin(float x, float y, World world, Animation animation) {
        this.x = x;
        this.y = y;
        this.world = world;
        this.animation = animation;

        acceleration = 130;
        friction = 0.95f;

    }

    public Coin(float x, float y, World world, Animation animation, float acceleration, float friction) {
        this.x = x;
        this.y = y;
        this.world = world;
        this.animation = animation;
        this.acceleration = acceleration;
        this.friction = friction;

    }

    public void update(float delta) {
        // update animation time
        time += delta;

        if (target != null && target.isWafting()) {
            target = null;
        }

        // has no target
        else if (target == null) {

            float dist = Float.MAX_VALUE;

            for (int i = 0; i < world.getPlayers().size; i++) {
                Player p = world.getPlayers().get(i);

                // root bone position of player
                final float px = p.getBone("root").getWorldX() + p.getX();
                final float py = p.getBone("root").getWorldY() + p.getY();

                // distance
                float x_d = px - x;
                float y_d = py - y;
                float distance = (float) Math.sqrt(x_d * x_d + y_d * y_d);

                // check distance
                if (distance <= dist && distance <= 80) {
                    dist = distance;
                    target = p;

                    // normalize
                    if (distance != 0) {
                        x_d /= distance;
                        y_d /= distance;
                    }

                    // accelerate to player
                    if (distance <= 80) {
                        target = p;

                        vx += x_d * acceleration * delta;
                        vy += y_d * acceleration * delta;

                        vx *= friction;
                        vy *= friction;

                    }
                }
            }

        }

        // has already target
        else {

            Player p = target;

            // root bone position of player
            final float px = p.getBone("root").getWorldX() + p.getX();
            final float py = p.getBone("root").getWorldY() + p.getY();

            // distance
            float x_d = px - x;
            float y_d = py - y;
            float distance = (float) Math.sqrt(x_d * x_d + y_d * y_d);

            // normalize
            if (distance != 0) {
                x_d /= distance;
                y_d /= distance;
            }

            // update velocity
            vx += x_d * acceleration * delta;
            vy += y_d * acceleration * delta;
            vx *= friction;
            vy *= friction;

            // update position
            x += vx;
            y += vy;
        }

    }


    public void draw(SpriteBatch batch) {
        // draw centered
        TextureRegion r = animation.getKeyFrame(time);
        batch.draw(r, x - r.getRegionWidth() / 2, y - r.getRegionHeight() / 2);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
