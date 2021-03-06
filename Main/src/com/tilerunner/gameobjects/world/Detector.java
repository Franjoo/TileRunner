package com.tilerunner.gameobjects.world;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * User: Franjo
 * Date: 11.11.13
 * Time: 15:15
 * Project: TileRunner
 */
public final class Detector {
    private static final String TAG = Detector.class.getSimpleName();
    private static Detector instance = null;

    // tiled map properties
    private final TiledMap tiledMap;
    private final int numTilesX;
    private final int numTilesY;
    private final int tileWidth;
    private final int tileHeight;
    private final int mapWidth;
    private final int mapHeight;

    private boolean[][] solids;
    private int[][] energies;
    private boolean[][] destructible;
    private Step[][] steps;


    // map lists
    private Array<TiledMapTileLayer> collisionTileLayers;


    private Detector(final TiledMap tiledMap) {
        this.tiledMap = tiledMap;
        instance = this;


        // parse map properties
        numTilesX = Integer.parseInt(tiledMap.getProperties().get("width").toString());
        numTilesY = Integer.parseInt(tiledMap.getProperties().get("height").toString());
        tileWidth = Integer.parseInt(tiledMap.getProperties().get("tilewidth").toString());
        tileHeight = Integer.parseInt(tiledMap.getProperties().get("tileheight").toString());
        mapWidth = numTilesX * tileWidth;
        mapHeight = numTilesY * tileHeight;


        // collision tile layers
        collisionTileLayers = getCollisionTileLayers();


        // fill boolean solid array
        solids = new boolean[numTilesX][numTilesY];
        for (int w = 0; w < solids.length; w++) {
            for (int h = 0; h < solids[w].length; h++) {
                for (int i = 0; i < collisionTileLayers.size; i++) {
                    TiledMapTileLayer.Cell cell = collisionTileLayers.get(i).getCell(w, h);
                    solids[w][h] = cell != null && !cell.getTile().getProperties().containsKey("step");
                }
            }
        }

        // fill steps array
        try {
            steps = new Step[numTilesX][numTilesY];
            for (int w = 0; w < solids.length; w++) {
                for (int h = 0; h < solids[w].length; h++) {
                    for (int i = 0; i < collisionTileLayers.size; i++) {

                        TiledMapTileLayer.Cell cell = collisionTileLayers.get(i).getCell(w, h);

                        if (cell != null) {

                            MapProperties p = cell.getTile().getProperties();
                            if (p.containsKey("step")) {

                                int y1 = Integer.parseInt(p.get("step").toString().split(" ")[0]);
                                int y2 = Integer.parseInt(p.get("step").toString().split(" ")[1]);
                                int rotation = cell.getRotation();
                                boolean flipHorizontally = cell.getFlipHorizontally();
                                boolean flipVertically = cell.getFlipVertically();

                                assert rotation == 0;
                                assert flipVertically;

                                // flip y's
                                if (flipHorizontally) {
                                    int _y1 = y1;
                                    y1 = y2;
                                    y2 = _y1;
                                }

                                steps[w][h] = new Step(y1, y2);

                                System.out.println(steps[w][h]);
                            }

                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("failed to fill steps array at ");
            e.printStackTrace();
        }


        // fill int energies array
        energies = new int[numTilesX][numTilesY];
        destructible = new boolean[numTilesX][numTilesY];
        for (int w = 0; w < energies.length; w++) {
            for (int h = 0; h < energies[w].length; h++) {
                for (int i = 0; i < collisionTileLayers.size; i++) {
                    TiledMapTileLayer.Cell c = collisionTileLayers.get(i).getCell(w, h);
                    if (c != null) {
                        MapProperties p = c.getTile().getProperties();
                        if (p.containsKey("d")) { // destructible
                            energies[w][h] = Integer.parseInt(p.get("d").toString());
                            destructible[w][h] = true;
                        }
                    }
                }
            }
        }


    }


    /**
     * checks whether there is a solid tile at specified position
     *
     * @param x position x
     * @param y position y
     * @return whether point collides with solid tile or not
     */

    public boolean isSolid(final float x, final float y) {
        return solids[(int) (x) / tileWidth][(int) (y) / tileHeight];
    }

    public boolean isSolid(final int x, final int y) {
        return solids[x][y];
    }

    public void setSolid(final int x, final int y, final boolean isSolid) {
        solids[x][y] = isSolid;
    }

    public boolean isDestructible(final float x, final float y) {
        return destructible[(int) (x) / tileWidth][(int) (y) / tileHeight];
    }

    public void setDestructible(final int x, final int y, final boolean isDestructible) {
        destructible[x][y] = isDestructible;
    }

    public int getEnergy(final int x, final int y) {
        return energies[x][y];
    }

    public int getEnergy(final float x, final float y) {
        return energies[(int) (x) / tileWidth][(int) (y) / tileHeight];
    }

    public Step getStep(final int x, final int y) {
        return steps[x][y];
    }

    public Step getStep(final float x, final float y) {
        return steps[(int) (x) / tileWidth][(int) (y) / tileHeight];
    }

    protected void setEnergy(int x, int y, final int energy) {
        energies[x][y] = energy;
    }


    /**
     * returns a collision tiled map tile layer that contains tiles which
     * represents collision objects.<br>
     * <getPosition/>
     * note: the name of a collision tiled map tile layer starts with $c and must not contain a tmx object
     */
    private Array<TiledMapTileLayer> getCollisionTileLayers() {
        Array<TiledMapTileLayer> ctl = new Array<TiledMapTileLayer>();

        MapLayers layers = tiledMap.getLayers();
        for (int i = 0; i < layers.getCount(); i++) {
            if (layers.get(i).getName().startsWith("$c") && layers.get(i).getObjects().getCount() == 0) {
                ctl.add((TiledMapTileLayer) layers.get(i));
            }
        }
        return ctl;
    }


    //*** SINGLETON ***//
    public static Detector getInstance() {
        if (instance == null) throw new InstantiationError(TAG + " has not been initialized");
        return instance;
    }

    public static void initialize(TiledMap tiledMap) {
        new Detector(tiledMap);
    }

    public boolean[][] getSolids() {
        return solids;
    }

    public boolean isStep(float x, float y) {
        return getStep(x, y) != null;
    }

    public boolean containsSolid(Rectangle r) {
        return this.isSolid(r.x, r.y, r.width, r.height);
    }

    public boolean isSolid(final float x, final float y, final float width, final float height) {

        // divisor
        final int numWidth = (int) Math.ceil(width / World.TS);
        final int numHeight = (int) Math.ceil(height / World.TS);

        // factor
        final float dw = width / numWidth;
        final float dh = height / numHeight;

        // check intersection
        for (int w = 0; w <= numWidth; w++) {
            for (int h = 0; h <= numHeight; h++) {
                if (isSolid(x + w * dw, y + h * dh)) {
                    return true;
                }
            }
        }

        return false;
    }


    public static class Step {

        public int y1;
        public int y2;
        public float m;

        public Step(final int y1, final int y2) {
            this.y1 = y1;
            this.y2 = y2;

            m = ((float) (this.y2 - this.y1) * World.STEP) / World.TS;
        }

        @Override
        public String toString() {
            return ("step [" + this.y1 + "] [" + this.y2 + "] [" + m + "]");
        }
    }


}
