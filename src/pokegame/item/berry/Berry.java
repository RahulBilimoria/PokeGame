/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.item.berry;

import pokegame.item.Item;
import pokegame.pokemon.Pokemon;
import pokegame.utils.Utils;

/**
 *
 * @author Rahul
 */
public class Berry extends Item{
    
    public static final int ITEM_COUNT = 67;
    public static Berry itemList[] = new Berry[ITEM_COUNT];
    
    private int healAmount = 0;
    private int type;
    
    public Berry(int id, String name, int type) {
        super(name, 2, true, "", "", 0, 0);
        this.type = type;
    }
    
    public static void init(){
        String file = Utils.loadFileAsString("dat/item/berry/Berry.dat");
        String data[] = file.split("\\s+");
        for (int x = 0; x < ITEM_COUNT; x++){
            itemList[x] = new Berry(Utils.parseInt(data[x]),
                    data[x+1].replaceAll("_", " "),
                    Utils.parseInt(data[x+2]));
        }
    }
    
    @Override
    public void use(Pokemon p){
        
    }
    
    @Override
    public float battleUse(Pokemon player, Pokemon enemy){
        return 0;
    }
}
