/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.world.mapeditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import pokegame.handler.Handler;
import pokegame.handler.PropertiesEditorHandler;
import pokegame.pokemon.Pokemon;
import pokegame.pokemon.move.Move;
import pokegame.pokemon.move.Moveset;
import pokegame.utils.Utils;
import pokegame.world.scripts.Spawn;
import pokegame.world.scripts.SpawnList;

/**
 *
 * @author minim_000
 */
public class PropertiesEditor extends Editor {

    private Handler handler;
    private MapEditor mapEditor;
    private PropertiesEditorHandler peh;

    private SpawnList s1, s2, s3;

    private int width, height;
    private int currentSpawnList;

    private JFrame frame;
    private JMenuBar menu;
    private JMenuItem save, close, properties, spawns;
    private JTextArea mapName;
    private JTextArea up, down, left, right;
    private JLabel mapSpawnRateLabel;
    private JTextField mapSpawnRateField;
    private JLabel[] pokemonNameLabel = new JLabel[3],
            pokemonMinLvlLabel = new JLabel[3],
            pokemonMaxLvlLabel = new JLabel[3],
            pokemonHpLabel = new JLabel[3],
            pokemonAttLabel = new JLabel[3],
            pokemonDefLabel = new JLabel[3],
            pokemonSpattLabel = new JLabel[3],
            pokemonSpdefLabel = new JLabel[3],
            pokemonSpeedLabel = new JLabel[3],
            pokemonMoveLabel[] = new JLabel[3][4],
            pokemonSpawnRateLabel = new JLabel[3],
            itemNameLabel = new JLabel[3],
            itemDropRateLabel = new JLabel[3];
    private JTextField[] pokemonNameField = new JTextField[3],
            pokemonMinLvlField = new JTextField[3],
            pokemonMaxLvlField = new JTextField[3],
            pokemonHpField = new JTextField[3],
            pokemonAttField = new JTextField[3],
            pokemonDefField = new JTextField[3],
            pokemonSpattField = new JTextField[3],
            pokemonSpdefField = new JTextField[3],
            pokemonSpeedField = new JTextField[3],
            pokemonMoveField[] = new JTextField[3][4],
            pokemonSpawnRateField = new JTextField[3],
            itemNameField = new JTextField[3],
            itemDropRateField = new JTextField[3];
    private JButton pokemon[] = new JButton[3];
    private JPanel buttonPanel;
    private JComboBox safez;
    private String[] safeOptions;
    private JPanel title, adjMaps, safePanel, savePanel, pokemonPanel[] = new JPanel[3];
    private JButton ok, cancel;

