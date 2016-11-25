/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.handler;

import java.awt.Canvas;
import pokegame.Game;
import pokegame.entity.player.Player;
import pokegame.gfx.GameCamera;
import pokegame.input.KeyManager;
import pokegame.world.World;

/**
 *
 * @author Rahul
 */
public class Handler {
    
    private Game game;
    private World world;
    
    public Handler(Game game){
        this.game = game;
    }
    
    public int getWidth(){
        return game.getWidth();
    }
    
    public int getHeight(){
        return game.getHeight();
    }
    
    public Game getGame(){
        return game;
    }
    
    public World getWorld(){
        return world;
    }
    
    public GameCamera getGameCamera(){
        return game.getGameCamera();
    }
    
    public KeyManager getKeyManager(){
        return game.getKeyManager();
    }
    
    public Player getPlayer(){
        return world.getPlayer();
    }
    
    public Canvas getCanvas(){
        return game.getCanvas();
    }
    
    public void setMovable(boolean moveable){
        game.getKeyManager().setMoveable(moveable);
    }
    
    public void setWorld(World world){
        this.world = world;
    }
}
