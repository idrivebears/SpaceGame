/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacegame.server;

import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;
import com.spacegame.server.messages.*;

/**
 *
 * @author Cam
 */
public class ConfigureListeners {
    public static void configure(Server server){
        serialize();
        addListeners(server);
    }
    
    private static void serialize(){
        Serializer.registerClass(Update.class);
        Serializer.registerClass(Input.class);
        Serializer.registerClass(Test.class);
        Serializer.registerClass(ElementData.class);
    }
    
    private static void addListeners(Server server){
        server.addMessageListener(new ServerListener(), Update.class);
        server.addMessageListener(new ServerListener(), Input.class);
        server.addMessageListener(new ServerListener(), Test.class);
        server.addConnectionListener(new Disconnector()); //added listener for disconector
    }
}
