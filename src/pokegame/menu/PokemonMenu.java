/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import pokegame.entity.player.Player;
import pokegame.gfx.ImageLoader;
import pokegame.handler.Handler;
import pokegame.handler.PokemonMenuHandler;
import pokegame.pokemon.Pokemon;
import pokegame.pokemon.move.Move;
import pokegame.pokemon.move.Moveset;

/**
 *
 * @author Rahul
 */
public class PokemonMenu {

    private Handler handler;
    private PokemonMenuHandler pmh;

    private int selectedPokemon;
    private int replaceMove;
    private Move replace;

    private int hpTemp, attTemp, defTemp, spattTemp, spdefTemp, speedTemp, tpTemp;
    private boolean show;

    private JLabel[] pkmn = new JLabel[6];
    private JButton[] stats = new JButton[12];
    private JLabel name, level, nickname, hp, att, def, spatt, spdef, speed, tp, exptoLevel;
    private JLabel pokedexNumber, gender, currentPokemonImage;
    private JLabel type1, type2, nature, status;
    private JProgressBar expBar;
    private JLabel moveName[] = new JLabel[4],
            movePP[] = new JLabel[4],
            moveType[] = new JLabel[4],
            moveCategory[] = new JLabel[4];
    private JLabel background, hover, selected, active;

    private JButton learnMoves;
    private JButton setActivePokemon;
    private JButton move[] = new JButton[4],
            yes, cancel;

    private JPanel panel, pokemonPanel;
    private JPanel movePanel[] = new JPanel[4];
    private JPanel learnPanel[] = new JPanel[6];
    private JPanel pokemonInfoPanel, statsPanel, infoPanel, learnMovesPanel, selectedMove;

    private JLabel warning;
    private JButton closeButton;

    private Player player;

