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
        screen = new BattleScreen(this);
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
        screen.disableButtons();
        for (int x = 0; x < 6; x++) {
            if (player.getPokemon(x) != null) {
                if (player.getPokemon(x).getHp() > 0) {
                    addText("Pick your next pokemon.");
                    break;
                }
                else if (x == 6) {
                    lose(); // put fainted screen
                }
            }
        }
        return true;
    }
    
    @Override
    public void win(){
        screen.exit();
        player.addMoney(moneyGained);
        exit();
    }
    
    @Override
    public void lose(){
        screen.exit();
        player.respawn();
        player.getParty().heal();
        exit();
    }
}
