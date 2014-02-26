package com.tilerunner.gameobjects.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.tilerunner.gameobjects.crates.Crates;
import com.tilerunner.gameobjects.checkpoints.Checkpoints;
import com.tilerunner.gameobjects.collectibles.Coins;
import com.tilerunner.gameobjects.decorations.Decoration;
import com.tilerunner.gameobjects.decorations.Torch;
import com.tilerunner.gameobjects.enemies.IEnemy;
import com.tilerunner.gameobjects.platforms.Platforms;
import com.tilerunner.gameobjects.player.Player;
import com.tilerunner.gameobjects.traps.Traps;
import com.tilerunner.gameobjects.weapons.Bomb;
import com.tilerunner.gameobjects.weapons.Shiver;
import com.tilerunner.gameobjects.weapons.poolables.Bullet;
import com.tilerunner.gameobjects.weapons.poolables.Chunk;
import com.tilerunner.pathfinding.Pathfinder;
import com.tilerunner.screens.play.PlayController;
import com.tilerunner.shader.Shaders;
import com.tilerunner.util.Drawer;

import java.util.Iterator;

/**
 * User: Franjo
 * Date: 11.11.13
 * Time: 13:22
 * Project: TileRunner
 */

public class World {

    // static finals
    public static final int STEP = 4;
    public static int TILESIZE = 32;

    private Detector detector;
    private Pathfinder pathfinder;

    private TiledMap map;
    private TiledMapRenderer renderer;

    // map properties
    public final int numTilesX;
    public final int numTilesY;
    public final int tileWidth;
    public final int tileHeight;
    public final int mapWidth;
    public final int mapHeight;

    private Array<Player> players;
    private Array<IEnemy> enemies;

    private final PlayController playController;

    /* L A Y E R S */
    private TiledMapTileLayer gameLayer;
    private int[] layers_fg;
    private int[] layers_bg;
    private TiledMapTileLayer normalLayer;

    private Array<TiledMapTileLayer> tileLayers;

    // pools
    private Pool<Bullet> bulletPool;
    private Array<Bullet> activeBullets;
    private Pool<Chunk> chunkPool;
    private Array<Chunk> activeChunks;
    private TextureRegion chunkRegion;

    private Pool<Shiver> shiverPool;
    private Array<Shiver> activeShivers;


    // shaders
    private Shaders shaders;
    private TiledMapTileLayer tl_normal;
    private TiledMapTileLayer tl_diffuse;

    // map object collections
    private Traps traps;
    private Checkpoints checkpoints;
    private Coins coins;
    private Platforms platforms;
    private Crates crates;



    // decorations
    private Array<Decoration> decorations;


    // test
    private Bomb bomb;
    private TiledMapTileLayer ghostLayer;


    public World(PlayController playController) {
        this.playController = playController;

        // load tmx map
        map = new TmxMapLoader().load("maps/playground32.tmx");
//        map = new TmxMapLoader().load("maps/playground.tmx");

        // create collision detector
        Detector.initialize(map);
        detector = Detector.getInstance();

        // parse map properties
        numTilesX = Integer.parseInt(map.getProperties().get("width").toString());
        numTilesY = Integer.parseInt(map.getProperties().get("height").toString());
        tileWidth = Integer.parseInt(map.getProperties().get("tilewidth").toString());
        tileHeight = Integer.parseInt(map.getProperties().get("tileheight").toString());
        mapWidth = numTilesX * tileWidth;
        mapHeight = numTilesY * tileHeight;

        TILESIZE = tileWidth;

        // create pathfinder
        Pathfinder.initialize(detector.getSolids(), tileWidth, tileHeight);
        pathfinder = Pathfinder.getInstance();

        createLayers();

        // create checkpoints
        checkpoints = new Checkpoints(map,this);

        // create traps
        traps = new Traps(map, this);

        // platforms
        platforms = new Platforms(map, this);

        // crates
        crates = new Crates(map, this);

        createDecorations();

        // create coins
        coins = new Coins(map,this);


        // create shaders
        Shaders.initialize();
        shaders = Shaders.getInstance();

        // create lists
        players = new Array<>();

        // create tile layers
        createTileLayer();

        // bulletPool
        createPools();

        chunkRegion = new TextureRegion(Drawer.rectangleTexture(20, 20, new Color(1, 1, 1, 1)));


//        bomb = new Bomb(1280, 470);
    }