    public PokemonMenu(Handler handler) {
        this.handler = handler;
        this.player = handler.getPlayer();
        pmh = new PokemonMenuHandler(this, handler);
        panel = new JPanel(null);
        panel.setSize(handler.getCanvas().getWidth(), handler.getCanvas().getHeight());

        background = new JLabel(new ImageIcon(ImageLoader.loadImage("/menu/pokemon/pkmnMenuBackground.png")));
        active = new JLabel(new ImageIcon(ImageLoader.loadImage("/menu/pokemon/active.png")));
        active.setSize(active.getIcon().getIconWidth(), active.getIcon().getIconHeight());
        hover = new JLabel(new ImageIcon(ImageLoader.loadImage("/menu/pokemon/hover.png")));
        hover.setSize(hover.getIcon().getIconWidth(), hover.getIcon().getIconHeight());
        hover.setVisible(false);
        selected = new JLabel(new ImageIcon(ImageLoader.loadImage("/menu/pokemon/selected.png")));
        selected.setSize(selected.getIcon().getIconWidth(), selected.getIcon().getIconHeight());

        selectedPokemon = 0;
        hpTemp = 0;
        attTemp = 0;
        defTemp = 0;
        spattTemp = 0;
        spdefTemp = 0;
        speedTemp = 0;
        tpTemp = 0;
        show = true;

        currentPokemonImage = new JLabel(new ImageIcon(player.getPokemon(selectedPokemon).getFront()));

        pokemonPanel = loadPokemon();
        type1 = new JLabel();
        type2 = new JLabel();
        createLabels();
        createButtons();
        createMoves();
        loadSelectedInfo();

        closeButton = setButtonStyle("Close", 3);
        closeButton.addActionListener(pmh);
        setActivePokemon = setButtonStyle("Set Active", 2);
        setActivePokemon.addActionListener(pmh);
        learnMoves = setButtonStyle("Learn Moves", 2);
        learnMoves.addActionListener(pmh);

        type1 = new JLabel(new ImageIcon(player.getPokemon(selectedPokemon).getType1().getImage()));
        type2 = new JLabel();
        if (player.getPokemon(selectedPokemon).getType2() != null) {
            type2.setIcon(new ImageIcon(player.getPokemon(selectedPokemon).getType2().getImage()));
        }

        pokemonInfoPanel = new JPanel(null);
        pokemonInfoPanel.setOpaque(false);
        pokemonInfoPanel.setSize(475, 150);
        pokemonInfoPanel.add(currentPokemonImage).setBounds(360, 0, currentPokemonImage.getIcon().getIconWidth(), currentPokemonImage.getIcon().getIconHeight());
        pokemonInfoPanel.add(type1).setBounds(375, currentPokemonImage.getIcon().getIconHeight(), type1.getIcon().getIconWidth(), type1.getIcon().getIconHeight());
        pokemonInfoPanel.add(type2).setBounds(375 + type1.getIcon().getIconWidth(), currentPokemonImage.getIcon().getIconHeight(), type1.getIcon().getIconWidth(), type1.getIcon().getIconHeight());
        pokemonInfoPanel.add(pokedexNumber).setBounds(0, 0, 200, 20);
        pokemonInfoPanel.add(name).setBounds(0, 20, 200, 20);
        pokemonInfoPanel.add(level).setBounds(0, 40, 200, 20);
        pokemonInfoPanel.add(nickname).setBounds(0, 60, 200, 20);
        pokemonInfoPanel.add(nature).setBounds(0, 80, 200, 20);
        pokemonInfoPanel.add(gender).setBounds(0, 100, 200, 20);
        pokemonInfoPanel.add(status).setBounds(0, 120, 200, 20);

        statsPanel = new JPanel(null);
        statsPanel.setSize(475, 200);
        statsPanel.setOpaque(false);
        statsPanel.add(tp).setBounds(170, 0, 200, 20);
        statsPanel.add(hp).setBounds(30, 30, 100, 20);
        statsPanel.add(att).setBounds(30, 80, 100, 20);
        statsPanel.add(def).setBounds(30, 130, 100, 20);
        statsPanel.add(speed).setBounds(270, 30, 100, 20);
        statsPanel.add(spatt).setBounds(270, 80, 100, 20);
        statsPanel.add(spdef).setBounds(270, 130, 100, 20);
        statsPanel.add(exptoLevel).setBounds(115, 175, 300, 20);

        panel.add(pokemonPanel).setBounds(0, 5, pokemonPanel.getWidth(), pokemonPanel.getHeight());
        panel.add(pokemonInfoPanel).setBounds(40, 130, pokemonInfoPanel.getWidth(), pokemonInfoPanel.getHeight());
        panel.add(statsPanel).setBounds(40, 270, statsPanel.getWidth(), statsPanel.getHeight());
        addButtons();
        addMovePanels();
        panel.add(closeButton).setBounds(620, 550, 125, 25);
        panel.add(setActivePokemon).setBounds(320, 550, 125, 25);
        panel.add(learnMoves).setBounds(50, 550, 125, 25);
        panel.add(active).setBounds(46, 7, active.getWidth(), active.getHeight());
        panel.add(selected).setBounds(46, 7, selected.getWidth(), selected.getHeight());
        panel.add(hover).setBounds(0, 0, hover.getWidth(), hover.getHeight());
        panel.add(background).setBounds(0, 0, panel.getWidth(), panel.getHeight());
    }

    public void createMoves() {
        for (int x = 0; x < 4; x++) {
            movePanel[x] = new JPanel(null);
            movePanel[x].setOpaque(false);
            movePanel[x].setSize(200, 50);
            moveName[x] = new JLabel();
            moveName[x].setForeground(Color.white);
            movePP[x] = new JLabel();
            movePP[x].setForeground(Color.white);
            moveCategory[x] = new JLabel();
            moveType[x] = new JLabel();
            movePanel[x].add(moveName[x]).setBounds(0, 0, 150, 25);
            movePanel[x].add(movePP[x]).setBounds(0, 25, 150, 25);
            movePanel[x].add(moveCategory[x]).setBounds(150, 0, 50, 25);
            movePanel[x].add(moveType[x]).setBounds(150, 25, 50, 25);
        }
        for (int x = 0; x < 6; x++) {
            learnPanel[x] = new JPanel(null);
            learnPanel[x].setOpaque(false);
            learnPanel[x].setVisible(false);
            learnPanel[x].setSize(200, 50);
            learnPanel[x].addMouseListener(pmh);
        }
        selectedMove = new JPanel(null);
        selectedMove.setSize(210, 300);
        selectedMove.setOpaque(false);
        selectedMove.setVisible(false);
    }

