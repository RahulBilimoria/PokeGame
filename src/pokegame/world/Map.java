/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.world;

import java.awt.Graphics;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import pokegame.handler.Handler;
import pokegame.item.Item;
import pokegame.npc.NPC;
import pokegame.npc.PokemonTrainer;
import pokegame.npc.QuestCharacter;
import pokegame.pokemon.Pokemon;
import pokegame.pokemon.move.Moveset;
import pokegame.pokemon.nature.Nature;
import pokegame.pokemon.status.Status;
import pokegame.tiles.Tile;
import pokegame.utils.DocumentParser;
import pokegame.utils.Utils;
import pokegame.world.scripts.Script;
import pokegame.world.scripts.Shop;
import pokegame.world.scripts.Spawn;
import pokegame.world.scripts.SpawnList;
import pokegame.world.scripts.Warp;

/**
 *
 * @author minim_000
 */
public class Map {

    private class Tiles {

        private int tilesheet;
        private int tile;

        public Tiles(int tilesheet, int tile) {
            this.tilesheet = tilesheet;
            this.tile = tile;
        }

        public int getTilesheet() {
            return tilesheet;
        }

        public int getTile() {
            return tile;
        }

        public void setTileSheet(int t) {
            tilesheet = t;
        }

        public void setTile(int t) {
            tile = t;
        }
    }

    private Handler handler;
    private int up, down, left, right, curr;
    private String mapName;
    public final static int MAP_WIDTH = 32,
            MAP_HEIGHT = 32;
    private Tiles[][] ground1, ground2, mask1, mask2, fringe1, fringe2;
    private SpawnList spawnList1, spawnList2, spawnList3;
    private Script[][] scripts;
    private NPC[] npcs = new NPC[10];
    private boolean safeZone; // 0 true
    private int background;

    public Map(Handler handler, int curr, int x, int y) {
        this.handler = handler;
        this.curr = curr;
        scripts = new Script[MAP_WIDTH][MAP_HEIGHT];
        loadMap();
        background = 0;
        spawnNPCs(x, y);
    }
    
