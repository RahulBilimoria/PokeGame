/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.entity.player;

import java.util.ArrayList;
import pokegame.item.Item;
import pokegame.item.pokeball.Pokeball;
import pokegame.item.potion.Potion;

/**
 *
 * @author Rahul
 */
public class Bag {
    
    public static final int MAX_ITEMS = 64;
    
    private int itemsCount = 0;
    private ArrayList<Item> bag;
    
    public Bag(){
        bag = new ArrayList();
        bag.add(new Pokeball(0, 10));
        itemsCount++;
        bag.add(new Potion(1,3));
        itemsCount++;
    }
    
    public void addItem(Item i, int count){
        if (bag.contains(i)){
            bag.get(bag.indexOf(i)).addItem(count);
        }
    }
    
    public void removeItem(Item i, int count){
        if (bag.contains(i)){
            bag.get(bag.indexOf(i)).removeItem(count);
        }
    }
    
    public Item getItem(int i){
        return bag.get(i);
    }
    
    public Item getItem(String name){
        for (Item i: bag){
            if (i.getName().equalsIgnoreCase(name)){
                return i;
            }
        }
        return null;
    }
    
    public int getItemCount(){
        return itemsCount;
    }
    
    
    @Override
    public String toString(){
        String b = "";
        for (Item i:bag){
            b = b + i.toString();
        }
        return b;
    }
}
