/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.entity.player;

import pokegame.handler.Handler;
import pokegame.pokemon.Pokemon;
import pokegame.pokemon.move.Moveset;

/**
 *
 * @author Rahul
 */
public class Party {
    
    private Player player;
    private Handler h;
    
    private Pokemon[] pokemon = new Pokemon[6];
    private int partySize;
    
    public Party(Handler h, Player player){
        this.h = h;
        this.player = player;
        pokemon[0] = new Pokemon(h, 0, false, 1, 1, 1,
                                    11,1,1,1, new Moveset(0));
        pokemon[1] = new Pokemon(h, 1, false, 2, 8, 7,
                                    2,2,2,2, new Moveset(0, 1));
        pokemon[2] = new Pokemon(h, 2, false, 3, 6, 1,
                                    2,2,2,2, new Moveset(2, 3));
        pokemon[3] = new Pokemon(h, 3, false, 4, 1, 5,
                                    2,2,2,2, new Moveset(4, 5));
        pokemon[4] = new Pokemon(h, 4, false, 5, 2, 2,
                                    2,2,2,2, new Moveset(6, 7, 8));
        pokemon[5] = new Pokemon(h, 5, false, 6, 4, 1,
                                    2,2,2,2, new Moveset(9, 10, 11, 12));
        partySize = 6;
    }
    
    public String getPokemonName(int partyIndex){
        return pokemon[partyIndex].getName();
    }
    
    public Pokemon getPokemon(int partyIndex){
        return pokemon[partyIndex];
    }
    
    public void storePokemon(int partyIndex){
        partySize--;
        player.addToQuest(pokemon[partyIndex], -1);
        pokemon[partyIndex] = null;
    }
    
    public void addPokemon(int partyIndex, Pokemon p){
        partySize++;
        player.addToQuest(p, 1);
        pokemon[partyIndex] = p;
    }
    
    public void addPokemon(Pokemon p){
        for (int x = 0; x < 6; x++){
            if (pokemon[x] == null){
                partySize++;
                pokemon[x] = p;
                return;
            }
        }
    }
    
    public void removePokemon(int pokemonId, int pokemonLevel){
        for (int x = 0; x < 6; x++){
            if (pokemon[x] != null){
                if (pokemon[x].getID() == pokemonId && pokemon[x].getLevel() >= pokemonLevel){
                    pokemon[x] = null;
                    return;
                }
            }
        }
    }
    
    public String toString(int partyIndex){
        return pokemon[partyIndex].toString();
    }
    
    public int getPartySize(){
        return partySize;
    }
    
    public void swapPokemon(int x, int y){
        Pokemon temp = pokemon[x];
        pokemon[x] = pokemon[y];
        pokemon[y] = temp;
        if (player.getActiveNumber() == x){
            player.setActiveNumber(y);
        } else if (player.getActiveNumber() == y){
            player.setActiveNumber(x);
        }
    }
    
    public void heal(){
        for (int x = 0; x < 6; x++){
            if (pokemon[x] != null){
                pokemon[x].heal();
            }
        }
    }
    
    public int getNumberOfPokemon(int id, int level){
        if (id == -1) return 0;
        int amount = 0;
        for (int x = 0; x < 6; x++){
            if (pokemon[x] != null){
                if (pokemon[x].getID() == id && pokemon[x].getLevel() >= level){
                    amount++;
                }
            }
        }
        return amount;
    }
}
