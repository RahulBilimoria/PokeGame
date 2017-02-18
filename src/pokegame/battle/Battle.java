/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.battle;

import java.awt.Graphics;
import pokegame.entity.player.Bag;
import pokegame.entity.player.Player;
import pokegame.handler.BattleHandler;
import pokegame.handler.Handler;
import pokegame.item.pokeball.Pokeball;
import pokegame.item.potion.Potion;
import pokegame.pokemon.Pokemon;
import pokegame.pokemon.move.Move;

/**
 *
 * @author Rahul
 */
public class Battle {

    //When all your pokemon faint, respawn at pokemon center
    //When one of your pokemon faints, need selection to a new pokemon
    //When you win, need to show win screen
    private BattleHandler battleHandler;
    private Handler handler;
    private Player player;
    private Pokemon enemy;

    private int activePokemon;
    private int seconds;
    private int roundNumber;
    private long lastTime;
    private boolean fainted;
    private boolean bag;
    private boolean itemUsed;

    public Battle(Handler handler, Player player, Pokemon enemy) {
        this.handler = handler;
        this.player = player;
        this.enemy = enemy;
        seconds = 60;
        roundNumber = 0;
        lastTime = System.currentTimeMillis();
        activePokemon = player.getActiveNumber();
        fainted = false;
        bag = false;
        itemUsed = false;
    }

    public void tick() {
        if (System.currentTimeMillis() - lastTime > 1000) {
            lastTime = System.currentTimeMillis();
            seconds = seconds - 1;
            System.out.println(seconds);
            battleHandler.updateSeconds(seconds);
        } else if (seconds == 0) {
            damage(-1);
            battleHandler.updateEverything();
        }
        battleHandler.updateSeconds(seconds);
    }

    public void render(Graphics g) {

    }

    public void damage(int moveID) {
        roundNumber++;
        addText("------------------------------ Round: " + roundNumber);
        if (moveID == -1) {
            enemyMove();
            faintedPokemon();
        } else if (getPokemon().getSpeed() > enemy.getSpeed()) {
            allyMove(moveID);
            if (enemy.getHp() <= 0) {
                battleHandler.win(); // put winning screen, get exp etc
            } else {
                enemyMove();
            }
            faintedPokemon();
        } else {
            enemyMove();
            if (getPokemon().getHp() <= 0) {
                faintedPokemon();
            } else {
                allyMove(moveID);
            }
            if (enemy.getHp() <= 0) {
                battleHandler.win(); // put winning screen, get exp etc
            }
        }
        seconds = 60;
    }

    public void enemyMove() {
        Move m = enemy.getMoveset().getMove(1);
        int damage = getDamage(enemy, getPokemon(), m);
        addText(enemy.getName() + " used " + m.getName() + ".");
        if (Math.random() < m.getAccuracy()) {
            addText(getPokemon().getName() + " was hit for " + damage + " damage!");
            getPokemon().damage(damage);
        } else {
            addText(enemy.getName() + "'s attack missed!");
        }
    }

    public void allyMove(int moveID) {
        Move m = getPokemon().getMoveset().getMove(moveID);
        int damage = getDamage(getPokemon(), enemy, m);
        addText(getPokemon().getName() + " used " + m.getName() + ".");
        if (Math.random() < m.getAccuracy()) {
            addText(enemy.getName() + " was hit for " + damage + " damage!");
            enemy.damage(damage);
        } else {
            addText(getPokemon().getName() + "'s attack missed!");
        }
    }

    public int getDamage(Pokemon attack, Pokemon defend, Move m) {
        int att = 1;
        int def = 1;
        float stab = 1;
        float type = 1;
        float critical = 1;
        float rand = 100;
        if (m.getCategory() == 0) {
            att = attack.getAttack();
            def = defend.getDefense();
        } else if (m.getCategory() == 1) {
            att = attack.getSpatt();
            def = defend.getSpatt();
        }
        float damage = (((float) ((2 * attack.getLevel() + 10)) / 250) * ((float) (att) / def) * m.getPower() + 2);
        if (m.getType() == attack.getType1() || m.getType() == attack.getType2()) {
            stab = 1.5f;
        }
        type = defend.getModifier(m.getType());
        if (Math.random() <= 0.01) {
            critical = 2;
        }
        rand = (rand - (int) (Math.random() * 16)) / 100;
        float modifier = stab * type * critical * rand;
        return (int) (damage * modifier);
    }

