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
public class StoryCharacter extends NPC{
    
    private int storyId;
    private String speech;
    
    public StoryCharacter(Handler handler, int id, String name, int spriteId,
            int portraitID, int direction, float x, float y, int distanceToCenter, boolean canTurn,
            boolean canMove, boolean isSolid, int storyId, String speech) {
        super(handler, 1, id, name, spriteId, portraitID, direction, x, y, distanceToCenter, canTurn, canMove, isSolid);
        
        this.storyId = storyId;
        this.speech = speech;
    }
    
}
