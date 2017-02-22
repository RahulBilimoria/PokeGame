/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.storage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
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
    private JPanel boxPKMN, partyPKMN, menu, namePanel;
    private JButton leftArrow, rightArrow;
    private JLabel selected, hover;
    private JLabel boxName;
    private JLabel boxPokemon[] = new JLabel[25];
    private JLabel partyPokemon[] = new JLabel[6];
    private JLabel boxBackground, background;
    private JLabel draggedPokemon;
    
    public StorageScreen(Handler handler){
        this.handler = handler;
        this.player = handler.getPlayer();
        this.party = player.getParty();
        this.storage = player.getStorage();
        
        currentBox = 0;
        boxName = new JLabel(storage.getBoxName(currentBox));
        boxName.setFont(new Font("Calibri", Font.PLAIN, 30));
        namePanel = new JPanel();
        namePanel.setSize(335,57);
        namePanel.add(boxName);
        namePanel.setBackground(new Color(255,255,255,15));
        boxName.setForeground(Color.black);
        
        draggedPokemon = new JLabel();
        //draggedPokemon = new JLabel(new ImageIcon(ImageLoader.loadImage("/pokemon/icons/9.png")));
        draggedPokemon.setSize(92,67);
        draggedPokemon.setVisible(true);
        storageHandler = new StorageHandler(this, storage);
        
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
        
        boxBackground = new JLabel(new ImageIcon(ImageLoader.loadImage("/storage/box/1.png")));
        background = new JLabel(new ImageIcon(ImageLoader.loadImage("/storage/background.png")));
        selected = new JLabel(new ImageIcon(ImageLoader.loadImage("/storage/selected.png")));
        selected.setSize(selected.getIcon().getIconWidth(), selected.getIcon().getIconHeight());
        hover = new JLabel(new ImageIcon(ImageLoader.loadImage("/storage/hover.png")));
        hover.setSize(hover.getIcon().getIconWidth(), hover.getIcon().getIconHeight());
        
        createBox();
        createParty();
        createMenu();

        frame.add(draggedPokemon).setBounds(0,0,92,67);
        frame.add(selected).setBounds(21, (HEIGHT - boxPKMN.getHeight()) + 1, selected.getWidth(), selected.getHeight());
        frame.add(hover).setBounds(21, (HEIGHT - boxPKMN.getHeight()) + 1, hover.getWidth(), hover.getHeight());
        frame.add(boxPKMN).setBounds(21,(HEIGHT - boxPKMN.getHeight()) + 1,
                boxPKMN.getWidth(), boxPKMN.getHeight());
        frame.add(partyPKMN).setBounds(boxBackground.getIcon().getIconWidth()-20,
                (HEIGHT - boxBackground.getIcon().getIconHeight()) + (boxBackground.getIcon().getIconHeight()/2),
                partyPKMN.getWidth()+20, partyPKMN.getHeight());
        frame.add(namePanel).setBounds(84,HEIGHT - boxBackground.getIcon().getIconHeight(),namePanel.getWidth(),namePanel.getHeight());
        frame.add(boxBackground).setBounds(0,
                HEIGHT - boxBackground.getIcon().getIconHeight(),
                boxBackground.getIcon().getIconWidth(),
                boxBackground.getIcon().getIconHeight());
        frame.add(background).setBounds(0,0,WIDTH, HEIGHT);
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.pack();
    }
    
    private void createBox(){
        boxPKMN = new JPanel();
        boxPKMN.addMouseListener(storageHandler);
        boxPKMN.addMouseMotionListener(storageHandler);
        boxPKMN.setSize(460,335);
        boxPKMN.setLayout(new GridLayout(5,5));
        boxPKMN.setOpaque(false);
        for (int x = 0; x < 25; x++){
            if (storage.getId(currentBox, x) != -1){
                boxPokemon[x] = new JLabel(new ImageIcon(ImageLoader.loadImage("/pokemon/icons/" + 
                    (storage.getId(currentBox, x)+1) + ".png")));
            } else {
                boxPokemon[x] = new JLabel(new ImageIcon(ImageLoader.loadImage("/pokemon/icons/1.png")));
                boxPokemon[x].setIcon(null);
            }
            boxPKMN.add(boxPokemon[x]);
        }
    }
    
    private void createParty(){
        partyPKMN = new JPanel();
        partyPKMN.setSize(WIDTH - boxBackground.getIcon().getIconWidth()+20,boxBackground.getIcon().getIconHeight()/2);
        partyPKMN.setLayout(new GridLayout(6,2)); //USE GRIDBAGLAYOUT TO MAKE SECTIONS UNEVEN
        partyPKMN.setOpaque(false);
        for (int x = 0; x < 6; x++){
            if (party.getPokemon(x) != null){
            partyPokemon[x] = new JLabel(new ImageIcon(ImageLoader.loadImage("/pokemon/icons/" + 
                    (party.getPokemon(x).getID()+1) + ".png")));
            partyPKMN.add(partyPokemon[x]);
            partyPKMN.add(new JLabel("Pokemon Info"));
            }
            else {
                partyPKMN.add(new JLabel());
                partyPKMN.add(new JLabel());
            }
        }
    }
    
    private void createMenu(){
        menu = new JPanel();
    }
    
    private void exit(){
        handler.getWorld().closeStorage();
    }
    
    public JLabel getBoxPokemon(int x){
        return boxPokemon[x];
    }
    
    public void setBoxPokemonIcon(int x, Icon icon){
        boxPokemon[x].setIcon(icon);
    }
    
    public JLabel getPartyPokemon(int x){
        return partyPokemon[x];
    }
    
    public JPanel getBoxPanel(){
        return boxPKMN;
    }
    
    public void setSelectedLocation(int x, int y){
        selected.setLocation((x*92)+21,(y*67)+(HEIGHT - boxPKMN.getHeight()) + 1);
    }
    
    public void setHoverLocation(int x, int y){
        hover.setLocation((x*92)+21,(y*67)+(HEIGHT - boxPKMN.getHeight()) + 1);
    }
    
    public void enableDraggedPokemon(int x, int y){
        draggedX = x;
        draggedY = y;
        getBoxPokemon(x+y).setVisible(false);
        draggedPokemon.setIcon(getBoxPokemon(x+y).getIcon());
        draggedPokemon.setVisible(true);
    }
    
    public void moveDraggedPokemon(int x, int y){
        x = x - draggedPokemon.getWidth()/3 + 10;
        y = y - draggedPokemon.getHeight() + 5;
        draggedPokemon.setLocation(x, y);
    }
    
    public void disableDraggedPokemon(int x, int y){
        storage.swapPokemon(currentBox,draggedX+draggedY,x+y);
        draggedPokemon.setVisible(false);
        getBoxPokemon(draggedX+draggedY).setVisible(true);
        Icon temp = getBoxPokemon(draggedX+draggedY).getIcon();
        setBoxPokemonIcon(draggedX + draggedY, getBoxPokemon(x+y).getIcon());
        setBoxPokemonIcon(x+y, temp);
    }
}