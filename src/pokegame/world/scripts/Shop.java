/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.world.scripts;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Rahul
 */
public class Shop extends Script{
    
    private String fileName;
    
    public Shop() {
        super(7);
        fileName = "";
    }
    
    @Override
    public void render(Graphics g, int x, int y) {
        super.render(g, x + 10, y + 20, Color.cyan, "M");
    }
    
    public String getFileName(){
        return fileName;
    }
}
