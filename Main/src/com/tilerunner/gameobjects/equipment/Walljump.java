package com.tilerunner.gameobjects.equipment;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilerunner.gameobjects.player.Player;
import com.tilerunner.input.IGameInput;
import com.tilerunner.input.IGameInputController;

/**
 * User: Franjo
 * Date: 20.11.13
 * Time: 00:42
 * Project: TileRunner
 */
public class Walljump extends Equipment {

    private Player player;
    private final float vX_Max = 12000000;
    private final float aX_Max = 1.5f;

    public Walljump(Player player) {
        this.player = player;
    }

    @Override
    public void update(float deltaTime) {

        vX = 0;

        IGameInput input = player.getInputController();
        if (input != null && input.isA()) {

            if (player.hit_left) {
               vX = vX_Max;

                System.out.println("left walljump");
            } else if (player.hit_right) {
                vX = -vX_Max;
//                aX = -aX_Max;
                System.out.println("right walljump");

            }
        }

    }

    @Override
    public void render(SpriteBatch batch) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