    public void createButtons() {
        for (int x = 0; x < 12; x++) {
            if (x < 6) {
                stats[x] = setButtonStyle("", 0);
            } else {
                stats[x] = setButtonStyle("", 1);
            }
            stats[x].addActionListener(pmh);
        }

        move[0] = newButton("Move 1", 0);
        move[1] = newButton("Move 2", 0);
        move[2] = newButton("Move 3", 0);
        move[3] = newButton("Move 4", 0);
        yes = newButton("Yes", 1);
        cancel = newButton("Cancel", 2);
    }

    public JButton newButton(String text, int i) {
        JButton b = new JButton(text);
        b.setSize(60, 25);
        b.setForeground(Color.white);
        b.addActionListener(pmh);
        switch (i) {
            case 0:
                b.setIcon(new ImageIcon(ImageLoader.loadImage("/menu/pokemon/move-up.png")));
                b.setPressedIcon(new ImageIcon(ImageLoader.loadImage("/menu/pokemon/move-down.png")));
                break;
            case 1:
                b.setIcon(new ImageIcon(ImageLoader.loadImage("/menu/pokemon/ok-up.png")));
                b.setPressedIcon(new ImageIcon(ImageLoader.loadImage("/menu/pokemon/ok-down.png")));
                break;
            case 2:
                b.setIcon(new ImageIcon(ImageLoader.loadImage("/menu/pokemon/cancel-up.png")));
                b.setPressedIcon(new ImageIcon(ImageLoader.loadImage("/menu/pokemon/cancel-down.png")));
                break;
            default:
                break;
        }
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setBorder(null);
        b.setHorizontalTextPosition(SwingConstants.CENTER);
        return b;
    }

    public void addMovePanels() {
        panel.add(movePanel[0]).setBounds(570, 147, movePanel[0].getWidth(), movePanel[0].getHeight());
        panel.add(movePanel[1]).setBounds(570, 227, movePanel[1].getWidth(), movePanel[1].getHeight());
        panel.add(movePanel[2]).setBounds(570, 307, movePanel[2].getWidth(), movePanel[2].getHeight());
        panel.add(movePanel[3]).setBounds(570, 387, movePanel[3].getWidth(), movePanel[3].getHeight());
        panel.add(learnPanel[0]).setBounds(35, 137, learnPanel[0].getWidth(), learnPanel[0].getHeight());
        panel.add(learnPanel[1]).setBounds(35, 192, learnPanel[1].getWidth(), learnPanel[1].getHeight());
        panel.add(learnPanel[2]).setBounds(35, 247, learnPanel[2].getWidth(), learnPanel[2].getHeight());
        panel.add(learnPanel[3]).setBounds(35, 302, learnPanel[3].getWidth(), learnPanel[3].getHeight());
        panel.add(learnPanel[4]).setBounds(35, 357, learnPanel[4].getWidth(), learnPanel[4].getHeight());
        panel.add(learnPanel[5]).setBounds(35, 412, learnPanel[5].getWidth(), learnPanel[5].getHeight());
        panel.add(selectedMove).setBounds(275, 150, selectedMove.getWidth(), selectedMove.getHeight());
    }