    public void updateDecorations(float delta) {
        for (int i = 0; i < decorations.size; i++) {
            decorations.get(i).update(delta);
        }
    }

    public void renderDecorations(SpriteBatch batch) {
        batch.begin();
        for (int i = 0; i < decorations.size; i++) {
            decorations.get(i).draw(batch);
        }
        batch.end();
    }

    private void createDecorations() {
        decorations = new Array<>();
        for (int i = 0; i < map.getLayers().getCount(); i++) {
            if (map.getLayers().get(i).getObjects().getCount() != 0
                    && map.getLayers().get(i).getProperties().containsKey("deco")) {  // decoration object layer
                MapLayer l = map.getLayers().get(i);
                MapObjects objects = l.getObjects();
                for (int j = 0; j < objects.getCount(); j++) {
                    MapObject o = objects.get(j);

//                    o.getProperties().get("type")

                    // torch
                    if (o.getProperties().get("type").equals("torch")) {
                        System.out.println("torch!!!");

                        decorations.add(new Torch(o));
                    }
                }


            }
        }

    }

//    public void updateTraps(float delta) {
//        for (int i = 0; i < traps.size; i++) {
//            Trap t = traps.get(i);
//            t.update(delta);
//            for (int j = 0; j < players.size; j++) {
//                Player p = players.get(j);
//                if (t.isHit(p.getX(), p.getY())) {
//                    System.out.println("Hit");
//                }
//            }
//        }
//    }

//    public void renderTraps(SpriteBatch batch) {
//        for (int i = 0; i < traps.size; i++) {
//            traps.get(i).render(batch);
//        }
//    }

//    private void createTraps() {
//        traps = new Array<>();
//        for (int i = 0; i < map.getLayers().getCount(); i++) {
//            MapLayer l = map.getLayers().get(i);
//            if (l.getObjects().getCount() != 0 && l.getProperties().containsKey("traps")) {
//                MapObjects objects = l.getObjects();
//                for (int j = 0; j < objects.getCount(); j++) {
//                    MapObject o = objects.get(j);
//                    MapProperties p = o.getProperties();
//
//                    // saw
//                    if (o.getProperties().containsKey("saw")) {
//                        float x = Integer.parseInt(p.get("x").toString());
//                        float y = Integer.parseInt(p.get("y").toString());
//                        float w = Integer.parseInt(p.get("w").toString()) * TILESIZE;
//                        float h = Integer.parseInt(p.get("h").toString()) * TILESIZE;
////                        float vr = Float.parseFloat(p.get("vr").toString());
//
//                        Saw saw = new Saw(x, y, w, h, 20, new TextureRegion(new Texture(Gdx.files.internal("saw.png"))));
//                        traps.add(saw);
//                    }
//                }
//            }
//        }
//
//    }

    /**
     * creates pool contexts
     */
    private void createPools() {
        // bullet pool
        activeBullets = new Array<>();
        bulletPool = new Pool<Bullet>() {
            @Override
            protected Bullet newObject() {
                return new Bullet();
            }
        };

        // chunk pool
        activeChunks = new Array<>();
        chunkPool = new Pool<Chunk>() {
            @Override
            protected Chunk newObject() {
                return new Chunk();
            }
        };

        // shiver pool
        final World w = this;
        activeShivers = new Array<>();
        shiverPool = new Pool<Shiver>() {
            @Override
            protected Shiver newObject() {
                return new Shiver(w);
            }
        };


    }

    public void spawnBullet(float posX, float posY, float dirX, float dirY, float variance, float bulletSpeed) {
        Bullet item = bulletPool.obtain();
        item.init(posX, posY, dirX, dirY, variance, bulletSpeed);
        activeBullets.add(item);
    }

    public void spawnChunk(float x, float y, float vx, float vy, float maxScale, float minScale, TextureRegion sourceRegion) {
        Chunk item = chunkPool.obtain();
        item.init(x, y, vx, vy, maxScale, minScale, sourceRegion);
//        item.active = true;
        activeChunks.add(item);
    }

