/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.battle;

import pokegame.entity.ai.AI;
import pokegame.entity.player.Player;
import pokegame.handler.Handler;
import pokegame.npc.PokemonTrainer;
import pokegame.pokemon.Pokemon;

/**
 *
 * @author Rahul
 */
public class TrainerBattle extends Battle{
    
    private PokemonTrainer enemyTrainer;
    private Pokemon[] trainerPokemon;
    private Pokemon[] playerPokemon;
    
    public TrainerBattle(Handler handler, Player player, PokemonTrainer enemyTrainer) {
        super(handler, player, enemyTrainer.getActivePokemon(), enemyTrainer.getDifficulty());
        screen = new BattleScreen(this);
        this.enemyTrainer = enemyTrainer;
        trainerPokemon = enemyTrainer.getCopyOfPokemon();
        enemy = trainerPokemon[enemyTrainer.getActiveNumber()];
        playerPokemon = new Pokemon[6];
        playerPokemon[0] = player.getPokemon(player.getActiveNumber());
        for (int x = 1; x < 6; x++){
            playerPokemon[x] = null;
        }
    }

    @Override
    public boolean checkForFainted() {
        if (enemy.getHp() <= 0) {
            addExp();
            enemy = AI.chooseNextPokemon(enemyTrainer.getDifficulty(), trainerPokemon, ally);
            if (enemy == null) won = true;
            else screen.updateEnemyPokemon();
            return true;
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
}
