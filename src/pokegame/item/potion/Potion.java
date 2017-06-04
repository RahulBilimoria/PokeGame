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
    
    /* Healing ID = 0
    *  PPRestore ID = 1
    *  StatBoost ID = 2
    *  StatusRemove ID = 3
    */
    public static final int TOTAL_ITEM_COUNT = Healing.ITEM_COUNT +
                                               PPRestore.ITEM_COUNT +
                                               StatBoost.ITEM_COUNT +
                                               StatusRemove.ITEM_COUNT;

    private int typeOfPotion;
    
    public Potion(String name, int typeOfPotion, boolean battleUse) {
        super(name, 0, battleUse);
        this.typeOfPotion = typeOfPotion;
    }
    
    public static void init(){
        Healing.init();
        PPRestore.init();
        StatBoost.init();
        StatusRemove.init();
    }
}