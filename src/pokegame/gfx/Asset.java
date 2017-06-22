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
    public static final int TILESHEET_COUNT = 7;

    public static BufferedImage[] player_up;
    public static BufferedImage[] player_down;
    public static BufferedImage[] player_left;
    public static BufferedImage[] player_right;
    public static SpriteSheet[] tileSheets = new SpriteSheet[TILESHEET_COUNT];
    public static Sprites sprites;
    public static SpriteSheet portraits;
    public static SpriteSheet portraitBackgrounds;
    public static SpriteSheet playerSheet;
    public static SpriteSheet itemSheet;

    public static void init() {
        for (int x = 0; x < TILESHEET_COUNT; x++) {
            tileSheets[x] = new SpriteSheet(ImageLoader.loadImage("/sprite/spritesheet" + (x + 1) + ".png"));
        }
        itemSheet = new SpriteSheet(ImageLoader.loadImage("/sprite/items.png"), 32, 32);
        sprites = new Sprites(ImageLoader.loadImage("/sprite/sprites.png"), 32, 64);
        portraits = new SpriteSheet(ImageLoader.loadImage("/sprite/portraits.png"), 128, 192);
        portraitBackgrounds = new SpriteSheet(ImageLoader.loadImage("/sprite/portraitbackgrounds.png"), 146, 192);
        playerSheet = new SpriteSheet(ImageLoader.loadImage("/sprite/playersprite.png"));
        player_up = new BufferedImage[4];
        player_down = new BufferedImage[4];
        player_left = new BufferedImage[4];
        player_right = new BufferedImage[4];

        player_down[0] = playerSheet.crop(1 * width, 0 * height, width, 2 * height);
        player_down[1] = playerSheet.crop(0 * width, 0 * height, width, 2 * height);
        player_down[2] = playerSheet.crop(1 * width, 0 * height, width, 2 * height);
        player_down[3] = playerSheet.crop(2 * width, 0 * height, width, 2 * height);
        player_up[0] = playerSheet.crop(4 * width, 0 * height, width, 2 * height);
        player_up[1] = playerSheet.crop(3 * width, 0 * height, width, 2 * height);
        player_up[2] = playerSheet.crop(4 * width, 0 * height, width, 2 * height);
        player_up[3] = playerSheet.crop(5 * width, 0 * height, width, 2 * height);
        player_left[0] = playerSheet.crop(1 * width, 2 * height, width, 2 * height);
        player_left[1] = playerSheet.crop(0 * width, 2 * height, width, 2 * height);
        player_left[2] = playerSheet.crop(1 * width, 2 * height, width, 2 * height);
        player_left[3] = playerSheet.crop(2 * width, 2 * height, width, 2 * height);
        player_right[0] = playerSheet.crop(4 * width, 2 * height, width, 2 * height);
        player_right[1] = playerSheet.crop(3 * width, 2 * height, width, 2 * height);
        player_right[2] = playerSheet.crop(4 * width, 2 * height, width, 2 * height);
        player_right[3] = playerSheet.crop(5 * width, 2 * height, width, 2 * height);
    }

}
