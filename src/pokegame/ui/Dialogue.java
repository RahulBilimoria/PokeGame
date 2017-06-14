/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.ui;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import pokegame.gfx.ImageLoader;
import pokegame.handler.Handler;
import pokegame.npc.NPC;

/**
 *
 * @author Rahul
 */
public class Dialogue {
    
    private Handler handler;
    private NPC npc;
    
    private JFrame frame;
    private JLabel background;
    
    public Dialogue(Handler handler, NPC npc){
        this.handler = handler;
        this.npc = npc;
        
        frame = new JFrame(npc.getName());
        frame.setLayout(null);
        frame.getContentPane().setPreferredSize(new Dimension(450, 300));
        frame.setVisible(true);
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                exit();
            }
        }
        );
        
        background = new JLabel(new ImageIcon(ImageLoader.loadImage("/menu/dialogue/dialogue.png")));
        background.setSize(450, 300);
        
        frame.add(background).setBounds(0, 0, 450, 300);
        frame.pack();
    }
    
    public void exit(){
        handler.getPlayer().setEnabled(true);
        frame.dispose();
    }
}