    private void spawnNPCs(int xOffset, int yOffset){
        int x = 0;
        Document d = DocumentParser.loadDataFile("dat/world/npcs/npc1.xml");
        /*NodeList list = d.getElementsByTagName("Quest");
        for (x = 0; x < list.getLength(); x++) {
            Element character = (Element) (list.item(x));
            Element speech = (Element) character.getElementsByTagName("Speech").item(0);
            boolean canTurn = false;
            boolean canMove = false;
            boolean isSolid = false;
            if (Utils.parseInt(character.getElementsByTagName("LooksAround").item(0).getTextContent()) == 1)
                canTurn = true;
            if (Utils.parseInt(character.getElementsByTagName("WalksAround").item(0).getTextContent()) == 1)
                canMove = true;
            if (Utils.parseInt(character.getElementsByTagName("Solid").item(0).getTextContent()) == 1)
                isSolid = true;
            int length = Utils.parseInt(speech.getAttribute("size"));
            String[] s = new String[length];
            for (int y = 0; y < length; y++){
                s[y] = speech.getElementsByTagName("Text" + (y+1)).item(0).getTextContent();
            }
            npcs[x] = new QuestCharacter(handler,
                    Utils.parseInt(character.getElementsByTagName("ID").item(0).getTextContent()),
                    character.getElementsByTagName("Name").item(0).getTextContent(),
                    Utils.parseInt(character.getElementsByTagName("SpriteID").item(0).getTextContent()),
                    Utils.parseInt(character.getElementsByTagName("PortraitID").item(0).getTextContent()),
                    Utils.parseInt(character.getElementsByTagName("Direction").item(0).getTextContent()),
                    Utils.parseInt(character.getElementsByTagName("X").item(0).getTextContent()) * Tile.TILE_WIDTH + xOffset,
                    Utils.parseInt(character.getElementsByTagName("Y").item(0).getTextContent()) * Tile.TILE_HEIGHT + yOffset,
                    Utils.parseInt(character.getElementsByTagName("DistanceFromCenter").item(0).getTextContent()),
                    canTurn, canMove, isSolid,
                    Utils.parseInt(character.getElementsByTagName("QuestID").item(0).getTextContent()),
                    Utils.parseInt(character.getElementsByTagName("QuestSequence").item(0).getTextContent()),
                    s);
        }*/
        NodeList list = d.getElementsByTagName("Trainer");
        for (x = 0; x < list.getLength(); x++) {
            Element character = (Element) (list.item(x));
            NodeList party = ((Element)character.getElementsByTagName("Party").item(0)).getElementsByTagName("Pokemon");
            NodeList bag = character.getElementsByTagName("Bag");
            Pokemon pkmn[] = new Pokemon[party.getLength()];
            for (int y = 0; y < party.getLength(); y++){
                Element poke = (Element)(party.item(y));
                Element moveset = (Element) poke.getElementsByTagName("Moveset").item(0);
                boolean shiny = false;
                boolean gender = false;
                if (Utils.parseInt(poke.getElementsByTagName("Shiny").item(0).getTextContent()) == 1)
                    shiny = true;
                if (Utils.parseInt(poke.getElementsByTagName("Gender").item(0).getTextContent()) == 1)
                    gender = true;
                pkmn[y] = new Pokemon(handler,
                    Utils.parseInt(poke.getElementsByTagName("ID").item(0).getTextContent()),
                    shiny,
                    Utils.parseInt(poke.getElementsByTagName("Level").item(0).getTextContent()),
                    Utils.parseInt(poke.getElementsByTagName("Hp").item(0).getTextContent()),
                    Utils.parseInt(poke.getElementsByTagName("Attack").item(0).getTextContent()),
                    Utils.parseInt(poke.getElementsByTagName("Defense").item(0).getTextContent()),
                    Utils.parseInt(poke.getElementsByTagName("SpecialAttack").item(0).getTextContent()),
                    Utils.parseInt(poke.getElementsByTagName("SpecialDefense").item(0).getTextContent()),
                    Utils.parseInt(poke.getElementsByTagName("Speed").item(0).getTextContent()),
                    poke.getElementsByTagName("Nickname").item(0).getTextContent(),
                    Status.getStatusById(Utils.parseInt(poke.getElementsByTagName("ID").item(0).getTextContent())),
                    Utils.parseInt(poke.getElementsByTagName("TpPoints").item(0).getTextContent()),
                    Utils.parseInt(poke.getElementsByTagName("Friendship").item(0).getTextContent()),
                    Utils.parseInt(poke.getElementsByTagName("FriendshipRate").item(0).getTextContent()),
                    Nature.getNatureById(Utils.parseInt(poke.getElementsByTagName("ID").item(0).getTextContent())),
                    gender,
                    new Moveset(Utils.parseInt(moveset.getElementsByTagName("Move1").item(0).getTextContent()),
                                Utils.parseInt(moveset.getElementsByTagName("Move2").item(0).getTextContent()),
                                Utils.parseInt(moveset.getElementsByTagName("Move3").item(0).getTextContent()),
                                Utils.parseInt(moveset.getElementsByTagName("Move4").item(0).getTextContent())));
            }
            Item items[] = new Item[bag.getLength()];
            int itemAmount[] = new int[bag.getLength()];
            for (int y = 0; y < bag.getLength(); y++){
                Element item = (Element) (bag.item(y));
                items[y] = Item.items[Utils.parseInt(item.getElementsByTagName("ID").item(0).getTextContent())];
                itemAmount[y] = Utils.parseInt(item.getElementsByTagName("Amount").item(0).getTextContent());
            }
            boolean canTurn = false;
            boolean canMove = false;
            boolean isSolid = false;
            boolean aggresive = false;
            if (Utils.parseInt(character.getElementsByTagName("LooksAround").item(0).getTextContent()) == 1)
                canTurn = true;
            if (Utils.parseInt(character.getElementsByTagName("WalksAround").item(0).getTextContent()) == 1)
                canMove = true;
            if (Utils.parseInt(character.getElementsByTagName("Solid").item(0).getTextContent()) == 1)
                isSolid = true;
            if (Utils.parseInt(character.getElementsByTagName("Aggresive").item(0).getTextContent()) == 1)
                aggresive = true;
            npcs[x] = new PokemonTrainer(handler,
                    Utils.parseInt(character.getElementsByTagName("ID").item(0).getTextContent()),
                    character.getElementsByTagName("Name").item(0).getTextContent(),
                    Utils.parseInt(character.getElementsByTagName("SpriteID").item(0).getTextContent()),
                    Utils.parseInt(character.getElementsByTagName("PortraitID").item(0).getTextContent()),
                    Utils.parseInt(character.getElementsByTagName("Direction").item(0).getTextContent()),
                    Utils.parseInt(character.getElementsByTagName("X").item(0).getTextContent()) * Tile.TILE_WIDTH + xOffset,
                    Utils.parseInt(character.getElementsByTagName("Y").item(0).getTextContent()) * Tile.TILE_HEIGHT + yOffset,
                    Utils.parseInt(character.getElementsByTagName("DistanceFromCenter").item(0).getTextContent()),
                    canTurn, canMove, isSolid, aggresive,
                    Item.getItem(Utils.parseInt(character.getElementsByTagName("ItemReward").item(0).getTextContent())),
                    Utils.parseInt(character.getElementsByTagName("ItemRewardAmount").item(0).getTextContent()),
                    Utils.parseInt(character.getElementsByTagName("ZeniReward").item(0).getTextContent()),
                    pkmn, items, itemAmount);
        }
    }
    
