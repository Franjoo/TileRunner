package com.tilerunner.input.gamepads;

/**
 * User: Franjo
 * Date: 30.11.13
 * Time: 20:11
 * Project: TileRunner
 */
public interface IX360Gamepad {

    // poll method
    public void poll();

    // buttons
    public boolean is_A();
    public boolean is_B();
    public boolean is_X();
    public boolean is_Y();
    public boolean is_START();
    public boolean is_BACK();
    public boolean is_L1();
    public boolean is_R1();
    public boolean is_L3();
    public boolean is_R3();

    // sticks
    public float get_LS_X();
    public float get_LS_Y();
    public float get_RS_X();
    public float get_RS_Y();

    // dpad
    public boolean is_DPAD_NORTH();
    public boolean is_DPAD_SOUTH();
    public boolean is_DPAD_EAST();
    public boolean is_DPAD_WEST();

    public boolean is_DPAD_NORTH_EAST();
    public boolean is_DPAD_SOUTH_EAST();
    public boolean is_DPAD_NORTH_WEST();
    public boolean is_DPAD_SOUTH_WEST();

    // trigger
    public float get_TL();
    public float get_TR();

    // dead zones
    public void set_LS_Deadzone(float value);
    public void set_RS_Deadzone(float value);

}
