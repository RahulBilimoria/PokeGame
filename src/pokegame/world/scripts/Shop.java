/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.world.scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author Rahul
 */
public class Shop extends Script{
    
    public Shop() {
        super(7);
    }
    
    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(Color.cyan);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        g.drawString("S", x + 8, y + 16);
    }
}
