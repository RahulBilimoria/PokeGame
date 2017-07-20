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
import javax.swing.JLabel;
import pokegame.battle.Battle;
import pokegame.battle.BattleScreen;

/**
 *
 * @author Rahul
 */
public class BattleHandler implements ActionListener, MouseListener {

    private BattleScreen bs;
    private Battle battle;

    public BattleHandler(BattleScreen bs, Battle battle) {
        this.bs = bs;
        this.battle = battle;
    }

    public void disableButtons() {
        bs.disableButtons();
    }

    public void addText(String text) {
        bs.updateBattleHistory(text);
    }

    public void updateSeconds(int seconds) {
        bs.updateSeconds(seconds);
    }

    public void updateEverything() {
        bs.updateEverything();
    }

    public void changePanel(boolean hide) {
        bs.hidePokemon(hide);
    }
    
    public void caughtPokemon(){
        bs.exit();
    }

    public void lose() {
        bs.exit();
    }
    
    public void checkForFainted(){
        battle.checkForFainted();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bs.getMove(0)) {
            battle.startRound(0);
            bs.updateMoveLabels(0);
        } else if (e.getSource() == bs.getMove(1)) {
            battle.startRound(1);
            bs.updateMoveLabels(1);
        } else if (e.getSource() == bs.getMove(2)) {
            battle.startRound(2);
            bs.updateMoveLabels(2);
        } else if (e.getSource() == bs.getMove(3)) {
            battle.startRound(3);
            bs.updateMoveLabels(3);
        } else if (e.getSource() == bs.getBag()) {
            battle.openBag();
        } else if (e.getSource() == bs.getFlee()) {
            bs.exit(); //need flee % chance etc
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == bs.getLabel(0)) {
            battle.changePokemon(0);
            bs.updateMoves();
            if (!battle.getFainted()){
                battle.startRound(-1);
            }
        } else if (e.getSource() == bs.getLabel(1)) {
            battle.changePokemon(1);
            bs.updateMoves();
            if (!battle.getFainted()){
                battle.startRound(-1);
            }
        } else if (e.getSource() == bs.getLabel(2)) {
            battle.changePokemon(2);
            bs.updateMoves();
            if (!battle.getFainted()){
                battle.startRound(-1);
            }
        } else if (e.getSource() == bs.getLabel(3)) {
            battle.changePokemon(3);
            bs.updateMoves();
            if (!battle.getFainted()){
                battle.startRound(-1);
            }
        } else if (e.getSource() == bs.getLabel(4)) {
            battle.changePokemon(4);
            bs.updateMoves();
            if (!battle.getFainted()){
                battle.startRound(-1);
            }
        } else if (e.getSource() == bs.getLabel(5)) {
            battle.changePokemon(5);
            bs.updateMoves();
            if (!battle.getFainted()){
                battle.startRound(-1);
            }
        } else {
            JLabel label = (JLabel)e.getSource();
            battle.useItem(label.getText());
            bs.updateItemLabels(label);
        }
        battle.setFainted(false);
        bs.updateEverything();
    }

    @Override
    public void mousePressed(MouseEvent e) {

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
