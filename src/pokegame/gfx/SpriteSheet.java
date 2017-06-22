/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.gfx;

import java.awt.image.BufferedImage;
import pokegame.tiles.Tile;

/**
 *
 * @author Rahul
 */
public class SpriteSheet {

    protected int tileWidth, tileHeight;
    protected BufferedImage spritesheet;
    public BufferedImage[][] spriteSheet;

    public SpriteSheet(BufferedImage spritesheet) {
        this.spritesheet = spritesheet;
        tileWidth = Tile.TILE_WIDTH;
        tileHeight = Tile.TILE_HEIGHT;
        spriteSheet = new BufferedImage[(spritesheet.getWidth() / Tile.TILE_WIDTH)][(spritesheet.getHeight() / Tile.TILE_HEIGHT)];
        for (int x = 0; x < spritesheet.getWidth() / Tile.TILE_WIDTH; x++) {
            for (int y = 0; y < spritesheet.getHeight() / Tile.TILE_HEIGHT; y++) {
                spriteSheet[x][y] = crop(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
            }
        }
    }
    
    public SpriteSheet(BufferedImage spritesheet, int width, int height){
        this.spritesheet = spritesheet;
        tileWidth = width;
        tileHeight = height;
        spriteSheet = new BufferedImage[(spritesheet.getWidth() / width)][(spritesheet.getHeight()/height)];
        for (int x = 0; x < spritesheet.getWidth() / width; x++) {
            for (int y = 0; y < spritesheet.getHeight() / height; y++) {
                spriteSheet[x][y] = crop(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
            }
        }
    }

    public BufferedImage crop(int x, int y, int width, int height) {
        return spritesheet.getSubimage(x, y, width, height);
    }

    public BufferedImage getImage(int x, int y) {
        return spriteSheet[x][y];
    }

    public BufferedImage getSheet() {
        return spritesheet;
    }

    public int getWidth() {
        return spritesheet.getWidth();
    }

    public int getHeight() {
        return spritesheet.getHeight();
    }
}
