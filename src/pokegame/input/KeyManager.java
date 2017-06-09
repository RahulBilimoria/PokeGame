/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author minim_000
 */
public class KeyManager implements KeyListener {

    private boolean keys[];
    public boolean up, down, left, right, ctrl;
    private boolean moveable;

    public KeyManager() {
        keys = new boolean[256];
        moveable = true;
    }

    public void tick() {
        if (moveable) {
            up = keys[KeyEvent.VK_W];
            down = keys[KeyEvent.VK_S];
            left = keys[KeyEvent.VK_A];
            right = keys[KeyEvent.VK_D];
            ctrl = keys[17];
        } else {
            reset();
        }
    }

    public void reset() {
        keys[KeyEvent.VK_W] = false;
        keys[KeyEvent.VK_S] = false;
        keys[KeyEvent.VK_A] = false;
        keys[KeyEvent.VK_D] = false;
        keys[KeyEvent.CTRL_DOWN_MASK] = false;
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }
    
    public void setMoveable(boolean moveable){
        this.moveable = moveable;
    }
    
    public void unpressCTRL(){
        keys[17] = false;
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == 17) return;
        keys[ke.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        keys[ke.getKeyCode()] = false;
        if (ke.getKeyCode() == 17)
            keys[ke.getKeyCode()] = true;
    }

}
