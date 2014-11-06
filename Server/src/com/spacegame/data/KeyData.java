/*
 * This class is simply a container for the KEY pressed and the ID of the 
 * client who pressed the KEY
 */
package com.spacegame.data;

public class KeyData{
    public int ID;
    public char KEY;
    
    public KeyData(int clientID, char key){
        this.ID = clientID;
        this.KEY = key;
    }
    /*
    public int ID(){
        return ID;
    }
    public char KEY(){
        return KEY;
    }*/
}
