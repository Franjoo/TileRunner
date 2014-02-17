package test.light;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

/**
 * User: Franjo
 * Date: 02.12.13
 * Time: 13:58
 * Project: TileRunner
 */
public class LightTest extends ApplicationAdapter {

    //Minor differences:
//LibGDX Position attribute is a vec4
//u_projView is called u_projTrans
//we need to set ShaderProgram.pedantic to false
//LibGDX uses lower-left as origin (0, 0)

//    final String VERT =
//            "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" +
//                    "uniform mat4 u_projTrans;\n" +
//                    " \n" +
//                    "void main() {\n" +
//                    " gl_Position = u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" +
//                    "}";
//    final String FRAG =
//            "void main() {\n" +
//                    " gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);\n" +
//                    "}";

    final String VERT =
            "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" +
                    "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" +
                    "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" +
                    "uniform mat4 u_projTrans;\n" +
                    " \n" +
                    "varying vec4 vColor;\n" +
                    "varying vec2 vTexCoord;\n" +
                    "void main() {\n" +
                    " vColor = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" +
                    " vTexCoord = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" +
                    " gl_Position = u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" +
                    "}";
    final String FRAG =
//GL ES specific stuff
            "#ifdef GL_ES\n" //
                    + "#define LOWP lowp\n" //
                    + "precision mediump float;\n" //
                    + "#else\n" //
                    + "#define LOWP \n" //
                    + "#endif\n" + //
                    "varying LOWP vec4 vColor;\n" +
                    "varying vec2 vTexCoord;\n" +
                    "uniform sampler2D u_texture;\n" +
                    "void main() {\n" +
                    " vec4 texColor = texture2D(u_texture, vTexCoord);\n" +
                    " \n" +
                    " texColor.rgb = 1.0 - texColor.rgb;\n" +
                    " \n" +
                    " gl_FragColor = texColor * vColor;\n" +
                    "}";

    Texture tex;
    SpriteBatch batch;
    OrthographicCamera cam;
    ShaderProgram shader;

    World b2dWorld;
    RayHandler rayHandler;
    PointLight pointLight;
    Box2DDebugRenderer renderer;

    @Override
    public void create() {


        System.out.println(ShaderProgram.POSITION_ATTRIBUTE);


//the texture does not matter since we will ignore it anyways
//        tex = new Texture(256, 256, Pixmap.Format.RGBA8888);
        tex = new Texture(Gdx.files.internal("test/scene.png"));
//important since we aren't using some uniforms and attributes that SpriteBatch expects
        ShaderProgram.pedantic = false;
        shader = new ShaderProgram(VERT, FRAG);
//        System.out.println((Gdx.files.internal("shader/red.vert").readString()));
//        System.out.println((Gdx.files.internal("shader/red.vert").readString()));
//        shader = new ShaderProgram((Gdx.files.internal("shader/negative.vert")), (Gdx.files.internal("shader/negative.frag")));
        shader = new ShaderProgram((Gdx.files.internal("shader/post.vert")), (Gdx.files.internal("shader/post.frag")));
//        shader = new ShaderProgram((Gdx.files.internal("shader/red.vert")), (Gdx.files.internal("shader/red.frag")));
        if (!shader.isCompiled()) {
            System.err.println(shader.getLog());
            System.exit(0);
        }
        if (shader.getLog().length() != 0)
            System.out.println(shader.getLog());

        batch = new SpriteBatch(1000, shader);

//        shader.
        batch.setShader(shader);


        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.setToOrtho(false);

        b2dWorld = new World(new Vector2(), true);
        rayHandler = new RayHandler(b2dWorld);
        rayHandler.setCombinedMatrix(cam.combined);

        pointLight = new PointLight(rayHandler, 1000, Color.CYAN, 10000, 100, 100);
        renderer = new Box2DDebugRenderer();
    }

    @Override
    public void resize(int width, int height) {
        cam.setToOrtho(false, width, height);
        batch.setProjectionMatrix(cam.combined);

    }

    @Override
    public void render() {

//        PointLight light = new PointLight()

        b2dWorld.step(1/60f,6,1);

        renderer.render(b2dWorld,cam.combined);

//        batch.begin();
////        shader.setAttributef("Position", 1.0f, 1.0f, 1.0f, 1.0f);
//
////notice that LibGDX coordinate system origin is lower-left
//        batch.draw(tex, 10, 10);
//        batch.draw(tex, 10, 320, 32, 32);
//        batch.end();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        shader.dispose();
        tex.dispose();
    }

}

//    static final int VIEWPORT_WIDTH = 800;
//    static final int VIEWPORT_HEIGHT = 480;
//
//    SpriteBatch batch;
//    OrthographicCamera camera;
//    ShaderProgram program;
//    Array<Vector2> points;
//
//    // textures
//    Texture staticPoint;
//    Texture quad;
//
//    @Override
//    public void create() {
//        batch = new SpriteBatch();
//        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
//
//        staticPoint = Drawer.rectangleTexture(4, 4, new Color(1, 1, 0, 1));
//        points = new Array<>();
//
//        quad = new Texture(Gdx.files.internal("test/tile.png"));
//
//        setupShader();
//
////        //load our shader program and sprite batch
////        try {
////            //read the files into strings
////            final String VERTEX = Util.readFile(Util.getResourceAsStream(("shader/red.vert")));
////            final String FRAGMENT = Util.readFile(Util.getResourceAsStream("shader/red.frag"));
////
////            //create our shader program -- be sure to pass SpriteBatch's default attributes!
//////            ShaderProgram program = new ShaderProgram(Gdx.files.internal("shader/red.vert"), Gdx.files.internal("shader/red.frag"));
////            ShaderProgram program = new ShaderProgram(VERTEX, FRAGMENT);
////            ShaderProgram.pedantic = false;
////
////
////            //Good idea to log any warnings if they exist
////            if (program.getLog().length() != 0)
////                System.out.println(program.getLog());
////
////            //create our sprite batch
////            batch = new SpriteBatch();
//            program.setUniformMatrix("u_projView", camera.combined);
////            program.setUniformMatrix("u_projTrans", camera.combined);
//
//            batch.setShader(program);
//
//
////        } catch (Exception e) {
////            System.out.println(e);
////            // ... handle the exception ...
////        }
//    }
//
//    private void setupShader() {
//        ShaderProgram.pedantic = false;
//        program = new ShaderProgram(Gdx.files.internal("shader/red.vert").readString(),
//                Gdx.files.internal("shader/red.frag").readString());
//        batch.setShader(program);
//
//    }
//
//    @Override
//    public void render() {
//        // background color
//        Color c = Color.valueOf("000000");
//        Gdx.gl.glClearColor(c.r, c.g, c.b, c.a);
//        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
//
//        batch.setProjectionMatrix(camera.combined);
//
//        camera.setToOrtho(true);
//        camera.update();
//
//        updatePointList();
//        renderPoints(batch);
//    }
//
//    private void renderPoints(SpriteBatch batch) {
//        batch.begin();
//        program.setUniformMatrix("u_projView", camera.combined.toNormalMatrix());
//        batch.setShader(program);
//
//        for (int i = 0; i < points.size; i++) {
//            quad.bind(0);
////            staticPoint.bind();
////            batch.draw(staticPoint, points.get(i).x, points.get(i).y);
//            batch.draw(quad, 50, 50);
//        }
//        batch.end();
//
//    }
//
//    private void updatePointList() {
//        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
//            points.add(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
//        }
//
//    }
//
//}
