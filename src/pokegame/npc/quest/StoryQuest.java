/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.npc.quest;

/**
 *
 * @author Rahul
 */
public class StoryQuest extends Quest{
        
    private int npcId; // NPC to Interact with
    private int keyMapLocation; // Map Location to visit to continue story
    private int keyItemId; // Item to use possibly at map location (mostly key items would be needed i guess)
    private int levelsToGain; // Amount of levels to gain
    private int levelsGained; // Levels already gained
    
    private int unlockScript; //Script tile that can now be passed once quest is compeleted

    public StoryQuest(int id, String name, int itemId, int itemAmountRequired, 
            int pokemonId, int pokemonLevel, int pokemonAmountRequired, 
            int wildPokemonId, int wildPokemonLevel, int wildPokemonAmountRequired, 
            int mapLocation, int npcId, int keyMapLocation, int keyItemId, 
            int levelsToGain) {
        super(id, name, itemId, itemAmountRequired, pokemonId, pokemonLevel, 
                pokemonAmountRequired, wildPokemonId, wildPokemonLevel, 
                wildPokemonAmountRequired, mapLocation);
        this.npcId = npcId;
        this.keyMapLocation = keyMapLocation;
        this.keyItemId = keyItemId;
        this.levelsToGain = levelsToGain;
        this.levelsGained = 0;
        
    }
    
    public int getUnlockScript(){
        return unlockScript;
    }
    
    public int getNpcId(){
        return npcId;
    }
    
    public int getMapLocation(){
        return keyMapLocation;
    }
    
    public int getKeyItemId(){
        return keyItemId;
    }
    
    public int getLevelsToGain(){
        return levelsToGain;
    }
    
    public int getLevelsGained(){
        return levelsGained;
    }
}
