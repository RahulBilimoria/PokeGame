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
    
    public StoryCharacter(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, 0, true, false);
    }
    
}
