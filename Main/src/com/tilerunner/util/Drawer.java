package com.tilerunner.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

/**
 * User: Franjo
 * Date: 27.11.13
 * Time: 16:31
 * Project: TileRunner
 */
public class Drawer {

    public static Texture rectangleTexture(int w, int h, Color c) {
        Pixmap p = new Pixmap(w, h, Pixmap.Format.RGBA8888);
        p.setColor(c);
        p.fillRectangle(0, 0, w, h);
        return new Texture(p);
    }

}
