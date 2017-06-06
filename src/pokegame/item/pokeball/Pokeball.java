/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.item.pokeball;

import pokegame.item.Item;
import pokegame.pokemon.Pokemon;
import pokegame.utils.Utils;

/**
 *
 * @author Rahul
 */
public class Pokeball extends Item{
    
    /* Normal Pokeball ID = 0
     * Special Pokeball ID = 1
     */
    
    public static final int ITEM_COUNT = 11;
    public static Pokeball pokeballs[] = new Pokeball[ITEM_COUNT];
    
    public static final int TOTAL_ITEM_COUNT = ITEM_COUNT + 
                            SpecialPokeball.SPECIAL_ITEM_COUNT;
    
    private float catchRate;
    
    public Pokeball(int id, String name, float catchRate){
        super (name, 1, true);
        this.catchRate = catchRate;
    }
    
    public static void init(){
        String file = Utils.loadFileAsString("dat/item/pokeball/Pokeball.dat");
        String data[] = file.split("\\s+");
        for (int x = 0; x < ITEM_COUNT; x++){
            int i = x * 3;
            pokeballs[x] = new Pokeball(Utils.parseInt(data[i]),
                    data[i+1].replaceAll("_", " "),
                    Utils.parseFloat(data[i+2]));
        }
        SpecialPokeball.init();
    }
    
    public float getCatchRate(){
        return catchRate;
    }
    
    @Override
    public void use(Pokemon p){
        
    }
}