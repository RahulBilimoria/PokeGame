/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.entity;

import pokegame.entity.player.Bag;
import pokegame.entity.player.Party;
import pokegame.handler.Handler;
import pokegame.npc.NPC;
import pokegame.pokemon.Pokemon;

/**
 *
 * @author Rahul
 */
public abstract class Trainer extends NPC{
    
    protected int difficulty;

    protected int activePokemon;
    protected Party party;
    protected Bag bag;
        
    public Trainer(Handler handler, int type, int id, String name, int spriteId, int portraitID, int direction, float x, float y, int distanceFromCenter, boolean canTurn, boolean canMove, boolean isSolid) {
        super(handler, type, id, name, spriteId, portraitID, direction, x, y, distanceFromCenter, canTurn, canMove, isSolid);
    }
    
    public Pokemon getPokemon(int x){
        return party.getPokemon(x);
    }
    
    public int getDifficulty(){
        return difficulty;
    }
    
    public Pokemon getActivePokemon(){
        return party.getPokemon(activePokemon);
    }
    
    public int getActiveNumber(){
        return activePokemon;
    }
    
    public Pokemon[] getCopyOfPokemon(){
        Pokemon p[] = new Pokemon[6];
        for (int x = 0; x < 6; x++){
            if (party.getPokemon(x) != null){
                p[x] = party.getPokemon(x).copy();
            } else {
                p[x] = null;
            }
        }
        return p;
    }
    
}
