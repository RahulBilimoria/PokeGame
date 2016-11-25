/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.item.berry;

import pokegame.item.Item;

/**
 *
 * @author Rahul
 */
public abstract class Berry extends Item{
    
    public Berry(int id, int itemCount) {
        super(id, itemCount, "Berry");
    }
    
}
