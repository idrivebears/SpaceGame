/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacegame.networking;

import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Cam
 */
@Serializable
public class Input extends AbstractMessage {
    public Vector3f position;
    public Vector3f direction;
    
    public Input(){//Empty
        
    }
    public Input(Vector3f pos, Vector3f dir){
        this.position = pos;
        this.direction = dir;
    }
    
}