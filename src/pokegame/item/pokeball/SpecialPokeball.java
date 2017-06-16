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
import static pokegame.item.pokeball.Pokeball.pokeballs;
import pokegame.pokemon.Pokemon;
import pokegame.type.Type;
import pokegame.utils.DocumentParser;
import pokegame.utils.Utils;

/**
 *
 * @author Rahul
 */
public class SpecialPokeball extends Pokeball {

    public static final int SPECIAL_ITEM_COUNT = 16;
    public static Item specialPokeballs[] = new SpecialPokeball[SPECIAL_ITEM_COUNT];

    private int id;
    private int type;
    private float catchRates[] = new float[3];

    public SpecialPokeball(int id, String name, float catchRate, float catchRates[], int type, String effect, String description, int x, int y) {
        super(id, name, catchRate, effect, description, x, y);
        this.catchRates = catchRates;
        this.type = type;
    }

    public static void init() {
        Document d = DocumentParser.loadDataFile("dat/item/pokeball/SpecialPokeball.xml");
        NodeList list = d.getElementsByTagName("Pokeball");
        for (int temp = 0; temp < list.getLength(); temp++) {
            Element element = (Element) (list.item(temp));
            Element icon = (Element) (element.getElementsByTagName("Icon").item(0));
            Element catchRates = (Element) (element.getElementsByTagName("CatchRates").item(0));
            specialPokeballs[temp] = new SpecialPokeball(
            Utils.parseInt(element.getElementsByTagName("ID").item(0).getTextContent()),
            element.getElementsByTagName("Name").item(0).getTextContent(),
            Utils.parseFloat(catchRates.getElementsByTagName("Rate1").item(0).getTextContent()),
            new float[]{Utils.parseFloat(catchRates.getElementsByTagName("Rate2").item(0).getTextContent()),
                        Utils.parseFloat(catchRates.getElementsByTagName("Rate3").item(0).getTextContent()),
                        Utils.parseFloat(catchRates.getElementsByTagName("Rate4").item(0).getTextContent())},
            Utils.parseInt(element.getElementsByTagName("Condition").item(0).getTextContent()),
            element.getElementsByTagName("Effect").item(0).getTextContent(),
            element.getElementsByTagName("Description").item(0).getTextContent(),
            Utils.parseInt(icon.getElementsByTagName("x").item(0).getTextContent()),
            Utils.parseInt(icon.getElementsByTagName("y").item(0).getTextContent()));
        }
    }

    public int getType() {
        return type;
    }

    public float getSecondaryCatchRate(int id) {
        return catchRates[id];
    }

    @Override
    public void use(Pokemon p){
        
    }
    
    @Override
    public float battleUse(Pokemon player, Pokemon enemy){
        switch(type){
            case 0: return levelBall(player, enemy);
            case 1: return lureBall(player);
            case 2: return moonBall(enemy);
            case 3: return friendBall(enemy);
            case 4: return loveBall(player, enemy);
            case 5: return heavyBall(enemy);
            case 6: return fastBall(enemy);
            case 7: return repeatBall(enemy);
            case 8: return timerBall(player);
            case 9: return nestBall(enemy);
            case 10: return netBall(enemy);
            case 11: return diveBall(player);
            case 12: return luxuryBall(enemy);
            case 13: return healBall(enemy);
            case 14: return quickBall(enemy);
            case 15: return duskBall(enemy);
        }
        return 0;
    }
    
    private float levelBall(Pokemon player, Pokemon enemy){
        if (player.getLevel() <= enemy.getLevel()){
            return catchRate;
        } else if (player.getLevel() <= enemy.getLevel()*2){
            return catchRates[0];
        } else if (player.getLevel() <= enemy.getLevel()*4){
            return catchRates[1];
        } else
            return catchRates[2];
    }
    
    private float lureBall(Pokemon player){
        return catchRate;
    }
    
    private float moonBall(Pokemon e){
        if (e.getID() >= 28 && e.getID() <= 35 || e.getID() == 38 || e.getID() == 39){
            return catchRates[0];
        }
        return catchRate;
    }
    
    private float friendBall(Pokemon e){
        e.setFriendship(200);
        return catchRate;
    }
    
    private float loveBall(Pokemon player, Pokemon enemy){
        if (player.getID() == enemy.getID() && player.getGender() != enemy.getGender()){
            return catchRates[0];
        }
        return catchRate;
    }
    
    private float heavyBall(Pokemon e){
        return catchRate;
    }
    
    private float fastBall(Pokemon e){
        if (Pokemon.POKEMON_LIST[e.getID()].getSpeed() >= 10){
            return catchRates[0];
        }
        return catchRate;
    }
    
    private float repeatBall(Pokemon p){
        return catchRate;
    }
    
    private float timerBall(Pokemon p){
        return catchRate;
    }
    
    private float nestBall(Pokemon e){
        float rate = catchRates[0] - catchRates[1] * (e.getLevel() - catchRate);
        if (rate < 1.0)
            rate = 1.0f;
        return rate;
    }
    
    private float netBall(Pokemon e){
        if (e.getType1().getID() == Type.WATER || e.getType1().getID() == Type.BUG){
            return catchRates[0];
        } else if (e.getType2() != null){
            if (e.getType2().getID() == Type.WATER || e.getType2().getID() == Type.BUG){
                return catchRates[0];
            }
        }
        return catchRate;
    }
    
    private float diveBall(Pokemon p){
        return catchRate;
    }
    
    private float luxuryBall(Pokemon e){
        e.setFriendshipRate(3);
        return catchRate;
    }
    
    private float healBall(Pokemon p){
        return catchRate;
    }
    
    private float quickBall(Pokemon p){
        return catchRate;
    }
    
    private float duskBall(Pokemon p){
        return catchRate;
    }
}
