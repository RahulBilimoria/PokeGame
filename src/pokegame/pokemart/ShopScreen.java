/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.pokemart;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import pokegame.entity.player.Bag;
import pokegame.gfx.ImageLoader;
import pokegame.handler.Handler;
import pokegame.item.Item;

/**
 *
 * @author Rahul
 */
public class ShopScreen {
    
    private Handler handler;
    private Bag bag;
    
    private JFrame frame;
    private JPanel buy, sell, trade;
    private JButton buyOneButton, buyTenButton, sellOneButton, sellTenButton, 
                    tradeOneButton, tradeTenButton;
    private JLabel buyItem, buyCost, buyIcon; 
    private JLabel sellItem, sellCost, sellIcon;
    private JLabel tradeItem, tradeCost, tradeIcon;
    private JLabel background;
    private JComboBox buyOptions, sellOptions, tradeOptions;
    
    private String filename;
    private Item items[];
    
    public ShopScreen(Handler handler, Bag bag, String filename){
        this.handler = handler;
        this.bag = bag;
        this.filename = filename;
        frame = new JFrame("Shop");
        frame.setLayout(null);
        frame.setSize(400, 600);
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                exit();
            }
        }
        );
        
        background = new JLabel(new ImageIcon(ImageLoader.loadImage("/shop/shopBackground.png")));
        
        buy = createBuyPanel();
        sell = new JPanel();
        trade = new JPanel();

        sellOneButton = new JButton("Sell One");
        sellTenButton = new JButton("Sell Ten");
        tradeOneButton = new JButton("Trade One");
        tradeTenButton = new JButton("Trade Ten");
        
        frame.add(background).setBounds(0,0,background.getIcon().getIconWidth(), background.getIcon().getIconHeight());
              
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    private JPanel createBuyPanel(){
        JPanel p = new JPanel();
        buyOneButton = new JButton("Buy One");
        buyTenButton = new JButton("Buy Ten");
        buyItem = new JLabel("Buy: ");
        buyCost = new JLabel("Cost: ");
        buyIcon = new JLabel();
        buyIcon.setSize(50,50);
        return p;
    }
    
    private void createSellPanel(){
        
    }
    
    private void createTradePanel(){
        
    }
    
    public void exit(){
        handler.getWorld().closeShop();
    }
}
