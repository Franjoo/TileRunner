package com.tilerunner.pathfinding;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * User: Franjo
 * Date: 05.12.13
 * Time: 18:09
 * Project: TileRunner
 */
public class Path {

    // helper
    private Vector2 next;

    private Array<Node> pathNodes;
    private int tileWidth;
    private int tileHeight;

    public Path(Array<Node> pathNodes, int tileWidth, int tileHeight) {
        this.pathNodes = pathNodes;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        // helper
        next = new Vector2();



        setupPath();
    }

    private void setupPath() {
//        for (int i = 0; i < pathNodes.size; i++) {
//            Node n = pathNodes.get(i);
//            n.x *= tileWidth;
//            n.y *= tileHeight;
//        }
    }

    public Vector2 getNext(float x, float y, float speed) {
        if (pathNodes.size > 1) {
            x += (pathNodes.get(1).x * tileWidth - x) * 0.001f;
            y += (pathNodes.get(1).y * tileHeight - y) * 0.001f;
        }


//        if(pathNodes.size != 0){
//            x = pathNodes.get(0).x * tileWidth;
//            y = pathNodes.get(0).y * tileHeight;
//        }
//            int dx = (pathNodes.get(1).x - pathNodes.get(0).x) * tileWidth;
//            int dy = (pathNodes.get(1).y - pathNodes.get(0).y) * tileHeight;
//
//            x -= dx * 0.01f;
//            y -= dy * 0.01f;

        next.set(x, y);
        return next;
    }
}
