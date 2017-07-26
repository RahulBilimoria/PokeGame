/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.battle;

import java.awt.Color;
import pokegame.entity.ai.AI;
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
    private Pokemon[] leaderPokemon;
    private Pokemon[] playerPokemon;
    private int[] restrictedItems;
    
    
    public GymBattle(Handler handler, Player player, GymLeader leader) {
        super(handler, player, leader.getActivePokemon(), 0);
        screen = new BattleScreen(this);
        this.leader = leader;
        leaderPokemon = leader.getCopyOfPokemon();
        enemy = leaderPokemon[leader.getActiveNumber()];
        playerPokemon = new Pokemon[6];
        playerPokemon[0] = player.getPokemon(player.getActiveNumber());
        for (int x = 1; x < 6; x++){
            playerPokemon[x] = null;
        }
    }

    @Override
    public boolean checkForFainted() {
        if (enemy.getHp() <= 0) {
            String faintedPkmn = enemy.getName();
            addExp();
            enemy = AI.chooseNextPokemon(leader.getDifficulty(), leaderPokemon, ally);
            if (enemy == null) won = true;
            else {
                resetModifiers(false);
                screen.updateEnemyPokemon();
                addText(faintedPkmn + " has fainted! " + leader.getName() + " sent out " + enemy.getName()+"!");
            }
            return true;
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
                else if (x == 5) {
                    lose(); // put fainted screen
                }
            }
        }
        return true;
    }
    
    @Override
    public void win(){
        handler.getGame().addText("You've defeated Gym Leader " + leader.getName()+".\n", Color.blue);
        player.addBadge(leader.getRegion(), leader.getBadge());
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
