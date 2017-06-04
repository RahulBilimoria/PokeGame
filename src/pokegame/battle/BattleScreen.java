/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.battle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import pokegame.entity.player.Bag;
import pokegame.gfx.ImageLoader;
import pokegame.handler.BattleHandler;
import pokegame.pokemon.move.Move;

/**
 *
 * @author Rahul
 */
public class BattleScreen {

    private Battle battle;
    private final int WIDTH = 800,
            HEIGHT = 600;

    private JFrame frame;
    private JTextArea battleHistory;
    private JButton move1, move2, move3, move4, bag, flee;
    private JPanel movePanel, pokemonPanel, allyPokemon, enemyPokemon, bagPanel, expPanel;
    private JLabel[] pkmn = new JLabel[6];
    private JLabel[] items = new JLabel[30];
    private JProgressBar expBar;
    private JLabel background, expRemaining, secondsLeft,
            allyPokemonName, allyPokemonLevel, allyPokemonHp, ally,
            enemyPokemonName, enemyPokemonLevel, enemyPokemonHp, enemy;

    public BattleScreen(Battle battle) {
        this.battle = battle;

        frame = new JFrame("Battle Encountered");
        frame.setSize(WIDTH, HEIGHT);
        frame.setLayout(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                exit();
            }
        }
        );

        JPanel one = new JPanel();
        JPanel two = new JPanel();
        move1 = newMoveButton(0);
        move2 = newMoveButton(1);
        move3 = newMoveButton(2);
        move4 = newMoveButton(3);
        bag = newOtherButton("Bag");
        flee = newOtherButton("Flee");
        one.add(move1);
        one.add(move2);
        one.add(move3);
        one.add(move4);
        two.add(bag);
        two.add(flee);
        one.setOpaque(false);
        two.setOpaque(false);

        battleHistory = new JTextArea();
        battleHistory.setSize(WIDTH, 212 - 30);
        battleHistory.setEditable(false);
        JScrollPane p = new JScrollPane(battleHistory);
        p.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        battleHistory.setBackground(Color.black);
        battleHistory.setForeground(Color.cyan);
        battleHistory.setText("----------------------------------------------------------------- Round: 0");

        BattleHandler bh = new BattleHandler(this, battle);
        move1.addActionListener(bh);
        move2.addActionListener(bh);
        move3.addActionListener(bh);
        move4.addActionListener(bh);
        bag.addActionListener(bh);
        flee.addActionListener(bh);
        battle.addBattleHandler(bh);

        secondsLeft = new JLabel("0");

        expRemaining = new JLabel("Exp to Level: " + battle.getPokemon().getExp() + " / " + battle.getPokemon().getMaxExp());
        expBar = new JProgressBar(0, battle.getPokemon().getMaxExp());
        expBar.setValue(battle.getPokemon().getExp());
        expPanel = new JPanel(new GridLayout(2, 1));
        expPanel.setSize(WIDTH, 30);
        expRemaining.setForeground(Color.white);
        expPanel.setBackground(Color.black);
        expPanel.add(expRemaining);
        expPanel.add(expBar);

        background = new JLabel(new ImageIcon(ImageLoader.loadImage("/battle/background/grass6.png")));

        ally = new JLabel(new ImageIcon(battle.getPokemon().getBack()));
        allyPokemonName = new JLabel(battle.getPokemon().getName());
        allyPokemonLevel = new JLabel("Level: " + battle.getPokemon().getLevel());
        allyPokemonHp = new JLabel("Hp: " + battle.getPokemon().getHp() + " / "
                + battle.getPokemon().getMaxHp());

        enemy = new JLabel(new ImageIcon(battle.getEnemy().getFront()));
        enemyPokemonName = new JLabel(battle.getEnemy().getName());
        enemyPokemonLevel = new JLabel("Level: " + battle.getEnemy().getLevel() + "");
        enemyPokemonHp = new JLabel("Hp: " + battle.getEnemy().getHp() + " / "
                + battle.getEnemy().getMaxHp());

        allyPokemon = new JPanel(new GridLayout(4, 1, 0, 0));
        allyPokemon.add(allyPokemonName);
        allyPokemon.add(allyPokemonLevel);
        allyPokemon.add(allyPokemonHp);
        allyPokemon.setOpaque(false);

        enemyPokemon = new JPanel(new GridLayout(4, 1, 0, 0));
        enemyPokemon.add(enemyPokemonName);
        enemyPokemon.add(enemyPokemonLevel);
        enemyPokemon.add(enemyPokemonHp);
        enemyPokemon.setOpaque(false);

        JLabel bar = new JLabel(new ImageIcon(ImageLoader.loadImage("/battle/bar/tab.png")));
        movePanel = new JPanel(new BorderLayout());
        movePanel.setSize(WIDTH, 40);
        movePanel.add(one, BorderLayout.WEST);
        movePanel.add(two, BorderLayout.EAST);
        movePanel.setOpaque(false);

        bagPanel = new JPanel(new GridLayout(30, 1));
        bagPanel.setBackground(Color.black);
        bagPanel.setSize(WIDTH - background.getIcon().getIconWidth(), background.getIcon().getIconHeight());
        bagPanel.setVisible(false);
        addItems(bagPanel, bh);

        pokemonPanel = new JPanel(new GridLayout(3, 2));
        pokemonPanel.setBackground(Color.black);
        pokemonPanel.setSize(WIDTH - background.getIcon().getIconWidth(), background.getIcon().getIconHeight());
        pokemonPanel.setVisible(true);
        addPokemon(pokemonPanel, bh);

        System.out.println(pokemonPanel.getWidth());

        frame.add(ally).setBounds(100, 200, 100, 100);
        frame.add(allyPokemon).setBounds(180, 170, 100, 80);
        frame.add(enemy).setBounds(440, 80, 100, 100);
        frame.add(enemyPokemon).setBounds(410, 20, 100, 80);
        frame.add(background).setBounds(0, 0, background.getIcon().getIconWidth(), background.getIcon().getIconHeight());
        frame.add(movePanel).setBounds(0, background.getIcon().getIconHeight(), movePanel.getWidth(), movePanel.getHeight());
        frame.add(bar).setBounds(0, background.getIcon().getIconHeight(), movePanel.getWidth(), movePanel.getHeight());
        frame.add(pokemonPanel).setBounds(background.getIcon().getIconWidth(), 0, pokemonPanel.getWidth(), pokemonPanel.getHeight());
        frame.add(bagPanel).setBounds(background.getIcon().getIconWidth(), 0, bagPanel.getWidth(), bagPanel.getHeight());
        frame.add(expPanel).setBounds(0, movePanel.getHeight() + background.getHeight(), expPanel.getWidth(), expPanel.getHeight());
        frame.add(p).setBounds(0, movePanel.getHeight() + background.getHeight() + expPanel.getHeight(), battleHistory.getWidth() - 5, battleHistory.getHeight());
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setFocusable(true);
    }

    public JButton newOtherButton(String name) {
        JButton b = new JButton(name);
        b.setSize(75, 30);
        b.setForeground(Color.white);
        b.setIcon(new ImageIcon(ImageLoader.loadImage("/battle/move/other-button.png")));
        b.setRolloverIcon(new ImageIcon(ImageLoader.loadImage("/battle/move/other-hover.png")));
        b.setPressedIcon(new ImageIcon(ImageLoader.loadImage("/battle/move/other-click.png")));
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setBorder(null);
        b.setHorizontalTextPosition(SwingConstants.CENTER);
        return b;
    }

    public JButton newMoveButton(int id) {
        JButton b = new JButton();
        b.setSize(150, 30);
        b.setForeground(Color.white);
        b.setIcon(new ImageIcon(ImageLoader.loadImage("/battle/move/move-button.png")));
        b.setRolloverIcon(new ImageIcon(ImageLoader.loadImage("/battle/move/move-hover.png")));
        b.setPressedIcon(new ImageIcon(ImageLoader.loadImage("/battle/move/move-click.png")));
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setBorder(null);
        b.setHorizontalTextPosition(SwingConstants.CENTER);
        Move m = battle.getPokemon().getMoveset().getMove(id);
        String s = battle.getMoveName(id);
        if (s.equals("")) {
            b.setText("------------");
            b.setEnabled(false);
        } else {
            b.setText(s + " " + m.getPP() + "/" + m.getMaxPP());
            if (m.getPP() <= 0) {
                b.setEnabled(false);
            }
        }
        return b;
    }

    public void setButton(JButton button, int id) {
        Move m = battle.getPokemon().getMoveset().getMove(id);
        String s = battle.getMoveName(id);
        if (s.equals("")) {
            button.setText("------------");
            button.setEnabled(false);
        } else {
            button.setText(s + " " + m.getPP() + "/" + m.getMaxPP());
            button.setEnabled(true);
        }
    }

    public void updateBattleHistory(String text) {
        battleHistory.setForeground(Color.cyan);
        battleHistory.append(text);
        battleHistory.setCaretPosition(battleHistory.getDocument().getLength());
    }

    public void updateEverything() {
        updatePokemon();
        updateLabels();
    }

    public void updateLabels() {
        allyPokemonName.setText(battle.getPokemon().getName());
        allyPokemonLevel.setText("Level: " + battle.getPokemon().getLevel());
        allyPokemonHp.setText("Hp: " + battle.getPokemon().getHp() + " / "
                + battle.getPokemon().getMaxHp());
        enemyPokemonName.setText(battle.getEnemy().getName());
        enemyPokemonLevel.setText("Level: " + battle.getEnemy().getLevel() + "");
        enemyPokemonHp.setText("Hp: " + battle.getEnemy().getHp() + " / "
                + battle.getEnemy().getMaxHp());
        expRemaining.setText("Exp to Level: " + battle.getPokemon().getExp() + " / " + battle.getPokemon().getMaxExp());
        expBar.setMaximum(battle.getPokemon().getMaxExp());
        expBar.setValue(battle.getPokemon().getExp());
    }

    public void updateMoves() {
        setButton(move1, 0);
        setButton(move2, 1);
        setButton(move3, 2);
        setButton(move4, 3);
        bag.setEnabled(true);
        flee.setEnabled(true);
    }

    public void updateMoveLabels(JButton button, int moveID) {
        Move m = battle.getPokemon().getMoveset().getMove(moveID);
        String s = battle.getMoveName(moveID);
        if (s.equals("")) {
            button.setText("------------");
        } else {
            button.setText(s + " " + m.getPP() + "/" + m.getMaxPP());
            if (m.getPP() <= 0) {
                button.setEnabled(false);
            }
        }
    }

    public void updateItemLabels(JLabel label) {
        String name[] = label.getText().split(" | ");
        if (name.length >= 2) {
            label.setText(battle.getBag().getItemCount(name[2]) + " | " + name[2]);
        }
    }

    public void updateSeconds(int seconds) {
        secondsLeft.setText(seconds + "");
    }

    public void addPokemon(JPanel panel, BattleHandler bh) {
        for (int x = 0; x < 6; x++) {
            if (battle.getPlayer().getParty().getPokemon(x) != null) {
                pkmn[x] = new JLabel(new ImageIcon(battle.getPlayer().getParty().getPokemon(x).getFront()));
                pkmn[x].addMouseListener(bh);
                panel.add(pkmn[x]);
                if (battle.getActiveNumber() == x) {
                    pkmn[x].setVisible(false);
                } else if (battle.getPlayer().getParty().getPokemon(x).getHp() <= 0) {
                    pkmn[x].setVisible(false);
                }
            } else {
                pkmn[x] = new JLabel();
                pkmn[x].addMouseListener(bh);
                pkmn[x].setVisible(false);
                panel.add(pkmn[x]);
            }
        }
    }

    public void hidePokemon(boolean hide) {
        pokemonPanel.setVisible(!hide);
        bagPanel.setVisible(hide);
    }

    public void updatePokemon() {
        for (int x = 0; x < 6; x++) {
            if (battle.getPlayer().getPokemon(x) != null) {
                if (battle.getActiveNumber() == x || battle.getPlayer().getPokemon(x).getHp() <= 0) {
                    pkmn[x].setVisible(false);
                } else {
                    pkmn[x].setVisible(true);
                }
            }
        }
        ally.setIcon(new ImageIcon(battle.getPokemon().getBack()));
    }

    public void addItems(JPanel panel, BattleHandler bh) {
        Bag b = battle.getBag();
        int i = 0;
        for (int x = 0; x < b.getBagCount(); x++) {
            if (b.getItem(x).getBattleUseable()) {
                items[i] = new JLabel(b.getItemCount(x) + " | " + b.getItem(x).getName());
                items[i].setForeground(Color.white);
                items[i].addMouseListener(bh);
                panel.add(items[i]);
                i++;
            }
            if (i >= 30) {
                break;
            }
        }
        for (int x = i; x < 30; x++) {
            items[x] = new JLabel("------------------------------------------");
            items[x].setForeground(Color.white);
            items[x].setEnabled(false);
            items[x].addMouseListener(bh);
            panel.add(items[x]);
        }
    }

    public void disableButtons() {
        move1.setEnabled(false);
        move2.setEnabled(false);
        move3.setEnabled(false);
        move4.setEnabled(false);
        bag.setEnabled(false);
        flee.setEnabled(false);
    }

    public JButton getMove1() {
        return move1;
    }

    public JButton getMove2() {
        return move2;
    }

    public JButton getMove3() {
        return move3;
    }

    public JButton getMove4() {
        return move4;
    }

    public JButton getBag() {
        return bag;
    }

    public JButton getFlee() {
        return flee;
    }

    public JLabel getLabel(int arrayID) {
        return pkmn[arrayID];
    }

    public JLabel getBagLabel(int x) {
        return items[x];
    }

    public void exit() {
        battle.exit();
        frame.dispose();
    }
}
