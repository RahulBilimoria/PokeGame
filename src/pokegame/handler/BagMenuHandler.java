/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.handler;

import pokegame.menu.BagMenu;

/**
 *
 * @author Rahul
 */
public class BagMenuHandler {
    
    private Handler h;
    private BagMenu bm;
    
    public BagMenuHandler(BagMenu bm, Handler h){
        this.bm = bm;
        this.h = h;
    }
}
