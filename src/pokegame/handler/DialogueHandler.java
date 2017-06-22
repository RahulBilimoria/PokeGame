/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import pokegame.ui.Dialogue;

/**
 *
 * @author Rahul
 */
public class DialogueHandler implements ActionListener, KeyListener{

    private Dialogue dialogue;
    
    public DialogueHandler(Dialogue dialogue){
        this.dialogue = dialogue;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dialogue.getAccept()){
            dialogue.onAccept();
        } else if (e.getSource() == dialogue.getDecline()){
            dialogue.onDecline();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            dialogue.continueDialogue();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    
}
