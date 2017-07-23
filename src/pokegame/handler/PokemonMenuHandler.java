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
import javax.swing.JButton;
import javax.swing.JLabel;
import pokegame.menu.PokemonMenu;

/**
 *
 * @author Rahul
 */
public class PokemonMenuHandler implements ActionListener, MouseListener {

    PokemonMenu m;
    Handler h;

    public PokemonMenuHandler(PokemonMenu m, Handler h) {
        this.m = m;
        this.h = h;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == m.getLearnMoves()) {
            m.hidePanels();
        } else if (e.getSource() == m.getActiveButton()) {
            m.setActive();
        } else if (e.getSource() == m.getClose()) {
            h.getGame().getGameMenu().openPokemon();
        } else if (e.getSource() == m.getMoveButton(0)){
            m.addMove(0);
        } else if (e.getSource() == m.getMoveButton(1)){
            m.addMove(1);
        } else if (e.getSource() == m.getMoveButton(2)){
            m.addMove(2);
        } else if (e.getSource() == m.getMoveButton(3)){
            m.addMove(3);
        } else if (e.getSource() == m.getYes()){
            m.replaceMove();
            m.loadMoves();
        } else if (e.getSource() == m.getCancel()){
            m.cancelReplace();
        } else if (e.getSource() == m.getMoveUp()){
            m.addMoveIndex(-1);
            m.loadLearnableMoves();
        } else if (e.getSource() == m.getMoveDown()) {
            m.addMoveIndex(1);
            m.loadLearnableMoves();
        } else {
            JButton b = (JButton) e.getSource();
            m.addStat(b);
            m.update();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == m.getMove(0)) {
            m.showInfo(0);
        } else if (e.getSource() == m.getMove(1)) {
            m.showInfo(1);
        } else if (e.getSource() == m.getMove(2)) {
            m.showInfo(2);
        } else if (e.getSource() == m.getMove(3)) {
            m.showInfo(3);
        } else if (e.getSource() == m.getMove(4)) {
            m.showInfo(4);
        } else if (e.getSource() == m.getMove(5)) {
            m.showInfo(5);
        } else {
            JLabel label = (JLabel) e.getSource();
            m.saveData();
            m.setSelected(label);
            m.loadLearnableMoves();
            m.update();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() != m.getMove(0)
                && e.getSource() != m.getMove(1)
                && e.getSource() != m.getMove(2)
                && e.getSource() != m.getMove(3)
                && e.getSource() != m.getMove(4)
                && e.getSource() != m.getMove(5)) {
            JLabel label = (JLabel) e.getSource();
            m.hover(label);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        m.unHover();
    }

}
