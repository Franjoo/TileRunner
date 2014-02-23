package com.tilerunner.input;

/**
 * Created with IntelliJ IDEA.
 * User: Franjo
 * Date: 23.02.14
 * Time: 19:19
 */
public interface IGameInput {

    float stickX();

    float stickY();

    boolean isA();

    boolean isB();

    boolean isX();

    void poll();

}
