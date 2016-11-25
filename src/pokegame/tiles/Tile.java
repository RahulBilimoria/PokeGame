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
    
    public static int width = Asset.sheet.getWidth()/32;
    public static int height = Asset.sheet.getHeight()/32;
    public static Tile[] tiles = new Tile[width * height];
    protected BufferedImage texture;
    public static final int TILE_WIDTH = 32,
                            TILE_HEIGHT = 32;
    
    public Tile(BufferedImage texture){
        this.texture = texture;
    }
    
    public static void init(){
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                tiles[x + y*8] = new Tile(Asset.spriteSheet[x][y]);
            }
        }
    }
    
    public void tick(){
        
    }
    
    public boolean isSolid(){
        return false;
    }
    
    public void render(Graphics g, int x, int y){
        g.drawImage(texture, x, y,TILE_WIDTH, TILE_HEIGHT, null);
    }
}
