package com.tilerunner.input.gamepads;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;


public class X360Gamepad implements IX360Gamepad {

    /* * * * I D E N T I F I E R * * * */
    private static final int BTN_A = 0;
    private static final int BTN_B = 1;
    private static final int BTN_X = 2;
    private static final int BTN_Y = 3;
    private static final int BTN_L1 = 4;
    private static final int BTN_R1 = 5;
    private static final int BTN_BACK = 6;
    private static final int BTN_START = 7;
    private static final int BTN_L3 = 8;
    private static final int BTN_R3 = 9;

    private static final int STICK_LEFT_X = 1;
    private static final int STICK_LEFT_Y = 0;
    private static final int STICK_RIGHT_X = 3;
    private static final int STICK_RIGHT_Y = 2;

    private static final int TRIGGER = 4;


    /* * * * * V A L U E S * * * * */
    private boolean isA;
    private boolean isB;
    private boolean isX;
    private boolean isY;
    private boolean isSTART;
    private boolean isBACK;
    private boolean isR1;
    private boolean isR3;
    private boolean isL1;
    private boolean isL3;

    private PovDirection povDirection;

    private float stickLeftX;
    private float stickLeftY;
    private float stickRightX;
    private float stickRightY;

    private float triggerLeft;
    private float triggerRight;

    private float stickLeftDeadZone;
    private float stickRightDeadZone;


    // controller reference
    private Controller controller;

    public X360Gamepad(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void poll() {

        /* buttons */
        isA = controller.getButton(BTN_A);
        isB = controller.getButton(BTN_B);
        isX = controller.getButton(BTN_X);
        isY = controller.getButton(BTN_Y);
        isSTART = controller.getButton(BTN_START);
        isBACK = controller.getButton(BTN_BACK);
        isR1 = controller.getButton(BTN_R1);
        isR3 = controller.getButton(BTN_R3);
        isL1 = controller.getButton(BTN_L1);
        isL3 = controller.getButton(BTN_L3);

        /* pov direction */
        povDirection = controller.getPov(0);

        /* stick directions */
        stickLeftX = Math.abs(controller.getAxis(STICK_LEFT_X)) > stickLeftDeadZone ? controller.getAxis(STICK_LEFT_X) : 0;
        stickLeftY = Math.abs(controller.getAxis(STICK_LEFT_Y)) > stickLeftDeadZone ? controller.getAxis(STICK_LEFT_Y) : 0;
        stickRightX = Math.abs(controller.getAxis(STICK_RIGHT_X)) > stickRightDeadZone ? controller.getAxis(STICK_RIGHT_X) : 0;
        stickRightY = Math.abs(controller.getAxis(STICK_RIGHT_Y)) > stickRightDeadZone ? controller.getAxis(STICK_RIGHT_Y) : 0;

        /* trigger values */
        triggerLeft = controller.getAxis(TRIGGER);
        triggerRight = controller.getAxis(TRIGGER);

    }

    @Override
    public boolean is_A() {
        return isA;
    }

    @Override
    public boolean is_B() {
        return isB;
    }

    @Override
    public boolean is_X() {
        return isX;
    }

    @Override
    public boolean is_Y() {
        return isY;
    }

    @Override
    public boolean is_START() {
        return isSTART;
    }

    @Override
    public boolean is_BACK() {
        return isBACK;
    }

    @Override
    public boolean is_L1() {
        return isL1;
    }

    @Override
    public boolean is_R1() {
        return isR1;
    }

    @Override
    public boolean is_L3() {
        return isL3;
    }

    @Override
    public boolean is_R3() {
        return isR3;
    }

    @Override
    public float get_LS_X() {
        return stickLeftX;
    }

    @Override
    public float get_LS_Y() {
        return stickLeftY;
    }

    @Override
    public float get_RS_X() {
        return stickRightX;
    }

    @Override
    public float get_RS_Y() {
        return stickRightY;
    }

    @Override
    public boolean is_DPAD_NORTH() {
        return povDirection.compareTo(PovDirection.north) == 0;
    }

    @Override
    public boolean is_DPAD_SOUTH() {
        return povDirection.compareTo(PovDirection.south) == 0;
    }

    @Override
    public boolean is_DPAD_EAST() {
        return povDirection.compareTo(PovDirection.east) == 0;
    }

    @Override
    public boolean is_DPAD_WEST() {
        return povDirection.compareTo(PovDirection.west) == 0;
    }

    @Override
    public boolean is_DPAD_NORTH_EAST() {
        return povDirection.compareTo(PovDirection.northEast) == 0;
    }

    @Override
    public boolean is_DPAD_SOUTH_EAST() {
        return povDirection.compareTo(PovDirection.southEast) == 0;
    }

    @Override
    public boolean is_DPAD_NORTH_WEST() {
        return povDirection.compareTo(PovDirection.northWest) == 0;

    }

    @Override
    public boolean is_DPAD_SOUTH_WEST() {
        return povDirection.compareTo(PovDirection.southWest) == 0;
    }

    @Override
    public float get_TL() {
        return triggerLeft;
    }

    @Override
    public float get_TR() {
        return triggerRight;
    }

    @Override
    public void set_LS_Deadzone(float value) {
        if (value > 1 || value < 0) throw new IllegalArgumentException("deadzone value must be between 0 and 1");
        stickLeftDeadZone = value;
    }

    @Override
    public void set_RS_Deadzone(float value) {
        if (value > 1 || value < 0) throw new IllegalArgumentException("deadzone value must be between 0 and 1");
        stickRightDeadZone = value;
    }

}
