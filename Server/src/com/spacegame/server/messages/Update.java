/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacegame.server.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.spacegame.server.ElementData;
import java.util.ArrayList;

/**
 *
 * @author Cam
 */

@Serializable

//this is the message that will be sent FROM The server TO the client
public class Update extends AbstractMessage {
    private ArrayList<ElementData>infoPackage; 
    //container for the information
    //this will ususally (if not always) be a copy of the "library" in UpdateHandler
    
    public Update(){
        //empty constructor for serializer
    }
    public Update(ArrayList<ElementData>infoPackage){
        this.infoPackage = (ArrayList<ElementData>) infoPackage.clone(); //typecast?
    }
    public ArrayList<ElementData> getInfo(){
        //getter for the infoPackage.
        return this.infoPackage;
    }
}
