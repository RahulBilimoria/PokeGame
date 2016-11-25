/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.state;

import java.awt.Graphics;
import pokegame.handler.Handler;
import pokegame.world.World;

/**
 *
 * @author Rahul
 */
public class GameState {
    
    private Handler handler;
    private World world;
    
    public GameState(Handler handler){
        this.handler = handler;
        world = new World(handler);
        handler.setWorld(world);
    }
    
    public void tick(){
        world.tick();
    }
    
    public void render(Graphics g){
        world.render(g);
    }
}
