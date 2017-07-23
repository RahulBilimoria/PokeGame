/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.pokemon;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import pokegame.gfx.ImageLoader;
import pokegame.handler.Handler;
import pokegame.item.Item;
import pokegame.pokemon.move.Move;
import pokegame.pokemon.move.Moveset;
import pokegame.pokemon.move.learn.Learnset;
import pokegame.pokemon.nature.Nature;
import pokegame.pokemon.status.Status;
import pokegame.type.Type;
import pokegame.utils.Utils;

/**
 *
 * @author Rahul
 */
public class Pokemon {

    public static final int LEVELS = 99;
    public static final int EXP[][] = new int[4][LEVELS];

    public static final int POKEMON_COUNT = 151;
    public static final BasePokemon POKEMON_LIST[] = new BasePokemon[POKEMON_COUNT];

    public static class BasePokemon {

        private final int hp, att, def, spatt, spdef, speed; //Pokemons base stats
        private final int id; //Pokemon ID
        private final int evolvesTo, evolvesAt; //ID of evolved pokemon and level of evolution
        private final float genderChance;
        private final int expType, baseExp; //Type of exp gain (fast, slow, medium) and base exp given when defeated (for wild pokemon)
        private final int catchRate; //Pokemon catch rate
        private String name, description; //Name and description of pokemon
        private Type type1, type2; //Type(s) of pokemon
        //Images for the pokemon
        private BufferedImage front, back, shinyFront, shinyBack, icon;
        private Learnset learnableMoves;

        public BasePokemon(int id, String name, Type type1, Type type2,
                int evolvesTo, int evolvesAt, int hp, int att, int def,
                int spatt, int spdef, int speed, float genderChance,
                int catchRate, int baseExp) {
            this.id = id;
            this.name = name;
            this.type1 = type1;
            this.type2 = type2;
            this.evolvesTo = evolvesTo;
            this.evolvesAt = evolvesAt;
            this.genderChance = genderChance;
            this.catchRate = catchRate;
            this.baseExp = baseExp;
            this.hp = hp;
            this.att = att;
            this.def = def;
            this.spatt = spatt;
            this.spdef = spdef;
            this.speed = speed;
            expType = 0;
            learnableMoves = Learnset.LEARN_SET[id];
            loadImages();
        }

        private void loadImages() {
            icon = ImageLoader.loadImage("/pokemon/icons/" + (id + 1) + ".png");
            front = ImageLoader.loadImage("/pokemon/front/normal/" + (id + 1) + ".png");
            back = ImageLoader.loadImage("/pokemon/back/normal/" + (id + 1) + ".png");
            shinyFront = ImageLoader.loadImage("/pokemon/front/shiny/" + (id + 1) + ".png");
            shinyBack = ImageLoader.loadImage("/pokemon/back/shiny/" + (id + 1) + ".png");
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public Type getType1() {
            return type1;
        }

        public Type getType2() {
            return type2;
        }

        public int getEvolvesTo() {
            return evolvesTo;
        }

        public int getEvolvesAt() {
            return evolvesAt;
        }

        public float getGenderChance() {
            return genderChance;
        }

        public int getExpType() {
            return expType;
        }

        public int getBaseExp() {
            return baseExp;
        }

        public int getCatchRate() {
            return catchRate;
        }

        public int getHp() {
            return hp;
        }

        public int getAtt() {
            return att;
        }

        public int getDef() {
            return def;
        }

        public int getSpatt() {
            return spatt;
        }

        public int getSpdef() {
            return spdef;
        }

        public int getSpeed() {
            return speed;
        }

        public BufferedImage getIcon() {
            return icon;
        }

        public BufferedImage getFront(boolean shiny) {
            if (shiny) {
                return shinyFront;
            }
            return front;
        }

        public BufferedImage getBack(boolean shiny) {
            if (shiny) {
                return shinyBack;
            }
            return back;
        }

        public Learnset getLearnableMoves() {
            return learnableMoves;
        }
    }

    private Handler h;

