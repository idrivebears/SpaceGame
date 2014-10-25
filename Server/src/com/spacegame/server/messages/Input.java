/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacegame.server.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Cam
 */
@Serializable
public class Input extends AbstractMessage {
    char input;
    public Input(){
        this.input = 0; //empty constructor for serialization
    }
    public Input(char input){
        this.input = input;
    }
}
