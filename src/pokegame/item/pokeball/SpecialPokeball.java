/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.item.pokeball;

import pokegame.item.Item;
import pokegame.utils.Utils;

/**
 *
 * @author Rahul
 */
public class SpecialPokeball extends Pokeball {

    public static final int SPECIAL_ITEM_COUNT = 16;
    public static Item specialPokeballs[] = new SpecialPokeball[ITEM_COUNT];

    private int id;
    private int type;
    private float catchRates[] = new float[3];

    public SpecialPokeball(int id, String name, float catchRate, float catchRates[], int type) {
        super(id, name, catchRate);
        this.catchRates = catchRates;
        this.type = type;
    }

    public static void init() {
        String file = Utils.loadFileAsString("dat/item/pokeball/SpecialPokeball.dat");
        String data[] = file.split("\\s+");
        for (int x = 0; x < ITEM_COUNT; x++) {
            int i = x * 7;
            float rates[] = {Utils.parseFloat(data[i + 3]), Utils.parseFloat(data[i + 4]), Utils.parseFloat(data[i + 5])};
            specialPokeballs[x] = new SpecialPokeball(Utils.parseInt(data[i]),
                    data[i + 1].replaceAll("_", " "),
                    Utils.parseFloat(data[i + 2]),
                    rates,
                    Utils.parseInt(data[i + 6]));
        }
    }

    public int getType() {
        return type;
    }

    public float getSecondaryCatchRate(int id) {
        return catchRates[id];
    }

}
