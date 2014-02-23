package com.tilerunner.gameobjects.weapons;

import com.tilerunner.gameobjects.GameObject;
import com.tilerunner.gameobjects.player.IPlayable;

/**
 * User: Franjo
 * Date: 13.11.13
 * Time: 16:44
 * Project: TileRunner
 */
public abstract class Weapon extends GameObject {

    public abstract void shoot(float x, float y);

    public abstract void applyTo(GameObject go);



}
