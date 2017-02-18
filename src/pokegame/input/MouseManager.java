/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import pokegame.handler.Handler;

/**
 *
 * @author Rahul
 */
public class MouseManager implements MouseListener, MouseMotionListener {

    private Handler h;
    private int xOffset, yOffset;
    private boolean leftPressed, rightPressed;

    public MouseManager(Handler h) {
        this.h = h;
    }

    private void setOffsets() {
        xOffset = (int) ((h.getGameCamera().getXOffset() - (h.getGameCamera().getXOffset() % 32)) / 32);
        yOffset = (int) ((h.getGameCamera().getYOffset() - (h.getGameCamera().getYOffset() % 32)) / 32) - 1;
        if (h.getGameCamera().getYOffset() > 0) {
            yOffset += 1;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = true;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            rightPressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        setOffsets();
        if (e.getButton() == MouseEvent.BUTTON1 && h.getEditWorld()) {
            leftPressed = false;
            if (h.getMapEditor().getLayer() == -1) {
                if (h.getMapEditor().getScriptNumber() == 2) {
                    h.getWorld().setWarpScript(
                            (e.getX() - (e.getX() % 32)) / 32 + xOffset,
                            (e.getY() - (e.getY() % 32)) / 32 + yOffset,
                            h.getMapEditor().getScriptNumber(),
                            h.getMapEditor().getWarpMap(),
                            h.getMapEditor().getWarpX(),
                            h.getMapEditor().getWarpY());
                } else {
                    h.getWorld().setScript(
                            (e.getX() - (e.getX() % 32)) / 32 + xOffset,
                            (e.getY() - (e.getY() % 32)) / 32 + yOffset,
                            h.getMapEditor().getScriptNumber());
                }
            } else {
                h.getWorld().setTile(h.getMapEditor().getSelectedX(),
                        h.getMapEditor().getSelectedY(),
                        (e.getX() - (e.getX() % 32)) / 32 + xOffset,
                        (e.getY() - (e.getY() % 32)) / 32 + yOffset,
                        h.getMapEditor().getSelectedWidth(),
                        h.getMapEditor().getSelectedHeight(),
                        h.getMapEditor().getLayer(),
                        h.getMapEditor().getTileSheet());
            }

        } else if (e.getButton() == MouseEvent.BUTTON3 && h.getEditWorld()) {
            rightPressed = false;
            if (h.getMapEditor().getLayer() == -1) {
                h.getWorld().setScript(
                        (e.getX() - (e.getX() % 32)) / 32 + xOffset,
                        (e.getY() - (e.getY() % 32)) / 32 + yOffset,
                         0);
            } else {
                h.getWorld().removeTile(
                        (e.getX() - (e.getX() % 32)) / 32 + xOffset,
                        (e.getY() - (e.getY() % 32)) / 32 + yOffset,
                        h.getMapEditor().getLayer());
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e
    ) {

    }

    @Override
    public void mouseExited(MouseEvent e
    ) {

    }

    @Override
    public void mouseDragged(MouseEvent e
    ) {
        setOffsets();
        if (leftPressed && h.getEditWorld()) {
            if (h.getMapEditor().getLayer() == -1) {
                if (h.getMapEditor().getScriptNumber() == 2) {
                    h.getWorld().setWarpScript(
                            (e.getX() - (e.getX() % 32)) / 32 + xOffset,
                            (e.getY() - (e.getY() % 32)) / 32 + yOffset,
                            h.getMapEditor().getScriptNumber(),
                            h.getMapEditor().getWarpMap(),
                            h.getMapEditor().getWarpX(),
                            h.getMapEditor().getWarpY());
                } else {
                    h.getWorld().setScript(
                            (e.getX() - (e.getX() % 32)) / 32 + xOffset,
                            (e.getY() - (e.getY() % 32)) / 32 + yOffset,
                            h.getMapEditor().getScriptNumber());
                }
            } else {
                h.getWorld().setTile(h.getMapEditor().getSelectedX(),
                        h.getMapEditor().getSelectedY(),
                        (e.getX() - (e.getX() % 32)) / 32 + xOffset,
                        (e.getY() - (e.getY() % 32)) / 32 + yOffset,
                        h.getMapEditor().getSelectedWidth(),
                        h.getMapEditor().getSelectedHeight(),
                        h.getMapEditor().getLayer(),
                        h.getMapEditor().getTileSheet());
            }

        } else if (rightPressed && h.getEditWorld()) {
            if (h.getMapEditor().getLayer() == -1) {
                h.getWorld().setScript(
                        (e.getX() - (e.getX() % 32)) / 32 + xOffset,
                        (e.getY() - (e.getY() % 32)) / 32 + yOffset,
                        0);
            } else {
                h.getWorld().removeTile(
                        (e.getX() - (e.getX() % 32)) / 32 + xOffset,
                        (e.getY() - (e.getY() % 32)) / 32 + yOffset,
                        h.getMapEditor().getLayer());
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e
    ) {

    }
}
