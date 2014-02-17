package com.tilerunner.gameobjects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tilerunner.gameobjects.GameObject;
import com.tilerunner.gameobjects.equipment.Jetpack;
import com.tilerunner.gameobjects.weapons.GrenadeLauncher;
import com.tilerunner.gameobjects.weapons.MachineGun;
import com.tilerunner.gameobjects.world.Detector;
import com.tilerunner.core.C;
import com.tilerunner.gameobjects.equipment.Equipment;
import com.tilerunner.gameobjects.weapons.Weapon;
import com.tilerunner.gameobjects.world.World;
import com.tilerunner.input.IGameInputController;

/**
 * User: Franjo
 * Date: 11.11.13
 * Time: 13:25
 * Project: TileRunner
 */
public class Player extends GameObject {

    private final int id;


    // collision
    private Detector detector;
    public boolean hit_top;
    public boolean hit_bottom;
    public boolean hit_left;
    public boolean hit_right;

    // sounds
    private Sound sndJump;


    private final float aJ = 250;

    // equipment
    private Equipment eq;


    private Texture playerTexture;
    private Texture crosshairTexture;

    private Weapon weapon;
    private Vector2 dir_enemy;
    private Vector2 dir_stick;
    private float range = 1200;
    private float aim_tolerance = 0.02f;


    private IGameInputController input;
    private World world;
    private boolean isFixed;
    private boolean wasR1Pressed;

    public Player(World world, int id) {
        this.world = world;
        this.id = id;

        init();
    }

    private void init() {

        // position
        this.x = 1280;
        this.y = 640;
//        this.x = 192;
//        this.y = 4608;
        // dimension
//        this.width = 100;
//        this.height = 150;
        this.width = 64;
        this.height = 64;
        // velocity
        this.vX = 0;
        this.vY = 0;
        // acceleration
        this.aX = 75f;
        this.aY = 0;
        // friction
        this.frictionX = 0.94f;
        this.frictionY = 0;
        // limits
        this.vX_Max = 12;
        this.vX_Min = -12;
        this.vY_Max = 40;
        this.vY_Min = -40;


        // draw rectangular shape
        Pixmap pixmap = new Pixmap((int) (width), (int) (height), Pixmap.Format.RGBA8888);
        playerTexture = new Texture(pixmap.getWidth(), pixmap.getHeight(), Pixmap.Format.RGBA8888);

        // player playerTexture
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fillRectangle(0, 0, (int) width, (int) height);
        pixmap.setColor(1, 1, 0, 1);
        pixmap.drawLine((int) x, (int) y, (int) x + 20, (int) y);
        pixmap.drawLine((int) x, (int) y, (int) (x), (int) height + 20);
        playerTexture.draw(pixmap, 0, 0);

        // crosshair
        Pixmap pCrosshair = new Pixmap(8, 8, Pixmap.Format.RGBA8888);
        crosshairTexture = new Texture(pCrosshair.getWidth(), pCrosshair.getHeight(), Pixmap.Format.RGBA8888);
        pCrosshair.setColor(1, 0, 0, 1);
        pCrosshair.fillCircle(4, 4, 4);
        crosshairTexture.draw(pCrosshair, 0, 0);

        Rectangle r = new Rectangle();


        detector = Detector.getInstance();

        // weapon
        weapon = new MachineGun(world);
//        weapon = new GrenadeLauncher(world);
//        weapon = new FlameThrower(world);
        weapon.applyTo(this);
        dir_enemy = new Vector2();
        dir_stick = new Vector2();

        // equipment
        eq = new Jetpack(this);
//        eq = new Rope(this);
//        eq = new Walljump(this);


        createSounds();

    }

    private void createSounds() {
        sndJump = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.wav"));

    }


    @Override
    public void update(float delta) {



        if(wasR1Pressed && !input.is_R1_pressed()){
            isFixed = !isFixed;
        }

//        vX *= frictionX;

        if (!isFixed) {
            vX += input.get_left_stickX() * aX * delta;
        }

        vX *= frictionX;
//        vY += aY *delta;


//        vX += input.get_left_stickX() * vX_Max * delta;
        vY += C.GRAVITY * delta;


        // jump
        if (hit_bottom && input.get_isA()) {
//            sndJump.loop();
            sndJump.play();
            vY += aJ;
        }


        // equipment
        eq.update(delta);
//        aX *= eq.get_aX();
//        aY *= eq.get_aY();
        vX += eq.getvX();
        vY += eq.getvY();


        // weapon
        updateWeapon(delta);


        if (vY > vY_Max) vY = vY_Max;
        else if (vY < vY_Min) vY = vY_Min;
        if (vX > vX_Max) vX = vX_Max;
        else if (vX < vX_Min) vX = vX_Min;
        if (Math.abs(vX) < 0.02) vX = 0;


//        System.out.println(vY);

        x += vX;
        y += vY;

        setCollisionPosition();

        wasR1Pressed = input.is_R1_pressed();
    }

