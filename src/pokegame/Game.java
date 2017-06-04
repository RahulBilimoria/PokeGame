/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import pokegame.gfx.Asset;
import pokegame.gfx.GameCamera;
import pokegame.handler.Handler;
import pokegame.handler.InputHandler;
import pokegame.input.KeyManager;
import pokegame.input.MouseManager;
import pokegame.item.Item;
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
    private MouseManager mouseManager;
    private InputHandler ih;

    private GameState state;
    private Thread thread;
    
    private JTextArea text;
    private JTextField input;
    private JScrollPane scroll;

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
        frame.setLayout(null);
        createGameMenu();
        
        JPanel p = new JPanel(null);
        p.setOpaque(false);
        p.setSize(width, 125);
        p.setPreferredSize(new Dimension(width, 125));
        p.setMaximumSize(new Dimension(width, 125));
        p.setMinimumSize(new Dimension(width, 125));
        
        text = new JTextArea();
        text.setBackground(Color.black);
        text.setCaretColor(Color.gray);
        text.setForeground(Color.white);
        text.setSize(width, 100);
        text.setWrapStyleWord(true);
        text.setLineWrap(true);
        text.setEditable(false);
        
        scroll = new JScrollPane(text);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        input = new JTextField();
        input.setBackground(Color.black);
        input.setCaretColor(Color.gray);
        input.setForeground(Color.white);
        input.setSize(width, 25);
        p.add(scroll).setBounds(0,0,text.getWidth(), text.getHeight());
        p.add(input).setBounds(0,text.getHeight()-1, input.getWidth(), input.getHeight());

        Dimension d = new Dimension(width, height);
        canvas = new Canvas();
        canvas.setSize(width, height);
        canvas.setPreferredSize(d);
        canvas.setMaximumSize(d);
        canvas.setMinimumSize(d);
        canvas.setBackground(Color.black);

        frame.setSize(width+menu.getWidth()+5, height+153);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getLayeredPane().add(p).setBounds(menu.getWidth(), canvas.getHeight(), p.getWidth(), p.getHeight());
        frame.add(menu.getMenu()).setBounds(0,0,menu.getWidth(), menu.getHeight());
        frame.add(canvas).setBounds(menu.getWidth(), 0, canvas.getWidth(), canvas.getHeight());
    }

    private void createGameMenu() {
        menu = new GameMenu(100, height+153);
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
        handler = new Handler(this);
        
        frame.addKeyListener(keyManager);
        //frame.addMouseListener(mouseManager);
        //frame.addMouseMotionListener(mouseManager);
        canvas.addKeyListener(keyManager);
        menu.addKeyListener(keyManager);
        long time = System.nanoTime();
        Asset.init();
        long a = TimeUnit.MILLISECONDS.convert(System.nanoTime() - time, TimeUnit.NANOSECONDS);
        System.out.println("Asset Load Time: " + a + "ms");
        time = System.nanoTime();
        Tile.init();
        a = TimeUnit.MILLISECONDS.convert(System.nanoTime() - time, TimeUnit.NANOSECONDS);
        System.out.println("Tile Load Time: " + a + "ms");
        time = System.nanoTime();
        Script.init();
        a = TimeUnit.MILLISECONDS.convert(System.nanoTime() - time, TimeUnit.NANOSECONDS);
        System.out.println("Script Load Time: " + a + "ms");
        time = System.nanoTime();
        Type.init();
        a = TimeUnit.MILLISECONDS.convert(System.nanoTime() - time, TimeUnit.NANOSECONDS);
        System.out.println("Type Load Time: " + a + "ms");
        time = System.nanoTime();
        Move.init();
        a = TimeUnit.MILLISECONDS.convert(System.nanoTime() - time, TimeUnit.NANOSECONDS);
        System.out.println("Move Load Time: " + a + "ms");
        time = System.nanoTime();
        Nature.init();
        a = TimeUnit.MILLISECONDS.convert(System.nanoTime() - time, TimeUnit.NANOSECONDS);
        System.out.println("Nature Load Time: " + a + "ms");
        time = System.nanoTime();
        Learnset.init();
        a = TimeUnit.MILLISECONDS.convert(System.nanoTime() - time, TimeUnit.NANOSECONDS);
        System.out.println("Learnset Load Time: " + a + "ms");
        time = System.nanoTime();
        Pokemon.init();
        a = TimeUnit.MILLISECONDS.convert(System.nanoTime() - time, TimeUnit.NANOSECONDS);
        System.out.println("Pokemon Load Time: " + a + "ms");
        time = System.nanoTime();
        Item.init();
        a = TimeUnit.MILLISECONDS.convert(System.nanoTime() - time, TimeUnit.NANOSECONDS);
        System.out.println("Item Load Time: " + a + "ms");
        gameCamera = new GameCamera(handler, 0, 0);
        state = new GameState(handler);
        ih = new InputHandler(handler);
        mouseManager = new MouseManager(handler);
        canvas.addMouseListener(mouseManager);
        canvas.addMouseMotionListener(mouseManager);
        menu.addHandler(handler);
        input.addActionListener(ih);
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
                //FPS
                //System.out.println(ticks);
                ticks = 0;
                timer = 0;
            }
        }
        stop();
    }

    public void add(Component c) {
        frame.getLayeredPane().add(c).setBounds(menu.getWidth(), 0, c.getWidth(), c.getHeight());
    }

    public void remove(Component c) {
        frame.getLayeredPane().remove(c);
    }
    
    public void addText(){
        text.setForeground(Color.white);
        if (input.getText().equals("/mapeditor")){
            handler.getWorld().openMapEditor();
            canvas.requestFocus();
        }
        else if (input.getText().equals("/loc")){
            text.append("Map: " + handler.getWorld().getMapNumber() + "\n");
            text.append("X: " + handler.getWorld().getPlayerX() + "\n");
            text.append("Y: " + handler.getWorld().getPlayerY() + "\n");
        } else {
            text.append("Username: " + input.getText() + "\n");
        }
        text.setCaretPosition(text.getDocument().getLength());    
        input.setText("");
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public JTextField getInput(){
        return input;
    }

    public GameCamera getGameCamera() {
        return gameCamera;
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }
    
    public MouseManager getMouseManager(){
        return mouseManager;
    }
    
    public JFrame getFrame(){
        return frame;
    }

    public Canvas getCanvas() {
        return canvas;
    }
    
    public GameMenu getGameMenu(){
        return menu;
    }
}