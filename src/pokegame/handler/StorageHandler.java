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
import pokegame.entity.player.Party;
import pokegame.entity.player.Storage;
import pokegame.storage.StorageScreen;

/**
 *
 * @author Rahul
 */
public class StorageHandler implements ActionListener, MouseListener, MouseMotionListener {

    private final int MENUBAR_HEIGHT = 26;

    private Party party;
    private Storage storage;
    private StorageScreen storageScreen;
    private int xBox, yBox, xParty, yParty;

    private boolean boxDragging;
    private boolean partyDragging;

    public StorageHandler(StorageScreen storageScreen, Storage storage, Party party) {
        this.storageScreen = storageScreen;
        this.storage = storage;
        this.party = party;
        boxDragging = false;
        partyDragging = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == storageScreen.getLeftArrow()) {
            storageScreen.changeBox(-1);
        } else if (e.getSource() == storageScreen.getRightArrow()) {
            storageScreen.changeBox(+1);
        } else if (e.getSource() == storageScreen.getDeletePokemon()){
            storageScreen.deletePokemon();
        }
    }

    private void updateBoxPosition(int x, int y) {
        xBox = (x - 24) / (storageScreen.getBoxPanel().getWidth() / 5);
        yBox = (y - storageScreen.getBoxPanel().getY() - MENUBAR_HEIGHT) / (storageScreen.getBoxPanel().getHeight() / 5);
    }

    private void updatePartyPosition(int x, int y) {
        xParty = x;
        yParty = (y - MENUBAR_HEIGHT - storageScreen.getPartyPanel().getY()) / 35;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (withinBox(e.getX(), e.getY())) {
            storageScreen.setBoxSelectedLocation(xBox, yBox);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!boxDragging || !partyDragging) {
            if (withinBox(e.getX(), e.getY())) {
                storageScreen.setBoxSelectedLocation(xBox, yBox);
                if (storage.getId(storage.getCurrentBox(), xBox + yBox * 5) != -1) {
                    boxDragging = true;
                    storageScreen.enableBoxDraggedPokemon(xBox, yBox * 5);
                    storageScreen.moveBoxDraggedPokemon(e.getXOnScreen(), e.getYOnScreen());
                }
            } else if (withinParty(e.getX(), e.getY())) {
                if (party.getPokemon(yParty) != null) {
                    partyDragging = true;
                    storageScreen.enablePartyDraggedPokemon(yParty);
                    storageScreen.movePartyDraggedPokemon(e.getXOnScreen(), e.getYOnScreen());
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (boxDragging) {
            if (withinBox(e.getX(), e.getY())) {
                storageScreen.disableBoxDraggedPokemon(xBox, yBox);
            } else if (withinParty(e.getX(), e.getY())) {
                storageScreen.disableBoxDraggedPokemon(yParty);
            } else {
                storageScreen.disableBoxDraggedPokemon();
            }
        } else if (partyDragging) {
            if (withinBox(e.getX(), e.getY())) {
                storageScreen.disablePartyDraggedPokemon(xBox, yBox);
            } else if (withinParty(e.getX(), e.getY())) {
                storageScreen.disablePartyDraggedPokemon(yParty);
            } else {
                storageScreen.disablePartyDraggedPokemon();
            }
        }
        boxDragging = false;
        partyDragging = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        updateBoxPosition(e.getX(), e.getY());
        updatePartyPosition(e.getX(), e.getY());
        if (boxDragging) {
            storageScreen.moveBoxDraggedPokemon(e.getXOnScreen(), e.getYOnScreen());
        } else if (partyDragging) {
            storageScreen.movePartyDraggedPokemon(e.getXOnScreen(), e.getYOnScreen());
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        updateBoxPosition(e.getX(), e.getY());
        updatePartyPosition(e.getX(), e.getY());
        if (!boxDragging) {
            if (withinBox(e.getX(), e.getY())) {
                storageScreen.enableHover(false);
                storageScreen.setBoxHoverLocation(xBox, yBox);
            }
        }
        if (!partyDragging) {
            if (withinParty(e.getX(), e.getY())) {
                storageScreen.enableHover(true);
                storageScreen.setPartyHoverLocation(yParty);
            }
        }
        if (boxDragging || !withinBox(e.getX(), e.getY())) {
            storageScreen.disableHover(false);
        }
        if (partyDragging || !withinParty(e.getX(), e.getY())) {
            storageScreen.disableHover(true);
        }
    }

    private boolean withinBox(int x, int y) {
        return storageScreen.getBoxPanel().getBounds().contains(x, y - MENUBAR_HEIGHT);
    }

    private boolean withinParty(int x, int y) {
        return storageScreen.getPartyPanel().getBounds().contains(x, y - MENUBAR_HEIGHT);
    }
}