    public void faintedPokemon() {
        boolean f = true;
        if (getPokemon().getHp() <= 0) {
            fainted = true;
            addText(getPokemon().getName() + " has fainted!");
            if (bag)
                openBag();
            battleHandler.disableButtons();
            for (int x = 0; x < 6; x++) {
                if (player.getPokemon(x).getHp() > 0) {
                    f = false;
                    addText("Pick your next pokemon.");
                    break;
                }
            }
            if (f) {
                battleHandler.lose(); // put fainted screen
            }
        }
    }

    public boolean getFainted() {
        return fainted;
    }

    public void openBag() {
        bag = !bag;
        battleHandler.changePanel(bag);
    }

    public void useMove(int moveID) {
        getPokemon().getMoveset().getMove(moveID).usePP();
    }

    public void useItem(String name) {
        String item[] = name.split(" | ");
        if (item.length >= 2) {
            if (getBag().getItem(item[2]).getItemCount() > 0) {
                itemUsed = true;
                if (item[2].contains("ball") || item[2].contains("Ball")) {
                    usePokeball((Pokeball) getBag().getItem(item[2]));
                } else if (item[2].contains("Potion")) {
                    usePotion((Potion) getBag().getItem(item[2]));
                }
                getBag().getItem(item[2]).removeItem(1);
                damage(-1);
            }
            else
                addText("You have no more " + item[2] +"s!");
        }
    }

    public void usePokeball(Pokeball pokeball) {
        int a = (((3 * enemy.getMaxHp() - 2 * enemy.getHp()) * enemy.getCatchRate() * pokeball.getCatchRate()) / (3 * enemy.getMaxHp())) * enemy.getStatusInt();
        int b = 1048560 / (int) Math.sqrt(Math.sqrt(16711680 / a));
        for (int x = 0; x < 3; x++) {
            int y = (int) (Math.random() * 65535);
            System.out.println("Try " + x + ": b is (" + b + ") | y is(" + y + ").");
            if (y < b) {
                break;
            } else if (x == 2 && y > b) {
                catchPokemon();
            }
        }
    }

    public void usePotion(Potion potion) {
        getPokemon().addHp(potion.getHealAmount());
    }

    public void catchPokemon() {
        System.out.println("Pokemon Caught!");
        battleHandler.caughtPokemon();
    } // NEED TO DO SOMETHING WITH POKES AFTER CATCHING

    public void addExp() {
        // a | 1 for wild, 1.5 for trainer
        int a = 1;
        // t | 1 for original owner, 1.5 for otherwise
        int t = 1;
        // base pokemon exp
        // e | 1.5 for holding lucky egg, 1.5 otherwise
        int e = 1;
        //level of the enemy pokemon
        // p | exp power point or some shit (its gen 5 and 6 so fk it)
        int p = 1;
        // f | 1.2 if affection is 2 hearts or more, 1 otherwise
        int f = 1;
        // v | 1.2 if its passed the evolution level but hasnt evoled, 1 otherwise
        int v = 1;
        // s | 1 for pokemon participated in battle, 2 for exp share turned on
        int s = 1;
        int exp = (a * t * enemy.getBaseExp() * e * enemy.getLevel() * p * f * v) / (7 * s);
        System.out.println("You've gained " + exp + " Exp!");
        getPokemon().addExp(exp);
    }

    public String getMoveName(int moveID) {
        Move m = getPokemon().getMoveset().getMove(moveID);
        if (m == null) {
            return "";
        }
        return m.getName();
    }

    public int getActiveNumber() {
        return activePokemon;
    }

    public Pokemon getPokemon() {
        return player.getPokemon(activePokemon);
    }

    public Pokemon getEnemy() {
        return enemy;
    }

    public void addBattleHandler(BattleHandler battleHandler) {
        this.battleHandler = battleHandler;
        addText("Wild " + enemy.getName() + " encountered!");
        addText("Go " + getPokemon().getName() + "!");
    }

    public void addText(String text) {
        battleHandler.addText("\n" + text);
    }
    
    public void setFainted(boolean f){
        this.fainted = f;
    }

    public void changePokemon(int ap) {
        activePokemon = ap;
    }
    
    public boolean getItemUsed(){
        return itemUsed;
    }
    
    public void setItemUsed(boolean i){
        itemUsed = i;
    }

    public Bag getBag() {
        return player.getBag();
    }

    public Player getPlayer() {
        return player;
    }

    public void exit() {
        if (player.getActivePokemon().getHp() <= 0) {
            for (int x = 0; x < 6; x++) {
                if (player.getPokemon(x).getHp() > 0) {
                    player.setActiveNumber(x);
                    break;
                }
            }
        }
        handler.getWorld().setExit();
    }
}