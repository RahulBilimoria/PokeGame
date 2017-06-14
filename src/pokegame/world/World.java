/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.world;

import java.awt.Color;
import java.awt.Graphics;
import pokegame.battle.Battle;
import pokegame.battle.BattleScreen;
import pokegame.entity.player.Player;
import pokegame.handler.GameHandler;
import pokegame.handler.Handler;
import pokegame.pokemart.ShopScreen;
import pokegame.pokemon.Pokemon;
import pokegame.storage.StorageScreen;
import pokegame.tiles.Tile;
import pokegame.world.mapeditor.MapEditor;
import pokegame.world.scripts.Script;
import pokegame.world.scripts.Shop;
import pokegame.world.scripts.SpawnList;
import pokegame.world.scripts.Warp;
import pokegame.npc.NPC;
import pokegame.ui.Dialogue;

/**
 *
 * @author minim_000
 */
public class World {

    private Map currentMap, upMap, downMap, leftMap, rightMap;
    private Handler handler;
    private GameHandler gameHandler;
    private BattleScreen battleScreen;
    private StorageScreen storage;
    private ShopScreen shop;
    private Battle battle;
    private Pokemon p;
    private MapEditor me;

    private float playerX, playerY;
    private boolean b;
    private boolean edit;
    private boolean playerEnabled;
    private boolean scriptsVisible;

    public World(Handler handler) {
        this.handler = handler;
        gameHandler = new GameHandler(new Player(handler, 100, 100));
        loadMap(new Map(handler, 1, 0, 0));

        gameHandler.getPlayer().setX(8 * Tile.TILE_WIDTH);
        gameHandler.getPlayer().setY(8 * Tile.TILE_HEIGHT);
        b = true;
        playerEnabled = true;
    }

    public void loadMap(Map map) {
        currentMap = new Map(handler, map.getCurrent(), 0, 0);
        upMap = new Map(handler, map.getUp(), 0, -32 * Tile.TILE_HEIGHT);
        downMap = new Map(handler, map.getDown(), 0, 32 * Tile.TILE_HEIGHT);
        leftMap = new Map(handler, map.getLeft(), -32 * Tile.TILE_WIDTH, 0);
        rightMap = new Map(handler, map.getRight(), 32 * Tile.TILE_WIDTH, 0);
    }

    public void tick() {
        gameHandler.tick();
        tickMaps();
        if (edit) {
            gameHandler.getPlayer().setSpeed(8.0f);
            me.tick();
        } else {
            gameHandler.getPlayer().setSpeed(2.0f);
        }
        if (battle != null) {
            battle.tick();
        }
        if (!gameHandler.getPlayer().getIsMoving()) {
            b = true;
        }
        playerX = gameHandler.getPlayer().getX() / Tile.TILE_WIDTH;
        playerY = gameHandler.getPlayer().getY() / Tile.TILE_HEIGHT;
        if (!edit) {
            if (playerX <= -1) {
                loadMap(leftMap);
                gameHandler.getPlayer().setX((Map.MAP_WIDTH - 1) * Tile.TILE_WIDTH);
            } else if (playerX >= Map.MAP_WIDTH) {
                loadMap(rightMap);
                gameHandler.getPlayer().setX(0);
            } else if (playerY <= -1) {
                loadMap(upMap);
                gameHandler.getPlayer().setY((Map.MAP_HEIGHT - 1) * Tile.TILE_HEIGHT);
            } else if (playerY >= Map.MAP_HEIGHT) {
                loadMap(downMap);
                gameHandler.getPlayer().setY(0);
            }
            checkWarp(playerX, playerY);
            checkSpawn(playerX, playerY);
            checkHeal(playerX, playerY);
            checkStorage(playerX, playerY);
            checkShop(playerX, playerY);
            gameHandler.getPlayer().setMoved(false);
        } else {
            setBoundaries();
        }
    }

