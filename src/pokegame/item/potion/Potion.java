/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.item.potion;

import pokegame.item.Item;

/**
 *
 * @author Rahul
 */
public class Potion extends Item{
    
    private int healAmount = 20;
    
    public Potion(int id, int itemCount) {
        super(id, itemCount, "Potion");
    }
    
    public int getHealAmount(){
        return healAmount;
    }
    
    public String toString(){
        return "Potion\nCount: " + itemCount + "\n";
    }
}
