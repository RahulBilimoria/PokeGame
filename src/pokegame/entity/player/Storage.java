/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.entity.player;

import java.util.Arrays;
import pokegame.pokemon.Pokemon;
import pokegame.utils.Utils;

/**
 *
 * @author Rahul
 */
public class Storage {

    public static int BOXES_COUNT = 7;
    public static int BOXES_SIZE = 25;
    private static int DATA_PER_POKEMON = 25;

    Player player;
    private int currentBox;
    private int selectedPokemon;
    private PokemonBox[] pokemonBoxes = new PokemonBox[BOXES_COUNT];
    
    //GENDER: 0 = male, 1 = female
    //SHINY: 0 = true, 1 = false

    class PokemonBox {

        int id;
        String name;
        int box[][] = new int[BOXES_SIZE][25];

        public PokemonBox(int id, String[] data) {
            this.id = id;
            name = "Box " + (id+1);
            parseData(data);
        }

        public int getPokemonId(int y) {
            return box[y][0];
        }

        String pkmnData[];
        private void parseData(String[] data) {
            for (int x = 0; x < BOXES_SIZE; x++) {
                pkmnData = data[x].split("\\s+");
                for (int y = 0; y < DATA_PER_POKEMON; y++) {
                    box[x][y] = Utils.parseInt(pkmnData[y]);
                }
            }
        }
        
        public String getName(){
            return name;
        }
        
        public int[] getPokemon(int index){
            return box[index];
        }
        
        public void removePokemon(int index){
            box[index] = new int[DATA_PER_POKEMON];
            Arrays.fill(box[index], -1);
        }
        
        public void storePokemon(int index, int[] data){
            box[index] = data;
        }
        
        public void swapPokemon(int poke1, int poke2){
            for (int x = 0; x < 25; x++){
                int temp = box[poke1][x];
                box[poke1][x] = box[poke2][x];
                box[poke2][x] = temp;
            }
        }
        
        @Override
        public String toString(){
            String s = "";
            for (int x = 0; x < BOXES_SIZE; x++){
                for (int y = 0; y < DATA_PER_POKEMON; y++){
                    if (x == BOXES_SIZE-1 && y == DATA_PER_POKEMON-1){
                        s = s + box[x][y];
                    }
                    else if (y == DATA_PER_POKEMON-1){
                        s = s + box[x][y] + "\n";
                    } else
                        s = s + box[x][y] + " ";
                }
            }
            return s;
        }
    }

    public Storage(Player player) {
        this.player = player;
        currentBox = 0;
        selectedPokemon = 0;
        init();
    }

    public void init() { //created when player is created
        for (int x = 0; x < BOXES_COUNT; x++) {
            String p[] = Utils.loadFileAsString("dat/player/storage/box" + (x + 1) + ".box").split("\n");
            for (int y = 0; y < BOXES_SIZE; y++) {
                pokemonBoxes[x] = new PokemonBox(x, p);
            }
        }
    }
    
    public void saveBoxes(){
        for (int x = 0; x < BOXES_COUNT; x++){
            String box = pokemonBoxes[x].toString();
            Utils.saveStringAsFile("dat/player/storage/box" + (x+1) + ".box", box);
        }
    }
    
    public void storageToParty(int storageID, int partyID){
        Party p = player.getParty();
        if (p.getPokemon(partyID) != null){
            int data[] = p.getPokemon(partyID).toStorage();
            p.addPokemon(partyID, getPokemon(currentBox, storageID));
            removePokemon(currentBox, storageID);
            storePokemon(currentBox, storageID, data); // can shorten this easily
        } else {
            p.addPartySize(1);
            p.addPokemon(partyID, getPokemon(currentBox, storageID));
            removePokemon(currentBox, storageID);
        }
    }
    
    public boolean partyToStorage(int storageID, int partyID){
        Party p = player.getParty();
        if (getId(currentBox, storageID) != -1) {
            int[] data = p.getPokemon(partyID).toStorage(); //gets data from pokemon to store into box
            p.addPokemon(partyID, getPokemon(currentBox, storageID));
            removePokemon(currentBox, storageID);
            storePokemon(currentBox, storageID, data);
            return true;
        } else {
            storePokemon(currentBox, storageID, p.getPokemon(partyID).toStorage());
            p.addPartySize(-1);
            //stores the pokemon in the box
            p.storePokemon(partyID); //removes pokemon from party
            return false;
        }
    }

    public int getId(int boxNumber, int pokemonIndex) {
        return pokemonBoxes[currentBox].getPokemonId(pokemonIndex);
    }
    
    public String getBoxName(int boxNumber){
        return pokemonBoxes[boxNumber].getName();
    }

    public Pokemon getPokemon(int boxNumber, int pokemonIndex) {//need to return pokemon
        int data[] = pokemonBoxes[boxNumber].getPokemon(pokemonIndex);
        return new Pokemon(data);
    }
    
    public void removePokemon(int boxNumber, int pokemonIndex){
        pokemonBoxes[boxNumber].removePokemon(pokemonIndex);
    }

    public void storePokemon(int boxNumber, int pokemonIndex, int[] data) {//make pokemon.toStorage work
        pokemonBoxes[boxNumber].storePokemon(pokemonIndex, data);
    }
    
    public void swapPokemon(int box, int poke1, int poke2){
        pokemonBoxes[box].swapPokemon(poke1, poke2);
    }

    public int getCurrentBox() {
        return currentBox;
    }
    
    public void setCurrentBox(int currentBox){
        this.currentBox = currentBox;
    }

    public int getSelectedPokemon() {
        return selectedPokemon;
    }
    
    public void setSelectedPokemon(int selectedPokemon){
        this.selectedPokemon = selectedPokemon;
    }
}