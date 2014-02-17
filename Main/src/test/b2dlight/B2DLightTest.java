package test.b2dlight;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

/**
 * User: Franjo
 * Date: 04.12.13
 * Time: 13:28
 * Project: TileRunner
 */
public class B2DLightTest implements ApplicationListener {

    // libgdx stuff
    OrthographicCamera camera;
    FPSLogger logger;
    float width, height;

    // b2d components
    Box2DDebugRenderer debugRenderer;
    World world;
    RayHandler rayHandler;

    // bodies
    Body circleBody;
    PointLight pointLight;
    Array<PointLight> pointLights;


    @Override
    public void create() {
        // dimension
        width = Gdx.graphics.getWidth() / 5;
        height = Gdx.graphics.getHeight() / 5;

        // camera
        camera = new OrthographicCamera(width, height);
        camera.position.set(width / 2, height / 2, 0);
        camera.update();

        // world
        world = new World(new Vector2(0, -9.81f), false);

        // renderer
        debugRenderer = new Box2DDebugRenderer();

        // body
        BodyDef circleDef = new BodyDef();
        circleDef.type = BodyDef.BodyType.DynamicBody;
        circleDef.position.set(width / 2, height / 2);

        circleBody = world.createBody(circleDef);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(3f);

        // fixture definition
        FixtureDef circleFixture = new FixtureDef();
        circleFixture.shape = circleShape;
        circleFixture.density = 0.4f;
        circleFixture.friction = 0.2f;
        circleFixture.restitution = 1f;

        circleBody.createFixture(circleFixture);

        // ground
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(0, 3);

        Body groundBody = world.createBody(groundBodyDef);

        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(camera.viewportWidth * 2, 3f);
        groundBody.createFixture(groundBox, 0);


        // light stuff
        rayHandler = new RayHandler(world);
        rayHandler.setCombinedMatrix(camera.combined);

        pointLight = new PointLight(rayHandler, 5000, Color.CYAN, 100, (width / 2), (height / 2) + 15);
        pointLight.setColor(1, .8f, .3f, .8f);
        pointLight.setSoft(false);
        pointLight.setXray(true);

        pointLights = new Array<>();


//        new ConeLight(rayHandler,5000,Color.GREEN,400,(width/2) + 50, (height))
//        new PointLight(rayHandler, 5000, Color.CYAN, 100, (width - 100), (height / 2) + 15);

        // fps logger
        logger = new FPSLogger();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            float x = Gdx.input.getX() / 5;
            float y = height - Gdx.input.getY() / 5;

            Color c = new Color((float) Math.random(), (float) Math.random(), (float) Math.random(), 1);
            PointLight p = new PointLight(rayHandler, 5000, c, (float) (Math.random() * 100 + 50), x, y);
            p.setActive(false);
        }
//        pointLight.setPosition(x, y);

        debugRenderer.render(world, camera.combined);
        rayHandler.updateAndRender();

        world.step(1 / 60f, 6, 2);

        logger.log();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        world.dispose();
    }


}
