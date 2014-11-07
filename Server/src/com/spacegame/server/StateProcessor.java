package com.spacegame.server;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.spacegame.data.KeyData;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class StateProcessor {
    
    private static ArrayList<ElementData> elements = new ArrayList<ElementData>();
    private static ConcurrentLinkedQueue<KeyData> keysPressed = new ConcurrentLinkedQueue<KeyData>();
    
    public static void updatePlayer(KeyData input){
        boolean idExists = false;
        for(ElementData ed : elements){
            if(ed.getID() == input.ID){
                idExists = true;
                break;
            }
        }
        //If the player already exists, it adds the key to the queue
        if(idExists){
            keysPressed.add(input);
        }
        //If the player is not registered, it will create a new elementData with its
        //starting position, direction and angle, with the client-passed ID
        else{
            elements.add(new ElementData(input.ID, new Vector3f(), new Vector3f(), new Quaternion()));
            keysPressed.add(input);
        }
    }
    
    //This will start getting ElementDatas from the queue and calculate their movements
    public static void update(){
        KeyData keyPressed = keysPressed.poll();
        ElementData currentPlayer;
        //If the queue isnt empty
        if(keyPressed != null){
            //Getting the element data for the ID of key pressed
            //CANDIDATE FOR IMPROVEMENT/OPTIMIZATION
            for(ElementData e : elements){
                if(e.getID() == keyPressed.ID){
                    currentPlayer = e; //sets currentplayer(the one who pressed the key)
                    break;
                }
            }
            //start processing the key movement here (InputHandler code)
            
            
        }
        
    }
}
