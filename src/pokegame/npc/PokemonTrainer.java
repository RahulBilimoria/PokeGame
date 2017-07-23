/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.npc;

import java.awt.Color;
import pokegame.battle.TrainerBattle;
import pokegame.entity.Trainer;
import pokegame.entity.player.Bag;
import pokegame.entity.player.Party;
import pokegame.entity.player.Player;
import pokegame.handler.Handler;
import pokegame.item.Item;
import pokegame.pokemon.Pokemon;

/**
 *
 * @author Rahul
 */
public class PokemonTrainer extends Trainer{
    
    //Rewards from beating the trainer
    private Item item;
    private int itemAmount;
    private int zeniAmount;
    
    //Behaviour of the trainer
    private boolean aggresive;
        
    public PokemonTrainer(Handler handler, int id, String name, int spriteId,
            int portraitID, int direction, float x, float y, int distanceToCenter,
            boolean canTurn, boolean canMove, boolean isSolid, boolean aggresive,
            Item item, int itemAmount, int zeniAmount, Pokemon[] partyPKMN, 
            Item[] bagItems, int[] itemAmounts) {
        super(handler, 3, id, name, spriteId, portraitID, direction, x, y, distanceToCenter, canTurn, canMove, isSolid);
        this.aggresive = aggresive;
        this.item = item;
        this.itemAmount = itemAmount;
        this.zeniAmount = zeniAmount;
        party = new Party(handler, this);
        bag = new Bag(this);
        for (Pokemon partyPKMN1 : partyPKMN) {
            party.addPokemon(partyPKMN1);
        }
        for (int i = 0; i < bagItems.length; i++){
            bag.addItem(bagItems[i], itemAmounts[i]);
        }
        activePokemon = 0;
    }
    
    @Override
    public void onInteract(Player player){
        if (player.hasDefeated(id)){
            player.setEnabled(true);
            handler.getGame().addText("You have already defeated Pokemon Trainer " + name + "./n", Color.blue);
        } else {
            player.addBattle(new TrainerBattle(handler, player, this));
        }
    }
    
    public Item getItem(){
        return item;
    }
    
    public int getItemAmount(){
        return itemAmount;
    }
    
    public int getMoney(){
        return zeniAmount;
    }
}
