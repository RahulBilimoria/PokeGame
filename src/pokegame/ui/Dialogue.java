/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import pokegame.entity.player.Player;
import pokegame.gfx.Asset;
import pokegame.gfx.ImageLoader;
import pokegame.handler.DialogueHandler;
import pokegame.handler.Handler;
import pokegame.npc.NPC;
import pokegame.npc.QuestCharacter;

/**
 *
 * @author Rahul
 */
public class Dialogue {
    
    private Handler handler;
    private NPC npc;
    private Player player;
    private QuestCharacter questNpc;
    
    private JFrame frame;
    private JLabel background;
    private JLabel playerPortrait, playerPortraitBackground, npcPortrait, npcPortraitBackground;
    private JLabel playerName, npcName;
    private JLabel messages;
    private JButton accept, decline;
    
    private boolean done;
    
    private DialogueHandler dh;
    
    public Dialogue(Handler handler, NPC npc, Player player){
        this.handler = handler;
        this.npc = npc;
        this.player = player;
        
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
        
        dh = new DialogueHandler(this);
        frame.addKeyListener(dh);
        done = false;
        
        accept = new JButton("Accept");
        decline = new JButton("Refuse");
        
        accept.setVisible(false);
        decline.setVisible(false);
        
        accept.addActionListener(dh);
        decline.addActionListener(dh);
        
        int x = player.getPortraitID() % 8;
        int y = (int)(player.getPortraitID() / 8);
        playerPortrait = new JLabel(new ImageIcon(Asset.portraits.getImage(x, y)));
        playerPortraitBackground = new JLabel(new ImageIcon(Asset.portraitBackgrounds.getImage(1, 0)));
        
        x = npc.getPortraitID() % 8;
        y = (int)(npc.getPortraitID() / 8);
        npcPortrait = new JLabel(new ImageIcon(Asset.portraits.getImage(x, y)));
        npcPortraitBackground = new JLabel(new ImageIcon(Asset.portraitBackgrounds.getImage(1, 0)));
        
        Font myFont = new Font("Dialog", Font.BOLD, 16);
        
        playerName = new JLabel("Username", SwingConstants.CENTER);
        playerName.setFont(myFont);
        playerName.setForeground(Color.white);
        npcName = new JLabel(npc.getName(), SwingConstants.CENTER);
        npcName.setFont(myFont);
        npcName.setForeground(Color.white);
        messages = new JLabel("A");
        messages.setMaximumSize(new Dimension(315,90));
        messages.setFont(myFont);
        messages.setForeground(Color.white);
        if (npc.getType() == 0){
            questNpc = (QuestCharacter) npc;
            messages.setText("<html>" + questNpc.getNextMessage() + "</html>");
        }
        
        background = new JLabel(new ImageIcon(ImageLoader.loadImage("/menu/dialogue/dialogue.png")));
        background.setSize(450, 300);
        
        frame.add(accept).setBounds(350,210,75,30);
        frame.add(decline).setBounds(350,240,75,30);
        frame.add(messages).setBounds(15,170,315,90);
        frame.add(playerName).setBounds(165, 25, 120, 50);
        frame.add(npcName).setBounds(165, 115, 120, 50);
        frame.add(playerPortrait).setBounds(16,-11,128,192);
        frame.add(npcPortrait).setBounds(306,-11,128,192);
        frame.add(background).setBounds(0, 0, 450, 300);
        frame.add(npcPortraitBackground).setBounds(294,0,146,192);
        frame.add(playerPortraitBackground).setBounds(10,0,146,192);
        frame.pack();
    }
    
    public void continueDialogue(){
        if (done) return;
        if (questNpc != null){
            String s = questNpc.getNextMessage();
            if (s.equals("done")){
                done = true;
                showDecisionScreen();
            } else {
                messages.setText("<html>" + s + "</html>");
            }
        }
    }
    
    public void showDecisionScreen(){
        accept.setVisible(true);
        decline.setVisible(true);
    }
    
    public void onAccept(){
        player.addQuest(questNpc.getQuest());
        exit();
    }
    
    public void onDecline(){
        questNpc.resetDialogue();
        exit();
    }
    
    public JButton getAccept(){
        return accept;
    }
    
    public JButton getDecline(){
        return decline;
    }
    
    public void exit(){
        handler.getPlayer().setEnabled(true);
        frame.dispose();
    }
}