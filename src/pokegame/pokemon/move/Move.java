/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.pokemon.move;

import pokegame.type.Type;
import pokegame.utils.Utils;

/**
 *
 * @author Rahul
 */
public class Move {

    private static final int MOVE_COUNT = 621;
    private static final int MOVE_COL = 7; //columns
    public static final Move MOVE_LIST[] = new Move[MOVE_COUNT];

    private int id, maxPP, pp, power, category;
    private float acc;
    private String name;

    private Type type;

    public Move(int id, String name, Type type, int category, int maxPP, int power, float acc) {
        this.id = id;
        this.name = name;
        this.maxPP = maxPP;
        pp = maxPP;
        this.type = type;
        this.power = power;
        this.acc = acc;
        this.category = category;
    }

    public Move(Move move) {
        this.id = move.getId();
        this.name = move.getName();
        this.maxPP = move.getMaxPP();
        pp = maxPP;
        this.type = move.getType();
        this.power = move.getPower();
        this.acc = move.getAccuracy();
        this.category = move.getCategory();
    }

    public static void init() {
        String file = Utils.loadFileAsString("dat/pokemon/moves/MoveList.dat");
        String[] move = file.split("\\s+");
        for (int x = 0; x < MOVE_COUNT; x++) {
            int y = x * MOVE_COL;
            MOVE_LIST[x] = new Move(Utils.parseInt(move[y]),
                    move[y + 1].replaceAll("_", " "),
                    Type.TYPE_LIST[Utils.parseInt(move[y + 2])],
                    Utils.parseInt(move[y + 3]),
                    Utils.parseInt(move[y + 4]),
                    Utils.parseInt(move[y + 5]),
                    Utils.parseFloat(move[y + 6]));
        }
    }

    public static Move getMoveByName(String name) {
        for (int x = 0; x < MOVE_COUNT; x++) {
            if (MOVE_LIST[x].getName().equalsIgnoreCase(name.replaceAll("_", " "))) {
                return MOVE_LIST[x];
            }
        }
        return null;
    }

    public void usePP() {
        if (pp > 0) {
            pp = pp - 1;
        }
    }

    public void heal() {
        pp = maxPP;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPP() {
        return pp;
    }

    public int getMaxPP() {
        return maxPP;
    }

    public Type getType() {
        return type;
    }

    public int getPower() {
        return power;
    }

    public int getCategory() {
        return category;
    }

    public float getAccuracy() {
        return acc;
    }

    public void setPP(int num) {
        pp = num;
    }

}
