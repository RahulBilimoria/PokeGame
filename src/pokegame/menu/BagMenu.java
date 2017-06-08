/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import pokegame.entity.player.Bag.MyItem;
import pokegame.entity.player.Player;
import pokegame.gfx.ImageLoader;
import pokegame.handler.BagMenuHandler;
import pokegame.handler.Handler;
import pokegame.ui.CustomComponent;

/**
 *
 * @author Rahul
 */
public class BagMenu {

    private Handler h;
    private BagMenuHandler bmh;
    private Player player;

    private JPanel panel;
    private JPanel itemList;
    private JPanel pokemonPanel,
            pokemon[] = new JPanel[6];
    private JLabel pkmnIcons[] = new JLabel[6],
            pkmnHp[] = new JLabel[6],
            pkmnStatus[] = new JLabel[6];
    private JLabel itemInfo;
    private JLabel background;
    private JLabel bagName;
    private JButton discardItem, useItem, left, right;
    private JScrollPane itemPane;

    private ArrayList<MyItem> currentBag;
    private int listSize = 24;
    private int bagIndex;
    private int selectedItem;
    private int selectedPokemon;

    public BagMenu(Handler h) {
        this.h = h;
        this.player = h.getPlayer();
        bmh = new BagMenuHandler(this, h);
        bagIndex = 1;
        selectedItem = -1;
        selectedPokemon = -1;

        panel = new JPanel(null);
        panel.setSize(h.getCanvas().getWidth() / 3, h.getCanvas().getHeight());
        panel.setBackground(Color.black);
        background = new JLabel(new ImageIcon(ImageLoader.loadImage("/menu/bag/background.png")));

        bagName = new JLabel("Filler");
        bagName.setForeground(Color.white);
        discardItem = new JButton("Discard");
        discardItem.addActionListener(bmh);
        discardItem.setIcon(new ImageIcon(ImageLoader.loadImage("/menu/bag/idle.png")));
        discardItem.setRolloverIcon(new ImageIcon(ImageLoader.loadImage("/menu/bag/hover-discard.png")));
        discardItem.setPressedIcon(new ImageIcon(ImageLoader.loadImage("/menu/bag/pressed-discard.png")));
        discardItem.setContentAreaFilled(false);
        discardItem.setBorderPainted(false);
        discardItem.setBorder(null);
        discardItem.setHorizontalTextPosition(SwingConstants.CENTER);
        discardItem.setForeground(Color.white);

        useItem = new JButton("Use");
        useItem.addActionListener(bmh);
        useItem.setIcon(new ImageIcon(ImageLoader.loadImage("/menu/bag/idle.png")));
        useItem.setRolloverIcon(new ImageIcon(ImageLoader.loadImage("/menu/bag/hover-use.png")));
        useItem.setPressedIcon(new ImageIcon(ImageLoader.loadImage("/menu/bag/pressed-use.png")));
        useItem.setContentAreaFilled(false);
        useItem.setBorderPainted(false);
        useItem.setBorder(null);
        useItem.setHorizontalTextPosition(SwingConstants.CENTER);
        useItem.setForeground(Color.white);

        right = new JButton();
        right.addActionListener(bmh);
        right.setIcon(new ImageIcon(ImageLoader.loadImage("/menu/bag/right-arrow.png")));
        right.setRolloverIcon(new ImageIcon(ImageLoader.loadImage("/menu/bag/right-arrow-hover.png")));
        right.setPressedIcon(new ImageIcon(ImageLoader.loadImage("/menu/bag/right-arrow-pressed.png")));
        right.setContentAreaFilled(false);
        right.setBorderPainted(false);
        right.setBorder(null);
        right.setFont(new Font("Calibri", Font.PLAIN, 10));
        right.setHorizontalTextPosition(SwingConstants.CENTER);

        left = new JButton();
        left.setIcon(new ImageIcon(ImageLoader.loadImage("/menu/bag/left-arrow.png")));
        left.setRolloverIcon(new ImageIcon(ImageLoader.loadImage("/menu/bag/left-arrow-hover.png")));
        left.setPressedIcon(new ImageIcon(ImageLoader.loadImage("/menu/bag/left-arrow-pressed.png")));
        left.setContentAreaFilled(false);
        left.setBorderPainted(false);
        left.setBorder(null);
        left.setFont(new Font("Calibri", Font.PLAIN, 10));
        left.addActionListener(bmh);
        left.setHorizontalTextPosition(SwingConstants.CENTER);

        itemInfo = new JLabel("");
        /*itemInfo = new JLabel("<html>ID: <br/>"
                            + "Name: <br/>"
                            + "Quantity: <br/>"
                            + "Description: <br/>"
                            + "Effect: </html>");*/
        itemInfo.setForeground(Color.white);

        pokemonPanel = new JPanel(new GridLayout(6, 1));
        pokemonPanel.setBackground(Color.black);
        pokemonPanel.setVisible(false);
        itemList = new JPanel(new GridLayout(listSize, 1));
        itemList.setBackground(Color.GRAY);
        itemPane = new JScrollPane(itemList);
        itemPane.getVerticalScrollBar().setUnitIncrement(16);

        currentBag = player.getBag(bagIndex);

        for (int x = 0; x < 6; x++) {
            pokemon[x] = new JPanel();
            pokemon[x].addMouseListener(bmh);
            pkmnIcons[x] = new JLabel();
            pkmnHp[x] = new JLabel();
            pkmnHp[x].setForeground(Color.white);
            pkmnStatus[x] = new JLabel();
            pkmnStatus[x].setForeground(Color.white);
            pokemon[x].add(pkmnIcons[x]);
            pokemon[x].add(pkmnHp[x]);
            pokemon[x].add(pkmnStatus[x]);
            pokemon[x].setOpaque(false);
            pokemonPanel.add(pokemon[x]);
        }

        panel.add(bagName).setBounds(105, 3, 100, 25);
        panel.add(itemInfo).setBounds(5, panel.getHeight() - 175, panel.getWidth(), 100);
        panel.add(useItem).setBounds(panel.getWidth() - 77, panel.getHeight() - 27, 50, 25);
        panel.add(discardItem).setBounds(27, panel.getHeight() - 27, 50, 25);
        panel.add(left).setBounds(50, 3, 50, 25);
        panel.add(right).setBounds(panel.getWidth() - 100, 3, 50, 25);
        panel.add(pokemonPanel).setBounds(0, 30, panel.getWidth(), panel.getHeight() - 200);
        panel.add(itemPane).setBounds(0, 30, panel.getWidth(), panel.getHeight() - 200);
        panel.add(background).setBounds(0, 0, panel.getWidth(), panel.getHeight());
    }

