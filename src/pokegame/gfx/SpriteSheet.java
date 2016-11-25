/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.gfx;

import java.awt.image.BufferedImage;

/**
 *
 * @author Rahul
 */
public class SpriteSheet {
    private BufferedImage spritesheet;
    
    public SpriteSheet(BufferedImage spritesheet){
        this.spritesheet = spritesheet;
    }
    
    public BufferedImage crop(int x, int y, int width, int height){
        return spritesheet.getSubimage(x, y, width, height);
    }
    
    public BufferedImage getSheet(){
        return spritesheet;
    }
    
    public int getWidth(){
        return spritesheet.getWidth();
    }
    
    public int getHeight(){
        return spritesheet.getHeight();
    }
}
