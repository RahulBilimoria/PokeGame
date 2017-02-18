/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.entity.player;

import pokegame.pokemon.Pokemon;
import pokegame.utils.Utils;

/**
 *
 * @author Rahul
 */
public class Storage {
    
    private static int BOXES_COUNT = 6;
    private static int BOXES_SIZE = 30;
    
    Player player;
    private int currentBox;
    private int selectedPokemon;
    private int[][] boxes = new int[BOXES_COUNT][BOXES_SIZE];
    private String[][] pokemonBoxes = new String[BOXES_COUNT][BOXES_SIZE];
    
    public Storage(Player player){
        this.player = player;
        init();
    }
    
    public void init(){
        for (int x = 0; x < BOXES_COUNT; x++){
            String p[] = Utils.loadFileAsString("dat/player/storage/box" + (x+1) + ".box").split("\n");
            //String p[] = Utils.loadFileAsString("dat/player/storage/box" + (x+1) + ".box").split("\n")[0].split(" ");
            //pokemonBoxes[x] = 
            for (int y = 0; y < BOXES_SIZE; y++){
                boxes[x][y] = Utils.parseInt(p[y]);
            }
        }
    }
    
    public int getId(int x, int y){
        return boxes[x][y];
    }
    
    public void getPokemon(int x, int y){//need to return pokemon
        
    }
    
    public void storePokemon(Pokemon p, int x, int y){//make pokemon.toStorage work
        
    }
    
    public int getCurrentBox(){
        return currentBox;
    }
    
    public int getSelectedPokemon(){
        return selectedPokemon;
    }
}