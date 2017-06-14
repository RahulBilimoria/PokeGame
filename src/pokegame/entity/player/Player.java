/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.entity.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import pokegame.entity.Person;
import pokegame.entity.player.Bag.MyItem;
import pokegame.gfx.Animation;
import pokegame.gfx.Asset;
import pokegame.handler.Handler;
import pokegame.pokemon.Pokemon;
import pokegame.pokemon.move.Move;
import pokegame.tiles.Tile;

/**
 *
 * @author minim_000
 */
public class Player extends Person {

    private int id;
    private String name;

    private Animation up, down, left, right;
    private int direction;
    private int activePokemon;
    private int lastX, lastY;
    private boolean enabled;

    private Bag bag;
    private Party party;
    private Storage storage;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, Person.DEFAULT_CREATURE_WIDTH, Person.DEFAULT_CREATURE_HEIGHT);
        up = new Animation(400, Asset.player_up);
        down = new Animation(400, Asset.player_down);
        left = new Animation(400, Asset.player_left);
        right = new Animation(400, Asset.player_right);
        direction = 0;
        moved = false;
        bag = new Bag(this);
        party = new Party(handler, this);
        storage = new Storage(handler, this);
        activePokemon = 0;
        enabled = true;
    }

    @Override
    public void tick() {
        if (enabled) {
            if (!isMoving) {
                getInput();
            }
        }
        if (isMoving) {
            up.tick();
            down.tick();
            left.tick();
            right.tick();
            move();
        } else {
            xMove = 0;
            yMove = 0;
        }
        if (!(lastX == xTile && lastY == yTile)) {
            setMoved(true);
            lastX = xTile;
            lastY = yTile;
            
        }
        handler.getGameCamera().centerOnEntity(this);
    }

    private void getInput() {
        xMove = 0;
        yMove = 0;
        if (handler.getKeyManager().up) {
            isMoving = true;
            yMove = -speed;
        } else if (handler.getKeyManager().down) {
            isMoving = true;
            yMove = speed;
        } else if (handler.getKeyManager().left) {
            isMoving = true;
            xMove = -speed;
        } else if (handler.getKeyManager().right) {
            isMoving = true;
            xMove = speed;
        } else if (handler.getKeyManager().ctrl){
            handler.getKeyManager().unpressCTRL();
            enabled = false;
            switch (direction){
                case 0: handler.getWorld().interact(xTile, yTile - 1);
                    break;
                case 1: handler.getWorld().interact(xTile + 1, yTile);
                    break;
                case 2: handler.getWorld().interact(xTile, yTile + 1);
                    break;
                case 3: handler.getWorld().interact(xTile - 1, yTile);
                    break;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getCurrentAnimationFrame(), (int) (xPixel - handler.getGameCamera().getXOffset()),
                (int) (yPixel - handler.getGameCamera().getYOffset() - Tile.TILE_HEIGHT), width, 2 * height, null);
    }

    private BufferedImage getCurrentAnimationFrame() {
        if (xMove < 0) {
            direction = 3;
            return left.getCurrentFrame();
        } else if (xMove > 0) {
            direction = 1;
            return right.getCurrentFrame();
        } else if (yMove < 0) {
            direction = 0;
            return up.getCurrentFrame();
        } else if (yMove > 0) {
            direction = 2;
            return down.getCurrentFrame();
        } else {
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
        }
    }

    /*protected boolean collisionWithTile(int xPixel, int yPixel, int direction) {
        if (handler.getWorld().getEdit()) {
            return false;
        }
        switch (direction) {
            case 0:
                return handler.getWorld().getMap().getScript(xPixel, yPixel - 1, true).isSolid(); //up
            case 1:
                return handler.getWorld().getMap().getScript(xPixel + 1, yPixel, true).isSolid(); //right
            case 2:
                return handler.getWorld().getMap().getScript(xPixel, yPixel + 1, true).isSolid(); //down
            case 3:
                return handler.getWorld().getMap().getScript(xPixel - 1, yPixel, true).isSolid(); //left
            default:
                return false;
        }
    }*/

    public void setActiveNumber(int num) {
        activePokemon = num;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getActiveNumber() {
        return activePokemon;
    }

    public Pokemon getPokemon(int partyID) {
        return party.getPokemon(partyID);
    }

    public Pokemon getActivePokemon() {
        return party.getPokemon(activePokemon);
    }

    public BufferedImage getAllyImage() {
        return party.getPokemon(activePokemon).getBack();
    }

    public String getPokemonName() {
        return party.getPokemonName(activePokemon);
    }

    public Bag getBag() {
        return bag;
    }
    
    public ArrayList<MyItem> getBag(int bagID){
        return bag.getBag(bagID);
    }
    
    public String getBagName(int bagID){
        return bag.getBagName(bagID);
    }

    public Move getMove(int moveID) {
        return party.getPokemon(activePokemon).getMoveset().getMove(moveID);
    }

    public void printBag() {
        System.out.println(bag.toString());
    }

    public void printPokemon(int id) {
        System.out.println(party.toString(id));
    }

    public Party getParty() {
        return party;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
