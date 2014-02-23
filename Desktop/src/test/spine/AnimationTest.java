/******************************************************************************
 * Spine Runtime Software License - Version 1.1
 *
 * Copyright (c) 2013, Esoteric Software
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms in whole or in part, with
 * or without modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. A Spine Essential, Professional, Enterprise, or Education License must
 *    be purchased from Esoteric Software and the license must remain valid:
 *    http://esotericsoftware.com/
 * 2. Redistributions of source code must retain this license, which is the
 *    above copyright notice, this declaration of conditions and the following
 *    disclaimer.
 * 3. Redistributions in binary form must reproduce this license, which is the
 *    above copyright notice, this declaration of conditions and the following
 *    disclaimer, in the documentation and/or other materials provided with the
 *    distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *****************************************************************************/

package test.spine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.*;

public class AnimationTest extends ApplicationAdapter {
    SpriteBatch batch;
    Array<Event> events = new Array();

    SkeletonRenderer renderer;
    SkeletonRendererDebug debugRenderer;

    SkeletonData skeletonData;
    Skeleton skeleton;

    float time;



    // Animations
    private Animation walkAnimation;
    private float maxSpeed = 6;
    private float a = 1.05f;
    private float speed;


    private Animation idleAnimation;


    private float mul = 1;
    private float lastTime;

    public void create() {
        batch = new SpriteBatch();
        renderer = new SkeletonRenderer();
        debugRenderer = new SkeletonRendererDebug();

        final String name = "skeleton";

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(name + ".atlas"));

        if (true) {
            SkeletonJson json = new SkeletonJson(atlas);
            // json.setScale(2);
            skeletonData = json.readSkeletonData(Gdx.files.internal(name + ".json"));
        } else {
            SkeletonBinary binary = new SkeletonBinary(atlas);
            // binary.setScale(2);
            skeletonData = binary.readSkeletonData(Gdx.files.internal(name + ".skel"));
        }


        // create skeleton
        skeleton = new Skeleton(skeletonData);

        // set skin
        skeleton.setSkin(skeletonData.findSkin("rot"));
        skeleton.setToSetupPose();

        skeleton = new Skeleton(skeleton);


        walkAnimation = skeletonData.findAnimation("walk");
        idleAnimation = skeletonData.findAnimation("idle");


//
//        // recreate skeleton with skin
//        skeleton = new Skeleton(skeleton);
        skeleton.updateWorldTransform();

        skeleton.setX(300);
        skeleton.setY(20);
    }


    public void render() {
        float delta = Gdx.graphics.getDeltaTime() * 0.25f; // Reduced to make mixing easier to see.
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        time += delta;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            mul -= 0.015 * mul;
//            walkAnimation.setDuration(walkAnimation.getDuration() - delta * mul);

        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            mul += 0.015 * mul;
//            walkAnimation.setDuration(walkAnimation.getDuration() - delta * mul);

        }

        skeleton.updateWorldTransform();
        skeleton.update(Gdx.graphics.getDeltaTime());

        System.out.println("mul:" + mul);
        System.out.println("duration;" + walkAnimation.getDuration());

        walkAnimation.apply(skeleton, skeleton.getTime(), skeleton.getTime() + delta * mul, true, null);
//        idleAnimation.apply(skeleton, skeleton.getTime(), skeleton.getTime() + delta * mul, true, null);
        skeleton.setTime(skeleton.getTime() + delta * mul);



        batch.begin();
        renderer.draw(batch, skeleton);
        batch.end();

//        debugRenderer.draw(skeleton);
    }

    public void resize(int width, int height) {
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
        debugRenderer.getShapeRenderer().setProjectionMatrix(batch.getProjectionMatrix());
    }

    public static void main(String[] args) throws Exception {
        new LwjglApplication(new AnimationTest());
    }
}
