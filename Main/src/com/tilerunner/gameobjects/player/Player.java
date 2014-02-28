package com.tilerunner.gameobjects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.SkeletonBounds;
import com.tilerunner.gameobjects.crates.Crate;
import com.tilerunner.gameobjects.checkpoints.Checkpoint;
import com.tilerunner.gameobjects.platforms.Platform;
import com.tilerunner.gameobjects.traps.Trap;
import com.tilerunner.gameobjects.world.Detector;
import com.tilerunner.core.C;
import com.tilerunner.gameobjects.world.World;
import com.tilerunner.input.IGameInput;

import java.util.HashMap;

/**
 * User: Franjo
 * Date: 11.11.13
 * Time: 13:25
 * Project: TileRunner
 */
public class Player extends Creature implements IPlayable {

    private final int id;

    // collision
    private HashMap<String, Rectangle> bounds;
    private Detector detector;
    public boolean hit_top;
    public boolean hit_bottom;
    public boolean hit_left;
    public boolean hit_right;
    private boolean isOnPlatform;


    // animation
    private AnimationState state;

    // sounds
    private Sound sndJump;

    private final float aJ = 50;

    // mao objects
    private Checkpoint checkpoint;

    // wafting
    private boolean wafts;
    private float waftX;
    private float waftY;
    private float waftV;
    private float waftA;
    private float waftTime = 2;
    private float waftElapsed;


    private IGameInput input;
    private World world;

    // debug
    private Texture boundsTexture;

    public Player(String name, String path, String skin, float scale, World world, int id) {
        super(name, path, skin, scale);

        this.world = world;
        this.id = id;


        init();
    }

    public Player(World world, int id) {
        this("skeleton", "spine/lego_runner/", "blau", 0.4f, world, id);

//         init();
    }


    private void init() {

        // position
        this.x = 320;
        this.y = 5300;
//        this.x = 640;
//        this.y = 10600;

        // dimension
//        this.width = 64;
//        this.height = 64;
        this.width = 42;
        this.height = 110;
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
        this.vX_Max = 7;
        this.vX_Min = -7;
        this.vY_Max = 20;
        this.vY_Min = -20;

        // detector
        detector = Detector.getInstance();

        createSounds();

        setupAnimationStates();

        initSkeleton();

        drawBoundsTexture();
    }

    private void drawBoundsTexture() {
        Pixmap p = new Pixmap((int) width, (int) height, Pixmap.Format.RGBA8888);
        p.setColor(0, 1, 0, 1);
        for (int i = 0; i < 4; i++) {
            p.drawRectangle(i, i, (int) width - 2 * i, (int) height - 2 * i);
        }

        boundsTexture = new Texture(p);
    }

    private void initSkeleton() {
        float delta = Gdx.graphics.getDeltaTime();
        skeleton.updateWorldTransform();
        skeleton.update(delta);
        getSkeletonBounds().update(skeleton, true);

        createBounds();
    }

    private void createBounds() {
        bounds = new HashMap<>();
        addBoundingRectangle("bounds_medium");
        addBoundingRectangle("bounds_large");
    }

