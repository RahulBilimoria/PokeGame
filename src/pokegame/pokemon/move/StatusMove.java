/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.pokemon.move;

import pokegame.pokemon.Pokemon;
import pokegame.pokemon.status.Status;

/**
 *
 * @author Rahul
 */
public class StatusMove extends Move{
    
    private int type;
    private float statusChance;
    private Status statusInflict;
    private int statChange;
    private int statChangeAmount;
    
    public StatusMove(int id) {
        super(id);
    }
    
    public StatusMove(BaseMove move) {
        super(move);
    }
    
    public void applyStatus(Pokemon enemy){
        
    }
    
}
