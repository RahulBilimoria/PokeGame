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
public class Healing extends Potion{
    
    public static final int ITEM_COUNT = 18;
    public static Item healingItems[] = new Healing[ITEM_COUNT];
    
    private int id;
    private int healAmount;
    private int typeOfCondition;
    
    public Healing(int id, String name, int healAmount, int typeOfCondition) {
        super(name, 0, true);
        this.id = id;
        this.healAmount = healAmount;
        this.typeOfCondition = typeOfCondition;
    }
    
    public static void init(){
        String file = Utils.loadFileAsString("dat/item/medicine/Healing.dat");
        String data[] = file.split("\\s+");
        for (int x = 0; x < ITEM_COUNT; x++){
            int i = x*4;
            healingItems[x] = new Healing(Utils.parseInt(data[i]),
                    data[i+1].replaceAll("_", " "),
                    Utils.parseInt(data[i+2]),
                    Utils.parseInt(data[i+3]));
        }
    }
    
    public int getHealAmount(){
        return healAmount;
    }
    
    @Override
    public void use(Pokemon p){
        if (p.getHp() > 0){
            p.heal(healAmount);
            if (typeOfCondition == 1){
                p.setStatus(0);
            }
        } else {
            if (typeOfCondition == 2){
                p.heal((int)Math.ceil(p.getMaxHp()/2));
            } else if (typeOfCondition == 3){
                p.heal(p.getMaxHp());
            }
        }
    }
}
