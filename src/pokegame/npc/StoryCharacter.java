/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.npc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import pokegame.entity.player.Player;
import pokegame.handler.Handler;
import pokegame.item.Item;
import pokegame.npc.quest.Quest;
import pokegame.npc.quest.StoryQuest;
import pokegame.pokemon.Pokemon;
import pokegame.pokemon.move.Moveset;
import pokegame.pokemon.nature.Nature;
import pokegame.pokemon.status.Status;
import pokegame.utils.DocumentParser;
import pokegame.utils.Utils;

/**
 *
 * @author Rahul
 */
public class StoryCharacter extends NPC{
    
    private int storyId;
    private int storySequence;
    
    private String[] dialogue;
    private int dialogueLocation;
    
    private StoryQuest storyQuest;
    
    public StoryCharacter(Handler handler, int id, String name, int spriteId,
            int portraitID, int direction, float x, float y, int distanceToCenter, boolean canTurn,
            boolean canMove, boolean isSolid, int storyId, String[] speech) {
        super(handler, 1, id, name, spriteId, portraitID, direction, x, y, distanceToCenter, canTurn, canMove, isSolid);
        
        this.storyId = storyId;
        storyQuest = null;
        loadStoryQuest();
    }
    
    private void loadStoryQuest(){
        Document d = DocumentParser.loadDataFile("dat/game/quest/story" + storyId + ".xml");
        NodeList list = d.getElementsByTagName("Quest");
        Element element = (Element) (list.item(0));
        Element pokemon = (Element) (element.getElementsByTagName("PokemonReward").item(0));
        Element moveset = (Element) (pokemon.getElementsByTagName("Moveset").item(0));
        int itemRewardId = Utils.parseInt(element.getElementsByTagName("ItemRewardID").item(0).getTextContent());
        int pokemonRewardId = Utils.parseInt(pokemon.getElementsByTagName("ID").item(0).getTextContent());
        storyQuest = new StoryQuest(storyId, element.getElementsByTagName("Name").item(0).getTextContent(),
            Utils.parseInt(element.getElementsByTagName("ItemID").item(0).getTextContent()),
            Utils.parseInt(element.getElementsByTagName("ItemAmountRequired").item(0).getTextContent()),
            Utils.parseInt(element.getElementsByTagName("PokemonID").item(0).getTextContent()),
            Utils.parseInt(element.getElementsByTagName("PokemonLevel").item(0).getTextContent()),
            Utils.parseInt(element.getElementsByTagName("PokemonAmountRequired").item(0).getTextContent()),
            Utils.parseInt(element.getElementsByTagName("WildPokemonID").item(0).getTextContent()),
            Utils.parseInt(element.getElementsByTagName("WildPokemonLevel").item(0).getTextContent()),
            Utils.parseInt(element.getElementsByTagName("WildPokemonAmountRequired").item(0).getTextContent()),
            Utils.parseInt(element.getElementsByTagName("MapLocation").item(0).getTextContent()),
            Utils.parseInt(element.getElementsByTagName("NpcID").item(0).getTextContent()),
            Utils.parseInt(element.getElementsByTagName("KeyMapLocation").item(0).getTextContent()),
            Utils.parseInt(element.getElementsByTagName("KeyItemID").item(0).getTextContent()),
            Utils.parseInt(element.getElementsByTagName("LevelsToGain").item(0).getTextContent()));
        if (itemRewardId != -1)
            storyQuest.addReward(Item.items[itemRewardId], Utils.parseInt(element.getElementsByTagName("ItemRewardAmount").item(0).getTextContent()));
        storyQuest.addReward(Utils.parseInt(element.getElementsByTagName("PokemonExpReward").item(0).getTextContent()));
        if (pokemonRewardId != -1) {
            boolean shiny = false;
            if (Utils.parseInt(pokemon.getElementsByTagName("Shiny").item(0).getTextContent()) == 1) {
                shiny = true;
            }
            boolean male = false;
            if (Utils.parseInt(pokemon.getElementsByTagName("Gender").item(0).getTextContent()) == 1) {
                male = true;
            }
            storyQuest.addReward(new Pokemon(handler, pokemonRewardId, shiny,
                    Utils.parseInt(pokemon.getElementsByTagName("Level").item(0).getTextContent()),
                    Utils.parseInt(pokemon.getElementsByTagName("Hp").item(0).getTextContent()),
                    Utils.parseInt(pokemon.getElementsByTagName("Attack").item(0).getTextContent()),
                    Utils.parseInt(pokemon.getElementsByTagName("Defense").item(0).getTextContent()),
                    Utils.parseInt(pokemon.getElementsByTagName("SpecialAttack").item(0).getTextContent()),
                    Utils.parseInt(pokemon.getElementsByTagName("SpecialDefense").item(0).getTextContent()),
                    Utils.parseInt(pokemon.getElementsByTagName("Speed").item(0).getTextContent()),
                    pokemon.getElementsByTagName("Nickname").item(0).getTextContent(),
                    Status.getStatusById(Utils.parseInt(pokemon.getElementsByTagName("StatusID").item(0).getTextContent())),
                    Utils.parseInt(pokemon.getElementsByTagName("TpPoints").item(0).getTextContent()),
                    Utils.parseInt(pokemon.getElementsByTagName("Friendship").item(0).getTextContent()),
                    Utils.parseInt(pokemon.getElementsByTagName("FriendshipRate").item(0).getTextContent()),
                    Nature.getNatureById(Utils.parseInt(pokemon.getElementsByTagName("NatureID").item(0).getTextContent())),
                    male,
                    new Moveset(Utils.parseInt(moveset.getElementsByTagName("Move1").item(0).getTextContent()),
                            Utils.parseInt(moveset.getElementsByTagName("Move2").item(0).getTextContent()),
                            Utils.parseInt(moveset.getElementsByTagName("Move3").item(0).getTextContent()),
                            Utils.parseInt(moveset.getElementsByTagName("Move4").item(0).getTextContent())
                    )), Utils.parseInt(element.getElementsByTagName("PokemonRewardAmount").item(0).getTextContent()));
        }
    }
    
    @Override
    public void onInteract(Player player){
        
    }
    
    public String getNextMessage() {
        if (dialogueLocation >= dialogue.length) {
            return "done";
        }
        String s = dialogue[dialogueLocation];
        dialogueLocation++;
        return s;
    }
    
    public int getStoryId(){
        return storyId;
    }
    
    public StoryQuest getStoryQuest(){
        return storyQuest;
    }
}
