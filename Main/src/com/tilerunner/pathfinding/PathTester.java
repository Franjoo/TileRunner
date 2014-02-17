package com.tilerunner.pathfinding;

import com.badlogic.gdx.math.Vector2;

/**
 * User: Franjo
 * Date: 05.12.13
 * Time: 16:41
 * Project: TileRunner
 */
public class PathTester {

    public boolean[][] solids;
    private char solid = '#';
    private Vector2 start;
    private Vector2 goal;


    private Pathfinder finder;
    private Path path;

    public final String[] map = new String[]{
            "##############################",
            "##     s                  ####",
            "###    ###   #### ###### #####",
            "###    ####    ## ###### #####",
            "###    ####           ##    ##",
            "### ## ##      ###### ## #####",
            "### ## #### ######### ##    ##",
            "#   ## ####       ###    ## ##",
            "###           ### ###  #### ##",
            "##### ###  ###### #### #### ##",
            "##    #### ## ###       g   ##",
            "#####             #  ##### ###",
            "##############################",
    };

    public PathTester() {
        createSolids();
        setStart();
        setGoal();


        Pathfinder.initialize(solids,1,1);
        finder = Pathfinder.getInstance();
        path = finder.findPath((int) start.x, (int) start.y, (int) goal.x, (int) goal.y);

    }

    private void setGoal() {
        goal = new Vector2();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length(); x++) {
                if (map[y].contains("g")) goal.set(map[y].indexOf("g"), y);
            }
        }
    }

    private void setStart() {
        start = new Vector2();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length(); x++) {
                if (map[y].contains("s")) start.set(map[y].indexOf("s"), y);
            }
        }
    }

    private void createSolids() {

        final int w = map[0].length();
        final int h = map.length;

        solids = new boolean[w][h];
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                solids[x][y] = map[y].charAt(x) == '#';
            }
        }

    }

    public void traceMap() {
        StringBuilder s = new StringBuilder();

        for (int y = 0; y < solids[0].length; y++) {
            for (int x = 0; x < solids.length; x++) {
                if (solids[x][y]) s.append(solid);
                else s.append(" ");
            }
            s.append(" " + y + "\n");
        }

        char c = 'A';
        for (int i = 0; i < solids.length; i++) {
            s.append(c++);
        }

        System.out.println(s.toString());
    }

    public static void main(String[] args) {
        PathTester t = new PathTester();
    }

}
