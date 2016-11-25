/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.item.pokeball;

import pokegame.item.Item;

/**
 *
 * @author Rahul
 */
public class Pokeball extends Item{
    
    private int catchRate = 1;
    private boolean masterBall = false;
    
    public Pokeball(int id, int itemCount){
        super (id, itemCount, "Pokeball");
    }
    
    public int getCatchRate(){
        return catchRate;
    }
    
    public boolean getMasterBall(){
        return masterBall;
    }
    
    public String toString(){
        return "Pokeball\nCount: " + itemCount + "\n";
    }
}
