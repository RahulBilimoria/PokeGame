/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.entity;

import pokegame.entity.ai.AI;
import pokegame.entity.ai.Easy;
import pokegame.entity.ai.Hard;
import pokegame.entity.ai.Medium;
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
    
}