    public void tick(){
        npcs[0].tick();
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
                if (ground1[x1][y1].getTile() != -1) {
                    getTile(x1, y1, 0).render(g, (int) (x * Tile.TILE_WIDTH - handler.getGameCamera().getXOffset()),
                            (int) (y * Tile.TILE_HEIGHT - handler.getGameCamera().getYOffset()));
                }
                if (ground2[x1][y1].getTile() != -1) {
                    getTile(x1, y1, 1).render(g, (int) (x * Tile.TILE_WIDTH - handler.getGameCamera().getXOffset()),
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
                if (mask1[x1][y1].getTile() != -1) {
                    getTile(x1, y1, 2).render(g, (int) (x * Tile.TILE_WIDTH - handler.getGameCamera().getXOffset()),
                            (int) (y * Tile.TILE_HEIGHT - handler.getGameCamera().getYOffset()));
                }
                if (mask2[x1][y1].getTile() != -1) {
                    getTile(x1, y1, 3).render(g, (int) (x * Tile.TILE_WIDTH - handler.getGameCamera().getXOffset()),
                            (int) (y * Tile.TILE_HEIGHT - handler.getGameCamera().getYOffset()));
                }
            }
        }
        npcs[0].render(g);
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
                if (fringe1[x1][y1].getTile() != -1) {
                    getTile(x1, y1, 4).render(g, (int) (x * Tile.TILE_WIDTH - handler.getGameCamera().getXOffset()),
                            (int) (y * Tile.TILE_HEIGHT - handler.getGameCamera().getYOffset()));
                }
                if (fringe2[x1][y1].getTile() != -1) {
                    getTile(x1, y1, 5).render(g, (int) (x * Tile.TILE_WIDTH - handler.getGameCamera().getXOffset()),
                            (int) (y * Tile.TILE_HEIGHT - handler.getGameCamera().getYOffset()));
                }
            }
        }
    }

    public void renderScript(Graphics g, int xStart, int xEnd, int yStart, int yEnd) {
        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                if (scripts[x][y].getScriptNumber() != -1) {
                    getScript(x, y).render(g, (int) (x * Tile.TILE_WIDTH - handler.getGameCamera().getXOffset()),
                            (int) (y * Tile.TILE_HEIGHT - handler.getGameCamera().getYOffset()));
                }
            }
        }
    }

    public Tile getTile(int x, int y, int lvl) {
        if (x < 0 || y < 0 || x >= MAP_WIDTH || y >= MAP_HEIGHT) { // all this needs to be done in tile class
            return Tile.tiles[0][0];
        }
        Tile t = null;
        switch (lvl) {
            case 0:
                t = Tile.tiles[ground1[x][y].getTilesheet()][ground1[x][y].getTile()];
                break;
            case 1:
                t = Tile.tiles[ground2[x][y].getTilesheet()][ground2[x][y].getTile()];
                break;
            case 2:
                t = Tile.tiles[mask1[x][y].getTilesheet()][mask1[x][y].getTile()];
                break;
            case 3:
                t = Tile.tiles[mask2[x][y].getTilesheet()][mask2[x][y].getTile()];
                break;
            case 4:
                t = Tile.tiles[fringe1[x][y].getTilesheet()][fringe1[x][y].getTile()];
                break;
            case 5:
                t = Tile.tiles[fringe2[x][y].getTilesheet()][fringe2[x][y].getTile()];
                break;
            default:
                break;
        }
        if (t == null) {
            return Tile.tiles[0][0];
        }
        return t;
    }

    public void setTile(int x, int y, int myX, int myY, int layer, int tilesheet) {
        switch (layer) {
            case 0:
                ground1[myX][myY].setTileSheet(tilesheet);
                ground1[myX][myY].setTile(x + y * 8);
                break;
            case 1:
                ground2[myX][myY].setTileSheet(tilesheet);
                ground2[myX][myY].setTile(x + y * 8);
                break;
            case 2:
                mask1[myX][myY].setTileSheet(tilesheet);
                mask1[myX][myY].setTile(x + y * 8);
                break;
            case 3:
                mask2[myX][myY].setTileSheet(tilesheet);
                mask2[myX][myY].setTile(x + y * 8);
                break;
            case 4:
                fringe1[myX][myY].setTileSheet(tilesheet);
                fringe1[myX][myY].setTile(x + y * 8);
                break;
            case 5:
                fringe2[myX][myY].setTileSheet(tilesheet);
                fringe2[myX][myY].setTile(x + y * 8);
                break;
            default:
                break;
        }
    }

    public void removeTile(int myX, int myY, int layer) {
        switch (layer) {
            case 0:
                ground1[myX][myY].setTile(-1);
                break;
            case 1:
                ground2[myX][myY].setTile(-1);
                break;
            case 2:
                mask1[myX][myY].setTile(-1);
                break;
            case 3:
                mask2[myX][myY].setTile(-1);
                break;
            case 4:
                fringe1[myX][myY].setTile(-1);
                break;
            case 5:
                fringe2[myX][myY].setTile(-1);
                break;
            default:
                break;
        }
    }

    public Script getScript(int x, int y) {
        if (x < 0 || y < 0 || x >= MAP_WIDTH || y >= MAP_HEIGHT) { // all this needs to be done in tile class
            return Script.scripts[0];
        }
        Script s = scripts[x][y];
        if (s == null) {
            return Script.scripts[0];
        }
        /*if (check && handler.getGame().getGameMouseManager().getWorldEditor() != null) {
            return Script.scripts[0];
        }*/
        return s;
    }

    public void setScript(int myX, int myY, int script, int map, int x, int y) {
        if (myX >= 0 && myY >= 0 && myX < MAP_WIDTH && myY < MAP_HEIGHT) {
            switch (script) {
                case 0:
                    scripts[myX][myY] = Script.scripts[0];
                    break;
                case 1:
                    scripts[myX][myY] = Script.scripts[1];
                    break;
                case 2:
                    if (map > 0 && map <= 1000 && x >= 0 && y >= 0 && x < MAP_WIDTH && y < MAP_HEIGHT) {
                        scripts[myX][myY] = new Warp(map, x, y);
                    } else {
                    }
                    break;
                case 3:
                    scripts[myX][myY] = spawnList1;
                    break;
                case 4:
                    scripts[myX][myY] = spawnList2;
                    break;
                case 5:
                    scripts[myX][myY] = spawnList3;
                    break;
                case 6:
                    scripts[myX][myY] = Script.scripts[6];
                    break;
                case 7:
                    scripts[myX][myY] = new Shop();
                    break;
                case 8: 
                    scripts[myX][myY] = Script.scripts[8];
                    break;
                default:
                    break;
            }
        }
    }
    
    public boolean getSolid(int x, int y){
        if (x < 0 || y < 0 || x >= MAP_WIDTH || y >= MAP_HEIGHT) { // scripts dont work at boundaries so have to change for the future
            return false;
        }
        for (int z = 0; z < npcs.length; z++){
            if (npcs[z] == null) break;
            if (npcs[z].getXTile() == x && npcs[z].getYTile() == y && npcs[z].isSolid()){
                return true;
            }
        }
        Script s = scripts[x][y];
        if (s != null) {
            return s.isSolid();
        }
        return false;
    }

    public void setSpawns(SpawnList s1, SpawnList s2, SpawnList s3) {
        spawnList1 = s1;
        spawnList2 = s2;
        spawnList3 = s3;
        saveSpawnList();
    }

    private void loadMap() {
        String[] warp, spawn;
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

        ground1 = new Tiles[MAP_WIDTH][MAP_HEIGHT];
        ground2 = new Tiles[MAP_WIDTH][MAP_HEIGHT];
        mask1 = new Tiles[MAP_WIDTH][MAP_HEIGHT];
        mask2 = new Tiles[MAP_WIDTH][MAP_HEIGHT];
        fringe1 = new Tiles[MAP_WIDTH][MAP_HEIGHT];
        fringe2 = new Tiles[MAP_WIDTH][MAP_HEIGHT];
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                String a[] = tokens[(x + y * MAP_WIDTH) + 6].split(":");
                ground1[x][y] = new Tiles(Utils.parseInt(a[0]), Utils.parseInt(a[1]));
            }
        }
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                String a[] = tokens[(x + (y + MAP_WIDTH) * MAP_WIDTH) + 6].split(":");
                ground2[x][y] = new Tiles(Utils.parseInt(a[0]), Utils.parseInt(a[1]));
            }
        }
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                String a[] = tokens[(x + (y + 2 * MAP_WIDTH) * MAP_WIDTH) + 6].split(":");
                mask1[x][y] = new Tiles(Utils.parseInt(a[0]), Utils.parseInt(a[1]));
            }
        }
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                String a[] = tokens[(x + (y + 3 * MAP_WIDTH) * MAP_WIDTH) + 6].split(":");
                mask2[x][y] = new Tiles(Utils.parseInt(a[0]), Utils.parseInt(a[1]));
            }
        }
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                String a[] = tokens[(x + (y + 4 * MAP_WIDTH) * MAP_WIDTH) + 6].split(":");
                fringe1[x][y] = new Tiles(Utils.parseInt(a[0]), Utils.parseInt(a[1]));
            }
        }
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                String a[] = tokens[(x + (y + 5 * MAP_WIDTH) * MAP_WIDTH) + 6].split(":");
                fringe2[x][y] = new Tiles(Utils.parseInt(a[0]), Utils.parseInt(a[1]));
            }
        }

        file = Utils.loadFileAsString("dat/world/scripts/script" + curr + ".script");
        tokens = file.split("\\s+");
        file = Utils.loadFileAsString("dat/world/warps/warp" + curr + ".warp");
        warp = file.split("\\s+");
        file = Utils.loadFileAsString("dat/world/spawns/spawn" + curr + ".spawn");
        spawn = file.split("\\s+");

        if (spawn.length > 1) {
            spawnList1 = createSpawnList(spawn, 1, Utils.parseFloat(spawn[0]), "S1", 3);
        } else {
            spawnList1 = new SpawnList(3);
        }
        if (spawn.length > 49) {
            spawnList2 = createSpawnList(spawn, 49, Utils.parseFloat(spawn[0]), "S2", 4);
        } else {
            spawnList2 = new SpawnList(4);
        }
        if (spawn.length > 61) {
            spawnList3 = createSpawnList(spawn, 97, Utils.parseFloat(spawn[0]), "S3", 5);
        } else {
            spawnList3 = new SpawnList(5);
        }

        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                int i = Utils.parseInt(tokens[x + y * MAP_WIDTH]);
                switch (i) {
                    case 0:
                        scripts[x][y] = Script.scripts[0];
                        break;
                    case 1:
                        scripts[x][y] = Script.scripts[1];
                        break;
                    case 2:
                        scripts[x][y] = new Warp( //When saving warps, gotta save in order that they appear for this shit to FUCKING work
                                Utils.parseInt(warp[warpHead]),
                                Utils.parseInt(warp[warpHead + 1]),
                                Utils.parseInt(warp[warpHead + 2]));
                        warpHead += 3;
                        break;
                    case 3:
                        scripts[x][y] = spawnList1;
                        break;
                    case 4:
                        scripts[x][y] = spawnList2;
                        break;
                    case 5:
                        scripts[x][y] = spawnList3;
                        break;
                    case 6:
                        scripts[x][y] = Script.scripts[6];
                        break;
                    case 7:
                        scripts[x][y] = new Shop();
                        break;
                    default:
                        scripts[x][y] = Script.scripts[i];
                        break;
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
        for (int y = 0; y < MAP_HEIGHT; y++) {  //saves the ground 1
            for (int x = 0; x < MAP_WIDTH - 1; x++) {
                file = file + ground1[x][y].getTilesheet() + ":" + ground1[x][y].getTile() + " ";
            }
            file = file + ground1[MAP_WIDTH - 1][y].getTilesheet() + ":" + ground1[MAP_WIDTH - 1][y].getTile() + "\n";
        }
        for (int y = 0; y < MAP_HEIGHT; y++) {  //saves the ground 2
            for (int x = 0; x < MAP_WIDTH - 1; x++) {
                file = file + ground2[x][y].getTilesheet() + ":" + ground2[x][y].getTile() + " ";
            }
            file = file + ground2[MAP_WIDTH - 1][y].getTilesheet() + ":" + ground2[MAP_WIDTH - 1][y].getTile() + "\n";
        }
        for (int y = 0; y < MAP_HEIGHT; y++) {  //saves the mask 1
            for (int x = 0; x < MAP_WIDTH - 1; x++) {
                file = file + mask1[x][y].getTilesheet() + ":" + mask1[x][y].getTile() + " ";
            }
            file = file + mask1[MAP_WIDTH - 1][y].getTilesheet() + ":" + mask1[MAP_WIDTH - 1][y].getTile() + "\n";
        }
        for (int y = 0; y < MAP_HEIGHT; y++) {  //saves the mask 2
            for (int x = 0; x < MAP_WIDTH - 1; x++) {
                file = file + mask2[x][y].getTilesheet() + ":" + mask2[x][y].getTile() + " ";
            }
            file = file + mask2[MAP_WIDTH - 1][y].getTilesheet() + ":" + mask2[MAP_WIDTH - 1][y].getTile() + "\n";
        }
        for (int y = 0; y < MAP_HEIGHT; y++) {  //saves the fringe 1
            for (int x = 0; x < MAP_WIDTH - 1; x++) {
                file = file + fringe1[x][y].getTilesheet() + ":" + fringe1[x][y].getTile() + " ";
            }
            file = file + fringe1[MAP_WIDTH - 1][y].getTilesheet() + ":" + fringe1[MAP_WIDTH - 1][y].getTile() + "\n";
        }
        for (int y = 0; y < MAP_HEIGHT; y++) {  //saves the fringe 2
            for (int x = 0; x < MAP_WIDTH - 1; x++) {
                file = file + fringe2[x][y].getTilesheet() + ":" + fringe2[x][y].getTile() + " ";
            }
            file = file + fringe2[MAP_WIDTH - 1][y].getTilesheet() + ":" + fringe2[MAP_WIDTH - 1][y].getTile() + "\n";
        }
        Utils.saveStringAsFile("dat/world/maps/map" + curr + ".map", file);
        //scripts here
        file = "";
        String warps = "";
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH - 1; x++) {
                file = file + scripts[x][y].getScriptNumber() + " ";
                if (scripts[x][y].getScriptNumber() == 2) {
                    Warp w = (Warp) scripts[x][y];
                    warps = warps + w.getMapNumber() + "\n" + w.getxCoord() + "\n" + w.getyCoord() + "\n";
                }
            }
            if (y != MAP_HEIGHT - 1) {
                file = file + scripts[MAP_WIDTH - 1][y].getScriptNumber() + "\n";
            } else {
                file = file + scripts[MAP_WIDTH - 1][y].getScriptNumber();
            }
            if (scripts[MAP_WIDTH - 1][y].getScriptNumber() == 2) {
                Warp w = (Warp) scripts[MAP_WIDTH - 1][y];
                warps = warps + w.getMapNumber() + " " + w.getxCoord() + " " + w.getyCoord() + "\n";
            }
        }
        
        Utils.saveStringAsFile("dat/world/warps/warp" + curr + ".warp", warps);

        Utils.saveStringAsFile("dat/world/scripts/script" + curr + ".script", file);
    }

    public void saveSpawnList() {
        String file = spawnList1.getSpawnRate() + "\n";
        Spawn s = spawnList1.getSpawn(0);
        file = file + s.getPokemonId() + " " + s.getMinLevel() + " " + s.getMaxLevel() + " " + s.getSpawnRate() + "\n";
        file = file + s.getHp() + " " + s.getAtt() + " " + s.getDef() + " " + s.getSpatt() + " " + s.getSpdef() + " " + s.getSpeed() + "\n";
        file = file + s.getMoveId(0) + " " + s.getMoveId(1) + " " + s.getMoveId(2) + " " + s.getMoveId(3) + " \n";
        file = file + s.getItemId() + " " + s.getItemSpawnRate() + "\n";
        s = spawnList1.getSpawn(1);
        file = file + s.getPokemonId() + " " + s.getMinLevel() + " " + s.getMaxLevel() + " " + s.getSpawnRate() + "\n";
        file = file + s.getHp() + " " + s.getAtt() + " " + s.getDef() + " " + s.getSpatt() + " " + s.getSpdef() + " " + s.getSpeed() + "\n";
        file = file + s.getMoveId(0) + " " + s.getMoveId(1) + " " + s.getMoveId(2) + " " + s.getMoveId(3) + " \n";
        file = file + s.getItemId() + " " + s.getItemSpawnRate() + "\n";
        s = spawnList1.getSpawn(2);
        file = file + s.getPokemonId() + " " + s.getMinLevel() + " " + s.getMaxLevel() + " " + s.getSpawnRate() + "\n";
        file = file + s.getHp() + " " + s.getAtt() + " " + s.getDef() + " " + s.getSpatt() + " " + s.getSpdef() + " " + s.getSpeed() + "\n";
        file = file + s.getMoveId(0) + " " + s.getMoveId(1) + " " + s.getMoveId(2) + " " + s.getMoveId(3) + " \n";
        file = file + s.getItemId() + " " + s.getItemSpawnRate() + "\n";

        s = spawnList2.getSpawn(0);
        file = file + s.getPokemonId() + " " + s.getMinLevel() + " " + s.getMaxLevel() + " " + s.getSpawnRate() + "\n";
        file = file + s.getHp() + " " + s.getAtt() + " " + s.getDef() + " " + s.getSpatt() + " " + s.getSpdef() + " " + s.getSpeed() + "\n";
        file = file + s.getMoveId(0) + " " + s.getMoveId(1) + " " + s.getMoveId(2) + " " + s.getMoveId(3) + " \n";
        file = file + s.getItemId() + " " + s.getItemSpawnRate() + "\n";
        s = spawnList2.getSpawn(1);
        file = file + s.getPokemonId() + " " + s.getMinLevel() + " " + s.getMaxLevel() + " " + s.getSpawnRate() + "\n";
        file = file + s.getHp() + " " + s.getAtt() + " " + s.getDef() + " " + s.getSpatt() + " " + s.getSpdef() + " " + s.getSpeed() + "\n";
        file = file + s.getMoveId(0) + " " + s.getMoveId(1) + " " + s.getMoveId(2) + " " + s.getMoveId(3) + " \n";
        file = file + s.getItemId() + " " + s.getItemSpawnRate() + "\n";
        s = spawnList2.getSpawn(2);
        file = file + s.getPokemonId() + " " + s.getMinLevel() + " " + s.getMaxLevel() + " " + s.getSpawnRate() + "\n";
        file = file + s.getHp() + " " + s.getAtt() + " " + s.getDef() + " " + s.getSpatt() + " " + s.getSpdef() + " " + s.getSpeed() + "\n";
        file = file + s.getMoveId(0) + " " + s.getMoveId(1) + " " + s.getMoveId(2) + " " + s.getMoveId(3) + " \n";
        file = file + s.getItemId() + " " + s.getItemSpawnRate() + "\n";

        s = spawnList3.getSpawn(0);
        file = file + s.getPokemonId() + " " + s.getMinLevel() + " " + s.getMaxLevel() + " " + s.getSpawnRate() + "\n";
        file = file + s.getHp() + " " + s.getAtt() + " " + s.getDef() + " " + s.getSpatt() + " " + s.getSpdef() + " " + s.getSpeed() + "\n";
        file = file + s.getMoveId(0) + " " + s.getMoveId(1) + " " + s.getMoveId(2) + " " + s.getMoveId(3) + " \n";
        file = file + s.getItemId() + " " + s.getItemSpawnRate() + "\n";
        s = spawnList3.getSpawn(1);
        file = file + s.getPokemonId() + " " + s.getMinLevel() + " " + s.getMaxLevel() + " " + s.getSpawnRate() + "\n";
        file = file + s.getHp() + " " + s.getAtt() + " " + s.getDef() + " " + s.getSpatt() + " " + s.getSpdef() + " " + s.getSpeed() + "\n";
        file = file + s.getMoveId(0) + " " + s.getMoveId(1) + " " + s.getMoveId(2) + " " + s.getMoveId(3) + " \n";
        file = file + s.getItemId() + " " + s.getItemSpawnRate() + "\n";
        s = spawnList3.getSpawn(2);
        file = file + s.getPokemonId() + " " + s.getMinLevel() + " " + s.getMaxLevel() + " " + s.getSpawnRate() + "\n";
        file = file + s.getHp() + " " + s.getAtt() + " " + s.getDef() + " " + s.getSpatt() + " " + s.getSpdef() + " " + s.getSpeed() + "\n";
        file = file + s.getMoveId(0) + " " + s.getMoveId(1) + " " + s.getMoveId(2) + " " + s.getMoveId(3) + " \n";
        file = file + s.getItemId() + " " + s.getItemSpawnRate() + "\n";

        Utils.saveStringAsFile("dat/world/spawns/spawn" + curr + ".spawn", file);
    }

    public SpawnList createSpawnList(String[] spawn, int x, float y, String code, int spawnNumber) {
        Moveset moveSet1 = new Moveset(Utils.parseInt(spawn[x + 10]),
                Utils.parseInt(spawn[x + 11]),
                Utils.parseInt(spawn[x + 12]),
                Utils.parseInt(spawn[x + 13]));
        Moveset moveSet2 = new Moveset(Utils.parseInt(spawn[x + 26]),
                Utils.parseInt(spawn[x + 27]),
                Utils.parseInt(spawn[x + 28]),
                Utils.parseInt(spawn[x + 29]));
        Moveset moveSet3 = new Moveset(Utils.parseInt(spawn[x + 42]),
                Utils.parseInt(spawn[x + 43]),
                Utils.parseInt(spawn[x + 44]),
                Utils.parseInt(spawn[x + 45]));
        return new SpawnList(code, y, spawnNumber, new Spawn(Utils.parseInt(spawn[x + 0]),
                Utils.parseInt(spawn[x + 1]),
                Utils.parseInt(spawn[x + 2]),
                Utils.parseFloat(spawn[x + 3]),
                Utils.parseInt(spawn[x + 4]),
                Utils.parseInt(spawn[x + 5]),
                Utils.parseInt(spawn[x + 6]),
                Utils.parseInt(spawn[x + 7]),
                Utils.parseInt(spawn[x + 8]),
                Utils.parseInt(spawn[x + 9]),
                moveSet1,
                Utils.parseInt(spawn[x + 14]),
                Utils.parseFloat(spawn[x + 15])),
                new Spawn(Utils.parseInt(spawn[x + 16]),
                        Utils.parseInt(spawn[x + 17]),
                        Utils.parseInt(spawn[x + 18]),
                        Utils.parseFloat(spawn[x + 19]),
                        Utils.parseInt(spawn[x + 20]),
                        Utils.parseInt(spawn[x + 21]),
                        Utils.parseInt(spawn[x + 22]),
                        Utils.parseInt(spawn[x + 23]),
                        Utils.parseInt(spawn[x + 24]),
                        Utils.parseInt(spawn[x + 25]),
                        moveSet2,
                        Utils.parseInt(spawn[x + 30]),
                        Utils.parseFloat(spawn[x + 31])),
                new Spawn(Utils.parseInt(spawn[x + 32]),
                        Utils.parseInt(spawn[x + 33]),
                        Utils.parseInt(spawn[x + 34]),
                        Utils.parseFloat(spawn[x + 35]),
                        Utils.parseInt(spawn[x + 36]),
                        Utils.parseInt(spawn[x + 37]),
                        Utils.parseInt(spawn[x + 38]),
                        Utils.parseInt(spawn[x + 39]),
                        Utils.parseInt(spawn[x + 40]),
                        Utils.parseInt(spawn[x + 41]),
                        moveSet3,
                        Utils.parseInt(spawn[x + 46]),
                        Utils.parseFloat(spawn[x + 47])));
    }

    public SpawnList getSpawnList(int i) {
        switch (i) {
            case 0:
                return spawnList1;
            case 1:
                return spawnList2;
            case 2:
                return spawnList3;
            default:
                return null;
        }
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName.replaceAll(" ", "_");
    }
    
    public NPC[] getNpcs(){
        return npcs;
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
    
    public void setCurr(int curr){
        this.curr = curr;
    }

    public void setSafe(boolean safeZone) {
        this.safeZone = safeZone;
    }

    public int getBackground() {
        return background;
    }
}