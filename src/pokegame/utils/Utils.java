/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 *
 * @author minim_000
 */
public class Utils {

    public static String loadFileAsString(String path) {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line + "\n");
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static void saveStringAsFile(String worldName, String file) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("res/" + worldName), "utf-8"))) {
            writer.write(file);
            writer.close();
        } catch (IOException e){
            
        }
    }

    public static int parseInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public static float parseFloat(String number){
        try{
            return Float.parseFloat(number);
        }
        catch (NumberFormatException e){
            e.printStackTrace();
        }
        return -1;
    }

}
