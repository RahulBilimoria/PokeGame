/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.battle;

import pokegame.entity.player.Player;
import pokegame.handler.Handler;
import pokegame.npc.GymLeader;
import pokegame.pokemon.Pokemon;

/**
 *
 * @author Rahul
 */
public class GymBattle extends Battle{
    
    private GymLeader leader;
    private int[] restrictedItems;
    
    
    public GymBattle(Handler handler, Player player, Pokemon enemy) {
        super(handler, player, enemy, 0);
    }

    @Override
    public boolean checkForFainted() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
