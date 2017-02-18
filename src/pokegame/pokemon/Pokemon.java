/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.pokemon;

import java.awt.image.BufferedImage;
import pokegame.gfx.ImageLoader;
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
    public static final Pokemon POKEMON_LIST[] = new Pokemon[POKEMON_COUNT];

    static class BaseStats {

        private final int hp, att, def, spatt, spdef, speed;

        public BaseStats(int hp, int att, int def, int spatt, int spdef, int speed) {
            this.hp = hp;
            this.att = att;
            this.def = def;
            this.spatt = spatt;
            this.spdef = spdef;
            this.speed = speed;
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
    }

    private int id, evolvesTo, evolvesAt, level, currentHP, hp, att, def, spatt, spdef, speed;
    private float genderChance;
    private int myExp, maxExp, expType, baseExp;
    private int catchRate;
    private int tpPoints;
    private String nickname, pokemonName, description;
    //male = true, female = false
    private boolean gender, shiny;
    private BufferedImage front, back, icon;
    private BaseStats baseStats;
    private final Type type1, type2;
    private final Learnset MOVE_LIST;
    private Moveset moveset;
    private Nature nature;
    private Status status;

    public Pokemon(int id, String pokemonName, Type type1, Type type2, int evolvesTo, int evolvesAt, BaseStats baseStats, float genderChance, int catchRate, int baseExp) {
        this.id = id;
        this.pokemonName = pokemonName;
        this.type1 = type1;
        this.type2 = type2;
        this.evolvesTo = evolvesTo;
        this.evolvesAt = evolvesAt;
        this.baseStats = baseStats;
        this.MOVE_LIST = Learnset.LEARN_SET[id];
        this.genderChance = genderChance;
        this.catchRate = catchRate;
        this.baseExp = baseExp;
    }

    public Pokemon(Pokemon p, boolean shiny, int level, int hp,
            int att, int def, int spatt, int spdef, int speed, Moveset moveset) {
        this.id = p.id;
        this.pokemonName = p.pokemonName;
        this.nickname = "";
        this.evolvesTo = p.evolvesTo;
        this.evolvesAt = p.evolvesAt;
        this.shiny = shiny;
        this.level = level;
        this.hp = hp + p.baseStats.hp;
        this.att = att + p.baseStats.att;
        this.def = def + p.baseStats.def;
        this.spatt = spatt + p.baseStats.spatt;
        this.spdef = spdef + p.baseStats.spdef;
        this.speed = speed + p.baseStats.speed;
        this.type1 = p.type1;
        this.type2 = p.type2;
        this.MOVE_LIST = Learnset.LEARN_SET[id];
        this.moveset = moveset;
        this.myExp = 0;
        this.expType = 0;
        this.maxExp = EXP[expType][level];
        this.catchRate = p.getCatchRate();
        this.baseExp = p.getBaseExp();
        this.status = new Status();
        tpPoints = 0;
        currentHP = this.hp;
        nature = Nature.getRandomNature();
        chooseGender();
        loadImages();
    }

    public static void init() {
        String file = Utils.loadFileAsString("dat/pokemon/PokemonList.dat");
        String[] pokemon = file.split("\\s+");
        for (int x = 0; x < POKEMON_COUNT; x++) {
            int a = (x * 15);
            POKEMON_LIST[x] = new Pokemon(
                    Utils.parseInt(pokemon[a + 0]),
                    pokemon[a + 1],
                    Type.getType(Utils.parseInt(pokemon[a + 2])),
                    Type.getType(Utils.parseInt(pokemon[a + 3])),
                    Utils.parseInt(pokemon[a + 4]),
                    Utils.parseInt(pokemon[a + 5]),
                    new BaseStats(Utils.parseInt(pokemon[a + 6]),
                            Utils.parseInt(pokemon[a + 7]),
                            Utils.parseInt(pokemon[a + 8]),
                            Utils.parseInt(pokemon[a + 9]),
                            Utils.parseInt(pokemon[a + 10]),
                            Utils.parseInt(pokemon[a + 11])),
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

    public void chooseGender() {
        if (Math.random() < genderChance) {
            gender = true;
        }
        gender = false;
    }

    public void addExp(int exp) {
        this.myExp += exp;
        while (myExp >= maxExp) {
            myExp -= maxExp;
            level++;
            addTp(3);
            maxExp = EXP[expType][level];
            System.out.println("LEVEL UP");
            checkLevelUp();
            //check moves when level up
            //check evolution when leveling up
            //add tp
        }
    }

    public void checkLevelUp() {
        if (level == evolvesAt) {

        } else {
            Move[] learnable = MOVE_LIST.getLearnableMove(level);
            for (int x = 0; x < learnable.length; x++) {
                for (int y = 0; y < 4; y++) {
                    if (moveset.getMove(y) == null) {
                        moveset.setMove(y, learnable[x]);
                        break;
                    }
                }

            }
        }
    }

    public void loadImages() {
        icon = ImageLoader.loadImage("/pokemon/icons/" + (id + 1) + ".png");
        front = ImageLoader.loadImage("/pokemon/front/normal/" + (id + 1) + ".png");
        back = ImageLoader.loadImage("/pokemon/back/normal/" + (id + 1) + ".png");
        if (shiny) {
            front = ImageLoader.loadImage("/pokemon/front/shiny/" + (id + 1) + ".png");
            back = ImageLoader.loadImage("/pokemon/back/shiny/" + (id + 1) + ".png");
        }
    }

    public void damage(int dam) {
        this.currentHP = this.currentHP - dam;
    }

    public float getModifier(Type type) {
        float m = Type.MODIFIER[type.getID()][type1.getID()];
        if (type2 != null) {
            m = m * Type.MODIFIER[type.getID()][type2.getID()];
        }
        return m;
    }

    public void addTp(int tp) {
        this.tpPoints += tp;
    }

    public int getExp() {
        return myExp;
    }

    public int getMaxExp() {
        return maxExp;
    }

    public String getName() {
        return pokemonName;
    }

    public int getID() {
        return id;
    }

    public Type getType1() {
        return type1;
    }

    public String getGender() {
        if (gender) {
            return "Male";
        }
        return "Female";
    }

    public Type getType2() {
        return type2;
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

    public String getStatus() {
        return "Status";
    }

    public int getStatusInt() {
        return 1;
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

    public void setNick(String nickname) {
        this.nickname = nickname;
    }

    public BufferedImage getFront() {
        return front;
    }

    public BufferedImage getBack() {
        return back;
    }

    public BufferedImage getIcon() {
        return icon;
    }

    public Moveset getMoveset() {
        return moveset;
    }

    public int getCatchRate() {
        return catchRate;
    }

    public int getBaseExp() {
        return baseExp;
    }

    public Move[] getLearnableMoves() {
        return MOVE_LIST.getLearnableMove(level);
    }

    public static int getIdByName(String s) {
        for (int x = 0; x < POKEMON_COUNT; x++) {
            if (POKEMON_LIST[x].getName().toLowerCase().equals(s.toLowerCase())) {
                return x;
            }
        }
        return -1;
    }

    public String toStorage() {
        String s;
        s = id + " " + nickname + " " + level + " " + tpPoints + " ";
        if (gender) {
            s = s + "0 ";
        } else {
            s = s + "1 ";
        }
        s = s + myExp + " ";
        if (shiny) {
            s = s + "0 ";
        } else {
            s = s + "1 ";
        }
        s = s + nature.getId() + " " + status.getId() + " " + currentHP + " " + hp + " ";
        s = s + att + " " + def + " " + spatt + " " + spdef + " " + speed + " ";
        if (moveset.getMove(0) != null) {
            s = s + moveset.getMove(0).getId() + " ";
        }
        else 
            s = s + "-1 ";
        if (moveset.getMove(1) != null) {
            s = s + moveset.getMove(1).getId() + " ";
        }
        else 
            s = s + "-1 ";
        if (moveset.getMove(2) != null) {
            s = s + moveset.getMove(2).getId() + " ";
        }
        else 
            s = s + "-1 ";
        if (moveset.getMove(3) != null) {
            s = s + moveset.getMove(3).getId();
        }
        else 
            s = s + "-1";
        System.out.println(s);
        return s;
    }

    @Override
    public String toString() {
        String s = "";
        s = s + "ID:" + id + "\n";
        s = s + "Name:" + pokemonName + "\n";
        s = s + "Level:" + level + "\n";
        s = s + "Hp:" + hp + "\n";
        s = s + "Att:" + att + "\n";
        s = s + "Def:" + def + "\n";
        s = s + "Spatt:" + spatt + "\n";
        s = s + "Spdef:" + spdef + "\n";
        s = s + "Speed:" + speed + "\n";
        return s;
    }
}