    public void addButtons() {
        statsPanel.add(stats[0]).setBounds(120, 25, 45, 30);
        statsPanel.add(stats[1]).setBounds(120, 75, 45, 30);
        statsPanel.add(stats[2]).setBounds(120, 125, 45, 30);
        statsPanel.add(stats[3]).setBounds(370, 25, 45, 30);
        statsPanel.add(stats[4]).setBounds(370, 75, 45, 30);
        statsPanel.add(stats[5]).setBounds(370, 125, 45, 30);
        statsPanel.add(stats[6]).setBounds(175, 25, 45, 30);
        statsPanel.add(stats[7]).setBounds(175, 75, 45, 30);
        statsPanel.add(stats[8]).setBounds(175, 125, 45, 30);
        statsPanel.add(stats[9]).setBounds(425, 25, 45, 30);
        statsPanel.add(stats[10]).setBounds(425, 75, 45, 30);
        statsPanel.add(stats[11]).setBounds(425, 125, 45, 30);
    }

    public JButton setButtonStyle(String text, int i) {
        JButton b = new JButton();
        b.setSize(90, 25);
        b.setText(text);
        b.setHorizontalTextPosition(SwingConstants.CENTER);
        b.setForeground(Color.white);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setBorder(null);
        switch (i) {
            case 0:
                b.setIcon(new ImageIcon(ImageLoader.loadImage("/menu/pokemon/add-up.png")));
                b.setPressedIcon(new ImageIcon(ImageLoader.loadImage("/menu/pokemon/add-down.png")));
                break;
            case 1:
                b.setIcon(new ImageIcon(ImageLoader.loadImage("/menu/pokemon/remove-up.png")));
                b.setPressedIcon(new ImageIcon(ImageLoader.loadImage("/menu/pokemon/remove-down.png")));
                break;
            case 2:
                b.setIcon(new ImageIcon(ImageLoader.loadImage("/menu/pokemon/other-up.png")));
                b.setPressedIcon(new ImageIcon(ImageLoader.loadImage("/menu/pokemon/other-down.png")));
                break;
            case 3:
                b.setIcon(new ImageIcon(ImageLoader.loadImage("/menu/pokemon/close-up.png")));
                b.setPressedIcon(new ImageIcon(ImageLoader.loadImage("/menu/pokemon/close-down.png")));
                break;
            default:
                break;
        }
        return b;
    }

    public JPanel loadPokemon() {
        JPanel p = new JPanel(new GridLayout(1, 6));
        p.setSize(panel.getWidth(), 40);
        p.setOpaque(false);
        for (int x = 0; x < 6; x++) {
            if (player.getPokemon(x) != null) {
                pkmn[x] = new JLabel(new ImageIcon(player.getPokemon(x).getIcon()));
                pkmn[x].addMouseListener(pmh);
                p.add(pkmn[x]);
            } else {
                pkmn[x] = new JLabel(new ImageIcon());
                p.add(pkmn[x]);
            }

        }
        return p;
    }

    public void createLabels() {
        name = new JLabel();
        level = new JLabel();
        nickname = new JLabel();
        hp = new JLabel();
        att = new JLabel();
        def = new JLabel();
        spatt = new JLabel();
        spdef = new JLabel();
        speed = new JLabel();
        tp = new JLabel();
        exptoLevel = new JLabel();
        pokedexNumber = new JLabel();
        gender = new JLabel();
        nature = new JLabel();
        status = new JLabel();
        setStyle(name);
        setStyle(level);
        setStyle(nickname);
        setStyle(hp);
        setStyle(att);
        setStyle(def);
        setStyle(spatt);
        setStyle(spdef);
        setStyle(speed);
        setStyle(tp);
        setStyle(exptoLevel);
        setStyle(pokedexNumber);
        setStyle(gender);
        setStyle(nature);
        setStyle(status);
    }

    public void loadSelectedInfo() {
        loadLabels();
        loadMoves();
        loadPictures();
        updateButtons();
    }