    public void spawnShiver(float x, float y, float vX, float vY, TextureRegion shiver, TextureRegion afterglow) {
        Shiver item = shiverPool.obtain();
        item.init(x, y, vX, vY, shiver, afterglow);
        item.isActive = true;
        activeShivers.add(item);
    }


    public void updatePoolObjects(final float delta) {
        // bullets
        updateBullets(delta);

        // chunks
        updateChunks(delta);

        // shivers
        updateShivers(delta);
    }

    private void updateShivers(float delta) {
        Shiver s;
        int damage = 40;
        int len = activeShivers.size;

//        System.out.println("activeShivers: " + len);
        for (int i = len; --i >= 0; ) {
            s = activeShivers.get(i);
            s.update(delta);
            if (!s.isActive) {
                activeShivers.removeIndex(i);
                shiverPool.free(s);
            }
        }
    }

    public void renderPoolObjects(SpriteBatch batch) {
        // bullets
        renderBullets(batch);

        // chunks
        renderChunks(batch);

        // shivers
        renderShivers(batch);
    }

    private void renderShivers(SpriteBatch batch) {
        batch.begin();
        for (int i = 0; i < activeShivers.size; i++) {
            activeShivers.get(i).draw(batch);
        }
        batch.end();
    }

    public void renderBullets(final SpriteBatch batch) {
        batch.begin();
        for (int i = 0; i < activeBullets.size; i++) {
            activeBullets.get(i).draw(batch);
        }
        batch.end();

    }

    private void renderChunks(SpriteBatch batch) {
        batch.begin();
        for (int i = 0; i < activeChunks.size; i++) {
            activeChunks.get(i).draw(batch);
        }
        batch.end();
    }

    private void updateChunks(float delta) {
        Chunk c;
        int len = activeChunks.size;
//        System.out.println("activeChunks: " + len);
        for (int i = len; --i >= 0; ) {
            c = activeChunks.get(i);
            c.update(delta);

            if (!c.active) {
                activeChunks.removeIndex(i);
                chunkPool.free(c);
            }

        }
    }


    private void updateBullets(final float deltaTime) {

        Bullet b;
        int damage = 40;
        int len = activeBullets.size;

//        System.out.println("activeBullets: " + len);
        for (int i = len; --i >= 0; ) {
            b = activeBullets.get(i);
            b.update(deltaTime);

            int qx = (int) b.getX() / tileWidth;
            int qy = (int) b.getY() / tileHeight;

            if (detector.isSolid(b.getX(), b.getY())) {
                b.alive = false;

                if (detector.isDestructible(b.getX(), b.getY())) {

                    // spawn chunk
                    TextureRegion r = null;
                    for (int j = tileLayers.size - 1; j >= 0; j--) {
                        if (tileLayers.get(j).getCell(qx, qy) != null) {
                            r = tileLayers.get(j).getCell(qx, qy).getTile().getTextureRegion();
                            if (r != null) break;
                        }
                    }
                    spawnChunk(b.getX(), b.getY(),
                            (float) (b.dirX * (10 + Math.random() * 3)),
                            (float) (b.dirY * (10 + Math.random() * 3)),
                            (float) (0.4f + Math.random() * 0.6f),
                            (float) (0.0f + Math.random() * 0.3f),
                            r);


                    int energy = detector.getEnergy(qx, qy);
                    if (energy - damage > 0) {
                        energy -= damage;
                        detector.setEnergy(qx, qy, energy);
                    } else {
                        detector.setSolid(qx, qy, false);
                        detector.setDestructible(qx, qy, false);
                        for (TiledMapTileLayer l : tileLayers) {
                            if (!l.getName().contains("$ghost"))
                                l.setCell(qx, qy, null);
                        }
                    }
                }
            }

            if (!b.alive) {
                activeBullets.removeIndex(i);
                bulletPool.free(b);
            }

        }

    }

    private void createTileLayer() {
        tileLayers = new Array<>();
        for (int i = 0; i < map.getLayers().getCount(); i++) {
            MapLayer l = map.getLayers().get(i);
            if (l.getObjects().getCount() == 0) {
                tileLayers.add((TiledMapTileLayer) l);
            }
        }
    }

