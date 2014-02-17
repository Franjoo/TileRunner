package com.tilerunner.shader;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * User: Franjo
 * Date: 18.11.13
 * Time: 14:51
 * Project: TileRunner
 */
public class ShaderLayer extends TiledMapTileLayer {


    /**
     * Creates TiledMap layer
     *
     * @param width      layer width in tiles
     * @param height     layer height in tiles
     * @param tileWidth  tile width in pixels
     * @param tileHeight tile height in pixels
     */
    public ShaderLayer(int width, int height, int tileWidth, int tileHeight) {
        super(width, height, tileWidth, tileHeight);
    }
}
