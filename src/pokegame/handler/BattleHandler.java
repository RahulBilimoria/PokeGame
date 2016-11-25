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

    public void win() {
        battle.addExp();
        bs.exit();
    }
    
    public void caughtPokemon(){
        bs.exit();
    }

    public void lose() {
        bs.exit();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bs.getMove1()) {
            battle.damage(0);
            battle.useMove(0);
            bs.updateMoveLabels(bs.getMove1(), 0);
        } else if (e.getSource() == bs.getMove2()) {
            battle.damage(1);
            battle.useMove(1);
            bs.updateMoveLabels(bs.getMove2(), 1);
        } else if (e.getSource() == bs.getMove3()) {
            battle.damage(2);
            battle.useMove(2);
            bs.updateMoveLabels(bs.getMove3(), 2);
        } else if (e.getSource() == bs.getMove4()) {
            battle.damage(3);
            battle.useMove(3);
            bs.updateMoveLabels(bs.getMove4(), 3);
        } else if (e.getSource() == bs.getBag()) {
            battle.openBag();
        } else if (e.getSource() == bs.getFlee()) {
            bs.exit(); //need flee % chance etc
        }
        bs.updateEverything();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == bs.getLabel(0)) {
            battle.changePokemon(0);
            bs.updateMoves();
        } else if (e.getSource() == bs.getLabel(1)) {
            battle.changePokemon(1);
            bs.updateMoves();
        } else if (e.getSource() == bs.getLabel(2)) {
            battle.changePokemon(2);
            bs.updateMoves();
        } else if (e.getSource() == bs.getLabel(3)) {
            battle.changePokemon(3);
            bs.updateMoves();
        } else if (e.getSource() == bs.getLabel(4)) {
            battle.changePokemon(4);
            bs.updateMoves();
        } else if (e.getSource() == bs.getLabel(5)) {
            battle.changePokemon(5);
            bs.updateMoves();
        } else {
            JLabel label = (JLabel)e.getSource();
            battle.useItem(label.getText());
            bs.updateItemLabels(label);
        }
        if (!battle.getFainted() && battle.getItemUsed()) { //only attacks pokemon that u swap in if main poke
            battle.damage(-1);      // is still alive
            battle.setItemUsed(false);
        }
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
