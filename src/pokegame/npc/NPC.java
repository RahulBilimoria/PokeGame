/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.npc;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;
import pokegame.entity.Person;
import pokegame.entity.player.Player;
import pokegame.gfx.Animation;
import pokegame.gfx.Asset;
import pokegame.handler.Handler;
import pokegame.tiles.Tile;

/**
 *
 * @author Rahul
 */
public class NPC extends Person {

    protected int id; // The id of the npc
    protected String name; // The name of the npc
    protected int type; // Type of npc

    protected int portraitID;
    protected int centerX, centerY; // center that the NPC will 'revolve' around
    protected int distanceFromCenter; // distance in tiles NPC can walk from center
    protected int direction; // direction npc is walking in
    protected boolean isSolid; // can other players walk through them
    protected boolean canTurn; // can this NPC turn to look around
    protected boolean canMove; // can this NPC move from the center
    protected Animation up, down, left, right; // The NPCs sprites
    private boolean idle; // is this npc moving
    private int timeUntilMove; // time until npc can move again
    private long time; // time since last move

    //Gym Leaders
    //Quest NPCs
    //Story NPCs
    //Battle NPCs
    //Will add more as i think of them
    public NPC(Handler handler, int type, int id, String name, int spriteId, int portraitID, int direction, float x, float y, int distanceFromCenter,
            boolean canTurn, boolean canMove, boolean isSolid) {
        super(handler, x, y, DEFAULT_CREATURE_WIDTH, DEFAULT_CREATURE_HEIGHT);
        this.type = type;
        this.id = id;
        this.name = name;
        centerX = xTile;
        centerY = yTile;
        this.distanceFromCenter = distanceFromCenter;
        this.isSolid = isSolid;
        this.canTurn = canTurn;
        this.canMove = canMove;
        this.portraitID = portraitID;
        up = new Animation(400, Asset.sprites.getDirection(0, spriteId));
        down = new Animation(400, Asset.sprites.getDirection(1, spriteId));
        left = new Animation(400, Asset.sprites.getDirection(2, spriteId));
        right = new Animation(400, Asset.sprites.getDirection(3, spriteId));
        timeUntilMove = (int) (Math.random() * 5) + 1;
        time = System.nanoTime();
        this.direction = direction;
        idle = true;
    }

    @Override
    public void tick() {
        if (canMove) {
            if (isMoving) {
                up.tick();
                down.tick();
                left.tick();
                right.tick();
                move();
            } else if (idleActions()) {
                isMoving = true;
            }
        } else if (canTurn) {
            idleActions();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getCurrentAnimationFrame(), (int) (xPixel - handler.getGameCamera().getXOffset()),
                (int) (yPixel - handler.getGameCamera().getYOffset() - Tile.TILE_HEIGHT), width, 2 * height, null);
    }

    @Override
    public void moveX() {
        int x1 = (int) Math.ceil(xPixel / Tile.TILE_WIDTH);
        if (xMove > 0) { //right
            if (!handler.getWorld().getMap().getScript(xTile + 1, yTile).isSolid() && xTile - centerX < distanceFromCenter) {
                xPixel += xMove;
            }
        } else if (xMove < 0) { //left
            if (!handler.getWorld().getMap().getScript(x1 - 1, yTile).isSolid() && centerX - xTile < distanceFromCenter) {
                xPixel += xMove;
            }
        }
    }

    @Override
    public void moveY() {
        int y1 = (int) Math.ceil(yPixel / Tile.TILE_HEIGHT);
        if (yMove < 0) { // up
            if (!handler.getWorld().getMap().getScript(xTile, y1 - 1).isSolid() && centerY - yTile < distanceFromCenter) {
                yPixel += yMove;
            }
        } else if (yMove > 0) { // down 
            if (!handler.getWorld().getMap().getScript(xTile, yTile + 1).isSolid() && yTile - centerY < distanceFromCenter) {
                yPixel += yMove;
            }
        }
    }

    protected void changeDirection() {
        direction = (int) (Math.random() * 4);
        xMove = 0;
        yMove = 0;
        switch (direction) {
            case 0:
                yMove = -speed;
                break;
            case 1:
                xMove = speed;
                break;
            case 2:
                yMove = speed;
                break;
            case 3:
                xMove = -speed;
                break;
            default:
                break;
        }
    }

    private BufferedImage getCurrentAnimationFrame() {
        if (idle) {
            switch (direction) {
                case 0:
                    return up.idle();
                case 1:
                    return right.idle();
                case 2:
                    return down.idle();
                case 3:
                    return left.idle();
                default:
                    return down.idle();
            }
        } else {
            switch (direction) {
                case 0:
                    return up.getCurrentFrame();
                case 1:
                    return right.getCurrentFrame();
                case 2:
                    return down.getCurrentFrame();
                case 3:
                    return left.getCurrentFrame();
                default:
                    return down.idle();
            }
        }
    }

    public boolean idleActions() {
        idle = true;
        if (TimeUnit.SECONDS.convert(System.nanoTime() - time, TimeUnit.NANOSECONDS) < timeUntilMove) {
            return false;
        }
        idle = false;
        timeUntilMove = (int) (Math.random() * 5) + 1;
        time = System.nanoTime();
        changeDirection();
        return true;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public String getName() {
        return name;
    }
    
    public int getPortraitID(){
        return portraitID;
    }
    
    public int getType(){
        return type;
    }

    public void onInteract(Player player) {

    }
}
