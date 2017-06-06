/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.item.potion;

import pokegame.item.Item;
import pokegame.pokemon.Pokemon;
import pokegame.utils.Utils;

/**
 *
 * @author Rahul
 */
public class StatBoost extends Potion{
    
    public static final int ITEM_COUNT = 16;
    public static Item statItems[] = new StatBoost[ITEM_COUNT];
    
    private int id;
    private int statId, increaseAmount;
    
    public StatBoost(int id, String name, int statId, int increaseAmount) {
        super(name, 2, false);
        this.id = id;
        this.statId = statId;
        this.increaseAmount = increaseAmount;
    }
    
    public static void init(){
        String file = Utils.loadFileAsString("dat/item/medicine/StatBoost.dat");
        String data[] = file.split("\\s+");
        for (int x = 0; x < ITEM_COUNT; x++){
            int i = x * 4;
            statItems[x] = new StatBoost(Utils.parseInt(data[i]),
                    data[i+1].replaceAll("_", " "),
                    Utils.parseInt(data[i+2]),
                    Utils.parseInt(data[i+3]));
        }
    }
    
    public int getStatId(){
        return statId;
    }
    
    public int getIncreaseAmount(){
        return increaseAmount;
    }
    
    @Override
    public void use(Pokemon p){
        
    }
}
