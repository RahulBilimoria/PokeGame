/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.item;

import java.awt.image.BufferedImage;

/**
 *
 * @author Rahul
 */
public abstract class Item {
    
    protected int id;
    protected String name;
    protected int sellPrice;
    protected int itemCount;
    protected boolean battleUse;
    protected BufferedImage icon;
    
    public Item(int id, int itemCount, String name){
        this.id = id;
        this.itemCount = itemCount;
        this.name = name;
        battleUse = true;
    }
    
    public static void init(){
        
    }
    
    public void addItem(int i){
        itemCount += i;
    }
    
    public void removeItem(int i){
        itemCount -= i;
    }
    
    public int getID(){
        return id;
    }
    
    public int getItemCount(){
        return itemCount;
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
