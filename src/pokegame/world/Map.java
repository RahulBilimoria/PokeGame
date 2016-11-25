/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.world;

import java.awt.Graphics;
import pokegame.handler.Handler;
import pokegame.pokemon.move.Move;
import pokegame.pokemon.move.Moveset;
import pokegame.tiles.Tile;
import pokegame.utils.Utils;
import pokegame.world.scripts.Script;
import pokegame.world.scripts.Spawn;
import pokegame.world.scripts.SpawnList;
import pokegame.world.scripts.Warp;

/**
 *
 * @author minim_000
 */
public class Map {

    private Handler handler;
    private int up, down, left, right, curr;
    private String mapName;
    public final static int MAP_WIDTH = 32,
            MAP_HEIGHT = 32;
    private int[][] ground, mask, fringe, scripts;
    private Script[][] scripts2;
    private boolean safeZone; // 0 true
    private int background;

    public Map(Handler handler, int curr) {
        this.handler = handler;
        this.curr = curr;
        scripts2 = new Script[MAP_WIDTH][MAP_HEIGHT];
        loadMap();
        background = 0;
    }

    public void renderGround(Graphics g, int xStart, int xEnd, int yStart, int yEnd) {
        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                int x1 = x, y1 = y;
                if (x < 0) //to keep beyond borders of the currnet within their arraybounds
                {
                    x1 = x + MAP_WIDTH;
                } else if (y < 0) {
                    y1 = y + MAP_HEIGHT;
                } else if (x > MAP_WIDTH - 1) {
                    x1 = x - MAP_WIDTH;
                } else if (y > MAP_HEIGHT - 1) {
                    y1 = y - MAP_HEIGHT;
                }
                if (ground[x1][y1] != -1) {
                    getTile(x1, y1, 0).render(g, (int) (x * Tile.TILE_WIDTH - handler.getGameCamera().getXOffset()),
                            (int) (y * Tile.TILE_HEIGHT - handler.getGameCamera().getYOffset()));
                }
            }
        }
    }

    public void renderMask(Graphics g, int xStart, int xEnd, int yStart, int yEnd) {
        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                int x1 = x, y1 = y;
                if (x < 0) {
                    x1 = x + MAP_WIDTH;
                } else if (y < 0) {
                    y1 = y + MAP_HEIGHT;
                } else if (x > MAP_WIDTH - 1) {
                    x1 = x - MAP_WIDTH;
                } else if (y > MAP_HEIGHT - 1) {
                    y1 = y - MAP_HEIGHT;
                }
                if (mask[x1][y1] != -1) {
                    getTile(x1, y1, 1).render(g, (int) (x * Tile.TILE_WIDTH - handler.getGameCamera().getXOffset()),
                            (int) (y * Tile.TILE_HEIGHT - handler.getGameCamera().getYOffset()));
                }
            }
        }
    }

    public void renderFringe(Graphics g, int xStart, int xEnd, int yStart, int yEnd) {
        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                int x1 = x, y1 = y;
                if (x < 0) {
                    x1 = x + MAP_WIDTH;
                } else if (y < 0) {
                    y1 = y + MAP_HEIGHT;
                } else if (x > MAP_WIDTH - 1) {
                    x1 = x - MAP_WIDTH;
                } else if (y > MAP_HEIGHT - 1) {
                    y1 = y - MAP_HEIGHT;
                }
                if (fringe[x1][y1] != -1) {
                    getTile(x1, y1, 2).render(g, (int) (x * Tile.TILE_WIDTH - handler.getGameCamera().getXOffset()),
                            (int) (y * Tile.TILE_HEIGHT - handler.getGameCamera().getYOffset()));
                }
            }
        }
    }

    public void renderScript(Graphics g, int xStart, int xEnd, int yStart, int yEnd) {
        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                if (scripts[x][y] != -1) {
                    getScript(x, y, false).render(g, (int) (x * Tile.TILE_WIDTH - handler.getGameCamera().getXOffset()),
                            (int) (y * Tile.TILE_HEIGHT - handler.getGameCamera().getYOffset()));
                }
            }
        }
    }

    public Tile getTile(int x, int y, int lvl) {
        if (x < 0 || y < 0 || x >= MAP_WIDTH || y >= MAP_HEIGHT) { // all this needs to be done in tile class
            return Tile.tiles[0];
        }
        Tile t = null;
        switch (lvl) {
            case 0:
                t = Tile.tiles[ground[x][y]];
                break;
            case 1:
                t = Tile.tiles[mask[x][y]];
                break;
            case 2:
                t = Tile.tiles[fringe[x][y]];
                break;
            default:
                break;
        }
        if (t == null) {
            return Tile.tiles[0];
        }
        return t;
    }

    public void setTile(int x, int y, int myX, int myY, int layer) {
        x = x / Tile.TILE_WIDTH; //tilesheet X
        y = y / Tile.TILE_HEIGHT; //tilesheet Y
        myX = (myX - myX % Tile.TILE_WIDTH) / Tile.TILE_WIDTH;
        myY = (myY - myY % Tile.TILE_HEIGHT) / Tile.TILE_HEIGHT;
        switch (layer) {
            case 0:
                ground[myX][myY] = x + y * 8;
                break;
            case 1:
                mask[myX][myY] = x + y * 8;
                break;
            case 2:
                fringe[myX][myY] = x + y * 8;
                break;
            default:
                break;
        }
    }

    public void removeTile(int myX, int myY, int layer) {
        switch (layer) {
            case 0:
                ground[myX][myY] = 0;
                break;
            case 1:
                mask[myX][myY] = -1;
                break;
            case 2:
                fringe[myX][myY] = -1;
                break;
            default:
                break;
        }
    }
    
    public Script getScript(int x, int y, boolean check) {
        if (x < 0 || y < 0 || x >= MAP_WIDTH || y >= MAP_HEIGHT) { // all this needs to be done in tile class
            return Script.scripts[0];
        }       
        Script s = scripts2[x][y];
        if (s == null) {
            return Script.scripts[0];
        }
        /*if (check && handler.getGame().getGameMouseManager().getWorldEditor() != null) {
            return Script.scripts[0];
        }*/
        return s;
    }

    public void setScript(int myX, int myY, int script) {
        myX = myX + (int) handler.getGameCamera().getXOffset();
        myY = myY + (int) handler.getGameCamera().getYOffset();
        myX = (myX - myX % Tile.TILE_WIDTH) / Tile.TILE_WIDTH;
        myY = (myY - myY % Tile.TILE_HEIGHT) / Tile.TILE_HEIGHT;
        scripts[myX][myY] = script;
    }

    private void loadMap() {
        String[] warp, spawn;
        SpawnList spawnList1, spawnList2, spawnList3;
        int warpHead = 0;
        String file = Utils.loadFileAsString("dat/world/maps/map" + curr + ".map");
        String[] tokens = file.split("\\s+");
        mapName = tokens[0];
        up = Utils.parseInt(tokens[1]);
        down = Utils.parseInt(tokens[2]);
        left = Utils.parseInt(tokens[3]);
        right = Utils.parseInt(tokens[4]);
        safeZone = false;
        if (Utils.parseInt(tokens[5]) == 1) {
            safeZone = true;
        }

        ground = new int[MAP_WIDTH][MAP_HEIGHT];
        mask = new int[MAP_WIDTH][MAP_HEIGHT];
        fringe = new int[MAP_WIDTH][MAP_HEIGHT];
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                ground[x][y] = Utils.parseInt(tokens[(x + y * MAP_WIDTH) + 6]);
            }
        }
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                mask[x][y] = Utils.parseInt(tokens[(x + (y + MAP_WIDTH) * MAP_WIDTH) + 6]);
            }
        }
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                fringe[x][y] = Utils.parseInt(tokens[(x + (y + 2 * MAP_WIDTH) * MAP_WIDTH) + 6]);
            }
        }

        file = Utils.loadFileAsString("dat/world/scripts/script" + curr + ".script");
        tokens = file.split("\\s+");
        file = Utils.loadFileAsString("dat/world/warps/warp" + curr + ".warp");
        warp = file.split("\\s+");
        file = Utils.loadFileAsString("dat/world/spawns/spawn" + curr + ".spawn");
        spawn = file.split("\\s+");
        
        spawnList1 = createSpawnList(spawn,1, Utils.parseFloat(spawn[0]));
        spawnList2 = createSpawnList(spawn,31, Utils.parseFloat(spawn[0]));
        spawnList3 = createSpawnList(spawn,61, Utils.parseFloat(spawn[0]));

        scripts = new int[MAP_WIDTH][MAP_HEIGHT];

        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                int i = Utils.parseInt(tokens[x + y * MAP_WIDTH]);
                switch (i){
                    case 0: scripts2[x][y] = Script.scripts[0]; break;
                    case 1: scripts2[x][y] = Script.scripts[1]; break;
                    case 2: scripts2[x][y] = new Warp(
                                                Utils.parseInt(warp[warpHead]),
                                                Utils.parseInt(warp[warpHead + 1]),
                                                Utils.parseInt(warp[warpHead + 2]));
                                                warpHead += 3;  break;
                    case 3: scripts2[x][y] = spawnList1; break;
                    case 4: scripts2[x][y] = spawnList2; break;
                    case 5: scripts2[x][y] = spawnList3; break;
                    default: scripts2[x][y] = Script.scripts[i]; break;
                }
            }
        }
    }
    
    public void saveMap() {
        String file = mapName + "\n" + up + " " + down + " " + left + " " + right + "\n";
        if (safeZone) {
            file = file + "0\n";
        } else {
            file = file + "1\n";
        }
        for (int y = 0; y < MAP_HEIGHT; y++) {  //saves the ground
            for (int x = 0; x < MAP_WIDTH - 1; x++) {
                file = file + ground[x][y] + " ";
            }
            file = file + ground[MAP_WIDTH - 1][y] + "\n";
        }
        for (int y = 0; y < MAP_HEIGHT; y++) {  //saves the mask
            for (int x = 0; x < MAP_WIDTH - 1; x++) {
                file = file + mask[x][y] + " ";
            }
            file = file + mask[MAP_WIDTH - 1][y] + "\n";
        }
        for (int y = 0; y < MAP_HEIGHT; y++) {  //saves the mask
            for (int x = 0; x < MAP_WIDTH - 1; x++) {
                file = file + fringe[x][y] + " ";
            }
            file = file + fringe[MAP_WIDTH - 1][y] + "\n";
        }
        Utils.saveStringAsFile("worlds/maps/map" + curr + ".map", file);
        file = "";
        for (int y = 0; y < MAP_HEIGHT; y++) {  //saves the fringe
            for (int x = 0; x < MAP_WIDTH - 1; x++) {
                file = file + scripts[x][y] + " ";
            }
            if (y != MAP_HEIGHT - 1) {
                file = file + scripts[MAP_WIDTH - 1][y] + "\n";
            } else {
                file = file + scripts[MAP_WIDTH - 1][y];
            }
        }

        Utils.saveStringAsFile("worlds/scripts/script" + curr + ".script", file);
    }
    
    public SpawnList createSpawnList(String[] spawn, int x, float y){
        Moveset moveSet1 = new Moveset(Move.MOVE_LIST[Utils.parseInt(spawn[x+4])],
                                       Move.MOVE_LIST[Utils.parseInt(spawn[x+5])],
                                       Move.MOVE_LIST[Utils.parseInt(spawn[x+6])],
                                       Move.MOVE_LIST[Utils.parseInt(spawn[x+7])]);
        Moveset moveSet2 = new Moveset(Move.MOVE_LIST[Utils.parseInt(spawn[x+14])],
                                       Move.MOVE_LIST[Utils.parseInt(spawn[x+15])],
                                       Move.MOVE_LIST[Utils.parseInt(spawn[x+16])],
                                       Move.MOVE_LIST[Utils.parseInt(spawn[x+17])]);
        Moveset moveSet3 = new Moveset(Move.MOVE_LIST[Utils.parseInt(spawn[x+24])],
                                       Move.MOVE_LIST[Utils.parseInt(spawn[x+25])],
                                       Move.MOVE_LIST[Utils.parseInt(spawn[x+26])],
                                       Move.MOVE_LIST[Utils.parseInt(spawn[x+27])]);
        return new SpawnList(y,3,new Spawn(Utils.parseInt(spawn[x+0]),
                                             Utils.parseInt(spawn[x+1]),
                                             Utils.parseInt(spawn[x+2]),
                                             Utils.parseFloat(spawn[x+3]),
                                             moveSet1,
                                             Utils.parseInt(spawn[x+8]),
                                             Utils.parseFloat(spawn[x+9])),
                                   new Spawn(Utils.parseInt(spawn[x+10]),
                                             Utils.parseInt(spawn[x+11]),
                                             Utils.parseInt(spawn[x+12]),
                                             Utils.parseFloat(spawn[x+13]),
                                             moveSet2,
                                             Utils.parseInt(spawn[x+18]),
                                             Utils.parseFloat(spawn[x+19])),
                                   new Spawn(Utils.parseInt(spawn[x+20]),
                                             Utils.parseInt(spawn[x+21]),
                                             Utils.parseInt(spawn[x+22]),
                                             Utils.parseFloat(spawn[x+23]),
                                             moveSet3,
                                             Utils.parseInt(spawn[x+28]),
                                             Utils.parseFloat(spawn[x+29])));
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName.replaceAll(" ", "_");
    }

    public int getUp() {
        return up;
    }

    public int getDown() {
        return down;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getCurrent() {
        return curr;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public void setSafe(boolean safeZone) {
        this.safeZone = safeZone;
    }
    
    public int getBackground(){
        return background;
    }
}
