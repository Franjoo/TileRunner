package com.tilerunner.pathfinding;

/**
 * User: Franjo
 * Date: 05.12.13
 * Time: 15:27
 * Project: TileRunner
 */
public class Node implements Comparable {

    public int x, y;
    public Node parent;

    public int f, g, h;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Object other) {
        Node o1 = this;
        Node o2 = (Node) other;

        if (o1.f < o2.f) return -1;
        if (o1.f > o2.f) return 1;
        return 0;
    }

    @Override
    public String toString() {
        return x + " " + y;
    }

}
