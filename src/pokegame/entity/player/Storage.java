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

    private static int BOXES_COUNT = 7;
    private static int BOXES_SIZE = 25;
    private static int DATA_PER_POKEMON = 25;

    Player player;
    private int currentBox;
    private int selectedPokemon;
    private PokemonBox[] pokemonBoxes = new PokemonBox[BOXES_COUNT];

    class PokemonBox {

        int id;
        int box[][] = new int[BOXES_SIZE][25];

        public PokemonBox(int id, String[] data) {
            this.id = id;
            parseData(data);
        }

        public int getPokemonId(int y) {
            return box[y][0];
        }

        private void parseData(String[] data) {
            for (int x = 0; x < BOXES_SIZE; x++) {
                for (int y = 0; y < DATA_PER_POKEMON; y++) {
                    box[x][y] = Utils.parseInt(data[y]);
                }
            }
        }
    }

    public Storage(Player player) {
        this.player = player;
        init();
    }

    public void init() {
        for (int x = 0; x < BOXES_COUNT; x++) {
            String p[] = Utils.loadFileAsString("dat/player/storage/box" + (x + 1) + ".box").split("\n");
            //String p[] = Utils.loadFileAsString("dat/player/storage/box" + (x+1) + ".box").split("\n")[0].split(" ");
            //pokemonBoxes[x] = 
            for (int y = 0; y < BOXES_SIZE; y++) {
                pokemonBoxes[x] = new PokemonBox(x, p);
            }
        }
    }

    public int getId(int x, int y) {
        return pokemonBoxes[x].getPokemonId(y);
    }

    public void getPokemon(int x, int y) {//need to return pokemon

    }

    public void storePokemon(Pokemon p, int x, int y) {//make pokemon.toStorage work

    }

    public int getCurrentBox() {
        return currentBox;
    }

    public int getSelectedPokemon() {
        return selectedPokemon;
    }
}
