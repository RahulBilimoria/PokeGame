/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.npc;

import java.awt.Color;
import pokegame.battle.GymBattle;
import pokegame.entity.Trainer;
import pokegame.entity.player.Player;
import pokegame.handler.Handler;
import pokegame.item.Item;
import pokegame.pokemon.Pokemon;

/**
 *
 * @author Rahul
 */
public class GymLeader extends Trainer{
    
    private int badgeID;
    private int region;
    
    public GymLeader(Handler handler, int id, String name, int spriteId,
            int portraitID, int direction, float x, float y, int distanceToCenter,
            boolean canTurn, boolean canMove, boolean isSolid, int badgeID, Pokemon[] partyPKMN,
            Item[] bagItems, int[] itemAmounts) {
        super(handler, 2, id, name, spriteId, portraitID, direction, x, y, distanceToCenter, canTurn, canMove, isSolid);
        this.badgeID = badgeID;
        for (Pokemon partyPKMN1 : partyPKMN) {
            party.addPokemon(partyPKMN1);
        }
        for (int i = 0; i < bagItems.length; i++){
            bag.addItem(bagItems[i], itemAmounts[i]);
        }
    }
    
    @Override
    public void onInteract(Player player){
        if (player.getBadge(region, badgeID)){
            player.setEnabled(true);
            handler.getGame().addText("You have already defeated Gym Leader " + name + "./n", Color.blue);
        } else {
            player.addBattle(new GymBattle(handler, player, this));
        }
    }
    
    public int getRegion(){
        return region;
    }
    
    public int getBadge(){
        return badgeID;
    }
}
