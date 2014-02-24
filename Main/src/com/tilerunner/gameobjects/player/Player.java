package com.tilerunner.gameobjects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.SkeletonBounds;
import com.tilerunner.gameobjects.checkpoints.Checkpoint;
import com.tilerunner.gameobjects.traps.Trap;
import com.tilerunner.gameobjects.traps.Traps;
import com.tilerunner.gameobjects.world.Detector;
import com.tilerunner.core.C;
import com.tilerunner.gameobjects.world.World;
import com.tilerunner.input.IGameInput;

/**
 * User: Franjo
 * Date: 11.11.13
 * Time: 13:25
 * Project: TileRunner
 */
public class Player extends Creature implements IPlayable {

    private final int id;

    // collision
    private Detector detector;
    public boolean hit_top;
    public boolean hit_bottom;
    public boolean hit_left;
    public boolean hit_right;

    // animation
    private AnimationState state;

    // sounds
    private Sound sndJump;

    private final float aJ = 100;

    // mao objects
    private Checkpoint checkpoint;

    private IGameInput input;
    private World world;

    public Player(String name, String path, String skin, float scale, World world, int id) {
        super(name, path, skin, scale);

        this.world = world;
        this.id = id;


        init();
    }

    public Player(World world, int id) {
        this("skeleton", "spine/lego_runner/", "blau", 0.8f, world, id);


    }


    private void init() {

        // position
        this.x = 640;
        this.y = 10600;
        // dimension
//        this.width = 64;
//        this.height = 64;
        this.width = 0;
        this.height = 0;
        // velocity
        this.vX = 0;
        this.vY = 0;
        // acceleration
        this.aX = 75f;
        this.aY = 0;
        // friction
//        this.frictionX = 0.94f;
        this.frictionX = 0f;
        this.frictionY = 0;
        // limits
        this.vX_Max = 15;
        this.vX_Min = -15;
        this.vY_Max = 40;
        this.vY_Min = -40;

        // detector
        detector = Detector.getInstance();

        createSounds();

        setupAnimationStates();

//        skeletonDebugRenderer

//        skeleton.findBone("").


        //test
//        System.out.println("SIZE:" + getSkeletonBounds().getBoundingBoxes().size);
        System.out.println("SIZE:" + getSkeletonBounds().getBoundingBoxes().size);
//        for (int i = 0; i < getSkeletonBounds().getBoundingBoxes().get(0).getVertices().length; i++) {
//            System.out.println(getSkeletonBounds().getBoundingBoxes().get(0).getVertices()[i]);
//        }

    }

    private void setupAnimationStates() {
        AnimationStateData stateData = new AnimationStateData(skeletonData); // Defines mixing (crossfading) between animations.
        // walk
        stateData.setMix("walk", "idle", 0.2f);
        stateData.setMix("walk", "jump", 0.2f);
        // jump
        stateData.setMix("jump", "land", 0.2f);
        // land
        stateData.setMix("land", "walk", 0.2f);
        stateData.setMix("land", "idle", 0.2f);
        //idle
        stateData.setMix("idle", "walk", 0.2f);
        stateData.setMix("idle", "jump", 0.2f);


        state = new AnimationState(stateData);
        state.setAnimation(0, "idle", true);
        state.setTimeScale(1.6f);

    }

    private void createSounds() {
        sndJump = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.wav"));

    }


    @Override
    public void update(float delta) {
        super.update(delta);

        vX = input.stickX() * vX_Max;

//        vX *= frictionX;
        vY += C.GRAVITY * delta;


        // additional jump height
        if (current().equals("jump") && vY >= 0 && input.isA()) {
            vY += 0.7;
        }

        // jump start
        if (hit_bottom && input.isA()) {
            state.setAnimation(0, "jump", false);
            state.addAnimation(0, "land", false, 0);
            sndJump.play();
            vY += 23;
        }


//        if (current().equals("jump")){
//            state.setTimeScale(0.8f);
//        }else state.setTimeScale(1.6f);


//        if (vY == 0 && Math.abs(vX) <= 0.5) {
//            state.setAnimation(0, "idle", true);
//        }


        // set to limits
        if (vY > vY_Max) vY = vY_Max;
        else if (vY < vY_Min) vY = vY_Min;
        if (vX > vX_Max) vX = vX_Max;
        else if (vX < vX_Min) vX = vX_Min;
        if (Math.abs(vX) < 0.02) vX = 0;

        // set position
        x += vX;
        y += vY;


        // A N I M A T I O N

        if (state.getCurrent(0) == null) state.setAnimation(0, "idle", true);
        assert state.getCurrent(0).getAnimation() != null;


        skeleton.setFlipX(vX < 0);

        if (Math.abs(input.stickX()) >= 0.5f && !state.getCurrent(0).getAnimation().getName().equals("jump")) {
            if (!state.getCurrent(0).getAnimation().getName().equals("walk")) {
                state.setAnimation(0, "walk", true);
            }

//
//            if (!state.getCurrent(0).getAnimation().getName().equals("walk")) {
//                state.addAnimation(0, "walk", false, 0);
////                state.getCurrent(0).setNext();
//            }
//

        } else if (!state.getCurrent(0).getAnimation().getName().equals("idle")) {
            state.addAnimation(0, "idle", true, 0);
        }
//            state.clearTracks();
//            state.setAnimation(0,"walk",false);
//            state.setAnimation(0,"walk",true);
//            skeletonData.findAnimation("walk").apply(skeleton, skeleton.getTime(), skeleton.getTime() + delta, true, null);
//            skeleton.update(delta);
//        } else {
//            state.setAnimation(0, "idle", true);
//        }

        state.update(delta);
        state.apply(skeleton);

        // tile collision
        setTileCollisionPosition();

        // check trap collision
        checkTrapCollision();

        // set checkpoint
        setCheckpoint();


    }

