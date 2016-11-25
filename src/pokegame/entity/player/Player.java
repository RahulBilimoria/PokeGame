/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.entity.player;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import pokegame.entity.Person;
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

    private Animation up, down, left, right;
    private int direction;
    private int activePokemon;
    private int lastX, lastY;
    private boolean enabled;

    private Bag bag;
    private Party party;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, Person.DEFAULT_CREATURE_WIDTH, Person.DEFAULT_CREATURE_HEIGHT);
        up = new Animation(400, Asset.player_up);
        down = new Animation(400, Asset.player_down);
        left = new Animation(400, Asset.player_left);
        right = new Animation(400, Asset.player_right);
        direction = 0;
        moved = false;
        bag = new Bag();
        party = new Party();
        activePokemon = 0;
        enabled = true;
    }

    @Override
    public void tick() {
        if (enabled) {
            if (!isMoving) {
                getInput();
            }
            if (isMoving) {
                up.tick();
                down.tick();
                left.tick();
                right.tick();
                move();
                updateIntXY();
            }
            if (lastX == xi && lastY == yi) {
            } else {
                setMoved(true);
                lastX = xi;
                lastY = yi;
            }
            handler.getGameCamera().centerOnEntity(this);
        }
    }

    private void getInput() {
        if (!isMoving) {
            xMove = 0;
            yMove = 0;
        }
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
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getXOffset()),
                (int) (y - handler.getGameCamera().getYOffset() - Tile.TILE_HEIGHT), width, 2 * height, null);
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

    public void setActiveNumber(int num){
        activePokemon = num;
    }
    
    public int getActiveNumber(){
        return activePokemon;
    }
    
    public Pokemon getPokemon(int partyID){
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
    
    public Bag getBag(){
        return bag;
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
    
    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }
}
