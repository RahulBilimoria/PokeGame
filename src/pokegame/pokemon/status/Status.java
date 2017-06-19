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
public class Status {
    
    public static final Status NORMAL = new Status(0, "Normal");
    public static final Status POISONED = new Status(1, "Poisoned");
    public static final Status PARALYZED = new Status(2, "Paralyzed");
    public static final Status SLEEP = new Status(3, "Sleep");
    public static final Status BURN = new Status(4, "Burn");
    public static final Status FROZEN = new Status(5, "Frozen");
    public static final Status CONFUSION = new Status(6, "Confusion");
    
    private int id;
    private String name;
    
    public Status(int id, String name){
        this.id = 0;
        this.name = name;
    }
    
    public int getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }
}
