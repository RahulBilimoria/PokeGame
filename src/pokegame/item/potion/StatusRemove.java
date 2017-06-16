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
public class StatusRemove extends Potion{
    
    /**
     * 0 : NORMAL
     * 1 : POISONED
     * 2 : PARALYZED
     * 3 : SLEEP
     * 4 : BURN
     * 5 : FROZEN
     * 6 : CONFUSION
     * 7 : HEAL ALL
     * 8 : HEAL ALL
     */
    public static final int ITEM_COUNT = 14;
    public static Item statusItems[] = new StatusRemove[ITEM_COUNT];
    
    private int id;
    private int typeOfRemoval;
   
    public StatusRemove(int id, String name, int typeOfRemoval) {
        super(name, 3, true);
        this.id = id;
        this.typeOfRemoval = typeOfRemoval;
    }
    
    public static void init(){
        String file = Utils.loadFileAsString("dat/item/medicine/StatusCondition.dat");
        String data[] = file.split("\\s+");
        for (int x = 0; x < ITEM_COUNT; x++){
            int i = x * 3;
            statusItems[x] = new StatusRemove(Utils.parseInt(data[i]),
                    data[i+1].replaceAll("_", " "),
                    Utils.parseInt(data[i+2]));
        }
    }
    
    public int getTypeOfRemoval(){
        return typeOfRemoval;
    }
    
    @Override
    public void use(Pokemon p){
        if (p.getStatusInt() == typeOfRemoval || typeOfRemoval == 7 || typeOfRemoval == 8)
            p.setStatus(0);
    }
    
    @Override
    public float battleUse(Pokemon player, Pokemon enemy){
        return 0;
    }
}