    private TiledMapTileLayer copyLayer(TiledMapTileLayer source) {
        TiledMapTileLayer copy = new TiledMapTileLayer(source.getWidth(), source.getHeight(), (int) source.getTileWidth(), (int) source.getTileHeight());
        for (int i = 0; i < source.getWidth(); i++) {
            for (int j = 0; j < source.getHeight(); j++) {
                TiledMapTileLayer.Cell cc = new TiledMapTileLayer.Cell();
                TiledMapTileLayer.Cell sc = source.getCell(i, j);

                if (sc != null) {
                    cc.setTile(sc.getTile());
                    cc.setFlipHorizontally(sc.getFlipHorizontally());
                    cc.setFlipVertically(sc.getFlipVertically());
                    cc.setRotation(sc.getRotation());

                }
                copy.setCell(i, j, cc);

                copy.setVisible(true);
            }
        }
        return copy;
    }

    private void createLayers() {

        Array<Integer> helper_fg = new Array<>();
        Array<Integer> helper_bg = new Array<>();

        for (int i = 0; i < map.getLayers().getCount(); i++) {
            MapLayer l = map.getLayers().get(i);


            // foreground
            if (l.getName().contains("$fg")) {
                helper_fg.add(i);
            }
            // background
            else if (l.getName().contains("$bg")) {
                helper_bg.add(i);
            }

            // gamelayer
            else if (l.getName().contains("$gl")) {
                gameLayer = (TiledMapTileLayer) l;
            }

            // ghost
            else if (l.getName().contains("$ghost")) {
                ghostLayer = (TiledMapTileLayer) l;
            }

        }


        layers_fg = new int[helper_fg.size];
        layers_bg = new int[helper_bg.size];


        for (int i = 0; i < layers_fg.length; i++) layers_fg[i] = helper_fg.get(i).intValue();
        for (int i = 0; i < layers_bg.length; i++) layers_bg[i] = helper_bg.get(i).intValue();

        System.out.println("fg layers: " + layers_fg.length);
        System.out.println("bg layers: " + layers_bg.length);

        // shader layers
//        createShaderLayers();
    }

    private void createShaderLayers() {

        TiledMapTileSet ts_diffuse = map.getTileSets().getTileSet("blocks_diffuse");
        TiledMapTileSet ts_normal = new TiledMapTileSet();
        String name_normal = ts_diffuse.getProperties().get("shaderable").toString();
        Texture texture_normal = new Texture(Gdx.files.internal("maps/tilesets/shaderTextures/" + name_normal + ".png"));

        Iterator<TiledMapTile> it = ts_diffuse.iterator();
        while (it.hasNext()) {
            TiledMapTile t = it.next();
            TextureRegion r = t.getTextureRegion();

            ts_normal.putTile(t.getId(), new StaticTiledMapTile(new TextureRegion(
                    texture_normal, r.getRegionX(), r.getRegionY(), r.getRegionWidth(), r.getRegionHeight())));
        }


        tl_normal = new TiledMapTileLayer(numTilesX, numTilesY, tileWidth, tileHeight);
        tl_diffuse = (TiledMapTileLayer) map.getLayers().get("$bgShaderable");

        // set up cells
        for (int h = 0; h < tl_diffuse.getHeight(); h++) {
            for (int w = 0; w < tl_diffuse.getWidth(); w++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(ts_normal.getTile(tl_diffuse.getCell(w, h).getTile().getId()));

                tl_normal.setCell(w, h, cell);

//                // binding
                tl_normal.getCell(w, h).getTile().getTextureRegion().getTexture().bind(1);
                tl_diffuse.getCell(w, h).getTile().getTextureRegion().getTexture().bind(0);
            }
        }

        map.getLayers().add(tl_diffuse);
        System.out.println("num layers:" + map.getLayers().getCount());
        normalLayer = tl_normal;

    }

    public void createRenderer(SpriteBatch batch) {
        renderer = new OrthogonalTiledMapRenderer(map, batch);
        renderer.setView(playController.getCameraManager().getCamera_shared());
    }

    public Vector2 getSpawn() {
        return new Vector2();
    }