    private int id, level, currentHP, hp, att, def, spatt, spdef, speed;
    private int myExp, expToLevel, levelsGained;
    private int tpPoints;
    private int friendship;
    private int friendshipRate;
    private String nickname;
    //male = true,1 female = false,0
    private boolean gender, shiny, inBattle;
    private Moveset moveset;
    private Nature nature;
    private Status status;
    private Item heldItem;
    private ArrayList<Move> learnableMoves; //make it so you can learn moves if you accidently skip a level in a battle

    public Pokemon(Handler h, int id, boolean shiny, int level, int hp,
            int att, int def, int spatt, int spdef, int speed, Moveset moveset) {
        this.h = h;
        BasePokemon p = POKEMON_LIST[id];
        this.id = id;
        this.nickname = "";
        this.shiny = shiny;
        this.level = level;
        this.hp = hp + p.getHp();
        this.att = att + p.getAtt();
        this.def = def + p.getDef();
        this.spatt = spatt + p.getSpatt();
        this.spdef = spdef + p.getSpdef();
        this.speed = speed + p.getSpeed();
        this.moveset = moveset;
        this.myExp = 0;
        this.expToLevel = EXP[p.getExpType()][level];
        this.status = Status.NORMAL;
        tpPoints = 0;
        friendship = 0;
        friendshipRate = 1;
        currentHP = this.hp;
        nature = Nature.getRandomNature();
        chooseGender();
        inBattle = false;
        levelsGained = 0;
    }

    public Pokemon(Handler h, int id, boolean shiny, int level, int hp,
            int att, int def, int spatt, int spdef, int speed, String nickname,
            Status status, int tpPoints, int friendship, int friendshipRate,
            Nature nature, boolean gender, Moveset moveset) {
        this.h = h;
        this.id = id;
        this.nickname = nickname;
        this.shiny = shiny;
        this.level = level;
        this.hp = hp;
        this.att = att;
        this.def = def;
        this.spatt = spatt;
        this.spdef = spdef;
        this.speed = speed;
        this.moveset = moveset;
        this.myExp = 0;
        this.expToLevel = EXP[POKEMON_LIST[id].getExpType()][level];
        this.status = status;
        this.tpPoints = tpPoints;
        this.friendship = friendship;
        this.friendshipRate = friendshipRate;
        currentHP = this.hp;
        this.nature = nature;
        this.gender = gender;
        inBattle = false;
        levelsGained = 0;
    }

    public Pokemon(int[] data) {
        this.id = data[0];
        this.level = data[1];
        this.currentHP = data[2];
        this.hp = data[3];
        this.att = data[4];
        this.def = data[5];
        this.spatt = data[6];
        this.spdef = data[7];
        this.speed = data[8];
        this.gender = true;
        if (data[9] == 1) {
            this.gender = false;
        }
        this.shiny = true;
        if (data[10] == 1) {
            this.shiny = false;
        }
        this.expToLevel = data[11];
        this.tpPoints = data[12];
        this.nickname = data[13] + "";
        this.nature = Nature.NATURE_LIST[0];//Nature.NATURE_LIST[data[14]];
        this.status = Status.NORMAL;
        this.moveset = new Moveset(data[17], data[19], data[21], data[23]);
        for (int x = 0; x < 4; x++) {
            moveset.setMovePP(x, data[18 + 2 * x]);
        }
        inBattle = false;
        levelsGained = 0;
    }

    public static void init() {
        String file = Utils.loadFileAsString("dat/pokemon/PokemonList.dat");
        String[] pokemon = file.split("\\s+");
        for (int x = 0; x < POKEMON_COUNT; x++) {
            int a = (x * 15);
            POKEMON_LIST[x] = new BasePokemon(
                    Utils.parseInt(pokemon[a + 0]),
                    pokemon[a + 1],
                    Type.getType(Utils.parseInt(pokemon[a + 2])),
                    Type.getType(Utils.parseInt(pokemon[a + 3])),
                    Utils.parseInt(pokemon[a + 4]),
                    Utils.parseInt(pokemon[a + 5]),
                    Utils.parseInt(pokemon[a + 6]),
                    Utils.parseInt(pokemon[a + 7]),
                    Utils.parseInt(pokemon[a + 8]),
                    Utils.parseInt(pokemon[a + 9]),
                    Utils.parseInt(pokemon[a + 10]),
                    Utils.parseInt(pokemon[a + 11]),
                    Utils.parseFloat(pokemon[a + 12]),
                    Utils.parseInt(pokemon[a + 13]),
                    Utils.parseInt(pokemon[a + 14]));
        }
        for (int x = 0; x < 4; x++) {
            switch (x) {
                case 0:
                    file = Utils.loadFileAsString("dat/pokemon/level/Fast.dat");
                    break;
                case 1:
                    file = Utils.loadFileAsString("dat/pokemon/level/MediumFast.dat");
                    break;
                case 2:
                    file = Utils.loadFileAsString("dat/pokemon/level/MediumSlow.dat");
                    break;
                case 3:
                    file = Utils.loadFileAsString("dat/pokemon/level/Slow.dat");
                    break;
                default:
                    break;
            }
            pokemon = file.split("\\s+");
            for (int y = 0; y < LEVELS; y++) {
                EXP[x][y] = Utils.parseInt(pokemon[y]);
            }
        }
    }

