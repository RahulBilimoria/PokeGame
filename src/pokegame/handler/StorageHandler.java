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
import java.awt.event.MouseMotionListener;
import javax.swing.JLabel;
import pokegame.entity.player.Storage;
import pokegame.storage.StorageScreen;

/**
 *
 * @author Rahul
 */
public class StorageHandler implements ActionListener, MouseListener, MouseMotionListener {

    private Storage storage;
    private StorageScreen storageScreen;

    private boolean dragging;

    public StorageHandler(StorageScreen storageScreen, Storage storage) {
        this.storageScreen = storageScreen;
        this.storage = storage;
        dragging = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == storageScreen.getBoxPanel()) {
            storageScreen.setSelectedLocation(e.getX() / (460 / 5), e.getY() / (335 / 5));
            //x * 5 + y to get pokemon location in array WRONG
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (dragging) {
            dragging = false;
            storageScreen.disableDraggedPokemon(e.getX() / (460 / 5), e.getY() / (335 / 5) * 5);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == storageScreen.getBoxPanel()) {
            if (!dragging) {
                dragging = true;
                storageScreen.enableDraggedPokemon(e.getX() / (460 / 5), (e.getY() / (335 / 5)) * 5);
            }
            if (dragging) {
                storageScreen.moveDraggedPokemon(e.getXOnScreen(), e.getYOnScreen());
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!dragging) {
            if (storageScreen.getBoxPanel().contains(e.getPoint())) {
                storageScreen.setHoverLocation(e.getX() / (460 / 5), e.getY() / (335 / 5));
            }
        }
    }
}
