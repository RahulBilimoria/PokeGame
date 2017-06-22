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
public class Sprites extends SpriteSheet{
    
    public Sprites(BufferedImage spritesheet) {
        super(spritesheet);
    }
    
    public Sprites(BufferedImage spritesheet, int width, int height){
        super(spritesheet, width, height);
    }
    
    public BufferedImage[] getDirection(int direction, int y){
        BufferedImage[] animation = new BufferedImage[4];
        int x = 3*direction;
        // up = 0, right = 1, down = 2, left = 3
        animation[0] = spriteSheet[x+1][y];
        animation[1] = spriteSheet[x][y];
        animation[2] = spriteSheet[x+1][y];
        animation[3] = spriteSheet[x+2][y];
        return animation;
    }
    
}