    public void heal() {
        currentHP = hp;
        moveset.heal();
    }

    private void chooseGender() {
        if (Math.random() < POKEMON_LIST[id].getGenderChance()) {
            gender = true;
        }
        gender = false;
    }

    public void addExp(int exp) {
        this.myExp += exp;
        h.getGame().addText(POKEMON_LIST[id].getName() + " gained " + exp + " Exp!\n", Color.gray);
        while (myExp >= expToLevel) {
            myExp -= expToLevel;
            level++;
            if (inBattle){
                levelsGained++;
            }
            addTp(3);
            expToLevel = EXP[POKEMON_LIST[id].getExpType()][level];
            h.getGame().addText(POKEMON_LIST[id].getName() + " has leveled up!\n", Color.green);
            checkLevelUp();
            //check moves when level up
            //check evolution when leveling up
        }
    }

    public void checkLevelUp() {
        if (level == POKEMON_LIST[id].getEvolvesAt()) {

        }
        Move[] learnable = getLearnableMoves();
        for (int x = 0; x < learnable.length; x++) {
            for (int y = 0; y < 4; y++) {
                if (moveset.getMove(y) == null) {
                    moveset.setMove(y, learnable[x]);
                    break;
                }
            }

        }
    }

    public void damage(int dam) {
        this.currentHP = this.currentHP - dam;
    }

    public float getModifier(Type type) {
        float m = Type.MODIFIER[type.getID()][POKEMON_LIST[id].getType1().getID()];
        Type type2 = POKEMON_LIST[id].getType2();
        if (type2 != null) {
            m = m * Type.MODIFIER[type.getID()][type2.getID()];
        }
        return m;
    }

    public void addFriendship(int amount) {
        this.friendship = amount * friendshipRate;
    }

    public void addTp(int tp) {
        this.tpPoints += tp;
    }

    public void setInBattle(boolean inBattle){
        this.inBattle = inBattle;
    }
    
    public int getLevelsGained(){
        return levelsGained;
    }
    
    public void resetLevelsGained(){
        levelsGained = 0;
    }
    
    public int getExp() {
        return myExp;
    }

    public int getMaxExp() {
        return expToLevel;
    }

    public String getName() {
        return POKEMON_LIST[id].getName();
    }

    public int getID() {
        return id;
    }

    public Type getType1() {
        return POKEMON_LIST[id].getType1();
    }

    public Type getType2() {
        return POKEMON_LIST[id].getType2();
    }

    public boolean getGender() {
        return gender;
    }

    public String getNick() {
        return nickname;
    }

    public int getLevel() {
        return level;
    }

    public int getTP() {
        return tpPoints;
    }

    public int getHp() {
        return currentHP;
    }

    public int getMaxHp() {
        return hp;
    }

    public int getAttack() {
        return att;
    }

    public int getDefense() {
        return def;
    }

    public int getSpatt() {
        return spatt;
    }

    public int getSpdef() {
        return spdef;
    }

    public int getSpeed() {
        return speed;
    }

    public int getFriendship() {
        return friendship;
    }

    public int getFriendshipRate() {
        return friendshipRate;
    }

    public void addHp(int hp) {
        this.hp += hp;
    }

    public void addAttack(int att) {
        this.att += att;
    }

