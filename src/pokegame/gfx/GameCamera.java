/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.gfx;

import pokegame.entity.Person;
import pokegame.handler.Handler;

/**
 *
 * @author minim_000
 */
public class GameCamera {
    
    private Handler handler;
    private float xOffset, yOffset;
    
    public GameCamera(Handler handler,float xOffset, float yOffset){
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.handler = handler;
    }
    
    public void checkBlankSpace(){
        /*if (xOffset < 0){
            xOffset = 0;
        } else if (xOffset > Map.MAP_WIDTH* Tile.TILE_WIDTH - handler.getWidth()){
            xOffset = Map.MAP_WIDTH* Tile.TILE_WIDTH - handler.getWidth();
        }
        if (yOffset < 0){
            yOffset = 0;
        } if (yOffset > Map.MAP_HEIGHT* Tile.TILE_HEIGHT - handler.getHeight()){
            yOffset = Map.MAP_HEIGHT* Tile.TILE_HEIGHT - handler.getHeight();
        }*/
    }
    
    public void move(float xAmount, float yAmount){
        xOffset += xAmount;
        yOffset += yAmount;
        //checkBlankSpace();
    }
    
    public void centerOnEntity(Person e){
        xOffset = e.getX() - handler.getWidth() / 2 + e.getWidth()/2;
        yOffset = e.getY() - handler.getHeight() / 2 + e.getHeight()/2;
        //checkBlankSpace();
    }
    
    public float getXOffset(){
        return xOffset;
    }
    
    public void setXOffset(float xOffset){
        this.xOffset = xOffset;
    }
    
    public float getYOffset(){
        return yOffset;
    }
    
    public void setYOffset(float yOffset){
        this.yOffset = yOffset;
    }
}
