/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.battle;

import java.awt.Color;
import java.awt.Graphics;
import pokegame.entity.ai.AI;
import pokegame.entity.player.Bag;
import pokegame.entity.player.Player;
import pokegame.entity.player.Storage;
import pokegame.handler.BattleHandler;
import pokegame.handler.Handler;
import pokegame.item.pokeball.Pokeball;
import pokegame.item.potion.Healing;
import pokegame.item.potion.PPRestore;
import pokegame.item.potion.Potion;
import pokegame.item.potion.StatusRemove;
import pokegame.pokemon.Pokemon;
import pokegame.pokemon.move.Move;

/**
 *
 * @author Rahul
 */
public abstract class Battle {

    //When all your pokemon faint, respawn at pokemon center
    //When one of your pokemon faints, need selection to a new pokemon
    //When you win, need to show win screen
    protected BattleHandler battleHandler;
    protected BattleScreen screen;
    protected Handler handler;
    protected Player player;
    protected Pokemon ally, enemy;

    private int activePokemon;
    private int seconds;
    private int roundNumber;
    private int difficulty;
    private long lastTime;
    protected boolean fainted;
    protected boolean won;
    private boolean bag;
    private boolean itemUsed;

    public Battle(Handler handler, Player player, Pokemon enemy, int difficulty) {
        this.handler = handler;
        this.player = player;
        this.enemy = enemy;
        this.difficulty = difficulty;
        ally = player.getPokemon(player.getActiveNumber());
        seconds = 60;
        roundNumber = 0;
        lastTime = System.currentTimeMillis();
        activePokemon = player.getActiveNumber();
        fainted = false;
        bag = false;
        itemUsed = false;
        won = false;
    }

    public void tick() {
        if (System.currentTimeMillis() - lastTime > 1000) {
            lastTime = System.currentTimeMillis();
            seconds = seconds - 1;
            System.out.println(seconds);
            battleHandler.updateSeconds(seconds);
        }
        if (seconds == 0) {
            //roundStart(-1);
            battleHandler.updateEverything();
        }
        battleHandler.updateSeconds(seconds);
    }

    public void render(Graphics g) {

    }
    
    public void startRound(int allyMoveID){
        roundNumber++;
        addText("------------------------------ Round: " + roundNumber);
        battleSequence(allyMoveID, AI.chooseNextMove(difficulty, enemy, ally));
        seconds = 60;
        if (won) screen.exit();
        else screen.updateEverything();
    }

    public void battleSequence(int allyMoveID, int enemyMoveID) {
        if (fainted){
            fainted = false;
            return;
        }
        if (allyMoveID == -1) {
            battleTurn(enemy, ally, enemyMoveID);
        } else if (ally.getSpeed() > enemy.getSpeed()) {
            battleTurn(ally, enemy, allyMoveID);
            if (checkForFainted()) return;
            battleTurn(enemy, ally, enemyMoveID);
        } else if (ally.getSpeed() < enemy.getSpeed()) {
            battleTurn(enemy, ally, enemyMoveID);
            if (checkForFainted()) return;
            battleTurn(ally, enemy, allyMoveID);
        } else if (Math.random() < 0.5) {
            battleTurn(ally, enemy, allyMoveID);
            if (checkForFainted()) return;
            battleTurn(enemy, ally, enemyMoveID);
        } else {
            battleTurn(enemy, ally, enemyMoveID);
            if (checkForFainted()) return;
            battleTurn(ally, enemy, allyMoveID);
        }
        checkForFainted();
    }

    protected void battleTurn(Pokemon attacking, Pokemon defending, int attMoveID) {
        if (attMoveID == -1)return;
        Move m = attacking.getMoveset().getMove(attMoveID);
        System.out.println(m.getId() + ": " + m.getName());
        m.usePP();
        int damage = getDamage(attacking, defending, m);
        addText(attacking.getName() + " used " + m.getName() + ".");
        if (Math.random() < m.getAccuracy()) {
            addText(defending.getName() + " was hit for " + damage + " damage!");
            defending.damage(damage);
        } else {
            addText(attacking.getName() + "'s attack missed!");
        }
    }

    public abstract boolean checkForFainted();

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

    public void openBag() {
        bag = !bag;
        battleHandler.changePanel(bag);
    }

