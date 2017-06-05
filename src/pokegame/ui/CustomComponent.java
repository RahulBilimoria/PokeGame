/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.ui;

import javax.swing.JLabel;

/**
 *
 * @author Rahul
 */
public class CustomComponent extends JLabel{
    
    private int id;
    
    public CustomComponent(int id, String s){
        super(s);
        this.id = id;
    }
    
    public int getID(){
        return id;
    }
    
    @Override
    public String toString(){
        return id+"";
    }
}
