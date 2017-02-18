/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.world.mapeditor;

import java.awt.Graphics;
/**
 *
 * @author minim_000
 */
public abstract class Editor {
    
    protected boolean close;
    
    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract void exit();
    
}