    private void setupAnimationStates() {
        AnimationStateData stateData = new AnimationStateData(skeletonData); // Defines mixing (crossfading) between animations.
        // walk
        stateData.setMix("walk", "idle", 0.2f);
        stateData.setMix("walk", "jump", 0.2f);
        stateData.setMix("walk", "waft_start", 1f);

        // jump
        stateData.setMix("jump", "land", 0.2f);
        stateData.setMix("jump", "waft_start", 1f);
        // land
        stateData.setMix("land", "walk", 0.2f);
        stateData.setMix("land", "idle", 0.2f);
        stateData.setMix("land", "waft_start", 1f);
        //idle
        stateData.setMix("idle", "walk", 0.2f);
        stateData.setMix("idle", "jump", 0.2f);
        stateData.setMix("idle", "waft_start", 1f);


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

        // wafting
        if (wafts) {
            waftElapsed += delta;

            // is at checkpoint
            if (waftElapsed >= waftTime) {
                wafts = false;
                waftElapsed = 0;
            }

            // update waft
            else {
                x += waftX * waftV * delta;
                y += waftY * waftV * delta;

                state.update(delta);
                state.apply(skeleton);
            }
        }

        // not wafting
        else {

            vX = input.stickX() * vX_Max;

//        vX *= frictionX;
            vY += C.GRAVITY * delta;


            // additional jump height
            if (current().equals("jump") && vY >= 0 && input.isA()) {
                vY += 0.5;
            }

            // jump start
            if (hit_bottom && input.isA()) {
                state.setAnimation(0, "jump", false);
                state.addAnimation(0, "land", false, 0);
                sndJump.play();
                vY += 15;
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

            // platform collision
            setPlatformCollisionPosition(delta);

            // crates
            setBoxesCollisionPosition();

            // check trap collision
            checkTrapCollision();

            // set checkpoint
            setCheckpoint();
        }


    }

    private void setBoxesCollisionPosition() {
        for (int i = 0; i < world.boxes().getBoxes().size; i++) {
            Crate crate = world.boxes().getBoxes().get(i);

            boolean onTop = false;
//            SkeletonBounds bounds = getSkeletonBounds();


            // top
            if (crate.isHit(x, y, width, height) && !crate.isHit(x, y - vY, width, height)) {

                y = crate.getY() + crate.getHeight() + C.EPSILON;
                vY = 0;
                hit_bottom = true;
                onTop = true;

            }

//            // top
//            if ((crate.isHit(bounds.getMinX(), y) && !crate.isHit(bounds.getMinX(), y - vY))
//                    || (crate.isHit(bounds.getMaxX(), y) && !crate.isHit(bounds.getMaxX(), y - vY))) {
//
//                y = crate.getY() + crate.getHeight() + C.EPSILON;
//                vY = 0;
//                hit_bottom = true;
//                onTop = true;
//
//            }

            // right push
            if (vX > 0 && hit_bottom && !onTop && crate.isHit(x + width, y)) {
                x -= crate.push(vX);
            }

            // left push
            else if (vX < 0 && hit_bottom && !onTop && crate.isHit(x, y)) {
                x -= crate.push(vX);
            }

            // right air collision
            if (vX > 0 && !hit_bottom && crate.isHit(x, y, width, height)) {
                x = crate.getX() - width;
            }

            // left air collision
            else if (vX < 0 && !hit_bottom && crate.isHit(x, y, width, height)) {
                x = crate.getX() + crate.getWidth();
            }

        }

    }

    private void setCheckpoint() {
        Array<Checkpoint> checkpoints = world.checkpoints().getCheckpoints();
        for (int i = 0; i < checkpoints.size; i++) {
            if (checkpoints.get(i).isHit(x, y)) {
                checkpoint = checkpoints.get(i);
            }
        }
    }

    private void addBoundingRectangle(String name) {

//        assert !bounds.containsKey("name");

        for (int j = 0; j < getSkeletonBounds().getPolygons().size; j++) {

            System.out.println(getSkeletonBounds().getBoundingBoxes().get(j));

            if (getSkeletonBounds().getBoundingBoxes().get(j).toString().equals(name)) {
                FloatArray vertices = getSkeletonBounds().getPolygons().get(j);

                float minX, minY;
                float maxX, maxY;

                minX = minY = Float.MAX_VALUE;
                maxX = maxY = Float.MIN_VALUE;

                for (int i = 0; i < vertices.size; i += 2) {
                    float vx = vertices.get(i);
                    float vy = vertices.get(i + 1);

                    // x
                    if (vx < minX) minX = vx;
                    else if (vx > maxX) maxX = vx;

                    // y
                    if (vy < minY) minY = vy;
                    else if (vy > maxY) maxY = vy;

                }

                // compute width & height
                minY = 0;
                float width = maxX - minX;
                float height = maxY - minY;

                // put new Rectangle
                Rectangle rectangle = new Rectangle(minX, minY, width, height);
                System.out.println("put rectangle: " + rectangle);
                bounds.put(name, rectangle);
                break;
            }
        }


//        for (int i = 0; i < getSkeletonBounds().getBoundingBoxes().size; i++) {
//            System.out.println(getSkeletonBounds().getBoundingBoxes().get(i));
//
//            if (getSkeletonBounds().getBoundingBoxes().get(i).equals(name)) {
//                bb = getSkeletonBounds().getBoundingBoxes().get(i);
//                break;
//            }
//        }

//        float minX, minY;
//        float maxX, maxY;
//
//        minX = minY = Float.MAX_VALUE;
//        maxX = maxY = Float.MIN_VALUE;
//
//        float[] vertices = bb.getVertices();
//        for (int i = 0; i < vertices.length; i += 2) {
//            // x
//            if (vertices[i] < minX) minX = vertices[i];
//            else if (vertices[i] > maxX) maxX = vertices[i];
//
//            // y
//            if (vertices[i + 1] < minY) minY = vertices[i + 1];
//            else if (vertices[i + 1] > maxY) maxY = vertices[i + 1];
//
//        }
//
//        // compute width & height
//        float width = maxX - minX;
//        float height = maxY - minY;
//
//        // put new Rectangle
//        bounds.put(name, new Rectangle(x, y, width, height));

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

                        if (traps.get(i).isHit(polygon.get(u), polygon.get(u + 1))) {
                            System.out.println("hit trap: " + bounds.getBoundingBoxes().get(l));

                            wafts = true;
                            waft(checkpoint.getPointX(), checkpoint.getPointY());
                        }
                    }

                }

            }

        }
    }

    private void waft(float x, float y) {
        // distance
        float dirX = x - this.x;
        float dirY = y - this.y;
        float dist = (float) Math.sqrt(dirX * dirX + dirY * dirY);

        // normalize
        waftX = dirX / dist;
        waftY = dirY / dist;

        waftTime = 2;
        waftV = dist / waftTime;

        state.setAnimation(0, "waft_start", true);
//        waftV = 120;
    }

    private String current() {
        return state.getCurrent(0).getAnimation().getName();
    }


    @Override
    public void render(SpriteBatch batch) {
        skeleton.setX(x + width/2);
        super.render(batch);


        batch.begin();
        batch.draw(boundsTexture, x, y);
        batch.end();


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

        // reset collision bools
        hit_left = false;
        hit_right = false;
        hit_top = false;
        hit_bottom = false;

        // left
        if (vX < 0) {

            if (detector.isStep(x + width / 2, y - vY) && detector.isSolid(x, y + 10)) {

                System.out.println("matches");
                vX = 0;
                hit_left = true;
                x = (int) (x / World.TS) * World.TS + World.TS + C.EPSILON;

            } else if (detector.isStep(x + width / 2, y + 2) && !detector.isStep(x + width / 2, y)) {
                y += 2;
            }
//

            else if ((!detector.isStep(x + width / 2, y - vY) && detector.isSolid(x, y - vY, width, height))) {

                // (detector.isStep(x + width / 2, y - vY) && detector.isSolid(x, y - vY + 10, width, height))

                vX = 0;
                hit_left = true;
                x = (int) (x / World.TS) * World.TS + World.TS + C.EPSILON;

                System.out.println("left solid");
            }
        }

        // right
        else if (vX > 0) {

            if (detector.isStep(x + width / 2, y - vY) && detector.isSolid(x + width, y + 10)) {


                vX = 0;
                hit_right = true;
                x = (int) ((x + width) / World.TS) * World.TS - C.EPSILON - width;

            }

            // solid to step
            else if (detector.isStep(x + width / 2, y + 2) && !detector.isStep(x + width / 2, y)) {
                y += 2;

            } else if ((!detector.isStep(x + width / 2, y - vY) && detector.isSolid(x, y - vY, width, height))) {
                //detector.isStep(x + width / 2, y - vY) && detector.isSolid(x, y - vY + vY, width, height)

                vX = 0;
                hit_right = true;
                x = (int) ((x + width) / World.TS) * World.TS - C.EPSILON - width;

                System.out.println("right solid");

            }
        }

        // bottom
        if (vY < 0) {

            // step
            Detector.Step step = detector.getStep(x + width / 2, y);
            if (step != null) {

                float _y = y % World.TS;
                float _x = (x + width / 2) % World.TS;

                if (_y <= step.m * _x + step.y1 * World.STEP) {

                    y = (int) (y / world.tileHeight) * world.tileHeight + (step.m * _x + step.y1 * World.STEP) + C.EPSILON + 1;

                    hit_bottom = true;
                    vY = 0;
                }
            }

            // solid
            else if (detector.isSolid(x - vX, y, width, height)) {
                vY = 0;
                hit_bottom = true;
                y = (int) (y / world.tileHeight) * world.tileHeight + world.tileHeight + C.EPSILON;
            }
        }

        // top
        else if (vY > 0) {
            if (detector.isSolid(x - vX, y, width, height)) {
                vY = 0;
                hit_top = true;
                y = (int) ((y + height) / world.tileHeight) * world.tileHeight - C.EPSILON - height;
            }
        }

    }


