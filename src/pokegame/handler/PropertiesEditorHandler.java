/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import pokegame.world.mapeditor.PropertiesEditor;

/**
 *
 * @author minim_000
 */
public class PropertiesEditorHandler implements ActionListener{

    private Handler handler;
    private PropertiesEditor pe;
    
    public PropertiesEditorHandler(Handler handler, PropertiesEditor pe){
        this.handler = handler;
        this.pe = pe;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pe.getOk() || e.getSource() == pe.getSave()){
            pe.saveMap();
            pe.exit();
        } else if (e.getSource() == pe.getCancel() || e.getSource() == pe.getClose()){
            pe.exit();
        } else if (e.getSource() == pe.getProperties()){
            pe.showProperties();
        } else if (e.getSource() == pe.getSpawns()){
            pe.showSpawns();
        } else if (e.getSource() == pe.getPokemonButton(0)){
            pe.saveSpawnList();
            pe.loadPokemon(0);
        } else if (e.getSource() == pe.getPokemonButton(1)){
            pe.saveSpawnList();
            pe.loadPokemon(1);
        } else if (e.getSource() == pe.getPokemonButton(2)){
            pe.saveSpawnList();
            pe.loadPokemon(2);
        }
    }
    
}
