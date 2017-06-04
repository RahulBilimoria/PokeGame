/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.item.potion;

import pokegame.item.Item;
import pokegame.utils.Utils;

/**
 *
 * @author Rahul
 */
public class StatusRemove extends Potion{
    
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
}
