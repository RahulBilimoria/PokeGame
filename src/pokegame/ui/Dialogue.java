/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.ui;

import pokegame.handler.Handler;
import pokegame.npc.NPC;

/**
 *
 * @author Rahul
 */
public class Dialogue {
    
    private Handler handler;
    private NPC npc;
    
    public Dialogue(Handler handler, NPC npc){
        this.handler = handler;
        this.npc = npc;
        
    }
    
}