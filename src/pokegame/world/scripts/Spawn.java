/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.world.scripts;

import java.util.Random;
import pokegame.pokemon.Pokemon;
import pokegame.pokemon.move.Moveset;

/**
 *
 * @author minim_000
 */
public class Spawn {

    private int pokemon_id;
    private int hp, att, def, spatt, spdef, speed;
    private boolean shiny;
    private int min_lvl, max_lvl;
    private float pokemon_spawn_rate, item_spawn_rate;
    private int item_id;
    private Moveset moveset;

    public Spawn(int pokemon_id, int min_lvl, int max_lvl, float pokemon_spawn_rate,
            int hp, int att, int def, int spatt, int spdef, int speed,
            Moveset moveset, int item_id, float item_spawn_rate) {
        this.pokemon_id = pokemon_id;
        this.min_lvl = min_lvl;
        this.max_lvl = max_lvl;
        this.pokemon_spawn_rate = pokemon_spawn_rate;
        this.hp = hp;
        this.att = att;
        this.def = def;
        this.spatt = spatt;
        this.spdef = spdef;
        this.speed = speed;
        this.moveset = moveset;
        this.item_id = item_id;
        this.item_spawn_rate = item_spawn_rate;
        assignShiny();
    }

    public void assignShiny() {
        shiny = false;
        Random rand = new Random();
        if (rand.nextInt(1000000) <= 1) {
            shiny = true;
        }
    }

    public Pokemon getPokemon() {
        //get level, multiply it with tp per level,
        //and assign it random amounts to stats until theres no more tp
        //for now its just 1 added to each stat cause fk it
        int lvl = (int) (Math.random() * (max_lvl - min_lvl)) + min_lvl;
        return new Pokemon(pokemon_id, shiny,
                lvl, 1, 1, 1, 1, 1, 1, moveset);
    }

    public int getPokemonId() {
        return pokemon_id;
    }

    public int getMinLevel() {
        return min_lvl;
    }

    public int getMaxLevel() {
        return max_lvl;
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

    public float getSpawnRate() {
        return pokemon_spawn_rate;
    }

    public int getItemId() {
        return item_id;
    }

    public float getItemSpawnRate() {
        return item_spawn_rate;
    }

    public int getMoveId(int i) {
        if (moveset.getMove(i) == null) {
            return -1;
        }
        return moveset.getMove(i).getId();
    }

    public Moveset getMoveset() {
        return moveset;
    }

    public String getName() {
        if (pokemon_id < 0){
            return "null";
        }
        return Pokemon.getPokemonName(pokemon_id);
    }

    public void setPokemon_id(int pokemon_id) {
        this.pokemon_id = pokemon_id;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setAtt(int att) {
        this.att = att;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public void setSpatt(int spatt) {
        this.spatt = spatt;
    }

    public void setSpdef(int spdef) {
        this.spdef = spdef;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setMin_lvl(int min_lvl) {
        this.min_lvl = min_lvl;
    }

    public void setMax_lvl(int max_lvl) {
        this.max_lvl = max_lvl;
    }

    public void setPokemon_spawn_rate(float pokemon_spawn_rate) {
        this.pokemon_spawn_rate = pokemon_spawn_rate;
    }

    public void setItem_spawn_rate(float item_spawn_rate) {
        this.item_spawn_rate = item_spawn_rate;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public void setMoveset(Moveset moveset) {
        this.moveset = moveset;
    }
}