    private void setCheckpoint() {
        Array<Checkpoint> checkpoints = world.checkpoints().getCheckpoints();
        for (int i = 0; i < checkpoints.size; i++) {
            if(checkpoints.get(i).isHit(x,y)){
                System.out.println("hit checkpoint");
                checkpoint = checkpoints.get(i);
            }
        }
    }

    private void checkTrapCollision() {

        Array<Trap> traps = world.traps().getTraps();
        for (int i = 0; i < traps.size; i++) {
            for (int j = 0; j < world.getPlayers().size; j++) {
                Player p = world.getPlayers().get(j);
                SkeletonBounds bounds = p.getSkeletonBounds();

                // polygonal shape
                for (int l = 0; l < bounds.getPolygons().size; l++) {

                    // vertices
                    FloatArray polygon = bounds.getPolygons().get(l);
                    for (int u = 0; u < polygon.size; u += 2) {

                        if (traps.get(i).isHit(polygon.get(u),polygon.get(u + 1))) {
                            System.out.println("hit trap: " + bounds.getBoundingBoxes().get(l));

                            x = checkpoint.getPointX();
                            y = checkpoint.getPointY();
                        }
                    }

                }

            }

        }
    }

    private String current() {
        return state.getCurrent(0).getAnimation().getName();
    }


    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);


    }

    @Override
    public float getX() {
        return x;


    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getVx() {
        return vX;
    }

    @Override
    public float getVy() {
        return vY;
    }

    public void setInputController(IGameInput input) {
        this.input = input;
    }

    public IGameInput getInputController() {
        return input;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Bone getBone(String name) {
        return skeleton.findBone(name);
    }

    private void setTileCollisionPosition() {

        // todo ruckeln in solid to step

        SkeletonBounds bounds = getSkeletonBounds();
//        System.out.println(bounds.getMaxX());

        hit_left = false;
        hit_right = false;
        hit_top = false;
        hit_bottom = false;

        // left
        if (vX < 0) {

            // solid to step
            if (detector.getStep(x, y + 2) != null && detector.getStep(x, y) == null) {
                y += 2;

//                System.out.println("solid to step left");
            }

            // step to solid
            else if (detector.getStep(x - vX, y) != null && detector.isSolid(x, y) && !detector.isSolid(x, y + 2)) {
                y += 2;

//                System.out.println("step to solid left");

            }

            // solid
            else if (detector.isSolid(x, y - vY) || detector.isSolid(x, y + height - vY)) {

                x = (int) (x / world.tileWidth) * world.tileWidth + world.tileWidth + C.EPSILON;
                hit_left = true;
                vX = 0;

            }
        }
        // right
        else if (vX > 0) {

            // solid to step
            if (detector.getStep(x, y + 2) != null && detector.getStep(x, y) == null) {
                y += 2;

//                System.out.println("solid to step right");

            }

            // step to solid
            else if (detector.getStep(x - vX, y) != null && detector.isSolid(x, y) && !detector.isSolid(x, y + 2)) {
                y += 2;

//                System.out.println("step to solid right");
            }

            // solid
            else if ((detector.isSolid(x + width, y - vY) || detector.isSolid(x + width, y + height - vY))) {

                x = (int) (x / world.tileWidth) * world.tileWidth - C.EPSILON;
                hit_right = true;
                vX = 0;

            }
        }

        // bottom
        if (vY < 0) {

            // step
            Detector.Step step = detector.getStep(x, y);
            if (step != null) {

                float _y = y % 64.0f;
                float _x = x % 64.0f;

                if (_y <= step.m * _x + step.y1 * World.STEP) {

                    y = (int) (y / world.tileHeight) * world.tileHeight + (step.m * _x + step.y1 * World.STEP) + C.EPSILON;

                    hit_bottom = true;
                    vY = 0;

                }

            } else if (detector.isSolid(x - vX, y) || detector.isSolid(x + width - vX, y)) {
                y = (int) (y / world.tileHeight) * world.tileHeight + world.tileHeight + C.EPSILON;
                hit_bottom = true;
                vY = 0;
            }
        }
        // top
        else if (vY > 0) {

            // step
            if (detector.getStep(x - vX, y + height) != null || detector.getStep(x + width - vX, y + height) == null) {
//                y += 2;
            } else if (detector.isSolid(x - vX, y + height) || detector.isSolid(x + width - vX, y + height)) {
                y = (int) (y / world.tileHeight) * world.tileHeight - C.EPSILON;
                hit_top = true;
                vY = 0;

//                System.out.println("top");
            }
        }

    }

    public void getPolygonVertices(){

    }
}
