/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.entity.ai;

import pokegame.pokemon.Pokemon;
import pokegame.pokemon.move.Move;

/**
 *
 * @author Rahul
 */
public class Easy extends AI{
        
    public Easy() {
        
    }

    public Pokemon chooseNextPokemon(Pokemon[] yourParty, Pokemon activePokemon) {
        for (int x = 0; x < 6; x++){
            Pokemon p = yourParty[x];
            if (p != null){
                if (p.getHp() >= 0){
                    return p;
                }
            }
        }
        return null;
    }
    
    /**
     * Easy AI only uses attack and sp attack moves, no special moves
     */
    public Move chooseNextMove(Pokemon yours, Pokemon theirs){
        for (int x = 0; x < 4; x++){
            Move m = yours.getMoveset().getMove(x);
            if (m != null){
                if (m.getPP() == 0) continue;
                if (m.getCategory() == 0 || m.getCategory() == 1){
                    return m;
                }
            }
        }
        return null;
    }
}