    public void refresh() {
        itemInfo.setText("");
        itemList.removeAll();
        currentBag = player.getBag(bagIndex);
        bagName.setText(player.getBagName(bagIndex));
        for (int x = 0; x < currentBag.size(); x++) {
            itemList.add(new CustomComponent(currentBag.get(x).getItem().getItemID(),
                    "x" + currentBag.get(x).getItemCount() + "    |    "
                    + currentBag.get(x).getItem().getName()
            )).addMouseListener(bmh);
            itemList.getComponent(x).setForeground(Color.black);
        }
        if (selectedPokemon >= 0) {
            pokemon[selectedPokemon].setBackground(Color.black);
        }
        itemList.revalidate();
        itemList.repaint();
    }

    public void updatePokemonPanel() {
        for (int x = 0; x < 6; x++) {
            if (player.getPokemon(x) != null) {
                pkmnIcons[x].setIcon(new ImageIcon(player.getPokemon(x).getIcon()));
                pkmnHp[x].setText("Hp: " + player.getPokemon(x).getHp() + "/" + player.getPokemon(x).getMaxHp());
                pkmnStatus[x].setText("Status");
            } else {
                pkmnIcons[x].setIcon(null);
                pkmnHp[x].setText("");
                pkmnStatus[x].setText("");
            }
        }
    }

    public void changeBag(boolean next) {
        if (next) {
            if (++bagIndex > 3) {
                bagIndex -= 4;
            }
        } else if (--bagIndex < 0) {
            bagIndex += 4;
        }
    }

    public void comparePanel(Object o) {
        if (!itemPane.isVisible()) {
            return;
        }
        CustomComponent c = (CustomComponent) o;
        if (selectedItem != -1) {
            itemList.getComponent(selectedItem).setForeground(Color.black);
        }
        for (int x = 0; x < itemList.getComponentCount(); x++) {
            if (itemList.getComponent(x) == c) {
                itemList.getComponent(x).setForeground(Color.cyan);
                selectedItem = x;
                updateSelectedItem();
                return;
            }
        }
    }