    public void renderForeground() {

//        renderer.setView(playController.getCameraManager().getCamera_shared());
//        renderer.render(new int[]{map.getLayers().getCount() - 1});

        renderer.render(layers_fg);
    }

    public void renderGameLayer(SpriteBatch batch) {
        batch.begin();
//        renderer.setView(playController.getCameraManager().getCamera_shared());
        renderer.renderTileLayer(gameLayer);
        batch.end();
    }

    public void renderBackground() {
//        renderer.setView(playController.getCameraManager().getCamera_shared());
        renderer.render(layers_bg);
    }

    public void renderGhostLayer(SpriteBatch batch) {
        batch.begin();
//        renderer.setView(playController.getCameraManager().getCamera_shared());
//        renderer.render(new int[]{map.getLayers().getCount() - 1});
//        renderer.renderTileLayer(ghostLayer);
        batch.end();
    }


    public void renderShaderLayers(SpriteBatch batch) {

        ShaderProgram p = shaders.getProgram();
        batch.begin();

        shaders.lightPosition.x = Gdx.input.getX();
        shaders.lightPosition.y = (Gdx.graphics.getHeight() - Gdx.input.getY());

        batch.setShader(p);
        p.setUniformf("ambientIntensity", shaders.ambientIntensity);
        p.setUniformf("attenuation", shaders.attenuation);
        p.setUniformf("light", shaders.lightPosition);
        p.setUniformi("useNormals", shaders.useNormals ? 1 : 0);
        p.setUniformi("useShadow", shaders.useShadow ? 1 : 0);
        p.setUniformf("strength", shaders.strength);


        for (int i = 0; i < tl_diffuse.getHeight(); i++) {
            for (int j = 0; j < tl_diffuse.getWidth(); j++) {
                tl_normal.getCell(j, i).getTile().getTextureRegion().getTexture().bind(1);
                tl_diffuse.getCell(j, i).getTile().getTextureRegion().getTexture().bind(0);

                batch.draw(tl_diffuse.getCell(j, i).getTile().getTextureRegion(), j * tileWidth, i * tileHeight);
            }
        }

        batch.setShader(null);
        batch.end();


    }

    public void setPlayers(Player... players) {
        System.out.println("sdasddasd");
        for (Player p : players) {
            this.players.add(p);
        }
    }

    public Array<Player> getPlayers() {
        return players;
    }

    private void parseSpawn() {

    }

    public void createEnemies() {
        enemies = new Array<>();
        for (int i = 0; i < map.getLayers().getCount(); i++) {
            if (map.getLayers().get(i).getObjects().getCount() == 0) {
                TiledMapTileLayer l = (TiledMapTileLayer) map.getLayers().get(i);
                for (int h = 0; h < l.getHeight(); h++) {
                    for (int w = 0; w < l.getWidth(); w++) {

                        // enemy found
                        if (l.getCell(w, h) != null && l.getCell(w, h).getTile().getProperties().containsKey("e")) {

//                            // drone
//                            if (l.getCell(w, h) != null && l.getCell(w, h).getTile().getProperties().containsKey("drone")) {
//                                Drone drone = new Drone(w * tileWidth, h * tileHeight, this);
//                                enemies.add(drone);
//                            }


//
//                            Enemy e = new Enemy(this, w * tileWidth, h * tileHeight, new MachineGun(this), 500);
//                            e.setPlayers(players);
//                            enemies.add(e);
                        }
                    }
                }
            }

        }

    }

    public Array<IEnemy> getEnemies() {
        return enemies;
    }

    public Coins coins(){
        return coins;
    }

    public Checkpoints checkpoints(){
        return checkpoints;
    }

    public void setCamera(OrthographicCamera camera) {
        renderer.setView(camera);
    }

    public MapRenderer getRenderer() {
        return renderer;
    }

    public Traps traps() {
        return traps;
    }

    public Platforms platforms() {
        return platforms;
    }

    public Crates boxes() {
        return crates;
    }


    public class Pools {

        private Array<Pool.Poolable> actives;
        private Array<Pool> pools;


        public void add(Pool pool) {
            pools.add(pool);
        }

        public void remove(Pool pool) {
            pools.removeValue(pool, true);
        }

        public void update(float delta) {

        }

        public void render(SpriteBatch batch) {

        }

    }

}