    private void updateWeapon(float delta) {
//        // helper variables
//        int nearest = Integer.MAX_VALUE;
//        double cos_min = Double.MAX_VALUE;
//        Enemy e;
//        float dist;
//        double cos;
//
        // set stick vector
        dir_stick.set(input.get_left_stickX(), input.get_left_stickY());
        if (input.get_isX()) {
            weapon.shoot(dir_stick.x, dir_stick.y);
        }
        weapon.update(delta);


//        dir_stick.nor();
//
//        Array<Enemy> enemies = world.getEnemies();
//
//        if (enemies.size != 0) {
//
//            // find nearest to aiming direction
//            for (int i = 0; i < world.getEnemies().size; i++) {
//                e = world.getEnemies().get(i);
//                dir_enemy.set(e.getX() - x, e.getY() - y);
//                dir_enemy.nor();
//
//                cos = dir_enemy.dot(dir_stick);
//                if (Math.abs(cos - 1) < cos_min) {
//                    cos_min = Math.abs(cos - 1);
//                    nearest = i;
//                }
//
//            }
//
//            // get nearest enemy
//            e = world.getEnemies().get(nearest);
//            dir_enemy.set(e.getX() - x, e.getY() - y);
//            dist = dir_enemy.len();
//            dir_enemy.nor();
//
//            cos = (dir_enemy.dot(dir_stick));
//
//            // apply auto aiming
//            if (input.get_trigger_right() != 0) {
//                if (dist <= range && cos > 1 - aim_tolerance && cos < 1 + aim_tolerance)
//                    weapon.shoot(dir_enemy.x, dir_enemy.y);
//                else weapon.shoot(dir_stick.x, dir_stick.y);
//            }
//        } else {
//            if (input.get_trigger_right() != 0) {
//                weapon.shoot(dir_stick.x, dir_stick.y);
//            }
//        }

        // update weapon

    }

    private void setCollisionPosition() {

        hit_left = false;
        hit_right = false;
        hit_top = false;
        hit_bottom = false;

        // left
        if (vX < 0) {
            if (detector.isSolid(x, y - vY) || detector.isSolid(x, y + height - vY)) {
                x = (int) (x / world.tileWidth) * world.tileWidth + world.tileWidth + C.EPSILON;
                hit_left = true;
                vX = 0;
            }
        }
        // right
        else if (vX > 0) {
            if (detector.isSolid(x + width, y - vY) || detector.isSolid(x + width, y + height - vY)) {
                x = (int) (x / world.tileWidth) * world.tileWidth - C.EPSILON;
                hit_right = true;
                vX = 0;
            }
        }

        // bottom
        if (vY < 0) {
            if (detector.isSolid(x - vX, y) || detector.isSolid(x + width - vX, y)) {
                y = (int) (y / world.tileHeight) * world.tileHeight + world.tileHeight + C.EPSILON;
                hit_bottom = true;
                vY = 0;
            }
        }
        // top
        else if (vY > 0) {
            if (detector.isSolid(x - vX, y + height) || detector.isSolid(x + width - vX, y + height)) {
                y = (int) (y / world.tileHeight) * world.tileHeight - C.EPSILON;
                hit_top = true;
                vY = 0;
            }
        }

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(playerTexture, x, y);
        if (dir_stick.len() >= 0.5) {
            batch.draw(crosshairTexture, x + width / 2 + dir_stick.x * 150, y + height / 2 + dir_stick.y * 150);

        }


//        if (Math.abs(input.get_left_stickX()) >= 0.5f && Math.abs(input.get_left_stickY()) >= 0.5f) {
//            batch.draw(crosshairTexture, x + width / 2 + input.get_left_stickX() * 75, y + height / 2 + input.get_left_stickY() * 75);
//        }
        batch.end();


        // weapon
        weapon.render(batch);

        // equipment
        eq.render(batch);
    }

    public void setInputController(IGameInputController input) {
        this.input = input;
    }

    public IGameInputController getInputController() {
        return input;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
