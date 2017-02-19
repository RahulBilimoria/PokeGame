/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.pokemon;

import java.awt.image.BufferedImage;
import pokegame.gfx.ImageLoader;
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

    static class BasePokemon {

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
        
        public void loadImages() {
            icon = ImageLoader.loadImage("/pokemon/icons/" + (id + 1) + ".png");
            front = ImageLoader.loadImage("/pokemon/front/normal/" + (id + 1) + ".png");
            back = ImageLoader.loadImage("/pokemon/back/normal/" + (id + 1) + ".png");
            shinyFront = ImageLoader.loadImage("/pokemon/front/shiny/" + (id + 1) + ".png");
            shinyBack = ImageLoader.loadImage("/pokemon/back/shiny/" + (id + 1) + ".png");
        }
        
        public int getId(){
            return id;
        }
        
        public String getName(){
            return name;
        }
        
        public String getDescription(){
            return description;
        }
        
        public Type getType1(){
            return type1;
        }
        
        public Type getType2(){
            return type2;
        }
        
        public int getEvolvesTo(){
            return evolvesTo;
        }
        
        public int getEvolvesAt(){
            return evolvesAt;
        }
        
        public float getGenderChance(){
            return genderChance;
        }
        
        public int getExpType(){
            return expType;
        }
        
        public int getBaseExp(){
            return baseExp;
        }
        
        public int getCatchRate(){
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

        public BufferedImage getIcon(){
            return icon;
        }
        
        public BufferedImage getFront(boolean shiny){
            if (shiny)
                return shinyFront;
            return front;
        }
        
        public BufferedImage getBack(boolean shiny){
            if (shiny)
                return shinyBack;
            return back;
        }
        
        public Learnset getLearnableMoves(){
            return learnableMoves;
        }
    }

    private int id, level, currentHP, hp, att, def, spatt, spdef, speed;
    private int myExp, expToLevel;
    private int tpPoints;
    private String nickname;
    //male = true, female = false
    private boolean gender, shiny;
    private Moveset moveset;
    private Nature nature;
    private Status status;
    private Item heldItem;

    public Pokemon(int id, boolean shiny, int level, int hp,
            int att, int def, int spatt, int spdef, int speed, Moveset moveset) {
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
        this.status = new Status();
        tpPoints = 0;
        currentHP = this.hp;
        nature = Nature.getRandomNature();
        chooseGender();
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

    public void chooseGender() {
        if (Math.random() < POKEMON_LIST[id].getGenderChance()) {
            gender = true;
        }
        gender = false;
    }

    public void addExp(int exp) {
        this.myExp += exp;
        while (myExp >= expToLevel) {
            myExp -= expToLevel;
            level++;
            addTp(3);
            expToLevel = EXP[POKEMON_LIST[id].getExpType()][level];
            System.out.println("LEVEL UP");
            checkLevelUp();
            //check moves when level up
            //check evolution when leveling up
            //add tp
        }
    }

    public void checkLevelUp() {
        if (level == POKEMON_LIST[id].getEvolvesAt()) {

        } else {
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

    public void addTp(int tp) {
        this.tpPoints += tp;
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

    public String getGender() {
        if (gender) {
            return "Male";
        }
        return "Female";
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
        return POKEMON_LIST[id].getLearnableMoves().getLearnableMove(level);
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
    
    public static String getPokemonName(int pokemonId){
        return POKEMON_LIST[pokemonId].getName();
    }
}
