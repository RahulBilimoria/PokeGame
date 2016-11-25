/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.world.scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author minim_000
 */
public class SpawnList extends Script {

    private Spawn spawn1, spawn2, spawn3;
    private float spawnRate;

    public SpawnList(float spawnRate, int spawn_number, Spawn spawn1, Spawn spawn2, Spawn spawn3) {
        super(spawn_number);
        this.spawnRate = spawnRate;
        this.spawn1 = spawn1;
        this.spawn2 = spawn2;
        this.spawn3 = spawn3;
    }

    public Spawn getSpawn() {
        while (true) {
            if (Math.random() < spawn1.getSpawnRate()) {
                return spawn1;
            } else if (Math.random() < spawn2.getSpawnRate()) {
                return spawn2;
            } else if (Math.random() < spawn3.getSpawnRate()) {
                return spawn3;
            }
        }
    }
    
    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(Color.yellow);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        g.drawString("S", x + 8, y + 16);
    }

    public float getSpawnRate() {
        return spawnRate;
    }
}