    public PropertiesEditor(Handler handler, MapEditor mapEditor) {
        this.handler = handler;
        this.mapEditor = mapEditor;

        peh = new PropertiesEditorHandler(handler, this);

        frame = new JFrame("Map Editor");
        frame.setAlwaysOnTop(true);
        frame.setLayout(null);

        width = 800;
        height = 500;

        currentSpawnList = 0;

        s1 = handler.getWorld().getMap().getSpawnList(0);
        s2 = handler.getWorld().getMap().getSpawnList(1);
        s3 = handler.getWorld().getMap().getSpawnList(2);

        menu = createMenu();
        adjMaps = createMapsPanel();
        title = createTitlePanel();
        safePanel = createSafePanel();
        savePanel = createSavePanel();
        buttonPanel = createButtonPanel();
        pokemonPanel[0] = createPokemonPanel(0);
        pokemonPanel[1] = createPokemonPanel(1);
        pokemonPanel[2] = createPokemonPanel(2);
        pokemonPanel[0].setVisible(false);
        pokemonPanel[1].setVisible(false);
        pokemonPanel[2].setVisible(false);
        buttonPanel.setVisible(false);

        mapSpawnRateLabel = new JLabel("Map Spawn Rate: ");
        mapSpawnRateField = new JTextField();

        mapSpawnRateLabel.setVisible(false);
        mapSpawnRateField.setVisible(false);

        setBorders();
        frame.setSize(800, 600);

        frame.add(menu).setBounds(0, 0, width, 25);
        frame.add(safePanel).setBounds(240, 100, 140, 50);
        frame.add(savePanel).setBounds(240, 200, 140, 50);
        frame.add(title).setBounds(50, 40, 300, 40);
        frame.add(adjMaps).setBounds(50, 120, 150, 120);

        frame.add(buttonPanel).setBounds(0, 35, buttonPanel.getWidth(), buttonPanel.getHeight());
        frame.add(pokemonPanel[0]).setBounds(25, 75, pokemonPanel[0].getWidth(), pokemonPanel[0].getHeight());
        frame.add(pokemonPanel[1]).setBounds(pokemonPanel[0].getWidth() + 25, 75, pokemonPanel[1].getWidth(), pokemonPanel[1].getHeight());
        frame.add(pokemonPanel[2]).setBounds(pokemonPanel[0].getWidth() + pokemonPanel[1].getWidth() + 25, 75, pokemonPanel[2].getWidth(), pokemonPanel[2].getHeight());
        frame.add(mapSpawnRateLabel).setBounds(250, 525, 150, 25);
        frame.add(mapSpawnRateField).setBounds(400, 525, 150, 25);

        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(handler.getGame().getFrame());
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                exit();
            }
        });
    }

    public void loadPokemon(int i) {
        mapSpawnRateField.setText(s1.getSpawnRate() + "");
        currentSpawnList = i;
        switch (i) {
            case 0:
                loadSpawnList(s1);
                break;
            case 1:
                loadSpawnList(s2);
                break;
            case 2:
                loadSpawnList(s3);
                break;
            default:
                break;
        }
    }

    public void loadSpawnList(SpawnList sl) {
        if (sl != null) {
            loadSpawn(sl.getSpawn(0), 0);
            loadSpawn(sl.getSpawn(1), 1);
            loadSpawn(sl.getSpawn(2), 2);
        }
    }

    public void loadSpawn(Spawn s, int i) {
        if (s != null) {
            pokemonNameField[i].setText(s.getName());
            pokemonMinLvlField[i].setText(s.getMinLevel() + "");
            pokemonMaxLvlField[i].setText(s.getMaxLevel() + "");
            pokemonHpField[i].setText(s.getHp() + "");
            pokemonAttField[i].setText(s.getAtt() + "");
            pokemonDefField[i].setText(s.getDef() + "");
            pokemonSpattField[i].setText(s.getSpatt() + "");
            pokemonSpdefField[i].setText(s.getSpdef() + "");
            pokemonSpeedField[i].setText(s.getSpeed() + "");
            pokemonMoveField[i][0].setText(s.getMoveset().getMoveName(0));
            pokemonMoveField[i][1].setText(s.getMoveset().getMoveName(1));
            pokemonMoveField[i][2].setText(s.getMoveset().getMoveName(2));
            pokemonMoveField[i][3].setText(s.getMoveset().getMoveName(3));
            pokemonSpawnRateField[i].setText(s.getSpawnRate() + "");
            itemNameField[i].setText(s.getItemId() + "");
            itemDropRateField[i].setText(s.getItemSpawnRate() + "");
        }
    }

    private JMenuBar createMenu() {
        JMenuBar b = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu map = new JMenu("Map");
        save = new JMenuItem("Save");
        close = new JMenuItem("Close");
        properties = new JMenuItem("Properties");
        spawns = new JMenuItem("Spawns");
        save.addActionListener(peh);
        close.addActionListener(peh);
        properties.addActionListener(peh);
        spawns.addActionListener(peh);
        file.add(save);
        file.add(close);
        map.add(properties);
        map.add(spawns);
        b.add(file);
        b.add(map);
        return b;
    }

    private JPanel createSafePanel() {
        JPanel p = new JPanel(new GridLayout(2, 1, 5, 5));
        safeOptions = new String[2];
        safeOptions[0] = "Safe Zone";
        safeOptions[1] = "Danger Zone";
        safez = new JComboBox(safeOptions);
        p.add(new JLabel("Map Morality"));
        p.add(safez);
        return p;
    }

    private JPanel createSavePanel() {
        JPanel p = new JPanel(new GridLayout(2, 1, 5, 5));
        ok = new JButton("Ok");
        cancel = new JButton("Cancel");
        ok.addActionListener(peh);
        cancel.addActionListener(peh);
        p.add(ok);
        p.add(cancel);
        return p;
    }

    private JPanel createTitlePanel() {
        JPanel p = new JPanel(new GridLayout(2, 1, 5, 5));
        mapName = new JTextArea(handler.getWorld().getMap().getMapName().replaceAll("_", " "));
        p.add(new JLabel("Map Name"));
        p.add(mapName);
        return p;
    }

    private JPanel createMapsPanel() {
        JPanel p = new JPanel(new GridLayout(4, 2, 5, 5));
        up = new JTextArea(handler.getWorld().getMap().getUp() + "");
        down = new JTextArea(handler.getWorld().getMap().getDown() + "");
        left = new JTextArea(handler.getWorld().getMap().getLeft() + "");
        right = new JTextArea(handler.getWorld().getMap().getRight() + "");
        p.add(new JLabel("Up"));
        p.add(up);
        p.add(new JLabel("Down"));
        p.add(down);
        p.add(new JLabel("Left"));
        p.add(left);
        p.add(new JLabel("Right"));
        p.add(right);
        return p;
    }

    public JPanel createPokemonPanel(int i) {
        JPanel p = new JPanel(null);
        p.setSize((width - 50) / 3, height - 50);
        pokemonNameLabel[i] = new JLabel("Pokemon: ");
        pokemonNameField[i] = new JTextField();
        pokemonMinLvlLabel[i] = new JLabel("Min Lvl: ");
        pokemonMinLvlField[i] = new JTextField();
        pokemonMaxLvlLabel[i] = new JLabel("Max Lvl: ");
        pokemonMaxLvlField[i] = new JTextField();
        pokemonHpLabel[i] = new JLabel("Hp: ");
        pokemonHpField[i] = new JTextField();
        pokemonAttLabel[i] = new JLabel("Att: ");
        pokemonAttField[i] = new JTextField();
        pokemonDefLabel[i] = new JLabel("Def: ");
        pokemonDefField[i] = new JTextField();
        pokemonSpattLabel[i] = new JLabel("SpAtt: ");
        pokemonSpattField[i] = new JTextField();
        pokemonSpdefLabel[i] = new JLabel("SpDef: ");
        pokemonSpdefField[i] = new JTextField();
        pokemonSpeedLabel[i] = new JLabel("Speed: ");
        pokemonSpeedField[i] = new JTextField();
        for (int x = 0; x < 4; x++) {
            pokemonMoveLabel[i][x] = new JLabel("Move " + (x + 1) + ":");
            pokemonMoveField[i][x] = new JTextField();
        }
        pokemonSpawnRateLabel[i] = new JLabel("Spawn Rate:");
        pokemonSpawnRateField[i] = new JTextField();
        itemNameLabel[i] = new JLabel("Item Dropped:");
        itemNameField[i] = new JTextField();
        itemDropRateLabel[i] = new JLabel("Drop Rate:");
        itemDropRateField[i] = new JTextField();
        int w = p.getWidth() / 2;
        p.add(pokemonNameLabel[i]).setBounds(0, 0, w, 25);
        p.add(pokemonNameField[i]).setBounds(w, 0, w, 25);
        p.add(pokemonMinLvlLabel[i]).setBounds(0, 25, w, 25);
        p.add(pokemonMinLvlField[i]).setBounds(w, 25, w, 25);
        p.add(pokemonMaxLvlLabel[i]).setBounds(0, 50, w, 25);
        p.add(pokemonMaxLvlField[i]).setBounds(w, 50, w, 25);
        p.add(pokemonHpLabel[i]).setBounds(0, 75, w, 25);
        p.add(pokemonHpField[i]).setBounds(w, 75, w, 25);
        p.add(pokemonAttLabel[i]).setBounds(0, 100, w, 25);
        p.add(pokemonAttField[i]).setBounds(w, 100, w, 25);
        p.add(pokemonDefLabel[i]).setBounds(0, 125, w, 25);
        p.add(pokemonDefField[i]).setBounds(w, 125, w, 25);
        p.add(pokemonSpattLabel[i]).setBounds(0, 150, w, 25);
        p.add(pokemonSpattField[i]).setBounds(w, 150, w, 25);
        p.add(pokemonSpdefLabel[i]).setBounds(0, 175, w, 25);
        p.add(pokemonSpdefField[i]).setBounds(w, 175, w, 25);
        p.add(pokemonSpeedLabel[i]).setBounds(0, 200, w, 25);
        p.add(pokemonSpeedField[i]).setBounds(w, 200, w, 25);
        for (int x = 0; x < 4; x++) {
            p.add(pokemonMoveLabel[i][x]).setBounds(0, 225 + (x * 25), w, 25);
            p.add(pokemonMoveField[i][x]).setBounds(w, 225 + (x * 25), w, 25);
        }
        p.add(pokemonSpawnRateLabel[i]).setBounds(0, 325, w, 25);
        p.add(pokemonSpawnRateField[i]).setBounds(w, 325, w, 25);
        p.add(itemNameLabel[i]).setBounds(0, 375, w, 25);
        p.add(itemNameField[i]).setBounds(w, 375, w, 25);
        p.add(itemDropRateLabel[i]).setBounds(0, 400, w, 25);
        p.add(itemDropRateField[i]).setBounds(w, 400, w, 25);
        return p;
    }

    public JPanel createButtonPanel() {
        JPanel p = new JPanel(null);
        p.setSize(width, 50);
        p.setOpaque(false);
        for (int x = 0; x < 3; x++) {
            pokemon[x] = new JButton("Spawn List " + (x + 1));
            pokemon[x].addActionListener(peh);
            p.add(pokemon[x]).setBounds(150 + (x * 150) + 10, 0, 150, 25);
        }
        return p;
    }

    private void setBorders() {
        Border border = BorderFactory.createBevelBorder(1);
        mapName.setBorder(border);
        up.setBorder(border);
        down.setBorder(border);
        left.setBorder(border);
        right.setBorder(border);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g = frame.getGraphics();
        g.setColor(Color.GRAY);
        g.drawRect(title.getX() - 25, title.getY() + 15, title.getWidth() + 50, title.getHeight() + 20);
        g.drawRect(adjMaps.getX() - 25, adjMaps.getY() + 15, adjMaps.getWidth() + 50, adjMaps.getHeight() + 20);
    }

    public void showProperties() {
        hideSpawns();
        safePanel.setVisible(true);
        savePanel.setVisible(true);
        title.setVisible(true);
        adjMaps.setVisible(true);
    }

    public void showSpawns() {
        hideProperties();
        loadPokemon(0);
        buttonPanel.setVisible(true);
        pokemonPanel[0].setVisible(true);
        pokemonPanel[1].setVisible(true);
        pokemonPanel[2].setVisible(true);
        mapSpawnRateLabel.setVisible(true);
        mapSpawnRateField.setVisible(true);
    }

    public void hideProperties() {
        safePanel.setVisible(false);
        savePanel.setVisible(false);
        title.setVisible(false);
        adjMaps.setVisible(false);
    }

    public void hideSpawns() {
        buttonPanel.setVisible(false);
        pokemonPanel[0].setVisible(false);
        pokemonPanel[1].setVisible(false);
        pokemonPanel[2].setVisible(false);
        mapSpawnRateLabel.setVisible(false);
        mapSpawnRateField.setVisible(false);
    }

    public void saveSpawnList() {
        s1.setSpawnRate(getFloat(mapSpawnRateField.getText()));
        s2.setSpawnRate(getFloat(mapSpawnRateField.getText()));
        s3.setSpawnRate(getFloat(mapSpawnRateField.getText()));
        switch (currentSpawnList) {
            case 0:
                saveSpawn(s1.getSpawn(0), 0);
                saveSpawn(s1.getSpawn(1), 1);
                saveSpawn(s1.getSpawn(2), 2);
                break;
            case 1:
                saveSpawn(s2.getSpawn(0), 0);
                saveSpawn(s2.getSpawn(1), 1);
                saveSpawn(s2.getSpawn(2), 2);
                break;
            case 2:
                saveSpawn(s3.getSpawn(0), 0);
                saveSpawn(s3.getSpawn(1), 1);
                saveSpawn(s3.getSpawn(2), 2);
                break;
            default:
                break;
        }
    }

    public void saveSpawn(Spawn s, int i) {
        int x = Pokemon.getIdByName(pokemonNameField[i].getText());
        if (x != -1) {
            s.setPokemon_id(x);
            int min = getInt(pokemonMinLvlField[i].getText());
            if (min == 0)
                min = 1;
            int max = getInt(pokemonMaxLvlField[i].getText());
            if (max == 0)
                max = 1;
            s.setMin_lvl(min);
            s.setMax_lvl(max);
            s.setHp(getInt(pokemonHpField[i].getText()));
            s.setAtt(getInt(pokemonAttField[i].getText()));
            s.setDef(getInt(pokemonDefField[i].getText()));
            s.setSpatt(getInt(pokemonSpattField[i].getText()));
            s.setSpdef(getInt(pokemonSpdefField[i].getText()));
            s.setSpeed(getInt(pokemonSpeedField[i].getText()));
            s.setPokemon_spawn_rate(getFloat(pokemonSpawnRateField[i].getText()));
            s.setItem_id(getInt(itemNameField[i].getText())); // this is ID right now need to change input to name
            s.setItem_spawn_rate(getFloat(itemDropRateField[i].getText()));
            Moveset m = new Moveset(new Move(Move.getMoveByName(pokemonMoveField[i][0].getText())), 
                    new Move(Move.getMoveByName(pokemonMoveField[i][1].getText())), 
                    new Move(Move.getMoveByName(pokemonMoveField[i][2].getText())), 
                    new Move(Move.getMoveByName(pokemonMoveField[i][3].getText())));
            s.setMoveset(m);
        } else {
            s.setPokemon_id(-1);
            s.setMin_lvl(-1);
            s.setMax_lvl(-1);
            s.setHp(-1);
            s.setAtt(-1);
            s.setDef(-1);
            s.setSpatt(-1);
            s.setSpdef(-1);
            s.setSpeed(-1);
            s.setPokemon_spawn_rate(-1);
            s.setItem_id(-1); // this is ID right now need to change input to name
            s.setItem_spawn_rate(-1);
            s.setMoveset(new Moveset(null));
        }
    }

    public void saveMapProperties() {
        boolean s;
        s = false;
        if (safez.getSelectedItem().equals(safeOptions[0])) {
            s = true;
        }
        handler.getWorld().saveProperties(mapName.getText(),
                Integer.parseInt(up.getText()),
                Integer.parseInt(down.getText()),
                Integer.parseInt(left.getText()),
                Integer.parseInt(right.getText()),
                s);
    }

    public void saveMap() {
        saveSpawnList();
        saveMapProperties();
        mapEditor.saveSpawns(s1, s2, s3);
    }

    @Override
    public void exit() {
        mapEditor.closeProperties();
        frame.dispose();
    }

    public int getInt(String s) {
        int i;
        try {
            i = Utils.parseInt(s);
        } catch (NumberFormatException e) {
            i = 0;
        }
        return i;
    }

    public float getFloat(String s) {
        float f;
        try {
            f = Utils.parseFloat(s);
        } catch (NumberFormatException e) {
            f = 0;
        }
        return f;
    }

    public JButton getPokemonButton(int i) {
        return pokemon[i];
    }

    public JButton getOk() {
        return ok;
    }

    public JButton getCancel() {
        return cancel;
    }

    public JMenuItem getSave() {
        return save;
    }

    public JMenuItem getClose() {
        return close;
    }

    public JMenuItem getProperties() {
        return properties;
    }

    public JMenuItem getSpawns() {
        return spawns;
    }
}