    public void comparePokemon(Object o) {
        if (!pokemonPanel.isVisible()) {
            return;
        }
        if (selectedPokemon != -1) {
            pokemon[selectedPokemon].setBackground(Color.black);
        }
        JPanel p = (JPanel) o;
        for (int x = 0; x < 6; x++) {
            if (pokemon[x] == p) {
                if (player.getPokemon(x) == null) {
                    selectedPokemon = -1;
                    return;
                }
                pokemon[x].setOpaque(true);
                pokemon[x].setBackground(Color.gray);
                selectedPokemon = x;
                return;
            }
        }
    }

    public void updateSelectedItem() {
        itemInfo.setText("<html>ID: " + currentBag.get(selectedItem).getItem().getItemID() + "<br/>"
                + "Name: " + currentBag.get(selectedItem).getItem().getName() + "<br/>"
                + "Quantity: " + currentBag.get(selectedItem).getItemCount() + "<br/>"
                + "Description: Not Implemented Yet<br/>"
                + "Effect: Not Implemented Yet</html>");
    }

    public void useItem() {
        if (currentBag.size() <= 0 || selectedItem < 0) {
            return;
        }
        if (currentBag.get(selectedItem).getItemCount() <= 0 || currentBag.get(selectedItem).getItem().getItemType() == 1) {
            return;
        }
        discardItem.setText("Cancel");
        if (pokemonPanel.isVisible()) {
            if (selectedPokemon < 0 || player.getPokemon(selectedPokemon) == null) {
                return;
            }
            if (currentBag.get(selectedItem).getItemCount() == 1) {
                currentBag.get(selectedItem).getItem().use(player.getPokemon(selectedPokemon));
                pokemonPanel.setVisible(false);
                itemPane.setVisible(true);
                selectedPokemon = 0;
                discardItem.setText("Discard");
                int i = selectedItem;
                setSelectedItem(-1);
                player.getBag().removeItem(currentBag.get(i).getItem(), -1);
                refresh();
            } else {
                currentBag.get(selectedItem).getItem().use(player.getPokemon(selectedPokemon));
                player.getBag().removeItem(currentBag.get(selectedItem).getItem(), -1);
                updatePokemonPanel();
                updateSelectedItem();
                //plan on adding text at the bottom that shows how much of the item is remaining.
            }
        } else {
            itemPane.setVisible(false);
            pokemonPanel.setVisible(true);
        }
    }

    public void discardItem() {
        if (pokemonPanel.isVisible()) {
            pokemonPanel.setVisible(false);
            itemPane.setVisible(true);
            discardItem.setText("Discard");
            refresh();
            selectedItem = -1;
            selectedPokemon = -1;
        } else {
            if (currentBag.size() <= 0 || selectedItem < 0) {
                return;
            }
            if (currentBag.get(selectedItem).getItemCount() <= 0) {
                return;
            }
            if (currentBag.get(selectedItem).getItemCount() == 1) {
                int i = selectedItem;
                setSelectedItem(-1);
                player.getBag().removeItem(currentBag.get(i).getItem(), -1);
                refresh();
            } else {
                player.getBag().removeItem(currentBag.get(selectedItem).getItem(), -1);
                ((CustomComponent) (itemList.getComponent(selectedItem))).setText(
                        "x" + currentBag.get(selectedItem).getItemCount() + "    |    "
                        + currentBag.get(selectedItem).getItem().getName());
            }
        }
    }
    
    //NEED TO SYNCHRONIZE DEPOSITING IN THE STORAGE AND BAG MENU SHOWING

    public void close() {
        if (selectedItem >= 0) {
            itemList.getComponent(selectedItem).setForeground(Color.black);
            selectedItem = -1;
        }
        if (selectedPokemon >= 0) {
            pokemon[selectedPokemon].setBackground(Color.black);
            selectedPokemon = -1;
        }
        itemPane.setVisible(true);
        pokemonPanel.setVisible(false);
    }

    public void setSelectedItem(int i) {
        selectedItem = i;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JButton getNext() {
        return right;
    }

    public JButton getPrevious() {
        return left;
    }

    public JButton getUse() {
        return useItem;
    }

    public JButton getDiscard() {
        return discardItem;
    }
}
