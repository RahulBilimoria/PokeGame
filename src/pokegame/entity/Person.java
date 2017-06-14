/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.entity;

import java.awt.Graphics;
import pokegame.handler.Handler;
import pokegame.tiles.Tile;

/**
 *
 * @author minim_000
 */
public abstract class Person {
    
    public static final float DEFAULT_SPEED = 2.0f;
    public static final int DEFAULT_CREATURE_WIDTH = 32,
            DEFAULT_CREATURE_HEIGHT = 32;

    protected float xPixel, yPixel;
    protected int xTile, yTile;
    protected float speed;
    protected float xMove, yMove;
    protected boolean isMoving;
    protected boolean moved;
    
    protected Handler handler;
    protected int width, height;
    
    public Person(Handler handler, float x, float y, int width, int height) {
        this.handler = handler;
        this.width = width;
        this.height = height;
        this.xPixel = x;
        this.yPixel = y;
        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
        isMoving = false;
        updateIntXY();
    }
    
    public void move() {
        moveX();
        moveY();
        stopMoving();
    }
    
    protected void stopMoving() {
        if (xPixel % Tile.TILE_WIDTH == 0 && yPixel % Tile.TILE_HEIGHT == 0) {
            isMoving = false;
            updateIntXY();
        }
    }

    public void moveX() {
        int x1 = (int)Math.ceil(xPixel / Tile.TILE_WIDTH);
        if (xMove > 0) { //right
            if (!handler.getWorld().getMap().getSolid(xTile + 1, yTile)){
                xPixel += xMove;
            }
        } else if (xMove < 0) { //left
            if (!handler.getWorld().getMap().getSolid(x1 - 1, yTile)){
                xPixel += xMove;
            }
        }
    }

    public void moveY() {
        int y1 = (int)Math.ceil(yPixel / Tile.TILE_HEIGHT);
        if (yMove < 0) { // up
            if (!handler.getWorld().getMap().getSolid(xTile, y1 - 1)) {
                yPixel += yMove;
            }
        } else if (yMove > 0) { // down 
            if (!handler.getWorld().getMap().getSolid(xTile, yTile + 1)) {
                yPixel += yMove;
            }
        }
    }
    
    protected void updateIntXY(){
        xTile = (int)(xPixel / Tile.TILE_WIDTH);
        yTile = (int)(yPixel / Tile.TILE_HEIGHT);
    }
    
    public abstract void tick();
    public abstract void render(Graphics g);
    
    public float getX(){
        return xPixel;
    }
    
    public void setX(float x){
        this.xPixel = x;
    }
    
    public float getY(){
        return yPixel;
    }
    
    public void setY(float y){
        this.yPixel = y;
    }
    
    public int getXTile(){
        return xTile;
    }
    
    public int getYTile(){
        return yTile;
    }
    
    public boolean getIsMoving(){
        return isMoving;
    }
    
    public void setIsMoving(boolean isMoving){
        this.isMoving = isMoving;
    }

    public int getWidth(){
        return width;
    }
    public void setWidth(int width){
        this.width = width;
    }
    public int getHeight(){
        return height;
    }
    public void setHeight(int height){
        this.height = height;
    }
    
    public void setMoved(boolean moved){
        this.moved = moved;
    }
    
    public boolean getMoved(){
        if (moved){
            moved = false;
            return true;
        }
        return false;
    }
}
