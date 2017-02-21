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
 * @author minim_000
 */
public class Warp extends Script {

    private int mapNumber, xCoord, yCoord;

    public Warp(int mapNumber, int xCoord, int yCoord) {
        super(2);
        this.mapNumber = mapNumber;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public void render(Graphics g, int x, int y) {
        g.setColor(Color.blue);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        g.drawString("W", x + 10, y + 20);
    }

    public int getMapNumber() {
        return mapNumber;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }
}
