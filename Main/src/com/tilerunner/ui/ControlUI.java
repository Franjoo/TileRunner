package com.tilerunner.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.tilerunner.input.IGameInput;

/**
 * Created with IntelliJ IDEA.
 * User: Franjo
 * Date: 25.02.14
 * Time: 18:39
 */
public class ControlUI {

    private Stage stage;
    private TouchInput listener;

    // buttons
    private Button btn_left;
    private Button btn_right;
    private Button btn_A;
    private Button btn_B;
    private Button btn_X;

    // touchpad
    private Touchpad touchpad;

    // button bounds
    private Rectangle rec_left;
    private Rectangle rec_right;
    private Rectangle rec_A;
    private Rectangle rec_B;
    private Rectangle rec_X;

    // debug bounds texures
    private Array<Texture> bounds;
    private Array<Rectangle> rects;

    public ControlUI() {
        init();

    }

    private void init() {

        // stage
        stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // listener
        listener = new TouchInput();

        // skin
        Skin skin = new Skin();
        skin.add("left", new Texture("ui/left.png"));
        skin.add("right", new Texture("ui/right.png"));
        skin.add("A", new Texture("ui/A.png"));
        skin.add("B", new Texture("ui/B.png"));
        skin.add("X", new Texture("ui/X.png"));
        skin.add("joystick_bg", new Texture("ui/joystick_bg.png"));
        skin.add("joystick_knob", new Texture("ui/joystick_knob.png"));

        // buttons
        btn_left = new Button(skin.getDrawable("left"), skin.getDrawable("left"));
        btn_right = new Button(skin.getDrawable("right"), skin.getDrawable("right"));
        btn_A = new Button(skin.getDrawable("A"), skin.getDrawable("A"));
        btn_B = new Button(skin.getDrawable("B"), skin.getDrawable("B"));
        btn_X = new Button(skin.getDrawable("X"), skin.getDrawable("X"));

        // representative rectangles
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // rectangles
        rec_left = new Rectangle(0, 0, w / 8, h / 3);
        rec_right = new Rectangle(rec_left.x + rec_left.width, 0, w / 6, h / 3);
        rec_A = new Rectangle(w - w / 8, 0, w / 8, h / 5);
        rec_X = new Rectangle(rec_A.x - w / 8, 0, w / 8, h / 5);
        rec_B = new Rectangle(w - w / 8, rec_A.y - h / 5, w / 8, h / 5);

        // position
        btn_left.setPosition(btn_left.getWidth() / 2, btn_left.getHeight() / 2);
        btn_right.setPosition(btn_left.getX() + 1.5f * btn_left.getWidth(), btn_left.getHeight() / 2);
        btn_A.setPosition(Gdx.graphics.getWidth() - 1.5f * btn_A.getWidth(), btn_left.getHeight() / 2);
        btn_X.setPosition(Gdx.graphics.getWidth() - 1.5f * btn_A.getWidth() - 1.5f * btn_X.getWidth(), btn_left.getHeight() / 2);
        btn_B.setPosition(Gdx.graphics.getWidth() - 1.5f * btn_B.getWidth(), btn_B.getHeight() / 2 + 1.5f * btn_A.getHeight());

        // touchpad
        Touchpad.TouchpadStyle style = new Touchpad.TouchpadStyle();
        Drawable joystickBG = skin.getDrawable("joystick_bg");
        Drawable joystickKnob = skin.getDrawable("joystick_knob");
        style.background = joystickBG;
        style.knob = joystickKnob;
        touchpad = new Touchpad(10, style);
        touchpad.setBounds(15, 75, 120, 120);

        // add actors
        stage.addActor(btn_left);
        stage.addActor(btn_right);
        stage.addActor(btn_A);
        stage.addActor(btn_B);
        stage.addActor(btn_X);
//        stage.addActor(touchpad);

    }

    public void update(float delta) {
        stage.act();

        drawBounds();

        listener.poll();
    }

    public void drawBounds() {
//        SpriteBatch batch = stage.getSpriteBatch();
//
//        batch.begin();
//        for (Texture t : bounds) {
//            batch.draw(t,);
//        }
//        batch.end();

    }

    public void drawBoundsTexture() {
        bounds = new Array<>();

        Pixmap p = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
        p.setColor(1, 0, 0, 1);
        p.drawRectangle(0, 0, 10, 10);
        bounds.add(new Texture(p));

    }

    public IGameInput getListener() {
        return listener;
    }

    public void render() {
        stage.draw();
    }


    class TouchInput extends InputAdapter implements IGameInput {

        private boolean isLeft;
        private boolean isRight;
        private boolean isA;
        private boolean isB;
        private boolean isX;

        private TouchInfo[] touches = new TouchInfo[3];

        public TouchInput() {
            Gdx.input.setInputProcessor(this);
            for (int i = 0; i < touches.length; i++) {
                touches[i] = new TouchInfo();
            }
        }

        class TouchInfo {
            public float touchX = 0;
            public float touchY = Gdx.graphics.getHeight();
            public boolean touched = false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            if (pointer < 5) {
                touches[pointer].touchX = screenX;
                touches[pointer].touchY = Gdx.graphics.getHeight() - screenY;
                touches[pointer].touched = true;
            }
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            if (pointer < 5) {
                touches[pointer].touchX = 0;
                touches[pointer].touchY = Gdx.graphics.getHeight();
                touches[pointer].touched = false;
            }
            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            if (pointer < 5) {
                touches[pointer].touchX = screenX;
                touches[pointer].touchY = Gdx.graphics.getHeight() - screenY;
                touches[pointer].touched = true;
            }
            return true;
        }

        @Override
        public void poll() {

            // reset button bools
            isLeft = false;
            isRight = false;
            isA = false;
            isB = false;
            isX = false;

            // update button bools
            for (int i = 0; i < touches.length; i++) {
                TouchInfo touch = touches[i];
                // left
                if (contains(touch.touchX, touch.touchY, rec_left)) {
                    isLeft = true;
                }

                // right
                if (contains(touch.touchX, touch.touchY, rec_right)) {
                    isRight = true;
                }

                // A
                if (contains(touch.touchX, touch.touchY, rec_A)) {
                    isA = true;
                }

                // B
                if (contains(touch.touchX, touch.touchY, rec_B)) {
                    isB = true;
                }

                // X
                if (contains(touch.touchX, touch.touchY, rec_X)) {
                    isX = true;
                }

            }

        }


        // contains helper methods
        private boolean contains(float x, float y, Actor a) {
            return a.getX() <= x && a.getX() + a.getWidth() >= x && a.getY() <= y && a.getY() + a.getHeight() >= y;
        }

        private boolean contains(float x, float y, Rectangle r) {
            return r.getX() <= x && r.getX() + r.getWidth() >= x && r.getY() <= y && r.getY() + r.getHeight() >= y;
        }


        //*** IGAMEINPUT INTERFACE METHODS ***//

        @Override
        public float stickX() {
            if (isLeft && isRight) return 0;
            else if (isLeft) return -1;
            else if (isRight) return 1;
            return 0;
        }

        @Override
        public float stickY() {
            return 0;
        }

        @Override
        public boolean isA() {
            return isA;
        }

        @Override
        public boolean isB() {
            return isB;
        }

        @Override
        public boolean isX() {
            return isX;
        }
    }
}

