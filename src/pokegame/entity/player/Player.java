/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.entity.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import pokegame.entity.Person;
import pokegame.entity.player.Bag.MyItem;
import pokegame.gfx.Animation;
import pokegame.gfx.Asset;
import pokegame.handler.Handler;
import pokegame.item.Item;
import pokegame.npc.quest.Quest;
import pokegame.pokemon.Pokemon;
import pokegame.pokemon.move.Move;
import pokegame.tiles.Tile;

/**
 *
 * @author minim_000
 */
public class Player extends Person {

    private int id;
    private String name;

    private Animation up, down, left, right;
    private int portraitID;
    private int direction;
    private int activePokemon;
    private int lastX, lastY;
    private int saveX, saveY, saveMap;
    private boolean enabled;

    private int money;
    private Bag bag;
    private Party party;
    private Storage storage;
    private ArrayList<Quest> activeQuests;
    private ArrayList<Integer> completedQuests;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, Person.DEFAULT_CREATURE_WIDTH, Person.DEFAULT_CREATURE_HEIGHT);
        up = new Animation(400, Asset.player_up);
        down = new Animation(400, Asset.player_down);
        left = new Animation(400, Asset.player_left);
        right = new Animation(400, Asset.player_right);
        portraitID = 2;
        direction = 0;
        moved = false;
        bag = new Bag(this);
        party = new Party(handler, this);
        storage = new Storage(handler, this);
        activePokemon = 0;
        enabled = true;
        activeQuests = new ArrayList<>();
        completedQuests = new ArrayList<>();
    }

    @Override
    public void tick() {
        if (enabled) {
            if (!isMoving) {
                getInput();
            }
        }
        if (isMoving) {
            up.tick();
            down.tick();
            left.tick();
            right.tick();
            move();
        } else {
            xMove = 0;
            yMove = 0;
        }
        if (!(lastX == xTile && lastY == yTile)) {
            setMoved(true);
            lastX = xTile;
            lastY = yTile;

        }
        handler.getGameCamera().centerOnEntity(this);
    }

    private void getInput() {
        xMove = 0;
        yMove = 0;
        if (handler.getKeyManager().up) {
            isMoving = true;
            yMove = -speed;
        } else if (handler.getKeyManager().down) {
            isMoving = true;
            yMove = speed;
        } else if (handler.getKeyManager().left) {
            isMoving = true;
            xMove = -speed;
        } else if (handler.getKeyManager().right) {
            isMoving = true;
            xMove = speed;
        } else if (handler.getKeyManager().ctrl) {
            handler.getKeyManager().unpressCTRL();
            enabled = false;
            switch (direction) {
                case 0:
                    handler.getWorld().interact(xTile, yTile - 1);
                    break;
                case 1:
                    handler.getWorld().interact(xTile + 1, yTile);
                    break;
                case 2:
                    handler.getWorld().interact(xTile, yTile + 1);
                    break;
                case 3:
                    handler.getWorld().interact(xTile - 1, yTile);
                    break;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getCurrentAnimationFrame(), (int) (xPixel - handler.getGameCamera().getXOffset()),
                (int) (yPixel - handler.getGameCamera().getYOffset() - Tile.TILE_HEIGHT), width, 2 * height, null);
    }

    private BufferedImage getCurrentAnimationFrame() {
        if (xMove < 0) {
            direction = 3;
            return left.getCurrentFrame();
        } else if (xMove > 0) {
            direction = 1;
            return right.getCurrentFrame();
        } else if (yMove < 0) {
            direction = 0;
            return up.getCurrentFrame();
        } else if (yMove > 0) {
            direction = 2;
            return down.getCurrentFrame();
        } else {
            switch (direction) {
                case 0:
                    return up.idle();
                case 1:
                    return right.idle();
                case 2:
                    return down.idle();
                case 3:
                    return left.idle();
                default:
                    return down.idle();
            }
        }
    }

    /*protected boolean collisionWithTile(int xPixel, int yPixel, int direction) {
        if (handler.getWorld().getEdit()) {
            return false;
        }
        switch (direction) {
            case 0:
                return handler.getWorld().getMap().getScript(xPixel, yPixel - 1, true).isSolid(); //up
            case 1:
                return handler.getWorld().getMap().getScript(xPixel + 1, yPixel, true).isSolid(); //right
            case 2:
                return handler.getWorld().getMap().getScript(xPixel, yPixel + 1, true).isSolid(); //down
            case 3:
                return handler.getWorld().getMap().getScript(xPixel - 1, yPixel, true).isSolid(); //left
            default:
                return false;
        }
    }*/
    public void addQuest(Quest q) {
        if (completedQuests != null) {
            for (Integer x : completedQuests) {
                if (x == q.getId()) {
                    return;
                }
            }
        }
        if (activeQuests != null) {
            for (Quest activeQuest : activeQuests) {
                if (activeQuest.getId() == q.getId()) {
                    return;
                }
            }
        }
        Quest quest = q.cloneQuest();
        activeQuests.add(quest);
    }

    public void addToQuest(int pkmnId, int pkmnLvl) {
        if (activeQuests == null) {
            return;
        }
        for (Quest activeQuest : activeQuests) {
            if (activeQuest.getWildPokemonId() == pkmnId && activeQuest.getWildPokemonLvl() <= pkmnLvl) {
                activeQuest.addWildPokemonFainted();
            }
        }
    }

    public void addToQuest(Item item, int amount) {
        if (activeQuests == null) {
            return;
        }
        for (Quest activeQuest : activeQuests) {
            if (activeQuest.getItemId() == item.getItemID()) {
                activeQuest.addCurrentItems(amount);
            }
        }
    }

    public void addToQuest(Pokemon p, int amount) {
        if (activeQuests == null) {
            return;
        }
        for (Quest activeQuest : activeQuests) {
            if (activeQuest.getPokemonId() == p.getID() && p.getLevel() >= activeQuest.getPokemonLevel()) {
                activeQuest.addCurrentPokemon(amount);
            }
        }
    }

    public int hasQuest(Quest q) {
        if (completedQuests != null) {
            for (Integer x : completedQuests) {
                if (x == q.getId()) {
                    return 1;
                }
            }
        }
        if (activeQuests != null) {
            for (Quest activeQuest : activeQuests) {
                if (activeQuest.getId() == q.getId()) {
                    return 2;
                }
            }
        }
        return 0;
    }

    public Quest getQuest(Quest q) {
        for (Quest activeQuest : activeQuests) {
            if (activeQuest.getId() == q.getId()) {
                return activeQuest;
            }
        }
        return null;
    }

    public boolean checkQuest(Quest q) {
        for (Quest activeQuest : activeQuests) {
            if (activeQuest.getId() == q.getId()) {
                q = activeQuest;
                break;
            }
        }
        return q.checkQuest();
    }

    public String getQuestRemaining(Quest q) {
        return getQuest(q).toString();
    }

    public void handInItems(Quest q) {
        for (Quest activeQuest : activeQuests) {
            if (activeQuest.getId() == q.getId()) {
                int amount = Math.min(activeQuest.getRemainingItems(), bag.getNumberOfItems(activeQuest.getItemId()));
                bag.removeItem(Item.items[activeQuest.getItemId()], -amount);
                activeQuest.addCurrentItems(amount);
                return;
            }
        }
    }
    
    public void handInPokemon(Quest q){
        for (Quest activeQuest : activeQuests) {
            if (activeQuest.getId() == q.getId()) {
                int amount = Math.min(activeQuest.getRemainingPokemon(), party.getNumberOfPokemon(activeQuest.getPokemonId(), activeQuest.getPokemonLevel()));
                for (int x = 0; x < amount; x++){
                    party.removePokemon(activeQuest.getPokemonId(), activeQuest.getPokemonLevel());
                    activeQuest.addCurrentPokemon(1);
                }
                return;
            }
        }
    }

    public void giveReward(Quest q) {
        Item i = q.getItemReward();
        Pokemon p = q.getPokemonReward();
        int iAmount = q.getItemRewardAmount();
        int pAmount = q.getPokemonRewardAmount();
        int exp = (int) q.getPokemonExpReward();
        handler.getGame().addText("[" + q.getName() + "] Quest completed!\n", Color.orange);
        if (i != null) {
            handler.getGame().addText("[SYSTEM] You received " + iAmount + " " + i.getName() + "!\n", Color.MAGENTA);
            bag.addItem(i, iAmount);
        }
        if (p != null) {
            for (int x = 0; x < pAmount; x++) {
                if (party.getPartySize() == 6) {
                    storage.storePokemon(p.toStorage());
                } else {
                    handler.getGame().addText("[SYSTEM] " + p.getName() + " has been added to your party!\n", Color.MAGENTA);
                    party.addPokemon(p);
                }
            }
        }
        if (exp != -1) {
            party.getPokemon(activePokemon).addExp(exp);
        }
        completeQuest(q.getId());
    }

    public void completeQuest(int id) {
        removeQuest(id);
        completedQuests.add(id);
    }

    public void removeQuest(int id) {
        for (Quest activeQuest : activeQuests) {
            if (activeQuest.getId() == id) {
                activeQuests.remove(activeQuest);
                break;
            }
        }
    }

    public void setActiveNumber(int num) {
        activePokemon = num;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getActiveNumber() {
        return activePokemon;
    }

    public Pokemon getPokemon(int partyID) {
        return party.getPokemon(partyID);
    }

    public Pokemon getActivePokemon() {
        return party.getPokemon(activePokemon);
    }

    public BufferedImage getAllyImage() {
        return party.getPokemon(activePokemon).getBack();
    }

    public String getPokemonName() {
        return party.getPokemonName(activePokemon);
    }

    public Bag getBag() {
        return bag;
    }

    public ArrayList<MyItem> getBag(int bagID) {
        return bag.getBag(bagID);
    }

    public String getBagName(int bagID) {
        return bag.getBagName(bagID);
    }

    public Move getMove(int moveID) {
        return party.getPokemon(activePokemon).getMoveset().getMove(moveID);
    }

    public int getPortraitID() {
        return portraitID;
    }

    public void printBag() {
        System.out.println(bag.toString());
    }

    public void printPokemon(int id) {
        System.out.println(party.toString(id));
    }

    public Party getParty() {
        return party;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
