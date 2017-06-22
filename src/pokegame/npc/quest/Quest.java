/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.npc.quest;

import pokegame.item.Item;
import pokegame.pokemon.Pokemon;

/**
 *
 * @author Rahul
 */
public class Quest {
    
    private int id;
    private String name;
    
    private int itemId;
    private int itemAmountRequired;
    private int currentItemAmount;
    private int pokemonId;
    private int pokemonLevel;
    private int pokemonAmountRequired;
    private int currentPokemonAmount;
    private int wildPokemonId;
    private int wildPokemonLevel;
    private int wildPokemonAmountRequired;
    private int currentWildPokemonAmount;
    private int mapLocation; // -1 means any map
    
    private Item itemReward;
    private int itemRewardAmount;
    private Pokemon pokemonReward;
    private int pokemonRewardAmount;
    private int pokemonExpReward;
        
    public Quest(int id, String name, int itemId, int itemAmountRequired){
        this.id = id;
        this.name = name;
        this.itemId = itemId;
        this.itemAmountRequired = itemAmountRequired;
        pokemonId = -1;
        wildPokemonId = -1;
        mapLocation = -1;
        itemReward = null;
        pokemonReward = null;
        pokemonExpReward = -1;
    }
    
    public Quest(int id, String name, int pokemonId, int pokemonLevel, int pokemonAmountRequired){
        this.id = id;
        this.name = name;
        this.pokemonId = pokemonId;
        this.pokemonLevel = pokemonLevel;
        this.pokemonAmountRequired = pokemonAmountRequired;
        itemId = -1;
        wildPokemonId = -1;
        mapLocation = -1;
        itemReward = null;
        pokemonReward = null;
        pokemonExpReward = -1;
    }
    
    public Quest(int id, String name, int wildPokemonId, int wildPokemonLevel, int wildPokemonAmountRequired, int mapLocation){
        this.id = id;
        this.name = name;
        this.wildPokemonId = wildPokemonId;
        this.wildPokemonLevel = wildPokemonLevel;
        this.wildPokemonAmountRequired = wildPokemonAmountRequired;
        this.mapLocation = mapLocation;
        itemId = -1;
        pokemonId = -1;
        itemReward = null;
        pokemonReward = null;
        pokemonExpReward = -1;
    }
    
    public void addRequirement(int itemId, int itemAmountRequired){
        this.itemId = itemId;
        this.itemAmountRequired = itemAmountRequired;
    }
    
    public void addRequirement(int pokemonId, int pokemonLevel, int pokemonAmountRequired){
        this.pokemonId = pokemonId;
        this.pokemonLevel = pokemonLevel;
        this.pokemonAmountRequired = pokemonAmountRequired;
    }
    
    public void addRequirement(int wildPokemonId, int wildPokemonLevel, int wildPokemonAmountRequired, int mapLocation){
        this.wildPokemonId = wildPokemonId;
        this.wildPokemonLevel = wildPokemonLevel;
        this.wildPokemonAmountRequired = wildPokemonAmountRequired;
        this.mapLocation = mapLocation;
    }
    
    public void addReward(Item item, int amount){
        this.itemReward = item;
        this.itemRewardAmount = amount;
    }
    
    public void addReward(Pokemon pokemon, int amount){
        this.pokemonReward = pokemon;
        this.pokemonRewardAmount = amount;
    }
    
    public void addReward(int exp){
        this.pokemonExpReward = exp;
    }
    
    public Item giveItemReward(){
        return itemReward;
    }
    
    public int getItemRewardAmount(){
        return itemRewardAmount;
    }
    
    public Pokemon givePokemonReward(){
        return pokemonReward;
    }
    
    public int givePokemonRewardAmount(){
        return pokemonRewardAmount;
    }
    
    public float givePokemonExpReward(){
        return pokemonExpReward;
    }
    
    public int getId(){
        return id;
    }
    
    public boolean checkQuest(){
        if (itemId != -1){
            if (itemAmountRequired > currentItemAmount){
                return false;
            }
        }
        if (pokemonId != -1){
            if (pokemonAmountRequired > currentPokemonAmount){
                return false;
            }
        }
        if (wildPokemonId != -1){
            if (wildPokemonAmountRequired > currentWildPokemonAmount){
                return false;
            }
        }
        return true;
    }   
}