    public void render(Graphics g) {
        int xStart = (int) Math.max(0, handler.getGameCamera().getXOffset() / Tile.TILE_WIDTH),
                xEnd = (int) Math.min(Map.MAP_WIDTH, (handler.getGameCamera().getXOffset() + handler.getWidth()) / Tile.TILE_WIDTH + 1),
                yStart = (int) Math.max(0, handler.getGameCamera().getYOffset() / Tile.TILE_HEIGHT),
                yEnd = (int) Math.min(Map.MAP_HEIGHT, (handler.getGameCamera().getYOffset() + handler.getHeight()) / Tile.TILE_HEIGHT + 1),
                xUnderflow = (int) Math.min(0, handler.getGameCamera().getXOffset() / Tile.TILE_WIDTH),
                xOverflow = (int) Math.max(Map.MAP_WIDTH, (handler.getGameCamera().getXOffset() + handler.getWidth()) / Tile.TILE_WIDTH + 1),
                yUnderflow = (int) Math.min(0, handler.getGameCamera().getYOffset() / Tile.TILE_HEIGHT),
                yOverflow = (int) Math.max(Map.MAP_HEIGHT, (handler.getGameCamera().getYOffset() + handler.getHeight()) / Tile.TILE_HEIGHT + 1);

        currentMap.renderGround(g, xStart, xEnd, yStart, yEnd);
        upMap.renderGround(g, xStart, xEnd, yUnderflow - 1, 0);
        downMap.renderGround(g, xStart, xEnd, Map.MAP_HEIGHT, yOverflow);
        leftMap.renderGround(g, xUnderflow - 1, 0, yStart, yEnd);
        rightMap.renderGround(g, Map.MAP_WIDTH, xOverflow, yStart, yEnd);

        currentMap.renderMask(g, xStart, xEnd, yStart, yEnd);
        upMap.renderMask(g, xStart, xEnd, yUnderflow - 1, 0);
        downMap.renderMask(g, xStart, xEnd, Map.MAP_HEIGHT, yOverflow);
        leftMap.renderMask(g, xUnderflow - 1, 0, yStart, yEnd);
        rightMap.renderMask(g, Map.MAP_WIDTH, xOverflow, yStart, yEnd);

        gameHandler.render(g);

        currentMap.renderFringe(g, xStart, xEnd, yStart, yEnd);
        upMap.renderFringe(g, xStart, xEnd, yUnderflow - 1, 0);
        downMap.renderFringe(g, xStart, xEnd, Map.MAP_HEIGHT, yOverflow);
        leftMap.renderFringe(g, xUnderflow - 1, 0, yStart, yEnd);
        rightMap.renderFringe(g, Map.MAP_WIDTH, xOverflow, yStart, yEnd);

        if (edit) {
            me.render(g);
            if (scriptsVisible) {
                currentMap.renderScript(g, xStart, xEnd, yStart, yEnd);
            }
        }
    }
    
    public void tickMaps(){
        currentMap.tick();
        upMap.tick();
        downMap.tick();
        leftMap.tick();
        rightMap.tick();
    }

