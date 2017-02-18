/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.world.mapeditor;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.ScrollPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import pokegame.entity.Person;
import pokegame.gfx.Asset;
import pokegame.gfx.SpriteSheet;
import pokegame.handler.Handler;
import pokegame.handler.MapEditorHandler;
import pokegame.tiles.Tile;
import pokegame.utils.Utils;
import pokegame.world.World;
import pokegame.world.scripts.SpawnList;

/**
 *
 * @author Rahul
 */
public class MapEditor {

    private Handler h;
    private MapEditorHandler meh;
    private World w;
    private PropertiesEditor pe;
    private SpriteSheet sheet;
    private BufferStrategy bs;

    private JFrame frame;
    private JPanel options, tiles;
    private JMenuBar menu;
    private Canvas canvas;
    private ScrollPane scroll;
    private JRadioButton ground1, ground2, mask1, mask2, fringe1, fringe2, script;
    private JMenuItem save, exit, properties, fill, clearLayer, clearScripts, clearAll,
            ts[] = new JMenuItem[7];
    private JComboBox scripts;
    private JTextField warpInMap, warpInX, warpInY;
    private JLabel wMapLabel, wXLabel, wYLabel;

    private int width, height;
    private int selectedX, selectedY, selectedWidth, selectedHeight;
    private int yOffset;
    private int layer;
    private int scriptNumber;
    private int tilesheet;

