/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.pokemon.move;

import java.awt.image.BufferedImage;
import pokegame.gfx.ImageLoader;
import pokegame.type.Type;
import pokegame.utils.Utils;

/**
 *
 * @author Rahul
 */
public class Move {

    private static final int MOVE_COUNT = 621;
    private static final int MOVE_COL = 7; //columns
    public static final BaseMove MOVE_LIST[] = new BaseMove[MOVE_COUNT];

    public static class BaseMove {
        
        private static BufferedImage[] image = new BufferedImage[3];

        private final int id, maxPP, power, category, numOfHits;
        private final float acc;
        private final String name;
        private final Type type;

        public BaseMove(int id, String name, Type type, int category, int maxPP,
                int power, float acc) {
            this.id = id;
            this.name = name;
            this.type = type;
            this.category = category;
            this.maxPP = maxPP;
            this.power = power;
            this.acc = acc;
            numOfHits = 1;
        }
        
        public static void init(){
            image[0] = ImageLoader.loadImage("/type/physical.png");
            image[1] = ImageLoader.loadImage("/type/special.png");
            image[2] = ImageLoader.loadImage("/type/neutral.png");
        }
        
        public int getId(){
            return id;
        }
        
        public String getName(){
            return name;
        }
        
        public float getAcc(){
            return acc;
        }
        
        public int getMaxPP(){
            return maxPP;
        }
        
        public int getPower(){
            return power;
        }
        
        public int getCategory(){
            return category;
        }
        
        public Type getType(){
            return type;
        }
        
        public BufferedImage getImage(){
            return image[category];
        }
    }

    private int id;
    private int pp;

    public Move(int id) {
        this.id = id;
        pp = MOVE_LIST[id].getMaxPP();
    }

    public Move(BaseMove move) {
        id = move.getId();
        pp = move.getMaxPP();
    }

    public static void init() {
        String file = Utils.loadFileAsString("dat/pokemon/moves/MoveList.dat");
        String[] move = file.split("\\s+");
        BaseMove.init();
        for (int x = 0; x < MOVE_COUNT; x++) {
            int y = x * MOVE_COL;
            MOVE_LIST[x] = new BaseMove(Utils.parseInt(move[y]),
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
                return new Move(MOVE_LIST[x]);
            }
        }
        return null;
    }

    public static Move getMoveById(int id) {
        if (id < 0 || id >= MOVE_COUNT) {
            return null;
        }
        return new Move(MOVE_LIST[id]);
    }

    public void usePP() {
        if (pp > 0) {
            pp--;
        }
    }

    public void heal() {
        pp = getMaxPP();
    }
    
    public void addPP(int i){
        pp+=i;
        if (pp > getMaxPP()){
            pp = getMaxPP();
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return MOVE_LIST[id].getName();
    }

    public int getPP() {
        return pp;
    }

    public int getMaxPP() {
        return MOVE_LIST[id].getMaxPP();
    }

    public Type getType() {
        return MOVE_LIST[id].getType();
    }

    public int getPower() {
        return MOVE_LIST[id].getPower();
    }

    public int getCategory() {
        return MOVE_LIST[id].getCategory();
    }

    public BufferedImage getCategoryImage() {
        return MOVE_LIST[id].getImage();
    }

    public float getAccuracy() {
        return MOVE_LIST[id].getAcc();
    }

    public void setPP(int num) {
        pp = num;
    }
}