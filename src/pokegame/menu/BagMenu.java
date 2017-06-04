/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.menu;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import pokegame.entity.player.Player;
import pokegame.handler.BagMenuHandler;
import pokegame.handler.Handler;

/**
 *
 * @author Rahul
 */
public class BagMenu {
    
    private Handler h;
    private BagMenuHandler bmh;
    private Player player;
    
    private JPanel panel;
    private JPanel itemList;
    private JPanel itemInfo;
    private JLabel background;
    private JLabel itemLabels[];
    private JButton throwAwayItem, useItem, left, right;
    private JScrollPane itemPane;
    
    private int bagIndex;
    
    public BagMenu(Handler h){
        this.h = h;
        this.player = h.getPlayer();
        bmh = new BagMenuHandler(this, h);
        bagIndex = 0;
        
        panel = new JPanel(null);
        panel.setSize(h.getCanvas().getWidth()/3, h.getCanvas().getHeight());
        panel.setBackground(Color.black);
        
        throwAwayItem = new JButton("Discard 1");
        useItem = new JButton("Use 1");
        left = new JButton("Left");
        right = new JButton("Right");
        
        itemList = new JPanel(new GridLayout(100,1));
        itemPane = new JScrollPane(itemList);
        
        
    }
    
    public JPanel getPanel(){
        return panel;
    }
}
