/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.pokemon.status;

import pokegame.pokemon.Pokemon;

/**
 *
 * @author Rahul
 */
public class Status {
    
    public static final Status NORMAL = new Status(0, "Normal");
    public static final Status POISONED = new Status(1, "Poison");
    public static final Status PARALYZED = new Status(2, "Paralyzed");
    public static final Status SLEEP = new Status(3, "Sleep");
    public static final Status BURN = new Status(4, "Burn");
    public static final Status FROZEN = new Status(5, "Frozen");
    public static final Status TOXIC = new Status(6, "Poison");
    
    private int id;
    private String name;
    
    public Status(int id, String name){
        this.id = id;
        this.name = name;
    }
    
    public int getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }
    
    public static Status getStatusById(int id){
        switch(id){
            case 1: return POISONED;
            case 2: return PARALYZED;
            case 3: return SLEEP;
            case 4: return BURN;
            case 5: return FROZEN;
            case 6: return TOXIC;
            default: return NORMAL;
        }
    }
    
    public static int applyStatus(Pokemon p, int duration){
        switch(p.getStatus().getId()) {
            case 1: return applyPoison(p);
            case 2: return -2;
            case 3: return -3;
            case 4: return applyBurn(p);
            case 5: return -5;
            case 6: return applyToxic(p, duration);
            default: return 0;
        }
    }
    
    private static int applyPoison(Pokemon p){
        return Math.max(p.getMaxHp()/8, 1);
    }
    
    private static int applyBurn(Pokemon p){
        return Math.max(p.getMaxHp()/16, 1);
    }
    
    private static int applyToxic(Pokemon p, int d){
        return Math.max(p.getMaxHp()/16, 1) * d;
    }
}
