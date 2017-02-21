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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    
    private JFrame frame;
    private JPanel boxPKMN, partyPKMN, menu, namePanel;
    private JButton leftArrow, rightArrow;
    private JLabel boxName;
    private JLabel boxPokemon[] = new JLabel[25];
    private JLabel partyPokemon[] = new JLabel[6];
    private JLabel boxBackground, background;
    
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
        
        createBox();
        createParty();
        createMenu();

        frame.add(boxPKMN).setBounds(21,(HEIGHT - boxPKMN.getHeight()) + 1,
                boxPKMN.getWidth(), boxPKMN.getHeight());
        frame.add(partyPKMN).setBounds(boxBackground.getIcon().getIconWidth(),
                (HEIGHT - boxBackground.getIcon().getIconHeight()) + (boxBackground.getIcon().getIconHeight()/2),
                partyPKMN.getWidth(), partyPKMN.getHeight());
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
        boxPKMN.setSize(462,336);
        boxPKMN.setLayout(new GridLayout(5,5));
        boxPKMN.setOpaque(false);
        for (int x = 0; x < 25; x++){
            if (storage.getId(currentBox, x) != -1){
                boxPokemon[x] = new JLabel(new ImageIcon(ImageLoader.loadImage("/pokemon/icons/" + 
                    (storage.getId(currentBox, x)+1) + ".png")));
            } else {
                boxPokemon[x] = new JLabel();
            }
            boxPKMN.add(boxPokemon[x]);
        }
    }
    
    private void createParty(){
        partyPKMN = new JPanel();
        partyPKMN.setSize(WIDTH - boxBackground.getIcon().getIconWidth(),boxBackground.getIcon().getIconHeight()/2);
        partyPKMN.setLayout(new GridLayout(6,2));
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
    
    public JLabel getPartyPokemon(int x){
        return partyPokemon[x];
    }
}