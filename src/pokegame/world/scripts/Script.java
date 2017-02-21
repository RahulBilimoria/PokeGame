/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.world.scripts;

import java.awt.Graphics;

/**
 *
 * @author minim_000
 */
public class Script {
    
    // BLOCK = 1
    // WARP = 2
    // SPAWNLIST = 3, 4, 5
    // HEAL = 6
    // SHOP = 7
    // STORAGE = 8

    public static Script[] scripts = new Script[32];
    
    protected int scriptNumber;
    
    public Script(int scriptNumber) {
        this.scriptNumber = scriptNumber;
    }

    public void tick() {

    }

    public void render(Graphics g, int x, int y) {
        
    }

    public static void init() {
        scripts[0] = new Script(0);
        scripts[1] = new Block();
        for (int x = 2; x < 32; x++) {
            scripts[x] = new Script(x);
        }
        scripts[6] = new Heal();
        scripts[8] = new Storage();
    }

    public void setScriptNumber(int scriptNumber) {
        this.scriptNumber = scriptNumber;
    }

    public int getScriptNumber() {
        return scriptNumber;
    }

    public boolean isSolid() {
        return false;
    }

    public boolean isWarp(){
        return (scriptNumber == 2);
    }
    
    public boolean isFish(){
        return (scriptNumber == 3);
    }
}
