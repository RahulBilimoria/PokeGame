/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.item.potion;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import pokegame.item.Item;
import pokegame.pokemon.Pokemon;
import pokegame.pokemon.status.Status;
import pokegame.utils.DocumentParser;
import pokegame.utils.Utils;

/**
 *
 * @author Rahul
 */
public class Healing extends Potion{
    
    public static final int ITEM_COUNT = 18;
    public static Item healingItems[] = new Healing[ITEM_COUNT];
    
    private int id;
    private int healAmount;
    private int typeOfCondition;
    
    public Healing(int id, String name, int healAmount, int typeOfCondition, String effect, String description, int x, int y) {
        super(name, 0, true, effect, description, x, y);
        this.id = id;
        this.healAmount = healAmount;
        this.typeOfCondition = typeOfCondition;
    }
    
    public static void init(){
        Document d = DocumentParser.loadDataFile("dat/item/medicine/Healing.xml");
        NodeList list = d.getElementsByTagName("Item");
        for (int temp = 0; temp < list.getLength(); temp++) {
            Element element = (Element) (list.item(temp));
            Element icon = (Element) (element.getElementsByTagName("Icon").item(0));
            healingItems[temp] = new Healing(
            Utils.parseInt(element.getElementsByTagName("ID").item(0).getTextContent()),
            element.getElementsByTagName("Name").item(0).getTextContent(),
            Utils.parseInt(element.getElementsByTagName("HealAmount").item(0).getTextContent()),
            Utils.parseInt(element.getElementsByTagName("Condition").item(0).getTextContent()),
            element.getElementsByTagName("Effect").item(0).getTextContent(),
            element.getElementsByTagName("Description").item(0).getTextContent(),
            Utils.parseInt(icon.getElementsByTagName("x").item(0).getTextContent()),
            Utils.parseInt(icon.getElementsByTagName("y").item(0).getTextContent()));
            Item.items[temp] = healingItems[temp];
        }
    }
    
    public int getHealAmount(){
        return healAmount;
    }
    
    @Override
    public void use(Pokemon p){
        if (p.getHp() > 0){
            p.heal(healAmount);
            if (typeOfCondition == 1){
                p.setStatus(Status.NORMAL);
            }
        } else {
            if (typeOfCondition == 2){
                p.heal((int)Math.ceil(p.getMaxHp()/2));
            } else if (typeOfCondition == 3){
                p.heal(p.getMaxHp());
            }
        }
    }
    
    @Override
    public float battleUse(Pokemon player, Pokemon enemy){
        return 0;
    }
}
