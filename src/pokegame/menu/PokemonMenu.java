/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.menu;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import pokegame.handler.Handler;

/**
 *
 * @author Rahul
 */
public class PokemonMenu {
    private Handler handler;
    
    private int selectedPokemon;
    
    private JLabel[] pkmn = new JLabel[6];
    private JButton[] stats = new JButton[12];
    private JLabel currentPkmn;
    private JLabel name, level, nickname, hp, att, def, spatt, spdef, speed, tp, exptoLevel;
    private JLabel pokedexNumber, gender;
    private JLabel type1, type2, nature, status;
    private JProgressBar expBar;
    private JLabel move1, move2, move3, move4;
    
    private JButton learnMoves;
    
    private JPanel panel;
    private JButton exitButton;
    
    public PokemonMenu(Handler handler){
        this.handler = handler;
        panel = new JPanel();
        panel.setSize(handler.getCanvas().getWidth(), handler.getCanvas().getHeight());
        panel.setBackground(Color.black);
    }
    
    public void update(){
        
    }

    public JPanel getPanel(){
        return panel;
    }
}
