package com.spacegame.server;

import java.util.ArrayList;
/*
 * StateProcessor contains 2 static linked lists, elements and keysPressed
 * elements handles the states of all the players in the server
 * keysPressed is a Queue with all of the recieved keys from clients
 */

public class StateProcessor {
    
    public static ArrayList<ElementData> elements = new ArrayList<ElementData>();
    
    //updatePlayers will take an input and add it to the Queue
    //the queue is later polled for processing the movements of players
    public static void updatePlayers(ElementData clientData) {
        boolean idExists = false;
        
        //Here we check for the element in the library with the matching ID and 
        //then update that player to the new stats.
        for(ElementData ed : elements){
            if(ed.getID() == clientData.getID()){
                idExists = true;
                ed.updateData(clientData);
                break;
            }
        }
        //If the player sending the package does not exist in the elements library,
        //they get addes as a new ElementData
        if(!idExists){
            elements.add(new ElementData(clientData));
        }
    }
}
