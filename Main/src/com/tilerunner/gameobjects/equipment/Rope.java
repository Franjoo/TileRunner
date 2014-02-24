package com.tilerunner.gameobjects.equipment;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.tilerunner.core.C;
import com.tilerunner.gameobjects.player.IPlayable;
import com.tilerunner.gameobjects.player.Player;
import com.tilerunner.gameobjects.world.Detector;
import com.tilerunner.input.IGameInput;

/**
 * User: Franjo
 * Date: 23.11.13
 * Time: 16:47
 * Project: TileRunner
 */
public class Rope extends Equipment {

    private IPlayable player;
    private TextureRegion region;

    private Texture texture_point;

    private Vector2 top;
    private boolean isAttached;
    private boolean isShooting;
    private float length;
    private float rotation;
    private float speed;

    private float h;

    private Vector2 _d = new Vector2(0, 1);
    private Vector2 distance = new Vector2();

    private float p_vX;
    private float p_vY;

    private Vector2 direction;
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 origin;
    private Vector2 anchor;

    private Detector detector;
    IGameInput input;

    public Rope(Player player) {
        this.player = player;

        detector = Detector.getInstance();

        top = new Vector2();
        direction = new Vector2();
        position = new Vector2();
        velocity = new Vector2();
        origin = new Vector2();
        anchor = new Vector2();

        speed = 12000;

        drawRope();
        drawPoint();
    }

    private void drawPoint() {
        Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 0, 0, 1);
        pixmap.fillCircle(0, 0, pixmap.getWidth());

        texture_point = new Texture(pixmap);
    }

    private void drawRope() {
        Pixmap pixmap = new Pixmap(6, 100, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());

        region = new TextureRegion(new Texture(pixmap));
    }

    @Override
    public void update(float deltaTime) {

        input = player.getInputController();

        if (input != null && input.isB() && !isShooting) {
            isShooting = true;
            isAttached = false;
            direction.set(1, 0);

            if (player.getVx() == 0) {
                direction.setAngle(90);
            } else {
                direction.setAngle(player.getVx() / Math.abs(player.getVx()) * -45 + 90);
            }
            direction.nor();

            vX = direction.x * speed;
            vY = direction.y * speed;
//            direction.set(vX, vY).nor();
            position.set(player.getX(), player.getY());
//            findAnchor();
        }

        if (input != null && input.isX()) {
            isShooting = false;
            isAttached = false;
            p_vY = 0;
            p_vX = 0;
            length = 0;
        }

        if (isAttached) {
            p_vX = -player.getVx();
            p_vY = -player.getVx();
        }

        updateRope(deltaTime);
    }

    private void updateRope(float deltaTime) {

        if (isAttached || isShooting) {

            if (!detector.isSolid(position.x + 8, position.y + 8)) {
                position.x += vX * deltaTime;
                position.y += vY * deltaTime;
            }

            // hits solid
            else {
                isShooting = false;
                isAttached = true;
            }
        }


        updatePlayerAttachment(deltaTime);

        origin.set(player.getX(), player.getY());
//        origin.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2);


        direction.set(position.x - origin.x, position.y - origin.y);
        rotation = direction.angle() - 90;
        length = direction.len();


//        System.out.println("rot: " + rotation);

//        System.out.println("height: " + h);

    }

    private void findAnchor() {
        int i = 0;
        while (true) {
            if (detector.isSolid(position.x + direction.x * i * 64, position.y + direction.y * i * 64)) {
                position.x += direction.x * i * 64;
                position.y += direction.y * i * 64;
                break;
            }
        }
    }

    private void updatePlayerAttachment(float deltaTime) {
//        h = position.y - player.getY() - player.getHeight();
        float v = (float) Math.sqrt(2 * -C.GRAVITY * h);

//        float vX =

//        if (isAttached) {
//            System.out.println(v);
//            p_vY = -player.get_vY();
//        }


        if (isAttached || isShooting) {

            if (input != null) {

                _d.setAngle(rotation + 90);
                System.out.println(_d);
//                p_vX = -player.get_vX();
//                p_vY = -player.get_vY();
                velocity.x += _d.x * v * deltaTime;
                velocity.y += -_d.y * v * deltaTime;


                distance.set(position.x - player.getX() + velocity.x, position.y - player.getY() + velocity.y);
                float difX = distance.len() - length;
                System.out.println(difX);

                p_vX = velocity.x;
//                p_vY = velocity.y;

//                player.vX = _d.x * v * deltaTime;
//                player.vY = -C.GRAVITY * deltaTime + (_d.y * C.GRAVITY * v * deltaTime);
//                player.vX = 2;
//                p_vY = -player.get_vY() + _d.x * v * deltaTime;
//                p_vX = -player.get_vX() - _d.y * v * deltaTime;

//                float stick_left = input.get_left_stickY();
//
//                if (isAttached && Math.abs(stick_left) >= 0.4) {
//                    float _vy = stick_left * 5;
//                    p_vY = -player.get_vY() + _vy;
//                }
            }

//            _d.setAngle(rotation + 90);
//            System.out.println(_d);

//            System.out.println(v);
        }
    }


    @Override
    public void render(SpriteBatch batch) {
        if (isShooting || isAttached) {
            batch.begin();
            batch.draw(region, origin.x, origin.y,
                    0, 0,
                    region.getRegionWidth(), region.getRegionHeight(),
                    1, length / region.getRegionHeight(), rotation, true);
            batch.end();

            batch.begin();
            batch.draw(texture_point, position.x, position.y);
            batch.end();
        }
    }


    @Override
    public float get_aX() {
        return 0;
    }

    @Override
    public float get_aY() {
        return 0;
    }

    @Override
    public float getvX() {
        return p_vX;
    }

    @Override
    public float getvY() {
        return p_vY;
    }

    @Override
    public float get_frictionX() {
        return 0;
    }

    @Override
    public float get_frictionY() {
        return 0;
    }
}
