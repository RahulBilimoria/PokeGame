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
import pokegame.npc.PokemonTrainer;
import pokegame.pokemon.Pokemon;

/**
 *
 * @author Rahul
 */
public class TrainerBattle extends Battle{
    
    private PokemonTrainer trainer;
    private Pokemon[] trainerPokemon;
    private Pokemon[] playerPokemon;
    
    public TrainerBattle(Handler handler, Player player, PokemonTrainer trainer) {
        super(handler, player, trainer.getActivePokemon(), trainer.getDifficulty());
        screen = new BattleScreen(this);
        this.trainer = trainer;
        trainerPokemon = trainer.getCopyOfPokemon();
        enemy = trainerPokemon[trainer.getActiveNumber()];
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
            enemy = AI.chooseNextPokemon(trainer.getDifficulty(), trainerPokemon, ally);
            if (enemy == null) won = true;
            else {
                screen.updateEnemyPokemon();
                addText(faintedPkmn + " has fainted! " + trainer.getName() + " sent out " + enemy.getName()+"!");
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
                else if (x == 6) {
                    lose(); // put fainted screen
                }
            }
        }
        return true;
    }
    
    @Override
    public void win(){
        handler.getGame().addText("You've defeated Pokemon Trainer " + trainer.getName()+".\n", Color.blue);
        player.addToDefeated(trainer.getID());
        player.addMoney(trainer.getMoney());
        if (trainer.getItem() != null){
            player.getBag().addItem(trainer.getItem(), trainer.getItemAmount());
        }
        exit();
    }
    
    @Override
    public void lose(){
        player.respawn();
        player.getParty().heal();
        exit();
    }
}
