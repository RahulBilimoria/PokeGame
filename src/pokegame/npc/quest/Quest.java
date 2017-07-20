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
        
    public Quest(int id, String name, int itemId, int itemAmountRequired, 
            int pokemonId, int pokemonLevel, int pokemonAmountRequired, 
            int wildPokemonId, int wildPokemonLevel, int wildPokemonAmountRequired, 
            int mapLocation){
        this.id = id;
        this.name = name;
        this.itemId = itemId;
        this.itemAmountRequired = itemAmountRequired;
        this.pokemonId = pokemonId;
        this.pokemonLevel = pokemonLevel;
        this.pokemonAmountRequired = pokemonAmountRequired;
        this.wildPokemonId = wildPokemonId;
        this.wildPokemonLevel = wildPokemonLevel;
        this.wildPokemonAmountRequired = wildPokemonAmountRequired;
        this.mapLocation = mapLocation;
        itemReward = null;
        pokemonReward = null;
        pokemonExpReward = -1;
        currentItemAmount = 0;
        currentPokemonAmount = 0;
        currentWildPokemonAmount = 0;
    }
    
    private Quest(int id, String name, int itemId, int itemAmountRequired, int pokemonId,
            int pokemonLevel, int pokemonAmountRequired, int wildPokemonId, int wildPokemonLevel,
            int wildPokemonAmountRequired, int mapLocation, Item item, int itemAmount, Pokemon pokemon,
            int pokemonAmount, int exp){
        this.id = id;
        this.name = name;
        this.itemId = itemId;
        this.itemAmountRequired = itemAmountRequired;
        this.pokemonId = pokemonId;
        this.pokemonLevel = pokemonLevel;
        this.pokemonAmountRequired = pokemonAmountRequired;
        this.wildPokemonId = wildPokemonId;
        this.wildPokemonLevel = wildPokemonLevel;
        this.wildPokemonAmountRequired = wildPokemonAmountRequired;
        this.mapLocation = mapLocation;
        this.itemReward = item;
        this.itemRewardAmount = itemAmount;
        this.pokemonReward = pokemon;
        this.pokemonRewardAmount = pokemonAmount;
        this.pokemonExpReward = exp;
        currentItemAmount = 0;
        currentPokemonAmount = 0;
        currentWildPokemonAmount = 0;
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
    
    public int getPokemonId(){
        return pokemonId;
    }
    
    public int getPokemonLevel(){
        return pokemonLevel;
    }
    
    public int getWildPokemonId(){
        return wildPokemonId;
    }
    
    public int getWildPokemonLvl(){
        return wildPokemonLevel;
    }
    
    public Item getItemReward(){
        return itemReward;
    }
    
    public int getItemRewardAmount(){
        return itemRewardAmount;
    }
    
    public Pokemon getPokemonReward(){
        return pokemonReward;
    }
    
    public int getPokemonRewardAmount(){
        return pokemonRewardAmount;
    }
    
    public float getPokemonExpReward(){
        return pokemonExpReward;
    }
    
    public int getId(){
        return id;
    }
    
    public int getItemId(){
        return itemId;
    }
    
    public int getItemAmount(){
        return itemAmountRequired;
    }
    
    public int getRemainingItems(){
        return itemAmountRequired - currentItemAmount;
    }
    
    public int getRemainingPokemon(){
        return pokemonAmountRequired - currentPokemonAmount;
    }
    
    public String getName(){
        return name;
    }
    
    public void addWildPokemonFainted(){
        currentWildPokemonAmount++;
    }
    
    public void addCurrentItems(int i){
        currentItemAmount += i;
    }
    
    public void addCurrentPokemon(int i){
        currentPokemonAmount += i;
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
    
    @Override
    public String toString(){
        String s = "";
        if (itemId != -1){
            if (itemAmountRequired > currentItemAmount){
                s = s + "Handed in " + currentItemAmount +"/"+ itemAmountRequired +" "+ Item.items[itemId].getName() + "s.\n";
            }
        }
        if (pokemonId != -1){
            if (pokemonAmountRequired > currentPokemonAmount){
                s = s + "Handed in " + currentPokemonAmount +"/"+ pokemonAmountRequired +" "+ Pokemon.getPokemonName(pokemonId) + "s.\n";
            }
        }
        if (wildPokemonId != -1){
            if (wildPokemonAmountRequired > currentWildPokemonAmount){
                s = s + currentWildPokemonAmount + "/" + wildPokemonAmountRequired + " " + Pokemon.getPokemonName(wildPokemonId)+ "s fainted.\n";
            }
        }
        return s;
    }
    
    public Quest cloneQuest(){
        return new Quest(id, name, itemId, itemAmountRequired, pokemonId,
        pokemonLevel, pokemonAmountRequired, wildPokemonId, wildPokemonLevel,
        wildPokemonAmountRequired, mapLocation, itemReward, itemRewardAmount,
        pokemonReward, pokemonRewardAmount, pokemonExpReward);
    }
}
