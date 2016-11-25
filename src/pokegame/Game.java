/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import pokegame.gfx.Asset;
import pokegame.gfx.GameCamera;
import pokegame.handler.Handler;
import pokegame.input.KeyManager;
import pokegame.menu.GameMenu;
import pokegame.pokemon.Pokemon;
import pokegame.type.Type;
import pokegame.pokemon.move.Move;
import pokegame.pokemon.move.learn.Learnset;
import pokegame.pokemon.nature.Nature;
import pokegame.state.GameState;
import pokegame.tiles.Tile;
import pokegame.world.scripts.Script;

/**
 *
 * @author Rahul
 */
public class Game implements Runnable {

    private JFrame frame;
    private Canvas canvas;
    private GameMenu menu;

    private BufferStrategy bs;
    private Graphics g;

    private Handler handler;
    private GameCamera gameCamera;
    private KeyManager keyManager;

    private GameState state;
    private Thread thread;

    private boolean running = false;

    private String title;
    private int width, height;

    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        keyManager = new KeyManager();

        createDisplay();
    }

    private void createDisplay() {
        frame = new JFrame(title);
        createGameMenu();

        Dimension d = new Dimension(width, height);
        canvas = new Canvas();
        canvas.setPreferredSize(d);
        canvas.setMaximumSize(d);
        canvas.setMinimumSize(d);

        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(menu.getMenu(), BorderLayout.WEST);
        frame.add(canvas);
        frame.pack();
    }

    private void createGameMenu() {
        menu = new GameMenu(100, height);
    }

    public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    public synchronized void stop() {
        if (running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void init() {
        frame.addKeyListener(keyManager);
        canvas.addKeyListener(keyManager);
        menu.addKeyListener(keyManager);
        handler = new Handler(this);
        menu.addHandler(handler);
        Asset.init();
        Tile.init();
        Script.init();
        Type.init();
        Move.init();
        Nature.init();
        Pokemon.init();
        Learnset.init();
        gameCamera = new GameCamera(handler, 0, 0);
        state = new GameState(handler);
    }

    private void tick() {
        keyManager.tick();
        state.tick();
    }

    private void render() {
        bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(2);
            return;
        }
        g = bs.getDrawGraphics();

        g.clearRect(0, 0, width, height);
        state.render(g);

        bs.show();
        g.dispose();
    }

    @Override
    public void run() {
        init();

        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if (delta >= 1) {
                tick();
                render();
                ticks++;
                delta--;
            }

            if (timer >= 1000000000) {
                System.out.println(ticks);
                ticks = 0;
                timer = 0;
            }
        }
        stop();
    }

    public void add(Component c) {
        frame.getLayeredPane().add(c).setBounds(menu.getWidth(), 0, canvas.getWidth(), canvas.getHeight());
    }

    public void remove(Component c) {
        frame.getLayeredPane().remove(c);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public GameCamera getGameCamera() {
        return gameCamera;
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
