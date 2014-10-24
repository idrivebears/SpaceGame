/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacegame.server;

import com.jme3.app.SimpleApplication;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.system.JmeContext;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cam
 */
public class SpaceServer extends SimpleApplication {
    public static void main(String args[]){
        SpaceServer app = new SpaceServer();
        app.start(JmeContext.Type.Headless);
        System.out.println("Server is running");
    }
    
    public void simpleInitApp(){
        Server server;
        try {
            server = Network.createServer(666);
            ConfigureListeners.configure(server);
        } catch (IOException ex) {
            Logger.getLogger(SpaceServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
