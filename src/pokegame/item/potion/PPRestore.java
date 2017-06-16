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
public class PPRestore extends Potion{
    
    public static final int ITEM_COUNT = 4;
    public static Item ppItems[] = new PPRestore[ITEM_COUNT];
    
    private int id;
    private int restoreAmount;
    private boolean restoreAllMoves;
    
    public PPRestore(int id, String name, int restoreAmount, boolean restoreAllMoves) {
        super(name, 1, true);
        this.id = id;
        this.restoreAmount = restoreAmount;
        this.restoreAllMoves = restoreAllMoves;
    }
    
    public static void init(){
        String file = Utils.loadFileAsString("dat/item/medicine/PPRestore.dat");
        String data[] = file.split("\\s+");
        for (int x = 0; x < ITEM_COUNT; x++){
            int i = x*4;
            boolean all = false;
            if (Utils.parseInt(data[i+3]) == 1)
                all = true;
            ppItems[x] = new PPRestore(Utils.parseInt(data[i]),
                    data[i+1].replaceAll("_", " "),
                    Utils.parseInt(data[i+2]),
                    all);
        }
    }
    
    public int getRestoreAmount(){
        return restoreAmount;
    }
    
    public boolean getRestoreAllMoves(){
        return restoreAllMoves;
    }
    
    @Override
    public void use(Pokemon p){
        if (restoreAllMoves){

        }
    }
    
    @Override
    public float battleUse(Pokemon player, Pokemon enemy){
        return 0;
    }
    
}
