/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.item;

import java.awt.image.BufferedImage;
import pokegame.item.pokeball.Pokeball;
import pokegame.item.potion.Potion;

/**
 *
 * @author Rahul
 */
public abstract class Item {
    
    /* POTION = 0
    /* POKEBALL = 1
    */
    public static Item items[];
    public static int itemCount = 0;
    
    protected int itemId;
    protected int typeOfItem;
    protected String name;
    protected int sellPrice;
    protected boolean battleUse;
    protected boolean heldItem;
    protected BufferedImage icon;
    
    public Item(String name, int typeOfItem, boolean battleUse){
        this.name = name;
        this.typeOfItem = typeOfItem;
        this.battleUse = battleUse;
        this.itemId = itemCount;
        itemCount++;
    }
    
    public static void init(){
        Potion.init();
        Pokeball.init();
        //Berry.init();
    }
    
    public int getItemID(){
        return itemId;
    }
    
    public int getItemType(){
        return typeOfItem;
    }
    
    public int getSellPrice(){
        return sellPrice;
    }
    
    public String getName(){
        return name;
    }
    
    public boolean getBattleUseable(){
        return battleUse;
    }
}
