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
public class PPRestore extends Potion{
    
    public static final int ITEM_COUNT = 4;
    public static Item ppItems[] = new PPRestore[ITEM_COUNT];
    
    private int id;
    private int restoreAmount;
    private boolean restoreAllMoves;
    
    public PPRestore(int id, String name, int restoreAmount, boolean restoreAllMoves, String effect, String description, int x, int y) {
        super(name, 1, true, effect, description, x, y);
        this.id = id;
        this.restoreAmount = restoreAmount;
        this.restoreAllMoves = restoreAllMoves;
    }
    
    public static void init(){
        /*String file = Utils.loadFileAsString("dat/item/medicine/PPRestore.dat");
        String data[] = file.split("\\s+");
        for (int x = 0; x < ITEM_COUNT; x++){
            int i = x*4;
            boolean all = false;
            if (Utils.parseInt(data[i+3]) == 1)
                all = true;
            ppItems[x] = new PPRestore(Utils.parseInt(data[i]),
                    data[i+1].replaceAll("_", " "),
                    Utils.parseInt(data[i+2]),
                    all);
        }*/
        Document d = DocumentParser.loadDataFile("dat/item/medicine/PPRestore.xml");
        NodeList list = d.getElementsByTagName("Item");
        for (int temp = 0; temp < list.getLength(); temp++) {
            Element element = (Element) (list.item(temp));
            Element icon = (Element) (element.getElementsByTagName("Icon").item(0));
            boolean restoreAll = false;
            if (Utils.parseInt(element.getElementsByTagName("Condition").item(0).getTextContent()) == 1)
                restoreAll = true;
            ppItems[temp] = new PPRestore(
                    Utils.parseInt(element.getElementsByTagName("ID").item(0).getTextContent()),
                    element.getElementsByTagName("Name").item(0).getTextContent(),
                    Utils.parseInt(element.getElementsByTagName("RestoreAmount").item(0).getTextContent()),
                    restoreAll,
                    element.getElementsByTagName("Effect").item(0).getTextContent(),
                    element.getElementsByTagName("Description").item(0).getTextContent(),
                    Utils.parseInt(icon.getElementsByTagName("x").item(0).getTextContent()),
                    Utils.parseInt(icon.getElementsByTagName("y").item(0).getTextContent()));
        }
    }
    
    public int getRestoreAmount(){
        return restoreAmount;
    }
    
    public boolean getRestoreAllMoves(){
        return restoreAllMoves;
    }
    
    @Override
    public void use(Pokemon p){
        if (restoreAllMoves){

        }
    }
    
    @Override
    public float battleUse(Pokemon player, Pokemon enemy){
        return 0;
    }
    
}