//        private void setTileCollisionPosition() {
//
//        // todo ruckeln in solid to step
////        Rectangle r = bounds.get("bounds_large");
////
////        float x = this.x + r.x;
////        float y = this.y + r.y;
////        float width = r.width;
////        float height = r.height;
//
//
//        hit_left = false;
//        hit_right = false;
//        hit_top = false;
//        hit_bottom = false;
//
//        // left
//        if (vX < 0) {
//
//            // solid to step
//            if (detector.getStep(this.x, y + 2) != null && detector.getStep(this.x, y) == null) {
//                y += 2;
//
////                System.out.println("solid to step left");
//            }
//
//            // step to solid
//            else if (detector.getStep(this.x - vX, y) != null && detector.isSolid(this.x, y) && !detector.isSolid(this.x, y + 2)) {
//                y += 2;
//
////                System.out.println("step to solid left");
//
//            }
//
//            // solid
//            else if (detector.isSolid(x, y - vY) || detector.isSolid(x, y + height - vY)) {
//
//                x = (int) (x / world.tileWidth) * world.tileWidth + world.tileWidth + C.EPSILON;
//                hit_left = true;
//                vX = 0;
//
//            }
//        }
//        // right
//        else if (vX > 0) {
//
//            // solid to step
//            if (detector.getStep(x, y + 2) != null && detector.getStep(x, y) == null) {
//                y += 2;
//
////                System.out.println("solid to step right");
//
//            }
//
//            // step to solid
//            else if (detector.getStep(x - vX, y) != null && detector.isSolid(x, y) && !detector.isSolid(x, y + 2)) {
//                y += 2;
//
////                System.out.println("step to solid right");
//            }
//
//            // solid
//            else if ((detector.isSolid(x + width, y - vY) || detector.isSolid(x + width, y + height - vY))) {
//
//                x = (int) ((x + width) / world.tileWidth) * world.tileWidth - C.EPSILON - width;
//                hit_right = true;
//                vX = 0;
//
//            }
//        }
//
//        // bottom
//        if (vY < 0) {
//
//            // step
//            Detector.Step step = detector.getStep(x, y);
//            if (step != null) {
//
//                float _y = y % World.TS;
//                float _x = x % World.TS;
//
//                if (_y <= step.m * _x + step.y1 * World.STEP) {
//
//                    y = (int) (y / world.tileHeight) * world.tileHeight + (step.m * _x + step.y1 * World.STEP) + C.EPSILON;
//
//                    hit_bottom = true;
//                    vY = 0;
//
//                }
//
//            } else if (detector.isSolid(x - vX, y) || detector.isSolid(x + width - vX, y)) {
//                y = (int) (y / world.tileHeight) * world.tileHeight + world.tileHeight + C.EPSILON;
//                hit_bottom = true;
//                vY = 0;
//            }
//        }
//        // top
//        else if (vY > 0) {
//
//            // step
////            if (detector.getStep(x - vX, y + height) != null || detector.getStep(x + width - vX, y + height) == null) {
//////                y += 2;
////            } else
//
//            if (detector.isSolid(x - vX, y + height) || detector.isSolid(x + width - vX, y + height)) {
//                y = (int) (y / world.tileHeight) * world.tileHeight - C.EPSILON;
//                hit_top = true;
//                vY = 0;
//
////                System.out.println("top");
//            }
//        }
//
////        this.y = y;
////        this.x = x - r.x;
//
//
//    }


