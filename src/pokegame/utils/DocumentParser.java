/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Rahul
 */
public class DocumentParser {
    
    public static Document loadDataFile(String path){
        
        try{
           File inputfile = new File(path); 
           DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
           DocumentBuilder builder = factory.newDocumentBuilder();
           Document document = builder.parse(inputfile);
           document.getDocumentElement().normalize();
           return document;
        } catch (FileNotFoundException e){
            e.printStackTrace();
            //System.out.println("File Not Found");
        } catch (ParserConfigurationException | SAXException | IOException e){
            
        }
        
        return null;
    }
    
}
