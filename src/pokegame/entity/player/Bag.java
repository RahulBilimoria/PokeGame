/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.entity.player;

import java.util.ArrayList;
import pokegame.item.Item;
import pokegame.item.pokeball.Pokeball;
import pokegame.item.potion.Healing;

/**
 *
 * @author Rahul
 */
public class Bag {

    public static final int MAX_ITEMS = 64;

    public class MyItem {

        Item item;
        int itemCount;

        public MyItem(Item item, int itemCount) {
            this.item = item;
            this.itemCount = itemCount;
        }

        public Item getItem() {
            return item;
        }
        
        public int getId(){
            return item.getItemID();
        }

        public int getItemCount() {
            return itemCount;
        }

        public void setItemCount(int i) {
            itemCount += i;
        }
    }

    private Player player;
    private int itemsCount = 0;
    private ArrayList<MyItem> bag;
    private ArrayList<MyItem> medicine, pokeballs, berries, keyItems;
    private ArrayList<MyItem> list[] = new ArrayList[5];
    public Bag(Player player) {
        this.player = player;
        bag = new ArrayList<>();
        medicine = new ArrayList<>();
        pokeballs = new ArrayList<>();
        berries = new ArrayList<>();
        keyItems = new ArrayList<>();
        addItem(Healing.healingItems[0], 5);
        addItem(Healing.healingItems[1], 1);
        addItem(Pokeball.pokeballs[0], 5);
        addItem(Pokeball.pokeballs[1], 1);
        addItem(Pokeball.pokeballs[2], 1);
        addItem(Pokeball.pokeballs[3], 1);
    }
    
    public ArrayList<MyItem> getBag(int bagID){
        switch(bagID){
            case 0: return medicine;
            case 1: return pokeballs;
            case 2: return berries;
            case 3: return keyItems;
            default: return bag;
        }
    }
    
    public String getBagName(int bagID){
        switch(bagID){
            case 0: return "Medicine";
            case 1: return "Pokeballs";
            case 2: return "Berries";
            case 3: return "Key Items";
            default: return "Bag";
        }
    }

    public void addItem(Item i, int count) {
        changeItemCount(i, count);
    }

    public void removeItem(Item i, int count) {
        changeItemCount(i, count);
    }

    public void removeItem(String name, int count) {
        for (int x = 0; x < bag.size(); x++) {
            if (bag.get(x).getItem().getName().equals(name)) {
                bag.get(x).setItemCount(count);
                if (bag.get(x).getItemCount() <= 0) {
                    bag.remove(x);
                }
                return;
            }
        }
    }

    public Item getItem(int i) {
        return bag.get(i).getItem();
    }

    public Item getItem(String name) {
        for (MyItem i : bag) {
            if (i.getItem().getName().equalsIgnoreCase(name)) {
                return i.getItem();
            }
        }
        return null;
    }

    public int getItemCount(String name) {
        for (MyItem i : bag) {
            if (i.getItem().getName().equalsIgnoreCase(name)) {
                return i.getItemCount();
            }
        }
        return 0;
    }

    public int getItemCount(int i) {
        return bag.get(i).getItemCount();
    }

    public int getBagCount() {
        return itemsCount;
    }

    public void changeItemCount(Item i, int count) {
        ArrayList<MyItem> bg;
        switch (i.getItemType()) {
            case 0:
                bg = medicine;
                break;
            case 1:
                bg = pokeballs;
                break;
            case 2:
                bg = berries;
                break;
            case 3:
                bg = keyItems;
                break;
            default:
                bg = bag;
                break;
        }
        for (int x = 0; x < bg.size(); x++) {
            if (bg.get(x).getItem().getItemID() == i.getItemID()) {
                bg.get(x).setItemCount(count);
                if (bg.get(x).getItemCount() <= 0){
                    bg.remove(x);
                }
                return;
            }
        }
        if (count > 0){
            bg.add(new MyItem(i, count));
        }
    }
    
    public int getNumberOfItems(int id){
        if (id == -1) return 0;
        ArrayList<MyItem> bg;
        switch (Item.items[id].getItemType()) {
            case 0:
                bg = medicine;
                break;
            case 1:
                bg = pokeballs;
                break;
            case 2:
                bg = berries;
                break;
            case 3:
                bg = keyItems;
                break;
            default:
                bg = bag;
                break;
        }
        for (MyItem i : bg) {
            if (i.getId() == id){
                return i.getItemCount();
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        String b = "";
        for (MyItem i : bag) {
            b = b + i.getItem().toString();
        }
        return b;
    }
}
