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
import pokegame.utils.DocumentParser;
import pokegame.utils.Utils;

/**
 *
 * @author Rahul
 */
public class StatBoost extends Potion{
    
    public static final int ITEM_COUNT = 16;
    public static Item statItems[] = new StatBoost[ITEM_COUNT];
    
    private int id;
    private int statId, increaseAmount;
    
    public StatBoost(int id, String name, int statId, int increaseAmount, String effect, String description, int x, int y) {
        super(name, 2, false, effect, description, x, y);
        this.id = id;
        this.statId = statId;
        this.increaseAmount = increaseAmount;
    }
    
    public static void init(){
        /*String file = Utils.loadFileAsString("dat/item/medicine/StatBoost.dat");
        String data[] = file.split("\\s+");
        for (int x = 0; x < ITEM_COUNT; x++){
            int i = x * 4;
            statItems[x] = new StatBoost(Utils.parseInt(data[i]),
                    data[i+1].replaceAll("_", " "),
                    Utils.parseInt(data[i+2]),
                    Utils.parseInt(data[i+3]));
        }*/
        Document d = DocumentParser.loadDataFile("dat/item/medicine/StatBoost.xml");
        NodeList list = d.getElementsByTagName("Item");
        for (int temp = 0; temp < list.getLength(); temp++) {
            Element element = (Element) (list.item(temp));
            Element icon = (Element) (element.getElementsByTagName("Icon").item(0));
            statItems[temp] = new StatBoost(
                Utils.parseInt(element.getElementsByTagName("ID").item(0).getTextContent()),
                element.getElementsByTagName("Name").item(0).getTextContent(),
                Utils.parseInt(element.getElementsByTagName("Condition").item(0).getTextContent()),
                Utils.parseInt(element.getElementsByTagName("BoostAmount").item(0).getTextContent()),
                element.getElementsByTagName("Effect").item(0).getTextContent(),
                element.getElementsByTagName("Description").item(0).getTextContent(),
                Utils.parseInt(icon.getElementsByTagName("x").item(0).getTextContent()),
                Utils.parseInt(icon.getElementsByTagName("y").item(0).getTextContent()));
        }
    }
    
    public int getStatId(){
        return statId;
    }
    
    public int getIncreaseAmount(){
        return increaseAmount;
    }
    
    @Override
    public void use(Pokemon p){
        switch(statId){
            case 0: p.addHp(increaseAmount);
                break;
            case 1: p.addAttack(increaseAmount);
                break;
            case 2: p.addDefense(increaseAmount);
                break;
            case 3: p.addSpAtt(increaseAmount);
                break;
            case 4: p.addSpDef(increaseAmount);
                break;
            case 5: p.addSpeed(increaseAmount);
            case 6: break;
            case 7: break;
            case 8: p.addLevel(increaseAmount);
                break;
            case 9:
                p.addHp(increaseAmount);
                p.addAttack(increaseAmount);
                p.addDefense(increaseAmount);
                p.addSpAtt(increaseAmount);
                p.addSpDef(increaseAmount);
                p.addSpeed(increaseAmount);
                break;
        }
    }
    
    @Override
    public float battleUse(Pokemon player, Pokemon enemy){
        return 0;
    }
}