    public void loadLabels() {
        name.setText("Name: " + player.getPokemon(selectedPokemon).getName());
        level.setText("Level: " + player.getPokemon(selectedPokemon).getLevel());
        nickname.setText("Nickname: " + player.getPokemon(selectedPokemon).getNick());
        hp.setText("Hp: " + (player.getPokemon(selectedPokemon).getMaxHp() + hpTemp));
        att.setText("Att: " + (player.getPokemon(selectedPokemon).getAttack() + attTemp));
        def.setText("Def: " + (player.getPokemon(selectedPokemon).getDefense() + defTemp));
        spatt.setText("Spatt: " + (player.getPokemon(selectedPokemon).getSpatt() + spattTemp));
        spdef.setText("Spdef: " + (player.getPokemon(selectedPokemon).getSpdef() + spdefTemp));
        speed.setText("Speed: " + (player.getPokemon(selectedPokemon).getSpeed() + speedTemp));
        tp.setText("TP Points: " + (player.getPokemon(selectedPokemon).getTP() - tpTemp));
        exptoLevel.setText("Experience Points: " + player.getPokemon(selectedPokemon).getExp() + " / " + player.getPokemon(selectedPokemon).getMaxExp());
        pokedexNumber.setText("Pokedex Number: " + (player.getPokemon(selectedPokemon).getID() + 1));
        gender.setText("Gender: " + player.getPokemon(selectedPokemon).getGender());
        nature.setText("Nature: " + player.getPokemon(selectedPokemon).getNature());
        status.setText("Status: " + player.getPokemon(selectedPokemon).getStatus());
    }

    public void loadMoves() {
        Moveset s = player.getPokemon(selectedPokemon).getMoveset();
        for (int x = 0; x < 4; x++) {
            Move m = s.getMove(x);
            if (m != null) {
                moveName[x].setText(m.getName());
                movePP[x].setText("PP: " + m.getPP() + " / " + m.getMaxPP());
                moveType[x].setIcon(new ImageIcon(m.getType().getImage()));
                moveCategory[x].setIcon(new ImageIcon(m.getCategoryImage()));
            } else {
                moveName[x].setText("");
                movePP[x].setText("");
                moveType[x].setIcon(null);
                moveCategory[x].setIcon(null);
            }
        }
    }

    public void loadPictures() {
        currentPokemonImage.setIcon(new ImageIcon(player.getPokemon(selectedPokemon).getFront()));
        type1.setIcon(new ImageIcon(player.getPokemon(selectedPokemon).getType1().getImage()));
        if (player.getPokemon(selectedPokemon).getType2() != null) {
            type2.setIcon(new ImageIcon(player.getPokemon(selectedPokemon).getType2().getImage()));
        } else {
            type2.setIcon(null);
        }
    }

    public void updateButtons() {
        if (player.getPokemon(selectedPokemon).getTP() > 0) {
            for (int x = 0; x < 12; x++) {
                stats[x].setVisible(true);
            }
        } else {
            for (int x = 0; x < 12; x++) {
                stats[x].setVisible(false);
            }
        }
    }

    public void updatePokemon() {
        for (int x = 0; x < 6; x++) {
            pkmn[x].removeMouseListener(pmh);
            if (player.getPokemon(x) == null) {
                pkmn[x].setIcon(null);
            } else {
                pkmn[x].setIcon(new ImageIcon(player.getPokemon(x).getIcon()));
                pkmn[x].addMouseListener(pmh);
            }
        }
    }

    public void hidePanels() {
        show = !show;
        if (show) {
            background.setIcon(new ImageIcon(ImageLoader.loadImage("/menu/pokemon/pkmnMenuBackground.png")));
            hideLearnableMoves();
            selectedMove.setVisible(false);
            learnMoves.setText("Learn Moves");
        } else {
            background.setIcon(new ImageIcon(ImageLoader.loadImage("/menu/pokemon/pkmnMenu-LearnMoves.png")));
            loadLearnableMoves();
            learnMoves.setText("Pokemon Info");
        }
        pokemonInfoPanel.setVisible(show);
        statsPanel.setVisible(show);

    }

    public void hideLearnableMoves() {
        for (int x = 0; x < 6; x++) {
            learnPanel[x].setVisible(false);
        }
    }

