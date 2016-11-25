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
