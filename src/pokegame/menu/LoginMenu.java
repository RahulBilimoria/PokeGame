/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import pokegame.Game;

/**
 *
 * @author Rahul
 */
public class LoginMenu {
    
    private String title;
    private int width, height;

    private JFrame frame;
    private JButton play;

    public LoginMenu(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        
        createScreen();
    }
    
    private void createScreen(){
        frame = new JFrame(title);
        play = new JButton("Play");
        play.addActionListener(new ButtonHandler());
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setLocationRelativeTo(null);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setResizable(false);

        frame.add(play);
    }
    
    private class ButtonHandler implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == play){
                frame.dispose();
                new Game(title, width, height).start();
            }
        }
    
        
}
}
