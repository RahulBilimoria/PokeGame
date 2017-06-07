/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.menu;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import pokegame.gfx.ImageLoader;
import pokegame.handler.GameMenuHandler;
import pokegame.handler.Handler;
import pokegame.input.KeyManager;

/**
 *
 * @author Rahul
 */
public class GameMenu {

    private Handler handler;
    private JPanel menuPanel;
    private JButton pokemon, bag, buddy, settings, logout;
    private JLabel background;

    private PokemonMenu m;
    private BagMenu bg;
    private BuddyMenu b;
    private SettingsMenu s;
    private int width, height;

    private boolean pMenu, bgMenu, bMenu, sMenu;

    public GameMenu(int width, int height) {
        this.width = width;
        this.height = height;
        pMenu = false;
        bgMenu = false;
        bMenu = false;
        sMenu = false;

        createMenu();
    }

    private void createMenu() {
        menuPanel = new JPanel(null);
        background = new JLabel(new ImageIcon(ImageLoader.loadImage("/menu/menu-back.png")));
        Dimension d = new Dimension(width, height);
        menuPanel.setPreferredSize(d);
        menuPanel.setMaximumSize(d);
        menuPanel.setMinimumSize(d);

        pokemon = newButton("Pokemon");
        bag = newButton("Bag");
        buddy = newButton("Buddy");
        settings = newButton("Settings");
        logout = newButton("Logout");

        menuPanel.add(pokemon).setBounds(5, 10, pokemon.getWidth(), pokemon.getHeight());
        menuPanel.add(bag).setBounds(5, 45, bag.getWidth(), bag.getHeight());
        menuPanel.add(buddy).setBounds(5, 80, buddy.getWidth(), buddy.getHeight());
        menuPanel.add(settings).setBounds(5, 115, settings.getWidth(), settings.getHeight());
        menuPanel.add(logout).setBounds(5, 150, logout.getWidth(), logout.getHeight());
        menuPanel.add(background).setBounds(0, 0, width, height);
    }

    public JButton newButton(String text) {
        JButton b = new JButton(text);
        b.setSize(90, 25);
        b.setForeground(Color.white);
        b.setIcon(new ImageIcon(ImageLoader.loadImage("/menu/button.png")));
        b.setRolloverIcon(new ImageIcon(ImageLoader.loadImage("/menu/button-hover.png")));
        b.setPressedIcon(new ImageIcon(ImageLoader.loadImage("/menu/button-click.png")));
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setBorder(null);
        b.setHorizontalTextPosition(SwingConstants.CENTER);
        return b;
    }

    public void openPokemon() {
        if (pMenu) {
            m.saveData();
            handler.setMovable(true);
            handler.getGame().remove(m.getPanel());
        } else {
            m.updatePokemon();
            m.updateSelected();
            m.update();
            handler.setMovable(false);
            handler.getGame().add(m.getPanel());
            if (bgMenu)
                openBag();
        }
        pMenu = !pMenu;
    }

    public void openBag() {
        if (bgMenu) {
            bg.close();
            handler.getGame().remove(bg.getPanel());
        } else {
            bg.refresh();
            bg.updatePokemonPanel();
            handler.getGame().add(bg.getPanel());
            if (pMenu)
                openPokemon();
        }
        bgMenu = !bgMenu;
    }

    public void openBuddy() {
        handler.setMovable(false);
    }

    public void openSettings() {
        handler.setMovable(false);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public JPanel getMenu() {
        return menuPanel;
    }

    public JButton getPokemon() {
        return pokemon;
    }

    public JButton getBag() {
        return bag;
    }

    public JButton getBuddy() {
        return buddy;
    }

    public JButton getSettings() {
        return settings;
    }

    public JButton getLogout() {
        return logout;
    }
    
    public void savePokemonMenu(){
        m.saveData();
    }
    
    public void updatePokemonMenu(){
        m.updatePokemon();
        m.updateSelected();
        m.update();
    }
    
    public void updateBagPokemon(){
        bg.updatePokemonPanel();
    }

    public void addHandler(Handler handler) {
        this.handler = handler;
        m = new PokemonMenu(handler);
        bg = new BagMenu(handler);
        addGameListener();
    }

    public void addKeyListener(KeyManager keyManager) {
        menuPanel.addKeyListener(keyManager);
        pokemon.addKeyListener(keyManager);
        bag.addKeyListener(keyManager);
        buddy.addKeyListener(keyManager);
        settings.addKeyListener(keyManager);
        logout.addKeyListener(keyManager);
    }

    public void addGameListener() {
        GameMenuHandler h = new GameMenuHandler(this);
        pokemon.addActionListener(h);
        bag.addActionListener(h);
        buddy.addActionListener(h);
        settings.addActionListener(h);
        logout.addActionListener(h);
    }
}
