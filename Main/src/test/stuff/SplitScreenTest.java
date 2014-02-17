package test;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/**
 * User: Franjo
 * Date: 12.11.13
 * Time: 18:29
 * Project: TileRunner
 */
public class SplitScreenTest implements ApplicationListener {

    SpriteBatch batch;
    Camera camera_p1;
    Camera camera_p2;

    Texture tP1;
    Texture tP2;

    Texture tGround;


    @Override
    public void create() {
        batch = new SpriteBatch(800,480);
        camera_p1 = new OrthographicCamera(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        camera_p2 = new OrthographicCamera(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        tP1 = new Texture(Gdx.files.internal("test/player_1.png"));
        tP2 = new Texture(Gdx.files.internal("test/player_2.png"));


        //tGround = new Texture(Gdx.files.internal("test/ground.png"));
    }

    @Override
    public void resize(int width, int height) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render() {

        // background color
        Color c = Color.valueOf("BAB6BF");
        Gdx.gl.glClearColor(c.r, c.g, c.b, c.a);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
        Matrix4 matrix4 = camera_p1.combined;
//        matrix4.setToRotation(new Vector3(1,0,1),30);

        batch.setProjectionMatrix(matrix4);
        update(Gdx.graphics.getDeltaTime());

        // draw ground
        batch.begin();
        batch.draw(tP1, 0, 0);

        batch.end();

        camera_p1.update();



        Gdx.gl.glViewport(Gdx.graphics.getWidth() / 2, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
        batch.setProjectionMatrix(camera_p2.combined);
        update(Gdx.graphics.getDeltaTime());
        // draw ground
        batch.begin();
        batch.draw(tP2, 0, 0);
        batch.end();

        camera_p2.update();


    }

    private void update(float deltaTime) {

        float speed = 4;


        // *** P1 ***
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera_p1.position.x -= speed;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera_p1.position.x += speed;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera_p1.position.y += speed;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera_p1.position.y -= speed;
        }


        // *** P2 ***
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera_p2.position.x -= speed;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera_p2.position.x += speed;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera_p2.position.y += speed;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera_p2.position.y -= speed;
        }
    }


    @Override
    public void pause() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resume() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void dispose() {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    class CameraHelper{

    }

}
