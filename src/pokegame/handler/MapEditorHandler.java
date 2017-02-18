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
import pokegame.tiles.Tile;
import pokegame.world.mapeditor.MapEditor;

/**
 *
 * @author Rahul
 */
public class MapEditorHandler implements ActionListener, MouseListener, MouseMotionListener{
    
    private Handler h;
    private MapEditor me;
    
    private int mouseX, mouseY;
    private int mouseWidth, mouseHeight;
    
    public MapEditorHandler(Handler h, MapEditor me){
        this.h = h;
        this.me = me;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        me.setScriptsVisible(false);
        me.checkScript();
        if (e.getSource() == me.getGround1()){
            me.setLayer(0);
        } else if (e.getSource() == me.getGround2()){
            me.setLayer(1);
        } else if (e.getSource() == me.getMask1()){
            me.setLayer(2);
        } else if (e.getSource() == me.getMask2()){
            me.setLayer(3);
        } else if (e.getSource() == me.getFringe1()){
            me.setLayer(4);
        } else if (e.getSource() == me.getFringe2()){
            me.setLayer(5);
        } else if (e.getSource() == me.getScript()){
            me.setLayer(-1);
            me.setScriptsVisible(true);
            me.checkScript();
        } else if (e.getSource() == me.getScripts()){
            me.setScriptsVisible(true);
            me.checkScript();
        } else if (e.getSource() == me.getSave()){
            me.saveWorld();
        } else if (e.getSource() == me.getExit()){
            me.exit();
        } else if (e.getSource() == me.getProperties()){
            me.openProperties();
        } else if (e.getSource() == me.getFill()){
            me.fill();
        } else if (e.getSource() == me.getClearLayer()){
            me.clearLayer();
        } else if (e.getSource() == me.getClearScripts()){
            me.clearScripts();
        } else if (e.getSource() == me.getClearAll()){
            me.clearAll();
        } for (int x = 0; x < 7; x++){
            if (e.getSource() == me.getTilesheet(x)){
                me.changeTilesheet(x);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        me.clicked();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        me.clicked();    
        mouseWidth = 0;
        mouseHeight = 0;
        me.released();
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

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseWidth = (e.getX() - (mouseX - mouseX%Tile.TILE_WIDTH)) / Tile.TILE_WIDTH;
        mouseHeight = (e.getY() - (mouseY - mouseY%Tile.TILE_HEIGHT)) / Tile.TILE_HEIGHT;
        me.released();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
    
    public int getMouseX(){
        return mouseX;
    }
    
    public int getMouseY(){
        return mouseY;
    }
    
    public int getWidth(){
        return mouseWidth;
    }
    
    public int getHeight(){
        return mouseHeight;
    }
}
