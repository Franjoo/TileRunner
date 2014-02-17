package com.tilerunner.input;

/**
 * User: Franjo
 * Date: 25.10.13
 * Time: 23:32
 * Project: Main
 */
public interface IGameInputController {


    public float get_left_stickX();

    public float get_left_stickY();

    public float get_right_stickX();

    public float get_right_stickY();

    public boolean get_isA();

    public boolean get_isB();

    public float get_trigger_left();

    public float get_trigger_right();

    public boolean get_isY();

    public boolean get_isX();

    public boolean is_DPAD_UP();

    public boolean is_DPAD_DOWN();

    public boolean is_DPAD_LEFT();

    public boolean is_DPAD_RIGHT();

    public boolean is_L1_pressed();

    public boolean is_R1_pressed();

    public boolean is_BACK_pressed();

    public boolean is_START_pressed();

    public boolean is_L3_pressed();

    public boolean is_R3_pressed();
}
