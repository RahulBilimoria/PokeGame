/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame;

import pokegame.menu.LoginMenu;

/**
 *
 * @author Rahul
 */
public class Launcher {
    
    
    public static void main(String args[]){
        new LoginMenu("PokeGame", 800, 600);
    }
}
