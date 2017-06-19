/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.item.berry;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import pokegame.item.Item;
import pokegame.pokemon.Pokemon;
import pokegame.pokemon.status.Status;
import pokegame.type.Type;
import pokegame.utils.DocumentParser;
import pokegame.utils.Utils;

/**
 *
 * @author Rahul
 */
public class Berry extends Item{
    
    public static final int ITEM_COUNT = 67;
    public static Berry itemList[] = new Berry[ITEM_COUNT];
    
    private int type;
    
    
    public Berry(int id, String name, int type, String effect, String description, int x, int y) {
        super(name, 2, true, effect, description, x, y);
        this.type = type;
    }
    
    public static void init(){
        Document d = DocumentParser.loadDataFile("dat/item/berry/Berry.xml");
        NodeList list = d.getElementsByTagName("Berry");
        for (int temp = 0; temp < list.getLength(); temp++) {
            Element element = (Element) (list.item(temp));
            Element icon = (Element) (element.getElementsByTagName("Icon").item(0));
            itemList[temp] = new Berry(
                    Utils.parseInt(element.getElementsByTagName("ID").item(0).getTextContent()),
                    element.getElementsByTagName("Name").item(0).getTextContent(),
                    Utils.parseInt(element.getElementsByTagName("Condition").item(0).getTextContent()),
                    element.getElementsByTagName("Effect").item(0).getTextContent(),
                    element.getElementsByTagName("Description").item(0).getTextContent(),
                    Utils.parseInt(icon.getElementsByTagName("x").item(0).getTextContent()),
                    Utils.parseInt(icon.getElementsByTagName("y").item(0).getTextContent())
            );
        }
    }
    
    @Override
    public void use(Pokemon p){
        switch(type){
            case 16: addFriendship(p, 0, -1, 15);
                break;
            case 17: addFriendship(p, 1, -1, 15);
                break;
            case 18: addFriendship(p, 2, -1, 15);
                break;
            case 19: addFriendship(p, 3, -1, 15);
                break;
            case 20: addFriendship(p, 4, -1, 15);
                break;
            case 21: addFriendship(p, 5, -1, 15);
                break;
        }
    }
    
    @Override
    public float battleUse(Pokemon player, Pokemon enemy){
        switch(type){
            case 0:case 1:case 2:case 3:case 4:case 5:case 6:case 7:case 8:
            case 9:
                healStatus(player, type);
                break;
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                healPokemon(player);
                break;
            case 22: return Type.FIRE;
            case 23: return Type.WATER;
            case 24: return Type.ELECTRIC;
            case 25: return Type.GRASS;
            case 26: return Type.ICE;
            case 27: return Type.FIGHT;
            case 28: return Type.POISON;
            case 29: return Type.GROUND;
            case 30: return Type.FLYING;
            case 31: return Type.PSYCHIC;
            case 32: return Type.BUG;
            case 33: return Type.ROCK;
            case 34: return Type.GHOST;
            case 35: return Type.DRAGON;
            case 36: return Type.DARK;
            case 37: return Type.STEEL;
            case 38: return Type.NORMAL;
            case 39: if (statBerry(player)) return 18;
            case 40: if (statBerry(player)) return 19;
            case 41: if (statBerry(player)) return 20;
            case 42: if (statBerry(player)) return 21;
            case 43: if (statBerry(player)) return 22;
            case 44: if (statBerry(player)) return 23;
            case 45: if (statBerry(player)) return 24;
            case 46: if (statBerry(player)) return 25;
            case 47: if (statBerry(player)) return 26;
            case 48: if (statBerry(player)) return 27;
            case 49: if (statBerry(player)) return 28;
            case 50: if (statBerry(player)) return 29;
            case 51: if (statBerry(player)) return 30;
            case 52: if (statBerry(player)) return 31;
            case 53: if (statBerry(player)) return 32;
        }
        return -1;
    }
    
    private boolean statBerry(Pokemon player){
        return player.getHp() <= (int)Math.floor(player.getMaxHp()/4);
    }
    
    private void healStatus(Pokemon player, int status){
        switch(status){
            case 0: if (player.getStatus() == Status.PARALYZED) player.setStatus(Status.NORMAL);
                break;
            case 1: if (player.getStatus() == Status.SLEEP) player.setStatus(Status.NORMAL);
                break;
            case 2: if (player.getStatus() == Status.POISONED) player.setStatus(Status.NORMAL);
                break;
            case 3: if (player.getStatus() == Status.BURN) player.setStatus(Status.NORMAL);
                break;
            case 4: if (player.getStatus() == Status.FROZEN) player.setStatus(Status.NORMAL);
                break;
            case 5: player.restoreMovePP(10);
                break;
            case 6: player.heal(10);
                break;
            case 7: if (player.getStatus() == Status.CONFUSION) player.setStatus(Status.NORMAL);
                break;
            case 8: player.setStatus(Status.NORMAL);
                break;
            case 9: player.heal((int)(Math.floor(player.getMaxHp()/4)));
                break;
        }
    }
    
    private void healPokemon(Pokemon player){
          if (player.getHp() <= (int)(Math.floor(player.getMaxHp()/4)))
              player.heal((int)(Math.floor(player.getMaxHp()/2)));
    }
    
    private void addFriendship(Pokemon player, int statID, int statAmount, int friendshipAmount){
        switch(statID){
            case 0: player.addHp(statAmount);
                break;
            case 1: player.addAttack(statAmount);
                break;
            case 2: player.addDefense(statAmount);
                break;
            case 3: player.addSpAtt(statAmount);
                break;
            case 4: player.addDefense(statAmount);
                break;
            case 5: player.addSpeed(statAmount);
                break;
        }
        player.addFriendship(friendshipAmount);
    }
}
