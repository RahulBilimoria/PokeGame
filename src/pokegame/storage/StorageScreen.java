/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.storage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import pokegame.entity.player.Party;
import pokegame.entity.player.Player;
import pokegame.entity.player.Storage;
import pokegame.gfx.ImageLoader;
import pokegame.handler.Handler;
import pokegame.handler.StorageHandler;
import pokegame.pokemon.Pokemon;

/**
 *
 * @author Rahul
 */
public class StorageScreen {

    private Handler handler;

    private StorageHandler storageHandler;
    private Player player;
    private Party party;
    private Storage storage;
    private final int WIDTH = 650,
            HEIGHT = 450;
    private int currentBox;
    private int draggedX, draggedY;

    private JFrame frame;
    private JPanel boxPKMN, partyPKMN, namePanel, pokemonInfoPanel;
    private JButton leftArrow, rightArrow, deletePokemon;
    private JLabel selected, hover, partyHover;
    private JLabel boxName;
    private JLabel pokemonInfo[] = new JLabel[10];
    private JLabel boxPokemon[] = new JLabel[25];
    private JLabel partyPokemon[] = new JLabel[6];
    private JLabel partyPokemonInfo[] = new JLabel[6];
    private JLabel boxBackground, background;
    private JLabel draggedPokemon;

