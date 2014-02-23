package com.tilerunner.gameobjects.collectibles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private boolean follows;
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

//        time = (float) (Math.random() * animation.animationDuration);
    }

    public Coin(float x, float y, World world, Animation animation, float acceleration, float friction) {
        this.x = x;
        this.y = y;
        this.world = world;
        this.animation = animation;
        this.acceleration = acceleration;
        this.friction = friction;

//        time = (float) (Math.random() * animation.animationDuration);
    }

    public void update(float delta) {
        // update animation time
        time += delta;

        // distance to player
        if (target == null) {

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

                if (distance <= dist && distance  <= 160) {
                    dist = distance;
                    target = p;

                    // normalize
                    if (distance != 0) {
                        x_d /= distance;
                        y_d /= distance;
                    }

                    if (distance <= 160) {
                        target = p;

                        vx += x_d * acceleration * delta;
                        vy += y_d * acceleration * delta;

                        vx *= friction;
                        vy *= friction;

                    }
                }
            }
        } else {
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


            vx += x_d * acceleration * delta;
            vy += y_d * acceleration * delta;

            vx *= friction;
            vy *= friction;


        }

// update position
        x += vx;
        y += vy;

    }

    public void draw(SpriteBatch batch) {
        batch.draw(animation.getKeyFrame(time), x, y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
