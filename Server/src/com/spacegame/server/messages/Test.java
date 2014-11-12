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
public class Test extends AbstractMessage{
    String message;
    public Test(){
        this.message = "blank";
    }
    public Test(String input){
        this.message = input;
    }
    public String getMessage(){
        return this.message;
    }
    
}
