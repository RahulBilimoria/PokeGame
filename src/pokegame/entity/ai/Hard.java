/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.entity.ai;

import java.util.Queue;
import pokegame.pokemon.Pokemon;
import pokegame.pokemon.move.Move;
import pokegame.pokemon.status.Status;
import pokegame.type.Type;

/**
 *
 * @author Rahul
 */
public class Hard extends AI{
    
    private float lastHp;
    private Queue<Move> myUsedMoves;
    
    public Hard() {
        
    }
    
    public void enemySwapped(){
        lastHp = -1;
    }

    public Pokemon chooseNextPokemon(Pokemon[] yourParty, Pokemon activePokemon) {
        float score = 0;
        float bestScore = 0;
        int bestPokemonIndex = 0;
        for (int x = 0; x < 6; x++){
            Pokemon p = yourParty[x];
            int moves = 0;
            score = 0;
            if (p != null){
                if (p.getHp() > 0){
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
                float hp = p.getHp() / (float)p.getMaxHp();
                if (hp <= 0.25){
                    score += 1;
                } else if (hp <= 0.5){
                    score += 2;
                } else if (hp <= 0.75){
                    score += 5;
                } else {
                    score += 10;
                }
                score = score + ((p.getLevel() / activePokemon.getLevel()) * 10);
                Status cond = p.getStatus();
                if (cond == Status.NORMAL){
                    score += 10;
                } else if (cond == Status.POISONED || cond == Status.BURN || cond == Status.CONFUSION){
                    score -= 5;
                } else {
                    score -= 10;
                }
                //Trainers pokemon moves vs ur pokemon type
                }
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
            }
            if (score > bestScore){
                bestScore = score;
                moveID = x;
            }
        }
        return yours.getMoveset().getMove(moveID);
    }
    
}
