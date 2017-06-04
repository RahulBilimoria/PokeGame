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
public class Block extends Script {

    public Block() {
        super(1);
    }

    @Override
    public void render(Graphics g, int x, int y) {
        super.render(g, x + 10, y + 20, Color.red, "B");
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
