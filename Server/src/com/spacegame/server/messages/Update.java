/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacegame.server.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.spacegame.server.VisualPlayer;
import java.util.ArrayList;

/**
 *
 * @author Cam
 */
@Serializable
public class Update extends AbstractMessage {
    private ArrayList<VisualPlayer>infoPackage;
    public Update(){
        //empty constructor for serializer
    }
    public Update(ArrayList<VisualPlayer>infoPackage){
        this.infoPackage = (ArrayList<VisualPlayer>) infoPackage.clone(); //typecast?
    }
    public ArrayList<VisualPlayer> getInfo(){
        return this.infoPackage;
    }
}