    public void useItem(String name) {
        String item[] = name.split(" | ");
        if (item.length >= 2) {
            if (getBag().getItemCount(item[2]) > 0) {
                itemUsed = true;
                if (item[2].contains("ball") || item[2].contains("Ball")) {
                    usePokeball((Pokeball) getBag().getItem(item[2]));
                } else if (item[2].contains("Potion")) {
                    usePotion((Potion) getBag().getItem(item[2]));
                }
                getBag().removeItem(item[2], 1);
                //roundStart(-1);
            } else {
                addText("You have no more " + item[2] + "s!");
            }
        }
    }

    public void usePokeball(Pokeball pokeball) {
        int a = (int) (((3 * enemy.getMaxHp() - 2 * enemy.getHp()) * enemy.getCatchRate() * pokeball.getCatchRate()) / (3 * enemy.getMaxHp())) * enemy.getStatus().getId();
        int b = 1048560 / (int) Math.sqrt(Math.sqrt(16711680 / a));
        for (int x = 0; x < 3; x++) {
            int y = (int) (Math.random() * 65535 * 1000);
            System.out.println("Try " + x + ": b is (" + b + ") | y is(" + y + ").");
            if (y < b) {
                break;
            } else if (x == 2 && y > b) {
                catchPokemon();
            }
        }
    }

    public void usePotion(Potion potion) {
        switch (potion.getItemType()) {
            case 0:
                Healing h = (Healing) potion;
                h.use(getPokemon());
                break;
            case 1:
                PPRestore p = (PPRestore) potion;
                System.out.println("Not done yet");
                break;
            case 2:
                addText("Cant use this item in battle!");
                break;
            case 3:
                StatusRemove r = (StatusRemove) potion;
                r.use(getPokemon());
                break;
        }
    }

    public void catchPokemon() {
        if (player.getParty().getPartySize() != 6) {
            for (int x = 0; x < 6; x++) {
                if (player.getParty().getPokemon(x) == null) {
                    enemy.setHandler(handler);
                    player.getParty().addPokemon(x, enemy);
                    battleHandler.caughtPokemon();
                    handler.getGame().addText(enemy.getName() + " has been added to your party.\n", Color.red);
                    return;
                }
            }
        } else {
            for (int x = 0; x < Storage.BOXES_COUNT; x++) {
                for (int y = 0; y < Storage.BOXES_SIZE; y++) {
                    if (player.getStorage().getPokemon(x, y).getID() == -1) {
                        enemy.setHandler(handler);
                        handler.getGame().addText(enemy.getName() + " has been sent to storage.\n", Color.red);
                        player.getStorage().storePokemon(x, y, enemy.toStorage());
                        battleHandler.caughtPokemon();
                        return;
                    }
                }
            }
        }
        handler.getGame().addText("No space in box, " + enemy.getName() + " has been released!\n", Color.red);
    } // NEED TO DO SOMETHING WITH POKES AFTER CATCHING

    public void addExp() {
        // a | 1 for wild, 1.5 for trainer
        int a = 1;
        // t | 1 for original owner, 1.5 for otherwise
        int t = 1;
        // base pokemon exp
        // e | 1.5 for holding lucky egg, 1 otherwise
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
        getPokemon().addExp(exp);
        player.addToQuest(enemy.getID(), enemy.getLevel());
    }

    public String getMoveName(int moveID) {
        Move m = getPokemon().getMoveset().getMove(moveID);
        if (m == null) {
            return "";
        }
        return m.getName();
    }
    
    protected void closeBag() {
        if (bag) {
            openBag();
        }
    }

    public int getActiveNumber() {
        return activePokemon;
    }

    public Pokemon getPokemon() {
        return ally;
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

    public void setFainted(boolean f) {
        this.fainted = f;
    }

    public void changePokemon(int ap) {
        ally = player.getPokemon(ap);
    }

    public boolean getItemUsed() {
        return itemUsed;
    }

    public void setItemUsed(boolean i) {
        itemUsed = i;
    }

    public Bag getBag() {
        return player.getBag();
    }

    public Player getPlayer() {
        return player;
    }

    public boolean getFainted() {
        return fainted;
    }

    public void exit() {
        if (player.getActivePokemon().getHp() <= 0) {
            for (int x = 0; x < 6; x++) {
                if (player.getPokemon(x) == null) continue;
                if (player.getPokemon(x).getHp() > 0) {
                    player.setActiveNumber(x);
                    break;
                }
            }
        }
        handler.getWorld().setExit();
    }
}
