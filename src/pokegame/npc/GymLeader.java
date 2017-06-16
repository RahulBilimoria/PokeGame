/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.npc;

import pokegame.handler.Handler;

/**
 *
 * @author Rahul
 */
public class GymLeader extends NPC{
    
    public GymLeader(Handler handler, float x, float y) {
        super(handler, x, y, 0, true, false);
    }
    
}
