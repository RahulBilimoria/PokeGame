/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.pokemon.nature;

import pokegame.utils.Utils;

/**
 *
 * @author Rahul
 */
public class Nature {
    
    public static final int NATURE_COUNT = 25;
    public static final Nature NATURE_LIST[] = new Nature[NATURE_COUNT];

    private int id;
    String name;
    private int hp, att, def, spatt, spdef ,speed;
    
    public Nature(int id, String name, int hp, int att, int def, int spatt, int spdef, int speed){
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.att = att;
        this.def = def;
        this.spatt = spatt;
        this.spdef = spdef;
        this.speed = speed;
    }
    
    public static void init(){
        String file = Utils.loadFileAsString("dat/pokemon/nature/Natures.dat");
        String nature[] = file.split("\\s+");
        for (int x = 0; x < NATURE_COUNT; x++){
            NATURE_LIST[x] = new Nature(Utils.parseInt(nature[(x*8)+0]),
                                        nature[(x*8)+1],
                                        Utils.parseInt(nature[(x*8)+2]),
                                        Utils.parseInt(nature[(x*8)+3]),
                                        Utils.parseInt(nature[(x*8)+4]),
                                        Utils.parseInt(nature[(x*8)+5]),
                                        Utils.parseInt(nature[(x*8)+6]),
                                        Utils.parseInt(nature[(x*8)+7]));
        }
    }
    
    public static Nature getRandomNature(){
        return NATURE_LIST[(int)(Math.random()*25)];
    }
    
    public static Nature getNatureById(int id){
        if (id >= 0 && id < 25){
            return NATURE_LIST[id];
        }
        return NATURE_LIST[0];
    }
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getAtt() {
        return att;
    }

    public int getDef() {
        return def;
    }

    public int getSpatt() {
        return spatt;
    }

    public int getSpdef() {
        return spdef;
    }

    public int getSpeed() {
        return speed;
    }
}
