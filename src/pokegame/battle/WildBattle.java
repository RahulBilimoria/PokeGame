/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.battle;

import pokegame.entity.player.Player;
import pokegame.handler.Handler;
import pokegame.pokemon.Pokemon;

/**
 *
 * @author Rahul
 */
public class WildBattle extends Battle {
    
    private int moneyGained;

    public WildBattle(Handler handler, Player player, Pokemon enemy, int moneyGained) {
        super(handler, player, enemy, 0);
        
        this.moneyGained = moneyGained;
    }

    @Override
    public boolean checkForFainted() {
        if (enemy.getHp() <= 0) {
            won = true;
        }
        if (ally.getHp() > 0) {
            return false;
        }
        fainted = true;
        addText(ally.getName() + " has fainted!");
        closeBag();
        battleHandler.disableButtons();
        for (int x = 0; x < 6; x++) {
            if (player.getPokemon(x) != null) {
                if (player.getPokemon(x).getHp() > 0) {
                    addText("Pick your next pokemon.");
                    break;
                }
                else if (x == 6) {
                    battleHandler.lose(); // put fainted screen
                }
            }
        }
        return true;
    }
    
    private int getMoneyGained(){
        return moneyGained;
    }
}
