/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.npc;

import pokegame.entity.player.Party;
import pokegame.handler.Handler;
import pokegame.item.Item;
import pokegame.pokemon.Pokemon;

/**
 *
 * @author Rahul
 */
public class PokemonTrainer extends NPC{
    
    private boolean aggresive;
    private Item item;
    private int itemAmount;
    private Pokemon pokemon;
    private int pokemonAmount;
    private int zeniAmount;
    
    private Party party;
    
    public PokemonTrainer(Handler handler, int id, String name, int spriteId,
            int portraitID, int direction, float x, float y, int distanceToCenter,
            boolean canTurn, boolean canMove, boolean isSolid, boolean aggresive,
            Item item, int itemAmount, Pokemon pokemon, int pokemonAmount,
            int zeniAmount, Party party) {
        super(handler, 3, id, name, spriteId, portraitID, direction, x, y, distanceToCenter, canTurn, canMove, isSolid);
        this.aggresive = aggresive;
        this.item = item;
        this.itemAmount = itemAmount;
        this.pokemon = pokemon;
        this.pokemonAmount = pokemonAmount;
        this.zeniAmount = zeniAmount;
        this.party = party;
    }
    
}