    public StorageScreen(Handler handler) {
        this.handler = handler;
        this.player = handler.getPlayer();
        this.party = player.getParty();
        this.storage = player.getStorage();

        currentBox = 0;

        frame = new JFrame("Pokemon Storage");
        frame.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setLayout(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                exit();
            }
        }
        );

        draggedPokemon = new JLabel();
        draggedPokemon.setSize(92, 67);
        draggedPokemon.setVisible(true);

        storageHandler = new StorageHandler(this, storage, party);
        frame.addMouseListener(storageHandler);
        frame.addMouseMotionListener(storageHandler);

        createHeader();
        createBox();
        createParty();

        frame.add(draggedPokemon).setBounds(0, 0, 92, 67);
        frame.add(selected).setBounds(21, (HEIGHT - boxPKMN.getHeight()) - 4, selected.getWidth(), selected.getHeight());
        frame.add(partyHover).setBounds(0, 0, partyHover.getWidth(), partyHover.getHeight());
        frame.add(hover).setBounds(21, (HEIGHT - boxPKMN.getHeight()) - 4, hover.getWidth(), hover.getHeight());
        frame.add(deletePokemon).setBounds(2, 2, deletePokemon.getWidth(), deletePokemon.getHeight());
        frame.add(boxPKMN).setBounds(21, (HEIGHT - boxPKMN.getHeight()) - 4, boxPKMN.getWidth(), boxPKMN.getHeight());
        frame.add(partyPKMN).setBounds(boxBackground.getIcon().getIconWidth(), (HEIGHT - boxBackground.getIcon().getIconHeight()) + (boxBackground.getIcon().getIconHeight() / 2) - 10, partyPKMN.getWidth() + 20, partyPKMN.getHeight() - 5);
        frame.add(pokemonInfoPanel).setBounds(boxBackground.getIcon().getIconWidth() + 10, 25, pokemonInfoPanel.getWidth(), pokemonInfoPanel.getHeight());
        frame.add(namePanel).setBounds(84, HEIGHT - boxBackground.getIcon().getIconHeight(), namePanel.getWidth(), namePanel.getHeight());
        frame.add(leftArrow).setBounds(15, HEIGHT - boxBackground.getIcon().getIconHeight() + 15, leftArrow.getWidth(), leftArrow.getHeight());
        frame.add(rightArrow).setBounds(leftArrow.getWidth() + namePanel.getWidth() + 55, HEIGHT - boxBackground.getIcon().getIconHeight() + 15, rightArrow.getWidth(), rightArrow.getHeight());
        frame.add(boxBackground).setBounds(0, HEIGHT - boxBackground.getIcon().getIconHeight(), boxBackground.getIcon().getIconWidth(), boxBackground.getIcon().getIconHeight());
        frame.add(background).setBounds(0, 0, WIDTH, HEIGHT);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.pack();
    }

    private JButton createButton(String icon, String iconPressed) {
        JButton b = new JButton();
        b.setIcon(new ImageIcon(ImageLoader.loadImage(icon))); //"/storage/right-arrow-up.png"
        b.setRolloverIcon(new ImageIcon(ImageLoader.loadImage(icon)));
        b.setPressedIcon(new ImageIcon(ImageLoader.loadImage(iconPressed))); //"/storage/right-arrow-down.png"
        b.setSize(b.getIcon().getIconWidth(), b.getIcon().getIconHeight());
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setBorder(null);
        b.addActionListener(storageHandler);
        return b;
    }

    private void createHeader() {
        boxName = new JLabel(storage.getBoxName(currentBox));
        boxName.setFont(new Font("Calibri", Font.PLAIN, 30));
        boxName.setForeground(Color.black);
        boxName.setOpaque(false);
        namePanel = new JPanel();
        namePanel.setSize(335, 57);
        namePanel.add(boxName);
        namePanel.setBackground(new Color(255, 255, 255, 15));
        namePanel.setOpaque(false);

        pokemonInfoPanel = new JPanel(new GridLayout(10, 1));
        pokemonInfoPanel.setSize(125, 195);
        pokemonInfoPanel.setOpaque(false);

        pokemonInfo[0] = new JLabel("Name: ");
        pokemonInfo[1] = new JLabel("Level: ");
        pokemonInfo[2] = new JLabel("Nature: ");
        pokemonInfo[3] = new JLabel("Gender: ");
        pokemonInfo[4] = new JLabel("HP: ");
        pokemonInfo[5] = new JLabel("ATT: ");
        pokemonInfo[6] = new JLabel("DEF: ");
        pokemonInfo[7] = new JLabel("SPATT: ");
        pokemonInfo[8] = new JLabel("SPDEF: ");
        pokemonInfo[9] = new JLabel("SPEED: ");
        for (int x = 0; x < 10; x++) {
            pokemonInfo[x].setForeground(Color.white);
            pokemonInfoPanel.add(pokemonInfo[x]);
        }

        leftArrow = createButton("/storage/left-arrow-up.png", "/storage/left-arrow-down.png");
        rightArrow = createButton("/storage/right-arrow-up.png", "/storage/right-arrow-down.png");
        deletePokemon = new JButton("Release");
        deletePokemon.setSize(120, 20);
        deletePokemon.addActionListener(storageHandler);
        boxBackground = new JLabel(new ImageIcon(ImageLoader.loadImage("/storage/box/1.png")));
        background = new JLabel(new ImageIcon(ImageLoader.loadImage("/storage/background.png")));
        selected = new JLabel(new ImageIcon(ImageLoader.loadImage("/storage/selected.png")));
        selected.setSize(selected.getIcon().getIconWidth(), selected.getIcon().getIconHeight());
        hover = new JLabel(new ImageIcon(ImageLoader.loadImage("/storage/hover.png")));
        hover.setSize(hover.getIcon().getIconWidth(), hover.getIcon().getIconHeight());
        partyHover = new JLabel(new ImageIcon(ImageLoader.loadImage("/storage/partyhover.png")));
        partyHover.setSize(partyHover.getIcon().getIconWidth(), partyHover.getIcon().getIconHeight());
        hover.setVisible(false);
        partyHover.setVisible(false);
    }

    private void createBox() {
        boxPKMN = new JPanel();
        boxPKMN.setSize(460, 335);
        boxPKMN.setLayout(new GridLayout(5, 5));
        boxPKMN.setOpaque(false);
        for (int x = 0; x < Storage.BOXES_SIZE; x++) {
            if (storage.getId(currentBox, x) != -1) {
                boxPokemon[x] = new JLabel(new ImageIcon(ImageLoader.loadImage("/pokemon/icons/"
                        + (storage.getId(currentBox, x) + 1) + ".png")));
            } else {
                boxPokemon[x] = new JLabel(new ImageIcon(ImageLoader.loadImage("/pokemon/icons/1.png")));
                boxPokemon[x].setIcon(null);
            }
            boxPKMN.add(boxPokemon[x]);
        }
    }

    private void createParty() {
        ImageIcon i = new ImageIcon(ImageLoader.loadImage("/pokemon/icons/1.png"));
        int width = i.getIconWidth() + 5;
        int height = i.getIconHeight() + 5;
        partyPKMN = new JPanel();
        partyPKMN.setLayout(null);
        partyPKMN.setSize(WIDTH - boxBackground.getIcon().getIconWidth() - 30, boxBackground.getIcon().getIconHeight() / 2);
        partyPKMN.setOpaque(false);
        String s;
        for (int x = 0; x < 6; x++) {
            if (party.getPokemon(x) != null) {
                //can change this since pokemon have a bufferedImage associated with them
                partyPokemon[x] = new JLabel(new ImageIcon(ImageLoader.loadImage("/pokemon/icons/"
                        + (party.getPokemon(x).getID() + 1) + ".png")));
                partyPKMN.add(partyPokemon[x]).setBounds(0, x * height, width, height);
                s = "Lvl: " + party.getPokemon(x).getLevel() + " HP: " + party.getPokemon(x).getHp() + "/" + party.getPokemon(x).getMaxHp();
                partyPokemonInfo[x] = new JLabel(s);
                partyPokemonInfo[x].setForeground(Color.white);
                partyPKMN.add(partyPokemonInfo[x]).setBounds(width, x * height, width * 2, height);
            } else {
                partyPokemon[x] = new JLabel();
                partyPKMN.add(partyPokemon[x]).setBounds(0, x * height, width, height);;
                partyPokemonInfo[x] = new JLabel();
                partyPokemonInfo[x].setForeground(Color.white);
                partyPKMN.add(partyPokemonInfo[x]).setBounds(width, x * height, width * 2, height);
            }
            //partyPokemon[x].setSize(width, height);
            //partyPokemonInfo[x].setSize(width*2, height);
        }
    }

    public void changeBox(int change) {
        currentBox += change;
        if (currentBox < 0) {
            currentBox = Storage.BOXES_COUNT - 1;
        } else if (currentBox >= Storage.BOXES_COUNT) {
            currentBox = 0;
        }
        storage.setCurrentBox(currentBox);
        //use storages current box value so no need to pass parameter
        //change box backgrounds too
        changeBoxIcons();
        boxName.setText(storage.getBoxName(currentBox));
        boxBackground.setIcon(new ImageIcon(ImageLoader.loadImage("/storage/box/" + (currentBox + 1) + ".png")));
    }

    public void changeBoxIcons() {
        for (int x = 0; x < Storage.BOXES_SIZE; x++) {
            if (storage.getId(currentBox, x) != -1) {
                boxPokemon[x].setIcon(new ImageIcon(ImageLoader.loadImage("/pokemon/icons/"
                        + (storage.getId(currentBox, x) + 1) + ".png")));
            } else {
                boxPokemon[x].setIcon(null);
            }
        }
    }

    public JLabel getBoxPokemon(int x) {
        return boxPokemon[x];
    }

    public void setBoxPokemonIcon(int x, Icon icon) {
        boxPokemon[x].setIcon(icon);
    }

    public JLabel getPartyPokemon(int x) {
        return partyPokemon[x];
    }

    public JPanel getBoxPanel() {
        return boxPKMN;
    }

    public JPanel getPartyPanel() {
        return partyPKMN;
    }

    public void setBoxSelectedLocation(int x, int y) {
        selected.setLocation((x * 92) + 21, (y * 67) + (HEIGHT - boxPKMN.getHeight()) - 4);
        storage.setSelectedPokemon(x + y * 5);
        updateLabels(x + (y * 5));
    }

    public void setBoxHoverLocation(int x, int y) {
        hover.setLocation((x * 92) + 21, (y * 67) + (HEIGHT - boxPKMN.getHeight()) - 4);
    }

    public void enableBoxDraggedPokemon(int x, int y) {
        draggedX = x;
        draggedY = y;
        getBoxPokemon(x + y).setVisible(false);
        draggedPokemon.setIcon(getBoxPokemon(x + y).getIcon());
        draggedPokemon.setVisible(true);
    }

    public void moveBoxDraggedPokemon(int x, int y) {
        x = x - draggedPokemon.getWidth() / 3 + 10;
        y = y - draggedPokemon.getHeight() + 5;
        Point p = frame.getLocationOnScreen();
        draggedPokemon.setLocation(x-((int)p.getX()), y-((int)p.getY()));
    }

    /**
     * Swaps two pokemon in the box
     * @param x row value
     * @param y column value
     */
    public void disableBoxDraggedPokemon(int x, int y) {
        storage.swapPokemon(currentBox, draggedX + draggedY, x + y * 5);
        draggedPokemon.setVisible(false);
        getBoxPokemon(draggedX + draggedY).setVisible(true);
        Icon temp = getBoxPokemon(draggedX + draggedY).getIcon();
        setBoxPokemonIcon(draggedX + draggedY, getBoxPokemon(x + y * 5).getIcon());
        setBoxPokemonIcon(x + y * 5, temp);
    }

    /**
     * Swaps a pokemon in the box to one in the players party
     * Drags from storage to party
     * @param y party index value
     */
    public void disableBoxDraggedPokemon(int y) {
        draggedPokemon.setVisible(false);
        getBoxPokemon(draggedX + draggedY).setVisible(true);
        Icon temp = getBoxPokemon(draggedX + draggedY).getIcon();
        setBoxPokemonIcon(draggedX + draggedY, partyPokemon[y].getIcon());
        partyPokemon[y].setIcon(temp);
        handler.getGame().getGameMenu().updatePokemonMenu();
        storage.storageToParty(draggedX + draggedY, y);
        partyPokemonInfo[y].setText("Lvl: " + party.getPokemon(y).getLevel() + " HP: " + party.getPokemon(y).getHp() + "/" + party.getPokemon(y).getMaxHp());
        handler.getGame().getGameMenu().updatePokemonMenu();
        handler.getGame().getGameMenu().updateBagPokemon();
    }

    public void disableBoxDraggedPokemon() {
        getBoxPokemon(draggedX + draggedY).setVisible(true);
        draggedPokemon.setVisible(false);
    }

    public void setPartyHoverLocation(int y) {
        partyHover.setLocation(partyPKMN.getX() + 5, (y * 35) + partyPKMN.getY());
    }

    public void enablePartyDraggedPokemon(int y) {
        draggedY = y;
        partyPokemon[y].setVisible(false);
        partyPokemonInfo[y].setVisible(false);
        draggedPokemon.setIcon(partyPokemon[y].getIcon());
        draggedPokemon.setVisible(true);
    }

    public void movePartyDraggedPokemon(int x, int y) {
        x = x - draggedPokemon.getWidth() / 3 + 10;
        y = y - draggedPokemon.getHeight() + 5;
        Point p = frame.getLocationOnScreen();
        draggedPokemon.setLocation(x-((int)p.getX()), y-((int)p.getY()));
    }

    /**
     * Swaps a party pokemon with one in storage
     * Drags from party to storage
     * @param x storage row value
     * @param y storage column value
     */
    public void disablePartyDraggedPokemon(int x, int y) {
        draggedPokemon.setVisible(false);
        partyPokemon[draggedY].setVisible(true);
        partyPokemonInfo[draggedY].setVisible(true);
        if (party.getPartySize() == 1) {
            System.out.println("Cant have zero pokemon in party!");
            return;
        }
        Icon temp = partyPokemon[draggedY].getIcon();
        partyPokemon[draggedY].setIcon(boxPokemon[x + y * 5].getIcon());
        setBoxPokemonIcon(x + y * 5, temp);
        handler.getGame().getGameMenu().updatePokemonMenu();
        if (storage.partyToStorage(x + y * 5, draggedY))
            partyPokemonInfo[draggedY].setText("Lvl: " + party.getPokemon(draggedY).getLevel() + " HP: " + party.getPokemon(draggedY).getHp() + "/" + party.getPokemon(draggedY).getMaxHp());
        else
            partyPokemonInfo[draggedY].setText(""); //"removes pokemon text"
        //set pokemon info text here too
        handler.getGame().getGameMenu().updatePokemonMenu();
        handler.getGame().getGameMenu().updateBagPokemon();
    }

    /**
     * Swaps a party pokemon with the one it was dropped on
     * @param y party index of pokemon to swap with 
     */
    //WHEN SWAPPED WITH A NULL SPOT, BREAKS THE VISUAL QUEUES
    public void disablePartyDraggedPokemon(int y) {
        draggedPokemon.setVisible(false);
        partyPokemon[draggedY].setVisible(true);
        partyPokemonInfo[draggedY].setVisible(true);
        Icon temp = partyPokemon[draggedY].getIcon();
        partyPokemon[draggedY].setIcon(partyPokemon[y].getIcon());
        partyPokemon[y].setIcon(temp);
        String temp2 = partyPokemonInfo[draggedY].getText();
        partyPokemonInfo[draggedY].setText(partyPokemonInfo[y].getText());
        partyPokemonInfo[y].setText(temp2);
        handler.getGame().getGameMenu().savePokemonMenu();
        player.getParty().swapPokemon(draggedY, y);
        handler.getGame().getGameMenu().updatePokemonMenu();
        handler.getGame().getGameMenu().updateBagPokemon();
        //set pokemon info text here too
    }

    public void disablePartyDraggedPokemon() {
        partyPokemon[draggedY].setVisible(true);
        partyPokemonInfo[draggedY].setVisible(true);
        draggedPokemon.setVisible(false);
    }

    public void updateLabels(int index) {
        if (storage.getId(currentBox, index) >= 0) {
            Pokemon p = storage.getPokemon(currentBox, index);
            pokemonInfo[0].setText("Name: " + p.getName());
            pokemonInfo[1].setText("Level: " + p.getLevel());
            pokemonInfo[2].setText("Nature: " + p.getNature());
            pokemonInfo[3].setText("Gender: " + p.getGender());
            pokemonInfo[4].setText("HP: " + p.getHp());
            pokemonInfo[5].setText("ATT: " + p.getAttack());
            pokemonInfo[6].setText("DEF: " + p.getDefense());
            pokemonInfo[7].setText("SPATT: " + p.getSpatt());
            pokemonInfo[8].setText("SPDEF: " + p.getSpdef());
            pokemonInfo[9].setText("SPEED: " + p.getSpeed());
        } else {
            pokemonInfo[0].setText("Name: ");
            pokemonInfo[1].setText("Level: ");
            pokemonInfo[2].setText("Nature: ");
            pokemonInfo[3].setText("Gender: ");
            pokemonInfo[4].setText("HP: ");
            pokemonInfo[5].setText("ATT: ");
            pokemonInfo[6].setText("DEF: ");
            pokemonInfo[7].setText("SPATT: ");
            pokemonInfo[8].setText("SPDEF: ");
            pokemonInfo[9].setText("SPEED: ");
        }
    }

    public void deletePokemon() {
        storage.removePokemon(currentBox, storage.getSelectedPokemon());
        getBoxPokemon(storage.getSelectedPokemon()).setIcon(null);
    }

    public JButton getLeftArrow() {
        return leftArrow;
    }

    public JButton getRightArrow() {
        return rightArrow;
    }

    public JButton getDeletePokemon() {
        return deletePokemon;
    }

    public void enableHover(boolean party) {
        if (party) {
            partyHover.setVisible(true);
        } else {
            hover.setVisible(true);
        }
    }

    public void disableHover(boolean party) {
        if (party) {
            partyHover.setVisible(false);
        } else {
            hover.setVisible(false);
        }
    }

    private void exit() {
        handler.getWorld().closeStorage();
        storage.saveBoxes();
    }
}
