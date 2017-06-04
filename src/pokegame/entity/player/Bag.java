/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.entity.player;

import java.util.ArrayList;
import pokegame.item.Item;

/**
 *
 * @author Rahul
 */
public class Bag {

    public static final int MAX_ITEMS = 64;

    class MyItem {

        Item item;
        int itemCount;

        public MyItem(Item item, int itemCount) {
            this.item = item;
            this.itemCount = itemCount;
        }

        public Item getItem() {
            return item;
        }

        public int getItemCount() {
            return itemCount;
        }

        public void setItemCount(int i) {
            itemCount += i;
        }
    }

    private int itemsCount = 0;
    private ArrayList<MyItem> bag;
    private ArrayList<MyItem> medicine, pokeballs, berries, keyItems;
    private ArrayList<MyItem> list[] = new ArrayList[5];
    public Bag() {
        bag = new ArrayList<>();
        medicine = new ArrayList<>();
        pokeballs = new ArrayList<>();
        berries = new ArrayList<>();
        keyItems = new ArrayList<>();
    }

//    public void addItem(Item i, int count){
//        for (int x = 0; x < bag.size(); x++){
//            if (bag.get(x).getItem().getName().equals(i.getName())){
//                bag.get(x).setItemCount(count);
//                return;
//            }
//        }
//        bag.add(new MyItem(i,count));
//    }
//    public void removeItem(Item i, int count){
//        for (int x = 0; x < bag.size(); x++){
//            if (bag.get(x).getItem().getName().equals(i.getName())){
//                bag.get(x).setItemCount(count);
//                if (bag.get(x).getItemCount() <= 0){
//                    bag.remove(x);
//                }
//                return;
//            }
//        }
//    }
    public void addItem(Item i, int count) {
        changeItemCount(i, count, 0);
    }

    public void removeItem(Item i, int count) {
        changeItemCount(i, count, 1);
    }
    
    public void getBagSlotSize(int index){
        switch (index){
            
        }
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

    public void changeItemCount(Item i, int count, int add) {
        ArrayList<MyItem> bg = new ArrayList<>();
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
                break;
            default:
                break;
        }
        for (int x = 0; x < bg.size(); x++) {
            if (bg.get(x).getItem().getItemID() == i.getItemID()) {
                bg.get(x).setItemCount(count);
                return;
            }
        }
        if (add == 0){
            bg.add(new MyItem(i, count));
        }
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