    public void loadLearnableMoves() {
        if (!show) {
            Move[] m = player.getPokemon(selectedPokemon).getLearnableMoves();
            int y = m.length;
            if (y > 6) {
                y = 6;
            }
            for (int x = 0; x < 6; x++) {
                learnPanel[x].removeAll();
                learnPanel[x].setVisible(false);
            }
            selectedMove.setVisible(false);
            JLabel name1, pp1, type3, cat1;
            for (int x = 0; x < y; x++) {
                learnPanel[x].setVisible(true);
                name1 = new JLabel(m[x].getName());
                pp1 = new JLabel("PP: " + m[x].getPP() + " / " + m[x].getMaxPP());
                type3 = new JLabel(new ImageIcon(m[x].getType().getImage()));
                cat1 = new JLabel(new ImageIcon(m[x].getCategoryImage()));
                name1.setForeground(Color.white);
                pp1.setForeground(Color.white);
                learnPanel[x].add(name1).setBounds(5, 0, 150, 25);
                learnPanel[x].add(pp1).setBounds(5, 25, 150, 25);
                learnPanel[x].add(cat1).setBounds(150, 0, 50, 25);
                learnPanel[x].add(type3).setBounds(150, 25, 50, 25);
            }
        }
    }

    public void showInfo(int i) {
        replace = player.getPokemon(selectedPokemon).getLearnableMoves()[i];
        boolean show = true;
        for (int x = 0; x < 4; x++) {
            if (player.getPokemon(selectedPokemon).getMoveset().getMove(x) == null) {
                break;
            }
            if (replace.getId() == player.getPokemon(selectedPokemon).getMoveset().getMove(x).getId()) {
                show = false;
                System.out.println("Move already learned!");
            }
        }
        if (show) {
            selectedMove.removeAll();
            JLabel name1, power, acc, maxPP, cat, type;
            warning = new JLabel("");
            warning.setForeground(Color.white);
            name1 = new JLabel("Move: " + replace.getName());
            name1.setForeground(Color.white);
            power = new JLabel("Power: " + replace.getPower());
            power.setForeground(Color.white);
            acc = new JLabel("Accuracy: " + ((int) (replace.getAccuracy() * 100)) + "%");
            acc.setForeground(Color.white);
            maxPP = new JLabel("PP: " + replace.getMaxPP());
            maxPP.setForeground(Color.white);
            cat = new JLabel(new ImageIcon(replace.getCategoryImage()));
            type = new JLabel(new ImageIcon(replace.getType().getImage()));
            selectedMove.add(name1).setBounds(5, 0, 150, 25);
            selectedMove.add(power).setBounds(5, 25, 150, 25);
            selectedMove.add(acc).setBounds(5, 50, 200, 25);
            selectedMove.add(maxPP).setBounds(5, 75, 200, 25);
            selectedMove.add(cat).setBounds(150, 20, 50, 25);
            selectedMove.add(type).setBounds(150, 50, 50, 25);
            selectedMove.add(move[0]).setBounds(25, 150, 60, 25);
            selectedMove.add(move[1]).setBounds(125, 150, 60, 25);
            selectedMove.add(move[2]).setBounds(25, 200, 60, 25);
            selectedMove.add(move[3]).setBounds(125, 200, 60, 25);
            selectedMove.add(yes).setBounds(40, 275, 60, 25);
            selectedMove.add(cancel).setBounds(110, 275, 60, 25);
            selectedMove.add(warning).setBounds(55, 240, 100, 25);
            yes.setVisible(false);
            cancel.setVisible(false);
            selectedMove.setVisible(true);
        }
    }

    public void addMove(int i) {
        yes.setVisible(true);
        cancel.setVisible(true);
        warning.setVisible(true);
        warning.setText("Replace Move " + (i + 1) + "?");
        replaceMove = i;
    }

    public void replaceMove() {
        yes.setVisible(false);
        cancel.setVisible(false);
        selectedMove.setVisible(false);
        player.getPokemon(selectedPokemon).getMoveset().setMove(replaceMove, replace); // if PP is universal with moves then this is why
    }

