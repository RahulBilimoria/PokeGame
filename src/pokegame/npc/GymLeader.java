/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.npc;

import pokegame.entity.Trainer;
import pokegame.entity.player.Party;
import pokegame.handler.Handler;

/**
 *
 * @author Rahul
 */
public class GymLeader extends Trainer{
    
    private int badgeId;
    private Party party;
    
    public GymLeader(Handler handler, int id, String name, int spriteId,
            int portraitID, int direction, float x, float y, int distanceToCenter,
            boolean canTurn, boolean canMove, boolean isSolid, int badgeId, Party party) {
        super(handler, 2, id, name, spriteId, portraitID, direction, x, y, distanceToCenter, canTurn, canMove, isSolid);
        this.badgeId = badgeId;
        this.party = party;
    }
    
}
