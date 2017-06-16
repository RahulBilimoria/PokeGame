/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.item.pokeball;

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
public class Pokeball extends Item {

    /* Normal Pokeball ID = 0
     * Special Pokeball ID = 1
     */
    public static final int ITEM_COUNT = 11;
    public static Pokeball pokeballs[] = new Pokeball[ITEM_COUNT];

    public static final int TOTAL_ITEM_COUNT = ITEM_COUNT
            + SpecialPokeball.SPECIAL_ITEM_COUNT;

    protected float catchRate;

    public Pokeball(int id, String name, float catchRate, String effect, String description, int x, int y) {
        super(name, 1, true, effect, description, x, y);
        this.catchRate = catchRate;
    }

    public static void init() {
        Document d = DocumentParser.loadDataFile("dat/item/pokeball/Pokeball.xml");
        NodeList list = d.getElementsByTagName("Pokeball");
        for (int temp = 0; temp < list.getLength(); temp++) {
            Element element = (Element) (list.item(temp));
            Element icon = (Element) (element.getElementsByTagName("Icon").item(0));
            pokeballs[temp] = new Pokeball(
                    Utils.parseInt(element.getElementsByTagName("ID").item(0).getTextContent()),
                    element.getElementsByTagName("Name").item(0).getTextContent(),
                    Utils.parseFloat(element.getElementsByTagName("CatchRate").item(0).getTextContent()),
                    element.getElementsByTagName("Effect").item(0).getTextContent(),
                    element.getElementsByTagName("Description").item(0).getTextContent(),
                    Utils.parseInt(icon.getElementsByTagName("x").item(0).getTextContent()),
                    Utils.parseInt(icon.getElementsByTagName("y").item(0).getTextContent()));
        }
        SpecialPokeball.init();
    }

    public float getCatchRate() {
        return catchRate;
    }

    @Override
    public void use(Pokemon p) {
        
    }
    
    @Override
    public float battleUse(Pokemon player, Pokemon enemy){
        return catchRate;
    }
}
