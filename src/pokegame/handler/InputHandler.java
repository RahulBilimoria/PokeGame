/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Rahul
 */
public class InputHandler implements ActionListener{
    
    Handler h;
    
    public InputHandler(Handler h){
        this.h = h;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == h.getGame().getInput()){
            h.getGame().addText();
        }
    }
    
}
