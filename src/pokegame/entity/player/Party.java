/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.entity.player;

import java.awt.Color;
import pokegame.entity.Person;
import pokegame.handler.Handler;
import pokegame.pokemon.Pokemon;
import pokegame.pokemon.move.Moveset;

/**
 *
 * @author Rahul
 */
public class Party {
    
    private Person person;
    private Handler h;
    
    private Pokemon[] pokemon = new Pokemon[6];
    private int partySize;
    
    public Party(Handler h, Person person){
        this.h = h;
        this.person = person;
        partySize = 0;
    }
    
    public String getPokemonName(int partyIndex){
        return pokemon[partyIndex].getName();
    }
    
    public Pokemon getPokemon(int partyIndex){
        return pokemon[partyIndex];
    }
    
    public void storePokemon(int partyIndex){
        partySize--;
        pokemon[partyIndex] = null;
        setActive();
    }
    
    public void addPokemon(int partyIndex, Pokemon p){
        partySize++;
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
    
    public int removePokemon(int pokemonId, int pokemonLevel){ // return a code instead for error handling
        for (int x = 0; x < 6; x++){
            if (pokemon[x] != null){
                if (pokemon[x].getID() == pokemonId && pokemon[x].getLevel() >= pokemonLevel){
                    if (partySize == 1){
                        h.getGame().addText("Can't have 0 pokemon in party!\n", Color.red);
                        return 0;
                    }
                    partySize--;
                    pokemon[x] = null;
                    setActive();
                    return 1;
                }
            }
        }
        return 0;
    }
    
    public void setActive(){
        Player player = (Player) person;
        if (pokemon[player.getActiveNumber()] != null)return;
        for (int x = 0; x < 6; x++){
            if (pokemon[x] != null){
                player.setActiveNumber(x);
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
        Player player = (Player) person;
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
