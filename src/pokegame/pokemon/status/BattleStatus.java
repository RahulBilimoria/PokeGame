/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.pokemon.status;

/**
 *
 * @author Rahul
 */
public class BattleStatus {
    
    public static final BattleStatus NORMAL = new BattleStatus(0, "Normal");
    public static final BattleStatus BOUND = new BattleStatus(1, "Bound");
    public static final BattleStatus CANT_ESCAPE = new BattleStatus(2, "Can't Escape");
    public static final BattleStatus CONFUSION = new BattleStatus(3, "Confusion");
    public static final BattleStatus CURSE = new BattleStatus(4, "Curse");
    public static final BattleStatus EMBARGO = new BattleStatus(5, "Embargo");
    public static final BattleStatus ENCORE = new BattleStatus(6, "Encore");
    public static final BattleStatus FLINCH = new BattleStatus(7, "Flinch");
    public static final BattleStatus HEAL_BLOCK = new BattleStatus(8, "Heal Block");
    public static final BattleStatus IDENTIFIED = new BattleStatus(9, "Identified");
    public static final BattleStatus INFATUATION = new BattleStatus(10, "Infatuation");
    public static final BattleStatus LEECH_SEED = new BattleStatus(11, "Leech Seed");
    public static final BattleStatus NIGHTMARE = new BattleStatus(12, "Nightmare");
    public static final BattleStatus PERISH_SONG = new BattleStatus(13, "Perish Song");
    public static final BattleStatus TAUNT = new BattleStatus(14, "Taunt");
    public static final BattleStatus TELEKINESIS = new BattleStatus(15, "Telekinesis");
    public static final BattleStatus TORMENT = new BattleStatus(16, "Torment");
    public static final BattleStatus AQUA_RING = new BattleStatus(17, "Aqua Ring");
    public static final BattleStatus BRACING = new BattleStatus(18, "Bracing");
    public static final BattleStatus CENTER_OF_ATTENTION = new BattleStatus(19, "Center of Attention");
    public static final BattleStatus DEFENSE_CURL = new BattleStatus(20, "Defense Curl");
    public static final BattleStatus GLOWING = new BattleStatus(21, "Glowing");
    public static final BattleStatus ROOTING = new BattleStatus(22, "Rooting");
    public static final BattleStatus MAGIC_COAT = new BattleStatus(23, "Magic Coat");
    public static final BattleStatus MAGNETIC_LEVITATION = new BattleStatus(24, "Magnetic Levitation");
    public static final BattleStatus MINIMIZE = new BattleStatus(25, "Minimize");
    public static final BattleStatus PROTECTION = new BattleStatus(26, "Protection");
    public static final BattleStatus TEAM_PROTECTION = new BattleStatus(27, "Team Protection");
    public static final BattleStatus RECHARGING = new BattleStatus(28, "Recharging");
    public static final BattleStatus SEMI_INVULNERABLE = new BattleStatus(29, "Semi-Invulnerable");
    public static final BattleStatus SUBSTITUTE = new BattleStatus(30, "Substitute");
    public static final BattleStatus TAKING_AIM = new BattleStatus(31, "Taking Aim");
    public static final BattleStatus TAKING_IN_SUNLIGHT = new BattleStatus(32, "Taking in Sunlight");
    public static final BattleStatus WITHDRAWING = new BattleStatus(33, "Withdrawing");
    public static final BattleStatus WHIPPING_UP_A_WHIRLWIND = new BattleStatus(34, "Whipping up a Whirlwind");
    
    
    private int id;
    private String name;
    
    public BattleStatus(int id, String name){
        this.id = id;
        this.name = name;
    }
    
    public int getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }
    
    public static BattleStatus getStatusById(int id){
        switch(id){
            case 1: return BOUND;
            case 2: return CANT_ESCAPE;
            case 3: return CONFUSION;
            case 4: return CURSE;
            case 5: return EMBARGO;
            case 6: return ENCORE;
            case 7: return FLINCH;
            case 8: return HEAL_BLOCK;
            case 9: return IDENTIFIED;
            case 10: return INFATUATION;
            case 11: return LEECH_SEED;
            case 12: return NIGHTMARE;
            case 13: return PERISH_SONG;
            case 14: return TAUNT;
            case 15: return TELEKINESIS;
            case 16: return TORMENT;
            case 17: return AQUA_RING;
            case 18: return BRACING;
            case 19: return CENTER_OF_ATTENTION;
            case 20: return DEFENSE_CURL;
            case 21: return GLOWING;
            case 22: return ROOTING;
            case 23: return MAGIC_COAT;
            case 24: return MAGNETIC_LEVITATION;
            case 25: return MINIMIZE;
            case 26: return PROTECTION;
            case 27: return TEAM_PROTECTION;
            case 28: return RECHARGING;
            case 29: return SEMI_INVULNERABLE;
            case 30: return SUBSTITUTE;
            case 31: return TAKING_AIM;
            case 32: return TAKING_IN_SUNLIGHT;
            case 33: return WITHDRAWING;
            case 34: return WHIPPING_UP_A_WHIRLWIND;
            default: return NORMAL;
        }
    }

}
