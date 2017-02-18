/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.entity.player;

import pokegame.pokemon.Pokemon;
import pokegame.pokemon.move.Move;
import pokegame.pokemon.move.Moveset;

/**
 *
 * @author Rahul
 */
public class Party {
    
    private Pokemon[] pokemon = new Pokemon[6];
    
    public Party(){
        pokemon[0] = new Pokemon(Pokemon.POKEMON_LIST[0], false, 1, 1, 1,
                                    11,1,1,1, new Moveset(Move.MOVE_LIST[0]));
        pokemon[1] = new Pokemon(Pokemon.POKEMON_LIST[1], false, 2, 8, 7,
                                    2,2,2,2, new Moveset(Move.MOVE_LIST[0], Move.MOVE_LIST[1]));
        pokemon[2] = new Pokemon(Pokemon.POKEMON_LIST[2], false, 3, 6, 1,
                                    2,2,2,2, new Moveset(Move.MOVE_LIST[2], Move.MOVE_LIST[3]));
        pokemon[3] = new Pokemon(Pokemon.POKEMON_LIST[3], false, 4, 1, 5,
                                    2,2,2,2, new Moveset(Move.MOVE_LIST[4], Move.MOVE_LIST[5]));
        pokemon[4] = new Pokemon(Pokemon.POKEMON_LIST[4], false, 5, 2, 2,
                                    2,2,2,2, new Moveset(Move.MOVE_LIST[6], Move.MOVE_LIST[7], Move.MOVE_LIST[8]));
        pokemon[5] = new Pokemon(Pokemon.POKEMON_LIST[5], false, 6, 4, 1,
                                    2,2,2,2, new Moveset(Move.MOVE_LIST[9], Move.MOVE_LIST[10], Move.MOVE_LIST[11], Move.MOVE_LIST[12]));
    }
    
    public String getPokemonName(int partyID){
        return pokemon[partyID].getName();
    }
    
    public Pokemon getPokemon(int partyID){
        return pokemon[partyID];
    }
    
    public String toString(int partyID){
        return pokemon[partyID].toString();
    }
    
    public void heal(){
        for (int x = 0; x < 6; x++){
            if (pokemon[x] != null){
                pokemon[x].heal();
            }
        }
    }
}
