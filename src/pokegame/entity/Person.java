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

    protected float x, y;
    protected int xi, yi;
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
        this.x = x;
        this.y = y;
        xi = (int)(x / Tile.TILE_WIDTH);
        yi = (int)(y / Tile.TILE_WIDTH);
        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
        isMoving = false;
    }
    
    public void move() {
        moveX();
        moveY();
        stopMoving();
    }
    
    protected void stopMoving() {
        if (x % Tile.TILE_WIDTH == 0 && y % Tile.TILE_HEIGHT == 0) {
            isMoving = false;
        }
    }

    public void moveX() {
        float x1 = x / Tile.TILE_WIDTH;
        int y1 = (int) y / Tile.TILE_HEIGHT;
        if (xMove > 0) { //right
            if (!collisionWithTile((int) x1, (int) y1, 1)) {
                x += xMove;
            }
        } else if (xMove < 0) { //left
            if (!collisionWithTile((int) Math.ceil(x1), (int) y1, 3)) {
                x += xMove;
            }
        }
    }

    public void moveY() {
        int x1 = (int) x / Tile.TILE_WIDTH;
        float y1 = y / Tile.TILE_HEIGHT;
        if (yMove < 0) { // up
            if (!collisionWithTile((int) x1, (int) Math.ceil(y1), 0)) {
                y += yMove;
            }
        } else if (yMove > 0) { // down 
            if (!collisionWithTile((int) x1, (int) y1, 2)) {
                y += yMove;
            }
        }
    }
    
    protected void updateIntXY(){
        xi = (int)(x / Tile.TILE_WIDTH);
        yi = (int)(y / Tile.TILE_WIDTH);
    }

    protected boolean collisionWithTile(int x, int y, int direction) {
        switch (direction) {
            case 0:
                return handler.getWorld().getMap().getScript(x, y - 1, true).isSolid(); //up
            case 1:
                return handler.getWorld().getMap().getScript(x + 1, y, true).isSolid(); //right
            case 2:
                return handler.getWorld().getMap().getScript(x, y + 1, true).isSolid(); //down
            case 3:
                return handler.getWorld().getMap().getScript(x - 1, y, true).isSolid(); //left
            default:
                return false;
        }
    }
    
    public abstract void tick();
    public abstract void render(Graphics g);
    
    public float getX(){
        return x;
    }
    
    public void setX(float x){
        this.x = x;
    }
    
    public float getY(){
        return y;
    }
    
    public void setY(float y){
        this.y = y;
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
        return moved;
    }
}
