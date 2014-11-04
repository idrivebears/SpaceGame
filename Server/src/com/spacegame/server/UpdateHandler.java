/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacegame.server;

import java.util.ArrayList;

/**
 *
 * @author Cam
 */
public class UpdateHandler {
    //library contains all the information of the moving elements of the game
    
    private static ArrayList<ElementData> elements = new ArrayList<ElementData>();
    
    public static void update(int id, char input){
        
        //if the id is not in elements, add new id to it
        
        //client sent input, update the elements
        elements.set(id, updatePlayer(id, input)); //gets the player and updates it.
        
        
        
    }
    private static ElementData updatePlayer(int id, char input){
        //process all player movements for each ElementData
        
        return new ElementData(); //dummy elementData, that will be assigned to a certain player
    }
    
    public static ArrayList<ElementData> getElements(){
        return elements; //gets the library (mainly used by the broadcasting function)
    }
}
