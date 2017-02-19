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
        pokemon[0] = new Pokemon(0, false, 1, 1, 1,
                                    11,1,1,1, new Moveset(0));
        pokemon[1] = new Pokemon(1, false, 2, 8, 7,
                                    2,2,2,2, new Moveset(0, 1));
        pokemon[2] = new Pokemon(2, false, 3, 6, 1,
                                    2,2,2,2, new Moveset(2, 3));
        pokemon[3] = new Pokemon(3, false, 4, 1, 5,
                                    2,2,2,2, new Moveset(4, 5));
        pokemon[4] = new Pokemon(4, false, 5, 2, 2,
                                    2,2,2,2, new Moveset(6, 7, 8));
        pokemon[5] = new Pokemon(5, false, 6, 4, 1,
                                    2,2,2,2, new Moveset(9, 10, 11, 12));
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
