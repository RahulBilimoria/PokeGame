/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.item;

import java.awt.image.BufferedImage;
import pokegame.gfx.Asset;
import pokegame.item.berry.Berry;
import pokegame.item.pokeball.Pokeball;
import pokegame.item.potion.Potion;
import pokegame.pokemon.Pokemon;

/**
 *
 * @author Rahul
 */
public abstract class Item {
    
    /* POTION = 0
    /* POKEBALL = 1
    /* BERRIES = 2
    /* KEY ITEMS = 3
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
    protected String effect;
    protected String description;
    
    public Item(String name, int typeOfItem, boolean battleUse, String effect, String description, int x, int y){
        this.name = name;
        this.typeOfItem = typeOfItem;
        this.battleUse = battleUse;
        this.itemId = itemCount;
        this.effect = effect;
        this.description = description;
        icon = Asset.itemSheet.spriteSheet[x][y];
        itemCount++;
    }
    
    public static void init(){
        Potion.init();
        Pokeball.init();
        Berry.init();
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
    
    public abstract void use(Pokemon p);
    
    public abstract float battleUse(Pokemon player, Pokemon enemy);
}