//    private void setTileCollisionPosition() {
//
//        // todo ruckeln in solid to step
//
//        SkeletonBounds bounds = getSkeletonBounds();
////        System.out.println(bounds.getMaxX());
//
//        hit_left = false;
//        hit_right = false;
//        hit_top = false;
//        hit_bottom = false;
//
//        // left
//        if (vX < 0) {
//
//            // solid to step
//            if (detector.getStep(x, y + 2) != null && detector.getStep(x, y) == null) {
//                y += 2;
//
////                System.out.println("solid to step left");
//            }
//
//            // step to solid
//            else if (detector.getStep(x - vX, y) != null && detector.isSolid(x, y) && !detector.isSolid(x, y + 2)) {
//                y += 2;
//
////                System.out.println("step to solid left");
//
//            }
//
//            // solid
//            else if (detector.isSolid(x, y - vY) || detector.isSolid(x, y + height - vY)) {
//
//                x = (int) (x / world.tileWidth) * world.tileWidth + world.tileWidth + C.EPSILON;
//                hit_left = true;
//                vX = 0;
//
//            }
//        }
//        // right
//        else if (vX > 0) {
//
//            // solid to step
//            if (detector.getStep(x, y + 2) != null && detector.getStep(x, y) == null) {
//                y += 2;
//
////                System.out.println("solid to step right");
//
//            }
//
//            // step to solid
//            else if (detector.getStep(x - vX, y) != null && detector.isSolid(x, y) && !detector.isSolid(x, y + 2)) {
//                y += 2;
//
////                System.out.println("step to solid right");
//            }
//
//            // solid
//            else if ((detector.isSolid(x + width, y - vY) || detector.isSolid(x + width, y + height - vY))) {
//
//                x = (int) (x / world.tileWidth) * world.tileWidth - C.EPSILON;
//                hit_right = true;
//                vX = 0;
//
//            }
//        }
//
//        // bottom
//        if (vY < 0) {
//
//            // step
//            Detector.Step step = detector.getStep(x, y);
//            if (step != null) {
//
//                float _y = y % World.TS;
//                float _x = x % World.TS;
//
//                if (_y <= step.m * _x + step.y1 * World.STEP) {
//
//                    y = (int) (y / world.tileHeight) * world.tileHeight + (step.m * _x + step.y1 * World.STEP) + C.EPSILON;
//
//                    hit_bottom = true;
//                    vY = 0;
//
//                }
//
//            } else if (detector.isSolid(x - vX, y) || detector.isSolid(x + width - vX, y)) {
//                y = (int) (y / world.tileHeight) * world.tileHeight + world.tileHeight + C.EPSILON;
//                hit_bottom = true;
//                vY = 0;
//            }
//        }
//        // top
//        else if (vY > 0) {
//
//            // step
//            if (detector.getStep(x - vX, y + height) != null || detector.getStep(x + width - vX, y + height) == null) {
////                y += 2;
//            } else if (detector.isSolid(x - vX, y + height) || detector.isSolid(x + width - vX, y + height)) {
//                y = (int) (y / world.tileHeight) * world.tileHeight - C.EPSILON;
//                hit_top = true;
//                vY = 0;
//
////                System.out.println("top");
//            }
//        }
//
//    }

    public void setPlatformCollisionPosition(float delta) {

        isOnPlatform = false;

        Array<Platform> platforms = world.platforms().getPlatforms();
        for (int i = 0; i < platforms.size; i++) {
            Platform platform = platforms.get(i);

            // bottom
            if (vY < 0) {

                if (platform.isHit(x, y, width,height)) {
                    y = platform.getY() + platform.getHeight() + C.EPSILON;
                    x += platform.getVx() * delta;
                    isOnPlatform = true;
                    hit_bottom = true;
                    vY = 0;

                }
            }
        }
    }

    public void getPolygonVertices() {

    }

    public boolean isWafting() {
        return wafts;
    }
}
