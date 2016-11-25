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
public class Spawn{
    
    private int pokemon_id;
    private boolean gender, shiny;
    private int min_lvl, max_lvl;
    private float pokemon_spawn_rate, item_spawn_rate;
    private int item_id;
    private Moveset moveset;

    public Spawn(int pokemon_id, int min_lvl, int max_lvl, float pokemon_spawn_rate,
                 Moveset moveset, int item_id, float item_spawn_rate) {
        this.pokemon_id = pokemon_id;
        this.min_lvl = min_lvl;
        this.max_lvl = max_lvl;
        this.pokemon_spawn_rate = pokemon_spawn_rate;
        this.moveset = moveset;
        this.item_id = item_id;
        this.item_spawn_rate = item_spawn_rate;
        assignGender();
        assignShiny();
    }
    
    public void assignGender(){
        gender = true;
    }
    
    public void assignShiny(){
        shiny = false;
        Random rand = new Random();
        if (rand.nextInt(1000000) <= 1)
            shiny = true;
    }
    
    public float getSpawnRate(){
        return pokemon_spawn_rate;
    }
    
    public Pokemon getPokemon(){ 
        //get level, multiply it with tp per level,
        //and assign it random amounts to stats until theres no more tp
        //for now its just 1 added to each stat cause fk it
        int lvl = (int)(Math.random()*(max_lvl - min_lvl))+min_lvl; 
        return new Pokemon(Pokemon.POKEMON_LIST[pokemon_id], gender, shiny, 
                                lvl, 1, 1, 1, 1, 1, 1, moveset);
    }
}
