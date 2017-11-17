/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.battle;

import java.awt.Color;
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
import pokegame.pokemon.status.BattleStatus;
import pokegame.pokemon.status.Status;

/**
 *
 * @author Rahul
 */
public abstract class Battle {
    
    protected class VolatileStatus{
        
        private BattleStatus status;
        private int statusDuration;
        private String moveName;
        
        public VolatileStatus(){
            status = BattleStatus.NORMAL;
            statusDuration = 1;
            moveName = "";
        }
        
        public void reset(){
            status = BattleStatus.NORMAL;
            statusDuration = 1;
            moveName = "";
        }
        
        public BattleStatus getStatus(){
            return status;
        }
        
        public void setStatus(BattleStatus status){
            this.status = status;
        }
        
        public int getDuration(){
            return statusDuration;
        }
        
        public void setDuration(int d){
            statusDuration = d;
        }
        
        public void addDuration(int i){
            statusDuration += i;
        }
        
        public String getText(){
            return moveName;
        }
        
        public void setText(String s){
            moveName = s;
        }
        
    }

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
    private int statusDuration;
    private VolatileStatus allyStatus[] = new VolatileStatus[35];
    private VolatileStatus enemyStatus[] = new VolatileStatus[35];
    private float allyModifiers[] = new float[7],
            enemyModifiers[] = new float[7];
    private int allyModifiers2[] = new int[7],
            enemyModifiers2[] = new int[7];
    // 0 attack, 1 defense, 2 spatt, 3 spdef, 4 speed, 5 accuracy, 6 evasion
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
        statusDuration = 1;
        lastTime = System.currentTimeMillis();
        activePokemon = player.getActiveNumber();
        fainted = false;
        bag = false;
        itemUsed = false;
        won = false;
        for (int x = 0; x < 7; x++) {
            allyModifiers[x] = 1;
            allyModifiers2[x] = 0;
            enemyModifiers[x] = 1;
            enemyModifiers2[x] = 0;
        }
        for (int x = 0; x < 35; x++) {
            allyStatus[x] = new VolatileStatus();
            enemyStatus[x] = new VolatileStatus();
        }
    }

    public void tick() {
        if (System.currentTimeMillis() - lastTime > 1000) {
            lastTime = System.currentTimeMillis();
            seconds = seconds - 1;
        }
        if (seconds == 0) {
            startRound(-1);
            screen.updateEverything();
        }
        screen.updateSeconds(seconds);
    }

    public void startRound(int allyMoveID) {
        roundNumber++;
        addText("----------------------------------------------------------------- Round: " + roundNumber);
        battleSequence(allyMoveID, AI.chooseNextMove(difficulty, enemy, ally));
        seconds = 60;
        if (won) {
            win();
        } else {
            screen.updateEverything();
        }
    }

    public void battleSequence(int allyMoveID, int enemyMoveID) {
        if (fainted) {
            fainted = false;
            return;
        }
        if (allyMoveID == -1) {
            battleTurn(enemy, ally, enemyMoveID, "Enemy ", "Ally ", enemyStatus, allyStatus);
        } else if (ally.getSpeed() > enemy.getSpeed()) {
            battleTurn(ally, enemy, allyMoveID, "Ally ", "Enemy ", allyStatus, enemyStatus);
            if (checkForFainted()) {
                return;
            }
            battleTurn(enemy, ally, enemyMoveID, "Enemy ", "Ally ", enemyStatus, allyStatus);
        } else if (ally.getSpeed() < enemy.getSpeed()) {
            battleTurn(enemy, ally, enemyMoveID, "Enemy ", "Ally ", enemyStatus, allyStatus);
            if (checkForFainted()) {
                return;
            }
            battleTurn(ally, enemy, allyMoveID, "Ally ", "Enemy ", allyStatus, enemyStatus);
        } else if (Math.random() < 0.5) {
            battleTurn(ally, enemy, allyMoveID, "Ally ", "Enemy ", allyStatus, enemyStatus);
            if (checkForFainted()) {
                return;
            }
            battleTurn(enemy, ally, enemyMoveID, "Enemy ", "Ally ", enemyStatus, allyStatus);
        } else {
            battleTurn(enemy, ally, enemyMoveID, "Enemy ", "Ally ", enemyStatus, allyStatus);
            if (checkForFainted()) {
                return;
            }
            battleTurn(ally, enemy, allyMoveID, "Ally ", "Enemy ", allyStatus, enemyStatus);
        }
        if (allyStatus[13].getStatus() == BattleStatus.PERISH_SONG){
            if (allyStatus[13].getDuration() >= 4){
                addText(ally.getName() + " fainted due to perish song!");
                ally.damage(999);
            } else allyStatus[13].addDuration(1);
        }
        if (enemyStatus[13].getStatus() == BattleStatus.PERISH_SONG){
            if (enemyStatus[13].getDuration() >= 4){
                addText("Enemy " + enemy.getName() + " fainted due to perish song!");
                enemy.damage(999);
            } else enemyStatus[13].addDuration(1);
        }
        checkForFainted();
        allyStatus[7].reset();
        enemyStatus[7].reset();
        allyStatus[18].reset();
        enemyStatus[18].reset();
        allyStatus[26].reset();
        enemyStatus[26].reset();
    }

    protected void battleTurn(Pokemon attacking, Pokemon defending, int attMoveID, String a, String d, VolatileStatus[] attackStatus, VolatileStatus[] defendStatus) {
        if (attMoveID == -1) return;
        Move m = attacking.getMoveset().getMove(attMoveID);
        int statusDamage = Status.applyStatus(attacking, statusDuration);
        if (applyStatus(statusDamage, attacking, a, attackStatus)) return;
        applyVolatileStatus(attacking, defending, attackStatus, true, "");
        m.usePP();
        int damage = getDamage(attacking, defending, m, attackStatus, defendStatus);
        addText(a + attacking.getName() + " used " + m.getName() + ".");
        if (Math.random() < m.getAccuracy(attacking, defending)) {
            if (attacking.getStatus() == Status.BURN && m.getCategory() == 0) {
                damage = damage / 2;
            }
            addText(d + defending.getName() + " was hit for " + damage + " damage!");
            defending.damage(damage);
        } else {
            addText(a + attacking.getName() + "'s attack missed!");
        }
        applyVolatileStatus(attacking, defending, attackStatus, false, "");
        if (statusDamage > 0) {
            addText(a + attacking.getName() + " took " + statusDamage + " damage from " + attacking.getStatus().getName() + "!");
            attacking.damage(statusDamage);
            statusDuration++;
        }
    }

    public abstract boolean checkForFainted();

    public abstract void win();

    public abstract void lose();

    public int getDamage(Pokemon attack, Pokemon defend, Move m, VolatileStatus[] attackStatus, VolatileStatus[] defendStatus) {
        int att = 1;
        int def = 1;
        int power = m.getPower();
        float stab = 1;
        float type = defend.getModifier(m.getType());
        float critical = 1;
        float rand = 100;
        /*if (attackStatus[20].getStatus() == BattleStatus.DEFENSE_CURL){
            if (m == Move.getMoveByName("Rollout") || m == Move.getMoveByName("Ice Ball")){
                power = power * 2;
            }
        }*/
        if (m.getCategory() == 0) {
            att = attack.getAttack();
            def = defend.getDefense();
        } else if (m.getCategory() == 1) {
            att = attack.getSpatt();
            def = defend.getSpatt();
        }
        float damage = (((float) ((2 * attack.getLevel() + 10)) / 250) * ((float) (att) / def) * power + 2);
        if (m.getType() == attack.getType1() || m.getType() == attack.getType2()) {
            stab = 1.5f;
        }
        if (Math.random() <= m.getCrit()) {
            critical = 2; // fix this because crits will never be proc'ed
        }
        rand = (rand - (int) (Math.random() * 16)) / 100;
        float modifier = stab * type * critical * rand;
        return (int) (damage * modifier);
    }
    
    public int getConfusionDamage(Pokemon p){
        int att = p.getAttack();
        int def = p.getDefense();
        float rand = (100 - (int) (Math.random() * 16)) / 100;
        float damage = (((float) ((2 * p.getLevel() + 10)) / 250) * ((float) (att) / def) * 40 + 2);
        return (int) (damage * rand);
    }

    public boolean applyStatus(int i, Pokemon attacking, String a, VolatileStatus[] s) {
        switch (i) {
            case -2: //paralyzed
                if (Math.random() <= 0.33) {
                    addText(a + attacking.getName() + " is paralyzed! It can't move!");
                    return true;
                }
                break;
            case -3: //asleep
                if (Math.random() < 0.25 && statusDuration != 1 || statusDuration == 4) {
                    addText(a + attacking.getName() + " woke up.");
                    s[12].reset();
                    attacking.setStatus(Status.NORMAL);
                    statusDuration = 1;
                } else {
                    addText(a + attacking.getName() + " is fast asleep.");
                    return true;
                }
                statusDuration++;
                break;
            case -5: //frozen
                if (Math.random() <= 0.2) {
                    addText(a + attacking.getName() + " is no longer frozen solid.");
                    attacking.setStatus(Status.NORMAL);
                    statusDuration = 1;
                } else {
                    addText(a + attacking.getName() + " is frozen solid.");
                    return true;
                }
                break;
        }
        return false;
    }

    private void applyVolatileStatus(Pokemon p, Pokemon other, VolatileStatus[] s, boolean startOfTurn, String name) {
        //needs to either return an int to indicate what to do, or split start and end of turn into two separate methods
        if (startOfTurn){
            if (s[3].getStatus() == BattleStatus.CONFUSION){
                addText(name + p.getName() + " is confused!");
                if (Math.random() < 0.33){
                    addText(name + p.getName() + " hurt itself in its confusion!");
                    p.damage(Math.max(getConfusionDamage(p),1));
                    // needs to prevent using actual move at this point
                }
                s[3].addDuration(1);
            } 
            if (s[6].getStatus() == BattleStatus.ENCORE){
                if (s[6].getDuration() == 4){
                    addText(name + "Filler for ENCORE dont know what to add yet.");
                    s[6].reset();
                    //needs to replace move id with the last move used
                } else s[6].addDuration(1);
            }
            if (s[7].getStatus() == BattleStatus.FLINCH){
                addText(name + p.getName() + " flinched!");
                s[7].reset();
                // needs to prevent actually moving
            }
            if (s[8].getStatus() == BattleStatus.HEAL_BLOCK){
                if (s[8].getDuration() == 6){
                    addText(name + BattleStatus.HEAL_BLOCK.getName() + " is no longer in effect on " + p.getName() + ".");
                    s[8].reset();
                    // need to block certain moves
                } else s[8].addDuration(1);
            }
            if (s[10].getStatus() == BattleStatus.INFATUATION){
                if (Math.random() < 0.5){
                    //needs to prevent attacking
                }
            }
            if (s[14].getStatus() == BattleStatus.TAUNT){
                //do something to preven from using status moves
                if (s[14].getDuration() >= 4){
                    addText(name + p.getName() + " is no longer taunted.");
                    s[14].reset();
                } else s[14].addDuration(1);
            }
            if (s[15].getStatus() == BattleStatus.TELEKINESIS){
                if (s[15].getDuration() >= 4){
                    addText(name + p.getName() + " is no longer under the effects of telekinesis.");
                    s[15].reset();
                } else s[14].addDuration(1);
            }
            if (s[16].getStatus() == BattleStatus.TORMENT){
                //do something to prevent using the same move
            }
            if (s[28].getStatus() == BattleStatus.RECHARGING){
                s[28].reset();
            }
        } else {
            if (s[1].getStatus() == BattleStatus.BOUND){
                if (s[1].getDuration() == 6 || s[1].getDuration() >= 4 && Math.random() < 0.5){
                    addText(name + p.getName() + " was freed from " + s[1].getText() + "!");
                    s[1].reset();
                } else {
                    addText(name + p.getName() + " is hurt by " + s[1].getText() + "!");
                    p.damage(Math.max(p.getMaxHp()/8,1));
                    s[1].addDuration(1);
                }
            }
            if (s[3].getStatus() == BattleStatus.CONFUSION){
                if (Math.random() < 0.33 || s[3].getDuration() == 5){
                    addText(name + p.getName() + " snapped out of his confusion.");
                    s[3].reset();
                }
            }
            if (s[4].getStatus() == BattleStatus.CURSE){
                p.damage(Math.max(p.getMaxHp()/4,1));
            }
            if (s[5].getStatus() == BattleStatus.EMBARGO){
                if (s[5].getDuration() == 5){
                    addText(BattleStatus.EMBARGO.getName() + " is no longer in effect on " + name + p.getName() + ".");
                    s[5].reset();
                } else s[5].addDuration(1);
            }
            if (s[11].getStatus() == BattleStatus.LEECH_SEED){
                int damage = Math.max(p.getMaxHp()/8,1);
                p.damage(damage);
                addText(name + p.getName() + " took " + damage + " damage from leech seed!");
                if (s[8].getStatus() != BattleStatus.HEAL_BLOCK) {
                    other.heal(damage);
                    addText(name + other.getName() + " is still under the effects of heal block");
                } else {
                    addText(name + other.getName() + " healed for " + damage + " points due to leech seed!");
                }
            }
            if (s[12].getStatus() == BattleStatus.NIGHTMARE){
                p.damage(Math.max(p.getMaxHp()/4,1));
            }
            if (s[17].getStatus() == BattleStatus.AQUA_RING){
                p.heal(Math.max(p.getMaxHp()/16, 1));
            }
            if (s[22].getStatus() == BattleStatus.ROOTING){
                p.heal(Math.max(p.getMaxHp()/16, 1));
            }
            if (s[24].getStatus() == BattleStatus.MAGNETIC_LEVITATION){
                if (s[24].getDuration() >= 6){
                    addText("Not sure what to add for this so, Magnet Levitation 24");
                    s[24].reset();
                } else s[24].addDuration(1);
            }
        }
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
                startRound(-1);
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
                h.use(ally);
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
                r.use(ally);
                break;
        }
    }

    public void catchPokemon() {
        if (player.getParty().getPartySize() != 6) {
            for (int x = 0; x < 6; x++) {
                if (player.getParty().getPokemon(x) == null) {
                    enemy.setHandler(handler);
                    player.getParty().addPokemon(x, enemy);
                    exit();
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
                        exit();
                        return;
                    }
                }
            }
        }
        handler.getGame().addText("No space in party or box, " + enemy.getName() + " has been released!\n", Color.red);
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
        ally.addExp(exp);
        player.addToQuest(enemy.getID(), enemy.getLevel());
    }

    public void resetModifiers(boolean ally) {
        if (ally) {
            for (int x = 0; x < 7; x++) {
                allyModifiers[x] = 1;
                allyModifiers2[x] = 0;
            }
            for (int x = 0; x < 35; x++) {
                allyStatus[x].reset();
            }
        } else {
            for (int x = 0; x < 7; x++) {
                enemyModifiers[x] = 1;
                enemyModifiers2[x] = 0;
            }
            for (int x = 0; x < 35; x++) {
                enemyStatus[x].reset();
            }
        }
    }

    public String getMoveName(int moveID) {
        Move m = ally.getMoveset().getMove(moveID);
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

    public Pokemon getAlly() {
        return ally;
    }

    public Pokemon getEnemy() {
        return enemy;
    }

    public void addBattleHandler(BattleHandler battleHandler) {
        this.battleHandler = battleHandler;
    }

    public void addText(String text) {
        screen.updateBattleHistory("\n" + text);
    }

    public void changePokemon(int index) {
        ally = player.getPokemon(index);
        statusDuration = 1;
        resetModifiers(true);
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
        if (ally.getHp() <= 0) {
            for (int x = 0; x < 6; x++) {
                if (player.getPokemon(x) == null) {
                    continue;
                }
                if (player.getPokemon(x).getHp() > 0) {
                    player.setActiveNumber(x);
                    break;
                }
            }
        }
        for (int x = 0; x < 6; x++) {
            if (player.getPokemon(x) == null) {
                continue;
            }
            if (player.getPokemon(x).getStatus() == Status.TOXIC) {
                player.getPokemon(x).setStatus(Status.POISONED);
            }
        }
        screen.exit();
        player.setEnabled(true);
        player.removeBattle();
        handler.getKeyManager().reset();
    }
}