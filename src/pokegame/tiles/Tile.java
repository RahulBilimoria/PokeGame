/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import pokegame.gfx.Asset;

/**
 *
 * @author Rahul
 */
public class Tile {

    //public static int width = Asset.tileSheets[0].getWidth()/32;
    //public static int height = Asset.tileSheets[0].getHeight()/32;
    //double array for tiles (first number is tilesheet, second is number of tiles in tilessheet)
    public static Tile[][] tiles = new Tile[Asset.TILESHEET_COUNT][];
    //public static Tile[] tiles = new Tile[width * height];
    protected BufferedImage texture;
    public static final int TILE_WIDTH = 32,
            TILE_HEIGHT = 32;

    public Tile(BufferedImage texture) {
        this.texture = texture;
    }

    public static void init() {
        int width, height;
        for (int x = 0; x < Asset.TILESHEET_COUNT; x++) {
            tiles[x] = new Tile[(Asset.tileSheets[x].getWidth() / 32) * (Asset.tileSheets[x].getHeight() / 32)];
        }
        for (int i = 0; i < Asset.TILESHEET_COUNT; i++) {
            width = Asset.tileSheets[i].getWidth()/32;
            height = Asset.tileSheets[i].getHeight()/32;
            for (int x = 0; x < width; x++){
                for (int y = 0; y < height; y++){
                    tiles[i][x + y*8] = new Tile(Asset.tileSheets[i].getImage(x, y));
                }
            }
            /*for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                tiles[x + y*8] = new Tile(Asset.spriteSheet[x][y]);
            }
        }*/
        }
    }

    public void tick() {

    }

    public boolean isSolid() {
        return false;
    }

    public void render(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }
}