    public void setTile(int x, int y, int myX, int myY, int width, int height, int layer, int tilesheet) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (myX + i >= 0 && myY + j >= 0 && myX + i < Map.MAP_WIDTH && myY + j < Map.MAP_HEIGHT) {
                    currentMap.setTile(x + i, y + j, myX + i, myY + j, layer, tilesheet);
                }
            }
        }
    }

    public void removeTile(int myX, int myY, int layer) {
        if (myX >= 0 && myX < Map.MAP_WIDTH && myY >= 0 && myY < Map.MAP_HEIGHT) {
            currentMap.removeTile(myX, myY, layer);
        }
    }

    public void setScript(int myX, int myY, int script) {
        currentMap.setScript(myX, myY, script, 0, 0, 0);
    }

    public void setWarpScript(int myX, int myY, int script, int map, int mapx, int mapy) {
        System.out.println("Map Number: " + map);
        System.out.println("Map X value: " + mapx);
        System.out.println("Map Y value: " + mapy);
        currentMap.setScript(myX, myY, 2, map, mapx, mapy);
    }

    public void setSpawns(SpawnList s1, SpawnList s2, SpawnList s3) {
        currentMap.setSpawns(s1, s2, s3);
    }

    public void fill(int x, int y, int layer, int tilesheet) {
        for (int i = 0; i < Map.MAP_WIDTH; i++) {
            for (int j = 0; j < Map.MAP_HEIGHT; j++) {
                currentMap.setTile(x, y, i, j, layer, tilesheet);
            }
        }
    }

    public void clearScripts() {
        for (int i = 0; i < Map.MAP_WIDTH; i++) {
            for (int j = 0; j < Map.MAP_HEIGHT; j++) {
                currentMap.setScript(i, j, 0, 0, 0, 0);
            }
        }
    }

    public void clearLayer(int layer) {
        for (int i = 0; i < Map.MAP_WIDTH; i++) {
            for (int j = 0; j < Map.MAP_HEIGHT; j++) {
                currentMap.removeTile(i, j, layer);
            }
        }
    }

    public void clearAll() {
        for (int i = 0; i < Map.MAP_WIDTH; i++) {
            for (int j = 0; j < Map.MAP_HEIGHT; j++) {
                currentMap.removeTile(i, j, 0);
                currentMap.removeTile(i, j, 1);
                currentMap.removeTile(i, j, 2);
                currentMap.removeTile(i, j, 3);
                currentMap.removeTile(i, j, 4);
                currentMap.removeTile(i, j, 5);
                currentMap.setScript(i, j, 0, 0, 0, 0);

            }
        }
    }

    public void saveWorld() {
        currentMap.saveMap();
        currentMap.saveSpawnList();
    }

    public Map getMap() {
        return currentMap;
    }

    public void saveProperties(String mn, int u, int d, int l, int r, boolean s) {
        currentMap.setMapName(mn);
        currentMap.setUp(u);
        currentMap.setDown(d);
        currentMap.setLeft(l);
        currentMap.setRight(r);
        currentMap.setSafe(s);
        currentMap.saveMap();
        loadMap(currentMap);
    }

    public GameHandler getEntityManager() {
        return gameHandler;
    }

    public void setBoundaries() {
        Player p = gameHandler.getPlayer();
        if (p.getX() / Tile.TILE_WIDTH < 0) { // not working wtf gotta set moving to false
            p.setX(0 * Tile.TILE_WIDTH);
            p.setIsMoving(false);
        } else if (p.getY() / Tile.TILE_HEIGHT < 0) {
            p.setY(0 * Tile.TILE_HEIGHT);
            p.setIsMoving(false);
        } else if (p.getX() / Tile.TILE_WIDTH > Map.MAP_WIDTH - 1) { // not working wtf gotta set moving to false
            p.setX((Map.MAP_WIDTH - 1) * Tile.TILE_WIDTH);
            p.setIsMoving(false);
        } else if (p.getY() / Tile.TILE_HEIGHT > Map.MAP_HEIGHT - 1) {
            p.setY((Map.MAP_HEIGHT - 1) * Tile.TILE_HEIGHT);
            p.setIsMoving(false);
        }
    }

    public void checkWarp(float x, float y) {
        Script s = currentMap.getScript((int) x, (int) y);
        if (s.getScriptNumber() == 2) {
            Warp s1 = (Warp) s;
            gameHandler.getPlayer().setIsMoving(false);
            gameHandler.getPlayer().setX(s1.getxCoord() * Tile.TILE_WIDTH);
            gameHandler.getPlayer().setY(s1.getyCoord() * Tile.TILE_HEIGHT);
            handler.getKeyManager().reset();
            loadMap(new Map(handler, s1.getMapNumber(), 0, 0));
        }
    }

    public void checkSpawn(float x, float y) {
        Script s = currentMap.getScript((int) x, (int) y);
        if (s.getScriptNumber() == 3 || s.getScriptNumber() == 4 || s.getScriptNumber() == 5) {
            if (gameHandler.getPlayer().getMoved()) {
                gameHandler.getPlayer().setMoved(false);
                SpawnList s1 = (SpawnList) s;
                float spawnRate = s1.getSpawnRate();
                if (Math.random() < spawnRate && b) {
                    handler.getKeyManager().reset();
                    gameHandler.getPlayer().setEnabled(false);
                    b = false;
                    p = s1.getSpawn().getPokemon();
                    battle = new Battle(handler, gameHandler.getPlayer(), s1.getSpawn().getPokemon());
                    battleScreen = new BattleScreen(battle);
                }
            }
        }
    }

    public void checkHeal(float x, float y) {
        Script s = currentMap.getScript((int) x, (int) y);
        if (s.getScriptNumber() == 6) {
            gameHandler.getPlayer().getParty().heal();
            if (gameHandler.getPlayer().getMoved())
                handler.getGame().addText("Your pokemon have been restored to full health.\n", Color.green);
        }
    }

    public void checkShop(float x, float y) {
        Script s = currentMap.getScript((int) x, (int) y);
        if (s.getScriptNumber() == 7 && shop == null) {
            if (gameHandler.getPlayer().getMoved()) {
                gameHandler.getPlayer().setMoved(false);
                gameHandler.getPlayer().setEnabled(false);
                handler.getKeyManager().reset();
                Shop s1 = (Shop) s;
                shop = new ShopScreen(handler, gameHandler.getPlayer().getBag(), s1.getFileName());
            }
        }
    }

    public void checkStorage(float x, float y) {
        Script s = currentMap.getScript((int) x, (int) y);
        if (s.getScriptNumber() == 8 && storage == null) {
            if (gameHandler.getPlayer().getMoved()) {
                gameHandler.getPlayer().setMoved(false);
                gameHandler.getPlayer().setEnabled(false);
                handler.getKeyManager().reset();
                storage = new StorageScreen(handler);
            }
        }
    }
    
    public void interact(int x, int y){
        NPC[] npcs = currentMap.getNpcs();
        for (int z = 0; z < npcs.length; z++){
            if (npcs[z].getXTile() == x && npcs[z].getYTile() == y){
                openDialogue(npcs[z]);
                return;
            }
        }
        gameHandler.getPlayer().setEnabled(true);
    }
    
    public void openDialogue(NPC npc){
        Dialogue d = new Dialogue(handler, npc);
    }

    public void openMapEditor() {
        if (!edit) {
            me = new MapEditor(handler, this);
            edit = true;
        } else {

        }
    }

    public void closeMapEditor() {
        edit = false;
        me = null;
    }

    public void reload() {
        loadMap(currentMap);
    }

    public void setExit() {
        gameHandler.getPlayer().setEnabled(true);
        handler.getKeyManager().reset();
        battle = null;
    }

    public void setPlayerEnabled(boolean enabled) {
        playerEnabled = enabled;
    }

    public void setScriptsVisible(boolean scriptsVisible) {
        this.scriptsVisible = scriptsVisible;
    }

    public int getMapNumber() {
        return currentMap.getCurrent();
    }

    public int getPlayerX() {
        return (int) playerX;
    }

    public int getPlayerY() {
        return (int) playerY;
    }

    public boolean getPlayerEnabled() {
        return playerEnabled;
    }

    public Player getPlayer() {
        return gameHandler.getPlayer();
    }

    public boolean getEdit() {
        return edit;
    }

    public MapEditor getMapEditor() {
        return me;
    }

    public void closeStorage() {
        handler.getKeyManager().reset();
        gameHandler.getPlayer().setEnabled(true);
        storage = null;
    }

    public void closeShop() {
        handler.getKeyManager().reset();
        gameHandler.getPlayer().setEnabled(true);
        shop = null;
    }
}
