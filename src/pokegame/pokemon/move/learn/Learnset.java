/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.pokemon.move.learn;

import pokegame.pokemon.Pokemon;
import pokegame.pokemon.move.Move;
import pokegame.utils.Utils;

/**
 *
 * @author Rahul
 */
public class Learnset {
    
    public static final Learnset LEARN_SET[] = new Learnset[151];
    
    private class LearnableMove{
        private final int LEVEL;
        private final String NAME;
        
        public LearnableMove(int level, String name){
            this.LEVEL = level;
            this.NAME = name;
        }
        
        public int getLevel(){
            return LEVEL;
        }
        
        public String getName(){
            return NAME;
        }
}
    
    private LearnableMove learnableMoves[];
    
    public Learnset(String[] learnMoves){
        learnableMoves = new LearnableMove[learnMoves.length/2];
        for (int x = 0; x < learnableMoves.length; x++){
            learnableMoves[x] = new LearnableMove(Utils.parseInt(learnMoves[x*2]), 
                                        learnMoves[(x*2)+1]);
        }
    }
    
    public static void init(){ 
        for (int x = 1; x < 10/*Pokemon.POKEMON_COUNT*/; x++){
            String file = Utils.loadFileAsString("dat/pokemon/moves/learnable/" + x + ".dat");
            String learnMoves[] = file.split("\\s+");
            LEARN_SET[x-1] = new Learnset(learnMoves);
        }
        
    }
    
    public Move[] getLearnableMove(int pokemonLevel){
        int moves = 0;
        int first = -1;
        for (int x = 0; x < learnableMoves.length; x++){
            if (learnableMoves[x].getLevel() == pokemonLevel){
                if (first == -1){
                    first = x;
                }
                moves++;
            }
        }
        Move[] move = new Move[moves];
        for (int x = 0; x < moves; x++){
            move[x] = Move.getMoveByName(learnableMoves[first].getName());
            first++;
        }
        return move;
    }
    
    public LearnableMove[] getLearnableMoves(){
        return learnableMoves;
    }
}
