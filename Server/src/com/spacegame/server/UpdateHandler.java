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
    
    private static ArrayList<ElementData> library = new ArrayList<ElementData>();
    
    public static void update(int id, char input){
        //client sent input, update the librarys
        library.set(id, updatePlayer(input)); //gets the player and updates it.
    }
    private static ElementData updatePlayer(char input){
        return new ElementData(true); //dummy elementData, that will be assigned to a certain player
    }
    
    public static ArrayList<ElementData> getGameInfo(){
        return library; //gets the library (mainly used by the broadcasting function)
    }
}
