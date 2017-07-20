/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.entity.ai;

import pokegame.pokemon.Pokemon;
import pokegame.pokemon.move.Move;
import pokegame.type.Type;

/**
 *
 * @author Rahul
 */
public class Medium extends AI{
    
    private int lastMoveUsed;
    
    public Medium() {
        lastMoveUsed = -1;
    }

    public Pokemon chooseNextPokemon(Pokemon[] yourParty, Pokemon activePokemon) {
        int moves;
        float score;
        float bestScore = 0;
        int bestPokemonIndex = 0;
        for (int x = 0; x < 6; x++){
            moves = 0;
            score = 0;
            Pokemon p = yourParty[x];
            if (p != null){
                Type type1 = activePokemon.getType1();
                Type type2 = activePokemon.getType2();
                Move move1 = p.getMoveset().getMove1();
                Move move2 = p.getMoveset().getMove2();
                Move move3 = p.getMoveset().getMove3();
                Move move4 = p.getMoveset().getMove4();
                if (move1 != null){
                    moves++;
                    score = score + (Type.getEffectiveness(move1.getType(), type1, type2) * 5 * (move1.getPP()/move1.getMaxPP()));
                } if (move2 != null){
                    moves++;
                    score = score + (Type.getEffectiveness(move2.getType(), type1, type2) * 5 * (move2.getPP()/move2.getMaxPP()));
                } if (move3 != null){
                    moves++;
                    score = score + (Type.getEffectiveness(move3.getType(), type1, type2) * 5 * (move3.getPP()/move3.getMaxPP()));
                } if (move4 != null){
                    moves++;
                    score = score + (Type.getEffectiveness(move4.getType(), type1, type2) * 5 * (move4.getPP()/move4.getMaxPP()));
                }
                score = score / moves;
                //Trainers pokemon moves vs ur pokemon type
            }
            if (score > bestScore){
                bestScore = score;
                bestPokemonIndex = x;
            }
        }
        if (bestScore == 0){
            return null;
        }
        return yourParty[bestPokemonIndex];
    }

    /**
     * Uses all types of moves based on their effectiveness against the current pokemon.
     * Uses attack moves if the enemy is below 25% hp
     */
    public Move chooseNextMove(Pokemon yours, Pokemon theirs) {
        float score;
        float bestScore = 0;
        int moveID = 0;
        Type type1 = theirs.getType1();
        Type type2 = theirs.getType2();
        for (int x = 0; x < 4; x++){
            score = 0;
            Move m = yours.getMoveset().getMove(x);
            if (m != null){
                score += Type.getEffectiveness(m.getType(), type1, type2) * 5;
                if (m.getCategory() == 2){
                    score = score * 0.75f;
                    if (theirs.getHp() / (float)theirs.getMaxHp() < 0.25){
                        score = score * 0.5f;
                    }
                }
                if (lastMoveUsed == x){
                    score = score * 0.75f;
                }
            }
            if (score > bestScore){
                bestScore = score;
                moveID = x;
            }
        }
        lastMoveUsed = moveID;
        return yours.getMoveset().getMove(moveID);
    }
    
}
