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
public class QuestCharacter extends NPC{
    
    public QuestCharacter(Handler handler, float x, float y, int width, int height, int distanceToCenter) {
        super(handler, x, y, width, height, distanceToCenter, false, true);
    }
    
}
