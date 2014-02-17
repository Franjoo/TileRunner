package com.tilerunner.pathfinding;

import com.badlogic.gdx.utils.Array;

/**
 * User: Franjo
 * Date: 05.12.13
 * Time: 17:33
 * Project: TileRunner
 */
public class Pathfinder {
    private static final String TAG = Pathfinder.class.getSimpleName();


    // singleton instance
    private static Pathfinder instance = null;

    // helper
    private Array<Node> neighbors;
    private String[][] pathString;

    private Array<Node> pathNodes;

    private Array<Node> openList;
    private Array<Node> closedList;
    private Node[][] nodes;

    private boolean[][] solids;
    private int tileWidth;
    private int tileHeight;

    private Pathfinder(boolean[][] solids, int tileWidth, int tileHeight) {
        this.solids = solids;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;


        // helper
        neighbors = new Array<>();
        pathNodes = new Array<>();

        openList = new Array<>();
        closedList = new Array<>();

        // create nodes list
        final int w = solids.length;
        final int h =solids[0].length;
        nodes = new Node[w][h];
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                nodes[x][y] = new Node(x, y);
            }
        }

    }

    public Path findPath(int sx, int sy, int tx, int ty) {

        openList.clear();
        closedList.clear();
        pathNodes.clear();


        long startTime = System.currentTimeMillis();

        // set start node
        openList.add(nodes[sx][sy]);
        Node currentNode = getCurrent();

        while (currentNode != nodes[tx][ty] && openList.size != 0) {

            currentNode = getCurrent();
            openList.removeValue(currentNode, true);
            closedList.add(currentNode);

            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if (!(x == 0 && y == 0)) {

                        // local coordinates
                        final int xp = x + currentNode.x;
                        final int yp = y + currentNode.y;

                        // neighbor
                        final Node n = nodes[xp][yp];

                        // check whether location is valid
                        if (!isSolid(xp, yp) && !closedList.contains(n, true)) {

                            if (!openList.contains(n, true)) {
                                openList.add(n);
                                n.parent = currentNode;
                                n.g = getCost(x, y) + currentNode.g;
                                n.h = getHeuristic(xp, yp, tx, ty);
                                n.f = n.g + n.h;
                            } else {
                                final int gp = getCost(n.parent.x - n.x, n.parent.y - n.y);
                                final int ga = getCost(currentNode.x - n.x, currentNode.x - n.y);

                                if (ga < gp) {
                                    n.parent = currentNode;
                                    n.g = ga + currentNode.g;
                                    n.h = getHeuristic(xp, yp, tx, ty);
                                    n.f = n.g + n.h;
                                }
                            }

                            openList.sort();

                        }
                    }
                }
            }
        }

        Node target = nodes[tx][ty];
        while (target != nodes[sx][sy]) {
            pathNodes.add(target);
            target = target.parent;
        }

        long endTime = System.currentTimeMillis();

        System.out.println("finding duration: " + (endTime - startTime));

        tracePath();

        return new Path(pathNodes, tileWidth, tileHeight);
    }


    public Node getCurrent() {
        openList.sort();
        return openList.first();
    }

    public int getCost(int x, int y) {
        return (Math.abs(x) + Math.abs(y) == 2) ? 14 : 10;
    }

    public int getHeuristic(int sx, int sy, int tx, int ty) {
        return (Math.abs(tx - sx) + Math.abs(ty - sy)) * 10;
    }

    private boolean isSolid(int x, int y) {
        return !(x < 0 || x >= solids.length || y < 0 || y >= solids[0].length) && solids[x][y];
    }

    public void tracePath() {
        final int w = solids.length;
        final int h = solids[0].length;
        StringBuilder b = new StringBuilder();

        for (int y = h-1; y >= 0; y--) {
            for (int x = 0; x < w; x++) {
                if (solids[x][y]) b.append("#");
                else b.append(" ");
            }
            b.append("\n");
        }

        char c = 'a';
        for (int i = pathNodes.size - 1; i >= 0; i--) {
            Node n = pathNodes.get(i);
            b.setCharAt((h - n.y - 1) * (w + 1) + n.x, c++);
        }

        System.out.println(b.toString());

    }

    public static void initialize(boolean[][] solids, int tileWidth, int tileHeight){
        instance = new Pathfinder(solids, tileWidth, tileHeight);
    }

    public static Pathfinder getInstance() {
        if (instance == null) throw new InstantiationError(TAG + " has not been initialized");
        return instance;
    }

}
