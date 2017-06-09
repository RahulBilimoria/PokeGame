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
import pokegame.gfx.Animation;
import pokegame.gfx.Asset;
import pokegame.handler.Handler;
import pokegame.tiles.Tile;

/**
 *
 * @author Rahul
 */
public class NPC extends Person {

    protected int centerX, centerY; // center that the NPC will 'revolve' around
    protected int distanceFromCenter; // distance in tiles NPC can walk from center
    protected int direction;
    protected boolean isSolid; // can other players walk through them
    protected boolean canMove; // can this NPC move from the center
    protected Animation up, down, left, right; // The NPCs sprites
    private int timeUntilMove; // time until npc can move again
    private long time; // time since last move

    //Gym Leaders
    //Quest NPCs
    //Story NPCs
    //Battle NPCs
    //Will add more as i think of them
    public NPC(Handler handler, float x, float y, int width, int height,
            int distanceFromCenter, boolean isSolid, boolean canMove) {
        super(handler, x, y, width, height);
        centerX = xi;
        centerY = yi;
        this.distanceFromCenter = distanceFromCenter;
        this.isSolid = isSolid;
        this.canMove = canMove;
        up = new Animation(400, Asset.player_up);
        down = new Animation(400, Asset.player_down);
        left = new Animation(400, Asset.player_left);
        right = new Animation(400, Asset.player_right);
        timeUntilMove = (int) (Math.random() * 5) + 1;
        time = System.nanoTime();
        direction = 0;
    }

    @Override
    public void tick() {
        if (isMoving) {
            move();
            updateIntXY();
        } else {
            if (TimeUnit.SECONDS.convert(System.nanoTime() - time, TimeUnit.NANOSECONDS) < timeUntilMove) return;
            timeUntilMove = (int) (Math.random() * 5) + 1;
            time = System.nanoTime();
            changeDirection();
            updateIntXY();
            isMoving = true;
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getXOffset()),
                (int) (y - handler.getGameCamera().getYOffset()- Tile.TILE_HEIGHT), width, 2 * height, null);
    }

    public void changeDirection() {
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
