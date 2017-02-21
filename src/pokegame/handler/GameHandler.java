/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.handler;

import java.awt.Graphics;
import pokegame.entity.player.Player;

/**
 *
 * @author Rahul
 */
public class GameHandler {
    
    private Player player;
    
    public GameHandler(Player player){
        this.player = player;
    }
    
    public void tick(){
        player.tick();
    }
    
    public void render(Graphics g){
        player.render(g);
    }
    
    public Player getPlayer(){
        return player;
    }
    
}
