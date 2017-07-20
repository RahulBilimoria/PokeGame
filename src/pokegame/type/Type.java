/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.type;

import java.awt.image.BufferedImage;
import pokegame.gfx.ImageLoader;
import pokegame.utils.Utils;

/**
 *
 * @author Rahul
 */
public class Type {
    
    private final static int TYPE_COUNT = 18;
    public final static Type TYPE_LIST[] = new Type[18];
    public final static float MODIFIER[][] = new float[18][18];
    public static final int NORMAL = 0;
    public static final int FIGHT = 1;
    public static final int FLYING = 2;
    public static final int POISON = 3;
    public static final int GROUND = 4;
    public static final int ROCK = 5;
    public static final int BUG = 6;
    public static final int GHOST = 7;
    public static final int STEEL = 8;
    public static final int FIRE = 9;
    public static final int WATER = 10;
    public static final int GRASS = 11;
    public static final int ELECTRIC = 12;
    public static final int PSYCHIC = 13;
    public static final int ICE = 14;
    public static final int DRAGON = 15;
    public static final int DARK = 16;
    public static final int FAIRY = 17;
    
    private int id;
    private String name;
    private BufferedImage image;
    
    public Type(int id, String name){
        this.id = id;
        this.name = name;
        setImage();
    }
    
    public static void init(){
        String file = Utils.loadFileAsString("dat/pokemon/type/TypeList.dat");
        String[] mod = file.split("\\s+");
        for (int x = 0; x < TYPE_COUNT; x++){
            TYPE_LIST[x] = new Type(x, mod[x]);
        }
        for (int x = 0; x < TYPE_COUNT; x++){
            for (int y = 0; y < TYPE_COUNT; y++){
                MODIFIER[x][y] = Utils.parseFloat(mod[(x*TYPE_COUNT) + y + TYPE_COUNT]);
            }
        } 
    }
    
    public static float getEffectiveness(Type moveType, Type type1, Type type2){
        if (type2 == null){
            return MODIFIER[moveType.getID()][type1.getID()];
        } else {
            return (MODIFIER[moveType.getID()][type1.getID()] * MODIFIER[moveType.getID()][type2.getID()]);
        }
    }
    
    public void setImage(){
        image = ImageLoader.loadImage("/type/" + name.toLowerCase() + ".png");
    }
    
    public BufferedImage getImage(){
        return image;
    }
    
    public static Type getType(int i){
        if (i < 0){
            return null;
        }
        return TYPE_LIST[i];
    }
    
    public int getID(){
        return id;
    }
    
    public String getName(){
        return name;
    }
}
