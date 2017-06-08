/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import pokegame.menu.BagMenu;

/**
 *
 * @author Rahul
 */
public class BagMenuHandler implements MouseListener, ActionListener{
    
    private Handler h;
    private BagMenu bm;
    
    public BagMenuHandler(BagMenu bm, Handler h){
        this.bm = bm;
        this.h = h;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bm.getNext()){
            bm.changeBag(true);
            bm.setSelectedItem(-1);
            bm.refresh();
        } else if (e.getSource() == bm.getPrevious()){
            bm.changeBag(false);
            bm.setSelectedItem(-1);
            bm.refresh();
        } else if (e.getSource() == bm.getUse()){
            bm.useItem();
        } else if (e.getSource() == bm.getDiscard()){
            bm.discardItem();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        bm.comparePanel(e.getSource());
        bm.comparePokemon(e.getSource());
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