    public MapEditor(Handler h, World w) {
        this.h = h;
        this.w = w;
        h.getPlayer().setSpeed(8.0f);
        init();
        meh = new MapEditorHandler(h, this);

        frame = new JFrame("Map Editor");

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                exit();
            }
        });

        canvas = createCanvas();
        tiles = createTiles();
        options = createOptions();
        menu = createMenu();
        scroll = createScrollPane();

        tiles.add(scroll);

        frame.setSize(options.getWidth() + scroll.getWidth() + 27, height);

        frame.add(options, BorderLayout.WEST);
        frame.add(scroll);
        frame.setJMenuBar(menu);
        
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void init() {
        sheet = Asset.tileSheets[0];
        yOffset = 0;
        scriptNumber = 0;
        width = 32 * (sheet.getWidth() / 32);
        height = width * 3;
        selectedX = -1;
        selectedY = -1;
        selectedWidth = -1;
        selectedHeight = -1;
        layer = 0;
        tilesheet = 0;
    }

    public void tick() {
        yOffset = (int) scroll.getScrollPosition().getY();
        if (yOffset + height > sheet.getHeight()) {
            yOffset = sheet.getHeight() - height;
        }
    }

    public void render(Graphics g) {
        bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(2);
            return;
        }
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g.drawImage(Asset.tileSheets[tilesheet].crop(0, yOffset, width, height), 0, yOffset, null);
        if (selectedX != -1 && selectedY != -1) {
            g.setColor(Color.RED);
            g.drawRect(selectedX * Tile.TILE_WIDTH, selectedY * Tile.TILE_HEIGHT,
                    selectedWidth * Tile.TILE_WIDTH - 1, selectedHeight * Tile.TILE_HEIGHT - 1);
        }
        bs.show();
        g.dispose();
    }

    private JPanel createTiles() {
        JPanel t = new JPanel();
        t.addMouseListener(meh);
        t.addMouseMotionListener(meh);
        Dimension d = new Dimension(Asset.tileSheets[tilesheet].getWidth() + 21, Asset.tileSheets[tilesheet].getHeight());
        t.setSize(d);
        t.setPreferredSize(d);
        t.setMaximumSize(d);
        t.setMinimumSize(d);
        t.setFocusable(false);
        return t;
    }

    private Canvas createCanvas() {
        Canvas c = new Canvas();
        c.addMouseListener(meh);
        c.addMouseMotionListener(meh);
        Dimension d = new Dimension(Asset.tileSheets[0].getWidth(), Asset.tileSheets[0].getHeight());
        //c.setSize(d);
        c.setPreferredSize(d);
        c.setMaximumSize(d);
        c.setMinimumSize(d);
        c.setFocusable(false);
        return c;
    }

    private ScrollPane createScrollPane() {
        ScrollPane sp = new ScrollPane();
        Dimension d = new Dimension(Asset.tileSheets[0].getWidth(), Asset.tileSheets[0].getHeight());
        sp.setSize(d);
        sp.setPreferredSize(d);
        sp.setMaximumSize(d);
        sp.setMinimumSize(d);
        sp.add(canvas);
        sp.getVAdjustable().setUnitIncrement(32);
        return sp;
    }

    private JMenuBar createMenu() {
        JMenuBar mb = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu map = new JMenu("Map");
        JMenu tilesheets = new JMenu("Tilesheet");
        properties = new JMenuItem("Properties");
        save = new JMenuItem("Save");
        exit = new JMenuItem("Exit");
        fill = new JMenuItem("Fill");
        clearLayer = new JMenuItem("Clear Layer");
        clearScripts = new JMenuItem("Clear Scripts");
        clearAll = new JMenuItem("Clear All");
        for (int x = 0; x < 7; x++) {
            ts[x] = new JMenuItem("Tilesheet " + x);
            tilesheets.add(ts[x]);
            ts[x].addActionListener(meh);
        }
        mb.add(file);
        mb.add(map);
        mb.add(tilesheets);
        file.add(save);
        file.add(exit);
        map.add(properties);
        map.add(fill);
        map.add(clearLayer);
        map.add(clearScripts);
        map.add(clearAll);
        properties.addActionListener(meh);
        save.addActionListener(meh);
        exit.addActionListener(meh);
        fill.addActionListener(meh);
        clearLayer.addActionListener(meh);
        clearScripts.addActionListener(meh);
        clearAll.addActionListener(meh);
        return mb;
    }

    private JPanel createOptions() {
        JPanel p = new JPanel();
        Dimension d = new Dimension(100, height);
        p.setSize(d);
        p.setPreferredSize(d);
        p.setMaximumSize(d);
        p.setMinimumSize(d);
        p.setLayout(null);
        ButtonGroup group = new ButtonGroup();
        ground1 = new JRadioButton("Ground 1");
        ground1.setSelected(true);
        ground1.addActionListener(meh);
        ground2 = new JRadioButton("Ground 2");
        ground2.addActionListener(meh);
        mask1 = new JRadioButton("Mask 1");
        mask1.addActionListener(meh);
        mask2 = new JRadioButton("Mask 2");
        mask2.addActionListener(meh);
        fringe1 = new JRadioButton("Fringe 1");
        fringe1.addActionListener(meh);
        fringe2 = new JRadioButton("Fringe 2");
        fringe2.addActionListener(meh);
        script = new JRadioButton("Script");
        script.addActionListener(meh);

        String scriptsStrings[] = {"Block", "Warp", "Spawns 1",
            "Spawns 2", "Spawns 3", "Heal", "Shop"};

        scripts = new JComboBox(scriptsStrings);
        scripts.addActionListener(meh);
        scripts.setVisible(false);

        wMapLabel = new JLabel("Map Number");
        wXLabel = new JLabel("X Coord");
        wYLabel = new JLabel("Y Coord");

        warpInMap = new JTextField();
        warpInX = new JTextField();
        warpInY = new JTextField();

        warpInMap.setVisible(false);
        warpInX.setVisible(false);
        warpInY.setVisible(false);
        wMapLabel.setVisible(false);
        wXLabel.setVisible(false);
        wYLabel.setVisible(false);

        group.add(ground1);
        group.add(ground2);
        group.add(mask1);
        group.add(mask2);
        group.add(fringe1);
        group.add(fringe2);
        group.add(script);
        p.add(ground1).setBounds(0, 0, 100, 25);
        p.add(ground2).setBounds(0, 25, 100, 25);
        p.add(mask1).setBounds(0, 50, 100, 25);
        p.add(mask2).setBounds(0, 75, 100, 25);
        p.add(fringe1).setBounds(0, 100, 100, 25);
        p.add(fringe2).setBounds(0, 125, 100, 25);
        p.add(script).setBounds(0, 150, 100, 25);
        p.add(scripts).setBounds(0, 175, 100, 25);
        p.add(wMapLabel).setBounds(0, 200, 100, 25);
        p.add(warpInMap).setBounds(0, 225, 100, 25);
        p.add(wXLabel).setBounds(0, 250, 100, 25);
        p.add(warpInX).setBounds(0, 275, 100, 25);
        p.add(wYLabel).setBounds(0, 300, 100, 25);
        p.add(warpInY).setBounds(0, 325, 100, 25);
        return p;
    }

    public void openProperties() {
        if (pe == null) {
            pe = new PropertiesEditor(h, this);
        }
    }

    public void closeProperties() {
        pe = null;
    }

    public void fill() {
        if (selectedX != -1 && selectedY != -1) {
            w.fill(selectedX, selectedY, layer, tilesheet);
        }
    }

    public void clearLayer() {
        w.clearLayer(layer);
    }

    public void clearScripts() {
        w.clearScripts();
    }

    public void clearAll() {
        w.clearAll();
    }
    
    public void changeTilesheet(int x){
        tilesheet = x;
        yOffset = 0;
        scroll.getVAdjustable().setValue(0);
    }

    public void clicked() {
        selectedX = (meh.getMouseX() - meh.getMouseX() % Tile.TILE_WIDTH) / Tile.TILE_WIDTH;
        selectedY = (meh.getMouseY() - meh.getMouseY() % Tile.TILE_HEIGHT) / Tile.TILE_HEIGHT;
    }

    public void released() {
        selectedWidth = meh.getWidth() + 1;
        selectedHeight = meh.getHeight() + 1;
        if (selectedX + selectedWidth > 8) {
            selectedWidth = 8 - selectedX;
        } else if (selectedWidth < 0) {
            selectedWidth = 0;
        }
        if (selectedY + selectedHeight > 400) {
            selectedHeight = 400 - selectedY;
        } else if (selectedHeight < 0) {
            selectedHeight = 0;
        }
    }

    public int getSelectedX() {
        return selectedX;
    }

    public int getSelectedY() {
        return selectedY;
    }

    public int getSelectedWidth() {
        return selectedWidth;
    }

    public int getSelectedHeight() {
        return selectedHeight;
    }

    public int getLayer() {
        return layer;
    }

    public JRadioButton getGround1() {
        return ground1;
    }

    public JRadioButton getGround2() {
        return ground2;
    }

    public JRadioButton getMask1() {
        return mask1;
    }

    public JRadioButton getMask2() {
        return mask2;
    }

    public JRadioButton getFringe1() {
        return fringe1;
    }

    public JRadioButton getFringe2() {
        return fringe2;
    }

    public JRadioButton getScript() {
        return script;
    }

    public JComboBox getScripts() {
        return scripts;
    }

    public JMenuItem getSave() {
        return save;
    }

    public JMenuItem getExit() {
        return exit;
    }

    public JMenuItem getProperties() {
        return properties;
    }

    public JMenuItem getFill() {
        return fill;
    }

    public JMenuItem getClearLayer() {
        return clearLayer;
    }

    public JMenuItem getClearScripts() {
        return clearScripts;
    }

    public JMenuItem getClearAll() {
        return clearAll;
    }
    
    public JMenuItem getTilesheet(int x){
        return ts[x];
    }
    
    public int getTileSheet(){
        return tilesheet;
    }

    public int getWarpMap() {
        try {
            return Utils.parseInt(warpInMap.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public int getWarpX() {
        try {
            return Utils.parseInt(warpInX.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public int getWarpY() {
        try {
            return Utils.parseInt(warpInY.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public void setScriptNumber(int scriptNumber) {
        this.scriptNumber = scriptNumber;
    }

    public int getScriptNumber() {
        return scriptNumber;
    }

    public void setScriptsVisible(boolean visible) {
        scripts.setVisible(visible);
    }

    public void checkScript() {
        int i = scripts.getSelectedIndex();
        String s = (String) scripts.getSelectedItem();
        if (s.toLowerCase().equals("warp")) {
            warpInMap.setVisible(true);
            warpInX.setVisible(true);
            warpInY.setVisible(true);
            wMapLabel.setVisible(true);
            wXLabel.setVisible(true);
            wYLabel.setVisible(true);
        } else {
            warpInMap.setVisible(false);
            warpInX.setVisible(false);
            warpInY.setVisible(false);
            wMapLabel.setVisible(false);
            wXLabel.setVisible(false);
            wYLabel.setVisible(false);
        }
        scriptNumber = i + 1;
    }

    public void saveWorld() {
        w.saveWorld();
    }

    public void saveSpawns(SpawnList s1, SpawnList s2, SpawnList s3) {
        w.setSpawns(s1, s2, s3);
    }

    public void exit() {
        w.reload();
        if (pe != null) {
            pe.exit();
        }
        w.closeMapEditor();
        try {
            Thread.sleep(100);
        } catch (Exception e) {
        }
        h.getPlayer().setSpeed(Person.DEFAULT_SPEED);
        frame.dispose();
    }
}
