package com.tilerunner.input.gamepads;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector2;
import com.tilerunner.input.IGameInputController;

/**
 * User: Franjo
 * Date: 25.10.13
 * Time: 19:40
 * Project: Main
 */
public class _X360Gamepad implements IGameInputController {

    public static int NUM_CONTROLLERS = 0;
    public final int id;

    // buttons
    public static final int BTN_A = 0;
    public static final int BTN_B = 1;
    public static final int BTN_X = 2;
    public static final int BTN_Y = 3;
    public static final int L1 = 4;
    public static final int R1 = 5;
    public static final int BTN_BACK = 6;
    public static final int BTN_START = 7;
    public static final int L3 = 8;
    public static final int R3 = 9;

    // dpad
    public static final PovDirection BUTTON_DPAD_UP = PovDirection.north;
    public static final PovDirection BUTTON_DPAD_DOWN = PovDirection.south;
    public static final PovDirection BUTTON_DPAD_RIGHT = PovDirection.east;
    public static final PovDirection BUTTON_DPAD_LEFT = PovDirection.west;

    // sticks
    private static final float STICK_DEAD = 0.0f;
    private static final int STICK_LEFT_X = 1;
    private static final int STICK_LEFT_Y = 0;
    private static final int STICK_RIGHT_X = 3;
    private static final int STICK_RIGHT_Y = 2;

    private Vector2 vec2 = new Vector2();

    // trigger
    public static final int TRIGGER = 4;
    public static final float TRGGER_MIN_VALUE = 0.0005f;

//    public static final int R2 = ;
//    public static final int L2 = ;

    private Controller c;

    public _X360Gamepad(Controller controller) {
        c = controller;

        id = NUM_CONTROLLERS;

        System.out.println("Controller created");

    }

    public String getName() {
        return c.getName();
    }

    //<editor-fold desc="button pressed getters">
    public boolean is_A_pressed() {

        Gdx.input.vibrate(1000);

//        testTriggers();

        return c.getButton(BTN_A);

    }

    public boolean is_B_pressed() {
        return c.getButton(BTN_B);
    }

    public boolean is_X_pressed() {
        return c.getButton(BTN_X);
    }

    public boolean is_Y_pressed() {
        return c.getButton(BTN_Y);
    }

//    public boolean is_DPAD_LEFT(){
//        return c.getPov(BUTTON_DPAD_LEFT.ordinal()).compareTo(BUTTON_DPAD_LEFT);
//    }
//
//    public PovDirection is_DPAD_RIGHT_pressed(){
//        return c.getPov(BUTTON_DPAD_RIGHT.ordinal());
//    }
//
//    public PovDirection is_DPAD_UP_pressed(){
//        return c.getPov(BUTTON_DPAD_UP.ordinal());
//    }
//
//    public PovDirection is_DPAD_DOWN_pressed(){
//        return c.getPov(BUTTON_DPAD_DOWN.ordinal());
//    }

    @Override
    public boolean is_L1_pressed() {
        return c.getButton(L1);
    }
    @Override
    public boolean is_R1_pressed() {
        return c.getButton(R1);
    }

    @Override
    public boolean is_BACK_pressed() {
        return c.getButton(BTN_BACK);
    }

    @Override
    public boolean is_START_pressed() {
        return c.getButton(BTN_START);
    }

    @Override
    public boolean is_L3_pressed() {
        return c.getButton(L3);
    }

    @Override
    public boolean is_R3_pressed() {
        return c.getButton(R3);
    }


    //</editor-fold>

    //<editor-fold desc="sticks">
    public float stick_left_X() {
        if (Math.abs(c.getAxis(STICK_LEFT_X)) > STICK_DEAD) return c.getAxis(STICK_LEFT_X);
        return 0;
    }

    public float stick_left_Y() {
        if (Math.abs(c.getAxis(STICK_LEFT_Y)) > STICK_DEAD) return -c.getAxis(STICK_LEFT_Y);
        return 0;
    }

    public float stick_left_intensity() {
//        vec2 = new Vector2(stick_left_X(),stick_left_Y());
//        System.out.println("vec 2 length :" + vec2.len2());
        float magnitude = (float) Math.sqrt(stick_left_X() * stick_left_X() + stick_left_Y() * stick_left_Y());
//        System.out.println(magnitude);
        return magnitude;
    }

    public float stick_right_X() {
//        if (Math.abs(c.getAxis(STICK_RIGHT_X)) > STICK_DEAD) return c.getAxis(STICK_RIGHT_X);
//        return 0;
        return c.getAxis(STICK_RIGHT_X);
    }

    public float stick_right_Y() {
//        if (Math.abs(c.getAxis(STICK_RIGHT_Y)) > STICK_DEAD) return -c.getAxis(STICK_RIGHT_Y);
//        return 0;
        return -(c.getAxis(STICK_RIGHT_Y));

    }
    //</editor-fold>

    public float trigger_left() {
        float t = c.getAxis(TRIGGER);
        if (t > TRGGER_MIN_VALUE) {
//            System.out.println(t);
            return t;
        }
        return 0;
    }

    public float trigger_right() {
        float t = c.getAxis(TRIGGER);
        if (t < 0) {
//            System.out.println(t);
            return -t;
        }
        return 0;
    }


    public void test() {

        trigger_left();
        trigger_right();
//
//        for (int i = 0; i < 10000; i++) {
////            if (c.getAxis(1)) System.out.println("-> " + i);
//
////            System.out.println(c.get);
//
//        }

//        System.out.println("L:" + trigger_left() + " | R:" + trigger_right());
//        System.out.println(c.;
//        System.out.println(c.getAxis(4));
    }


    public void trace() {
        StringBuilder s = new StringBuilder();
        // button pressed getters
        if (is_A_pressed()) s.append("Button [ A ] pressed");
        if (is_B_pressed()) s.append("Button [ B ] pressed");
        if (is_X_pressed()) s.append("Button [ X ] pressed");
        if (is_Y_pressed()) s.append("Button [ Y ] pressed");
        if (is_L1_pressed()) s.append("Button [ L1 ] pressed");
        if (is_R1_pressed()) s.append("Button [ R1 ] pressed");
        if (is_BACK_pressed()) s.append("Button [ BACK ] pressed");
        if (is_START_pressed()) s.append("Button [ START ] pressed");
        if (is_L3_pressed()) s.append("Button [ L3 ] pressed");
        if (is_R3_pressed()) s.append("Button [ R3 ] pressed");


        if (!s.equals("")) System.out.println(s.toString());
    }

    @Override
    public float get_left_stickX() {
        return stick_left_X();
    }

    @Override
    public float get_left_stickY() {
        return stick_left_Y();
    }

    @Override
    public float get_right_stickX() {
        return stick_right_X();
    }

    @Override
    public float get_right_stickY() {
        return stick_right_Y();
    }

    @Override
    public boolean get_isA() {
        return is_A_pressed();
    }

    @Override
    public boolean get_isB() {
        return is_B_pressed();
    }

    @Override
    public boolean get_isY() {
        return is_Y_pressed();
    }

    @Override
    public boolean get_isX() {
        return is_X_pressed();
    }

    @Override
    public boolean is_DPAD_UP() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean is_DPAD_DOWN() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean is_DPAD_LEFT() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean is_DPAD_RIGHT() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public float get_trigger_left() {
        return trigger_left();
    }

    @Override
    public float get_trigger_right() {
        return trigger_right();
    }



}