    public void addDefense(int def) {
        this.def += def;
    }

    public void addSpAtt(int spatt) {
        this.spatt += spatt;
    }

    public void addSpDef(int spdef) {
        this.spdef += spdef;
    }

    public void addSpeed(int speed) {
        this.speed += speed;
    }

    public void addLevel(int levels) {
        level += levels; // might have to change for learning moves to work properly
        addTp(3 * levels);
        levelsGained = levels;
        expToLevel = EXP[POKEMON_LIST[id].getExpType()][level];
        h.getGame().addText(POKEMON_LIST[id].getName() + " has leveled up!\n", Color.green);
        checkLevelUp();
    }

    public void setFriendship(int amount) {
        this.friendship = amount;
    }

    public void setFriendshipRate(int value) {
        this.friendshipRate = value;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void restoreMovePP(int amount) {
        moveset.restoreDepleted(amount);
    }

    public String getNature() {
        return "Nature";
    }

    public void heal(int heal) {
        this.currentHP += heal;
        if (currentHP >= hp) {
            currentHP = hp;
        }
    }

    public void setHandler(Handler handler) {
        this.h = handler;
    }

    public void setNick(String nickname) {
        this.nickname = nickname;
    }

    public BufferedImage getFront() {
        return POKEMON_LIST[id].getFront(shiny);
    }

    public BufferedImage getBack() {
        return POKEMON_LIST[id].getBack(shiny);
    }

    public BufferedImage getIcon() {
        return POKEMON_LIST[id].getIcon();
    }

    public Moveset getMoveset() {
        return moveset;
    }

    public int getCatchRate() {
        return POKEMON_LIST[id].getCatchRate();
    }

    public int getBaseExp() {
        return POKEMON_LIST[id].getBaseExp();
    }

    public Move[] getLearnableMoves() {
        return POKEMON_LIST[id].getLearnableMoves().getLearnableMove(level, levelsGained);
    }

    public static int getIdByName(String s) {
        for (int x = 0; x < POKEMON_COUNT; x++) {
            if (POKEMON_LIST[x].getName().toLowerCase().equals(s.toLowerCase())) {
                return x;
            }
        }
        return -1;
    }

    public int[] toStorage() {
        int data[] = new int[25];
        data[0] = id;
        data[1] = level;
        data[2] = currentHP;
        data[3] = hp;
        data[4] = att;
        data[5] = def;
        data[6] = spatt;
        data[7] = spdef;
        data[8] = speed;
        if (gender) {
            data[9] = 0;
        } else {
            data[9] = 1;
        }
        if (shiny) {
            data[10] = 0;
        } else {
            data[10] = 1;
        }
        data[11] = myExp;
        data[12] = tpPoints;
        data[13] = -1; //change
        data[14] = nature.getId();
        data[15] = status.getId();
        data[16] = -1; //change
        for (int x = 0; x < 4; x++) {
            if (moveset.getMove(x) != null) {
                data[(17 + (2 * x))] = moveset.getMove(x).getId();
                data[(18 + (2 * x))] = moveset.getMove(x).getPP();
            } else {
                data[(17 + (2 * x))] = -1;
                data[(18 + (2 * x))] = -1;
            }
        }
        return data;
    }

    @Override
    public String toString() {
        String s = "";
        s = s + "ID:" + id + "\n";
        s = s + "Name:" + POKEMON_LIST[id].getName() + "\n";
        s = s + "Level:" + level + "\n";
        s = s + "Hp:" + hp + "\n";
        s = s + "Att:" + att + "\n";
        s = s + "Def:" + def + "\n";
        s = s + "Spatt:" + spatt + "\n";
        s = s + "Spdef:" + spdef + "\n";
        s = s + "Speed:" + speed + "\n";
        return s;
    }

    public static String getPokemonName(int pokemonId) {
        return POKEMON_LIST[pokemonId].getName();
    }

    public Pokemon copy() {
        return new Pokemon(h, this.id, this.shiny, this.level, this.hp, this.att,
                this.def, this.spatt, this.spdef, this.speed, this.nickname,
                this.status, this.tpPoints, this.friendship, this.friendshipRate,
                this.nature, this.gender, this.moveset);
    }
}
