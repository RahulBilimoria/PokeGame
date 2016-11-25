/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.world;

import java.awt.Graphics;
import pokegame.battle.Battle;
import pokegame.battle.BattleScreen;
import pokegame.entity.player.Player;
import pokegame.handler.GameHandler;
import pokegame.handler.Handler;
import pokegame.pokemon.Pokemon;
import pokegame.tiles.Tile;
import pokegame.world.scripts.Script;
import pokegame.world.scripts.SpawnList;
import pokegame.world.scripts.Warp;

/**
 *
 * @author minim_000
 */
public class World {

    private Map currentMap, upMap, downMap, leftMap, rightMap;
    private Handler handler;
    private GameHandler gameHandler;
    private BattleScreen battleScreen;
    private Battle battle;
    private Pokemon p;
    
    private float playerX, playerY;
    private boolean b;
    private boolean playerEnabled;

    public World(Handler handler) {
        this.handler = handler;
        gameHandler = new GameHandler(handler, new Player(handler, 100, 100));
        loadMap(new Map(handler, 1));

        gameHandler.getPlayer().setX(4 * Tile.TILE_WIDTH);
        gameHandler.getPlayer().setY(4 * Tile.TILE_HEIGHT);
        b = true;
        playerEnabled = true;
    }

    public void loadMap(Map map) {
        currentMap = new Map(handler, map.getCurrent());
        upMap = new Map(handler, map.getUp());
        downMap = new Map(handler, map.getDown());
        leftMap = new Map(handler, map.getLeft());
        rightMap = new Map(handler, map.getRight());
    }

    public void tick() {
            gameHandler.tick();
            if (battle != null){
                battle.tick();
            }
            if (!gameHandler.getPlayer().getIsMoving()){
                b = true;
            }
            playerX = gameHandler.getPlayer().getX() / Tile.TILE_WIDTH;
            playerY = gameHandler.getPlayer().getY() / Tile.TILE_HEIGHT;
        //if (handler.getGame().getGameMouseManager().getWorldEditor() == null) {
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
        //} else {
            //setBoundaries();
        //}
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

        currentMap.renderMask(g, xStart, xEnd, yStart, yEnd+1);
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

        /*if (handler.getGame().getGameMouseManager().getWorldEditor() != null
                && handler.getGame().getGameMouseManager().getWorldEditor().getScript().isSelected()) {*/
            currentMap.renderScript(g, xStart, xEnd, yStart, yEnd);
        //}
    }

    public void setTile(int x, int y, int myX, int myY, int width, int height, int layer) {
        myX = myX + (int) handler.getGameCamera().getXOffset();
        myY = myY + (int) handler.getGameCamera().getYOffset();
        if (myX > 0 && myY > 0 && myX < Map.MAP_WIDTH * Tile.TILE_WIDTH && myY < Map.MAP_HEIGHT * Tile.TILE_HEIGHT) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    currentMap.setTile(x + Tile.TILE_WIDTH * i, y + Tile.TILE_HEIGHT * j, myX + Tile.TILE_WIDTH * i, myY + Tile.TILE_HEIGHT * j, layer);
                }
            }
        }
    }

    public void removeTile(int myX, int myY, int layer) {
        myX = myX + (int) handler.getGameCamera().getXOffset();
        myY = myY + (int) handler.getGameCamera().getYOffset();
        myX = (myX - myX % Tile.TILE_WIDTH) / Tile.TILE_WIDTH;
        myY = (myY - myY % Tile.TILE_HEIGHT) / Tile.TILE_HEIGHT;
        if (myX > 0 && myX < Map.MAP_WIDTH && myY > 0 && myY < Map.MAP_HEIGHT) {
            currentMap.removeTile(myX, myY, layer);
        }
    }

    public void setScript(int myX, int myY, int script) {
        currentMap.setScript(myX, myY, script);
    }

    public void saveWorld() {
        currentMap.saveMap();
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
        Script s = currentMap.getScript((int) x, (int) y, true);
        if (s.getScriptNumber() == 2) {
            Warp s1 = (Warp) s;
            gameHandler.getPlayer().setX(s1.getxCoord() * Tile.TILE_WIDTH);
            gameHandler.getPlayer().setY(s1.getyCoord() * Tile.TILE_HEIGHT);
            loadMap(new Map(handler, s1.getMapNumber()));
        }
    }

    public void checkSpawn(float x, float y) {
        Script s = currentMap.getScript((int) x, (int) y, true);
        if (s.getScriptNumber() == 3 || s.getScriptNumber() == 4 || s.getScriptNumber() == 5) {
            if (gameHandler.getPlayer().getMoved()) {
                gameHandler.getPlayer().setMoved(false);
                SpawnList s1 = (SpawnList) s;
                float spawnRate = s1.getSpawnRate();
                if (Math.random() < spawnRate && b){
                    handler.getKeyManager().reset();
                    gameHandler.getPlayer().setEnabled(false);
                    b = false;
                    p = s1.getSpawn().getPokemon();
                    battle = new Battle(handler, gameHandler.getPlayer(), s1.getSpawn().getPokemon());
                    battleScreen = new BattleScreen(battle);
                }
                //new battle screen etc gotta create that shit
            }
        }
    }

    public void reload() {
        loadMap(currentMap);
    }
    
    public void setExit(){
        gameHandler.getPlayer().setEnabled(true);
        handler.getKeyManager().reset();
        battle = null;
    }
    
    public void setPlayerEnabled(boolean enabled){
        playerEnabled = enabled;
    }
    
    public boolean getPlayerEnabled(){
        return playerEnabled;
    }
    
    public Player getPlayer(){
        return gameHandler.getPlayer();
    }
}
