/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.storage;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import pokegame.entity.player.Player;
import pokegame.entity.player.Storage;

/**
 *
 * @author Rahul
 */
public class StorageScreen {
    
    private Player player;
    public Storage storage;
    private final int WIDTH = 800,
                      HEIGHT = 800;
    
    private JFrame frame;
    private JPanel boxPKMN, partyPKMN, menu;
    private JLabel leftArrow, rightArrow, boxName;
    private JLabel boxPokemon[] = new JLabel[25];
    private JLabel party[] = new JLabel[6];
    private JLabel background;
    
    public StorageScreen(){
        frame = new JFrame("Pokemon Storage");
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
        createBox();
        createParty();
        createMenu();
        
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    private void createBox(){
        boxPKMN = new JPanel();
    }
    
    private void createParty(){
        partyPKMN = new JPanel();
    }
    
    private void createMenu(){
        menu = new JPanel();
    }
}