    public void cancelReplace() {
        yes.setVisible(false);
        cancel.setVisible(false);
        warning.setVisible(false);
        replaceMove = -1;
    }

    public void setStyle(JLabel label) {
        label.setFont(new Font("Bookman Old Style", Font.BOLD, 18));
        label.setForeground(Color.white);
    }

    public void setActive() {
        player.setActiveNumber(selectedPokemon);
        active.setLocation((133 * selectedPokemon) + 46, 7);
    }

    public void setSelected(JLabel label) {
        for (int x = 0; x < 6; x++) {
            if (pkmn[x] == label) {
                selected.setLocation((133 * x) + 46, 7);
                selectedPokemon = x;
                break;
            }
        }
    }

    public void hover(JLabel label) {
        for (int x = 0; x < 6; x++) {
            if (pkmn[x] == label) {
                hover.setVisible(true);
                hover.setLocation((133 * x) + 46, 7);
                break;
            }
        }
    }

    public void unHover() {
        hover.setVisible(false);
    }

    public void saveData() {
        if (player.getPokemon(selectedPokemon) == null)return;
        Pokemon p = player.getPokemon(selectedPokemon);
        p.addTp(-tpTemp);
        p.addHp(hpTemp);
        p.addAttack(attTemp);
        p.addDefense(defTemp);
        p.addSpAtt(spattTemp);
        p.addSpDef(spdefTemp);
        p.addSpeed(speedTemp);
        tpTemp = 0;
        hpTemp = 0;
        attTemp = 0;
        defTemp = 0;
        spattTemp = 0;
        spdefTemp = 0;
        speedTemp = 0;
    }

    public void addStat(JButton b) {
        int x;
        for (x = 0; x < 12; x++) {
            if (stats[x] == b) {
                break;
            }
        }
        if (x < 6) {
            if (tpTemp < player.getPokemon(selectedPokemon).getTP()) {
                tpTemp++;
                switch (x) {
                    case 0:
                        hpTemp += 2;
                        break;
                    case 1:
                        attTemp += 1;
                        break;
                    case 2:
                        defTemp += 1;
                        break;
                    case 3:
                        speedTemp += 1;
                        break;
                    case 4:
                        spattTemp += 1;
                        break;
                    case 5:
                        spdefTemp += 1;
                        break;
                    default:
                        break;
                }
            }
        } else if (tpTemp > 0) {
            if (x == 6 && hpTemp > 0) {
                hpTemp -= 2;
                tpTemp--;
            } else if (x == 7 && attTemp > 0) {
                attTemp -= 1;
                tpTemp--;
            } else if (x == 8 && defTemp > 0) {
                defTemp -= 1;
                tpTemp--;
            } else if (x == 9 && speedTemp > 0) {
                speedTemp -= 1;
                tpTemp--;
            } else if (x == 10 && spattTemp > 0) {
                spattTemp -= 1;
                tpTemp--;
            } else if (x == 11 && spdefTemp > 0) {
                spdefTemp -= 1;
                tpTemp--;
            }
        }
    }

    public void update() {
        loadSelectedInfo();
    }

    public void updateSelected() {
        if (player.getPokemon(selectedPokemon) == null) {
            for (int x = 0; x < 6; x++) {
                if (player.getPokemon(x) != null) {
                    selectedPokemon = x;
                    selected.setLocation((133 * x) + 46, 7);
                    player.setActiveNumber(selectedPokemon);
                    active.setLocation((133 * selectedPokemon) + 46, 7);
                    break;
                }
            }
        }
    }

    public JPanel getMove(int i) {
        return learnPanel[i];
    }

    public JButton getMoveButton(int i) {
        return move[i];
    }

    public JButton getYes() {
        return yes;
    }

    public JButton getCancel() {
        return cancel;
    }

    public JButton getLearnMoves() {
        return learnMoves;
    }

    public JButton getActiveButton() {
        return setActivePokemon;
    }

    public JButton getClose() {
        return closeButton;
    }

    public JPanel getPanel() {
        return panel;
    }
}
