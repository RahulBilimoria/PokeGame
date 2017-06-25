/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.npc;

import java.awt.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import pokegame.entity.player.Player;
import pokegame.handler.Handler;
import pokegame.item.Item;
import pokegame.npc.quest.Quest;
import pokegame.pokemon.Pokemon;
import pokegame.pokemon.move.Moveset;
import pokegame.pokemon.nature.Nature;
import pokegame.pokemon.status.Status;
import pokegame.ui.Dialogue;
import pokegame.utils.DocumentParser;
import pokegame.utils.Utils;

/**
 *
 * @author Rahul
 */
public class QuestCharacter extends NPC {

    private int questID; // ID of quest
    private int questSequence; // Part of the quest that this npc is in (usually 0 = start)
    // This is just for the future
    private String[] questDialogue; // Dialogue the npc says when talking to him before accepting the quest
    private int dialogueLocation; // Location in the dialogue array you are in (resets on decline)

    private Quest quest; // Quest the NPC gives out

    public QuestCharacter(Handler handler, int id, String name, int spriteId,
            int portraitID, int direction, float x, float y, int distanceToCenter,
            boolean canTurn, boolean canMove, boolean isSolid, int questID,
            int questSequence, String[] questDialogue) {
        super(handler, 0, id, name, spriteId, portraitID, direction, x, y, distanceToCenter, canTurn, canMove, isSolid);
        this.questID = questID;
        this.questSequence = questSequence;
        this.questDialogue = questDialogue;
        dialogueLocation = 0;
        quest = null;
        loadQuest();
    }

    private void loadQuest() {
        Document d = DocumentParser.loadDataFile("dat/game/quest/quest" + questID + ".xml");
        NodeList list = d.getElementsByTagName("Quest");
        Element element = (Element) (list.item(0));
        Element pokemon = (Element) (element.getElementsByTagName("PokemonReward").item(0));
        Element moveset = (Element) (pokemon.getElementsByTagName("Moveset").item(0));
        String questName = element.getElementsByTagName("Name").item(0).getTextContent();
        int itemId = Utils.parseInt(element.getElementsByTagName("ItemID").item(0).getTextContent());
        int pokemonId = Utils.parseInt(element.getElementsByTagName("PokemonID").item(0).getTextContent());
        int wildPokemonId = Utils.parseInt(element.getElementsByTagName("WildPokemonID").item(0).getTextContent());
        int itemRewardId = Utils.parseInt(element.getElementsByTagName("ItemRewardID").item(0).getTextContent());
        int pokemonRewardId = Utils.parseInt(pokemon.getElementsByTagName("ID").item(0).getTextContent());
        int pokemonExpReward = Utils.parseInt(element.getElementsByTagName("PokemonExpReward").item(0).getTextContent());
        if (itemId != -1) {
            quest = new Quest(questID, questName, itemId,
                    Utils.parseInt(element.getElementsByTagName("ItemAmountRequired").item(0).getTextContent()));
        }
        if (pokemonId != -1) {
            if (quest == null) {
                quest = new Quest(questID, questName, pokemonId,
                        Utils.parseInt(element.getElementsByTagName("PokemonLevel").item(0).getTextContent()),
                        Utils.parseInt(element.getElementsByTagName("PokemonAmountRequired").item(0).getTextContent()));
            } else {
                quest.addRequirement(pokemonId,
                        Utils.parseInt(element.getElementsByTagName("PokemonLevel").item(0).getTextContent()),
                        Utils.parseInt(element.getElementsByTagName("PokemonAmountRequired").item(0).getTextContent()));
            }
        }
        if (wildPokemonId != -1) {
            if (quest == null) {
                quest = new Quest(questID, questName, wildPokemonId,
                        Utils.parseInt(element.getElementsByTagName("WildPokemonLevel").item(0).getTextContent()),
                        Utils.parseInt(element.getElementsByTagName("WildPokemonAmountRequired").item(0).getTextContent()),
                        Utils.parseInt(element.getElementsByTagName("MapLocation").item(0).getTextContent()));
            } else {
                quest.addRequirement(wildPokemonId,
                        Utils.parseInt(element.getElementsByTagName("WildPokemonLevel").item(0).getTextContent()),
                        Utils.parseInt(element.getElementsByTagName("WildPokemonAmountRequired").item(0).getTextContent()),
                        Utils.parseInt(element.getElementsByTagName("MapLocation").item(0).getTextContent()));
            }
        }
        if (itemRewardId != -1) {
            quest.addReward(Item.items[itemRewardId], Utils.parseInt(element.getElementsByTagName("ItemRewardAmount").item(0).getTextContent()));
        }
        if (pokemonExpReward != -1) {
            quest.addReward(pokemonExpReward);
        }
        if (pokemonRewardId != -1) {
            boolean shiny = false;
            if (Utils.parseInt(pokemon.getElementsByTagName("Shiny").item(0).getTextContent()) == 1) {
                shiny = true;
            }
            boolean male = false;
            if (Utils.parseInt(pokemon.getElementsByTagName("Gender").item(0).getTextContent()) == 1) {
                male = true;
            }
            quest.addReward(new Pokemon(handler, pokemonRewardId, shiny,
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
    public void onInteract(Player player) {
        switch (player.hasQuest(quest)) {
            case 0: // Quest not accepted
                Dialogue d = new Dialogue(handler, this, player);
                break;
            case 1: // Quest already completed
                player.setEnabled(true);
                handler.getGame().addText("[" + quest.getName() + "] has already been completed.\n", Color.orange);
                break;
            case 2: // Quest in progress
                questInProgress(player);
                break;
        }
    }

    public String getNextMessage() {
        if (dialogueLocation >= questDialogue.length) {
            return "done";
        }
        String s = questDialogue[dialogueLocation];
        dialogueLocation++;
        return s;
    }

    public void questInProgress(Player player) {
        player.handInItems(quest);
            player.handInPokemon(quest);
        if (player.checkQuest(quest)) {
            player.giveReward(quest);
        }
        else {
            handler.getGame().addText("[" + quest.getName() + "] " + player.getQuestRemaining(quest), Color.orange);
        }
        player.setEnabled(true);
    }

    public Quest getQuest() {
        return quest;
    }

    public void resetDialogue() {
        dialogueLocation = 0;
    }
}
