package com.tilerunner.gameobjects.weapons;

import com.tilerunner.gameobjects.GameObject;

/**
 * User: Franjo
 * Date: 19.11.13
 * Time: 11:59
 * Project: TileRunner
 */
public abstract class ThrowableWeapon extends GameObject{

    public abstract void toss(float xDir, float yDir);

    public abstract void applyTo(GameObject go);


}
