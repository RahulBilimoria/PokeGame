/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import pokegame.menu.GameMenu;

/**
 *
 * @author Rahul
 */
public class GameMenuHandler implements ActionListener{

    private GameMenu lm;
    
    public GameMenuHandler(GameMenu lm){
        this.lm = lm;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == lm.getPokemon()){
            lm.openPokemon();
        }
        else if (e.getSource() == lm.getBag()){
            lm.openBag();
        }
        else if (e.getSource() == lm.getBuddy()){
            lm.openBuddy();
        }
        else if (e.getSource() == lm.getSettings()){
            lm.openSettings();
        }
        else if (e.getSource() == lm.getLogout()){
            System.exit(0);
        }
    }
    
}
