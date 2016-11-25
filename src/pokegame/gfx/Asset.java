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
public class Asset {
    
    private static final int width = 32, height = 32;
    
    public static BufferedImage[][] spriteSheet;
    public static BufferedImage[] player_up;
    public static BufferedImage[] player_down;
    public static BufferedImage[] player_left;
    public static BufferedImage[] player_right;
    public static BufferedImage[] start;
    public static SpriteSheet sheet;
    public static SpriteSheet playerSheet;
    
    public static void init(){
        sheet = new SpriteSheet(ImageLoader.loadImage("/sprite/spritesheet.png"));
        playerSheet = new SpriteSheet(ImageLoader.loadImage("/sprite/playersprite.png"));
        player_up = new BufferedImage[4];
        player_down = new BufferedImage[4];
        player_left = new BufferedImage[4];
        player_right = new BufferedImage[4];
        start = new BufferedImage[2];
        spriteSheet = new BufferedImage[sheet.getWidth()][sheet.getHeight()];
        for (int x = 0; x < sheet.getWidth()/32; x++){
            for (int y = 0; y < sheet.getHeight()/32; y++){
                spriteSheet[x][y] = sheet.crop(x*width,y*height,width,height);
            }
        }
        
        start[0] = sheet.crop(6*width, 2*height, 2*width, height);
        start[1] = sheet.crop(6*width, 3*height, 2*width, height);
        
        player_down[0] = playerSheet.crop(1*width, 0*height, width, 2*height);
        player_down[1] = playerSheet.crop(0*width, 0*height, width, 2*height);
        player_down[2] = playerSheet.crop(1*width, 0*height, width, 2*height);
        player_down[3] = playerSheet.crop(2*width, 0*height, width, 2*height);
        player_up[0] = playerSheet.crop(4*width, 0*height, width, 2*height);
        player_up[1] = playerSheet.crop(3*width, 0*height, width, 2*height);
        player_up[2] = playerSheet.crop(4*width, 0*height, width, 2*height);
        player_up[3] = playerSheet.crop(5*width, 0*height, width, 2*height);
        player_left[0] = playerSheet.crop(1*width, 2*height, width, 2*height);
        player_left[1] = playerSheet.crop(0*width, 2*height, width, 2*height);
        player_left[2] = playerSheet.crop(1*width, 2*height, width, 2*height);
        player_left[3] = playerSheet.crop(2*width, 2*height, width, 2*height);
        player_right[0] = playerSheet.crop(4*width, 2*height, width, 2*height);
        player_right[1] = playerSheet.crop(3*width, 2*height, width, 2*height);
        player_right[2] = playerSheet.crop(4*width, 2*height, width, 2*height);
        player_right[3] = playerSheet.crop(5*width, 2*height, width, 2*height);
    }
    
}
