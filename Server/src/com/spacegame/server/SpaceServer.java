/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacegame.server;

import com.jme3.app.SimpleApplication;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.system.JmeContext;
import com.spacegame.server.messages.Update;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cam
 */
public class SpaceServer extends SimpleApplication {
    Scanner in;
    int serverPort;
    Server server;
    public static void main(String args[]){
        SpaceServer serverApp = new SpaceServer();
        serverApp.runSetup();
        serverApp.start(JmeContext.Type.Headless);
        System.out.println("Server is running.");
    }
    
    @Override
    public void simpleInitApp(){
        try {
            server = Network.createServer(serverPort);
            ConfigureListeners.configure(server);
        } catch (IOException ex) {
            Logger.getLogger(SpaceServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //update method for the server, gets called n times per "frame"
    @Override
    public void simpleUpdate(float tpf){
        //update foo
        server.broadcast(new Update(UpdateHandler.getElements()));
    }
    
    private void runSetup(){
        in = new Scanner(System.in);
        System.out.println("Super Crazy Space Maniac Game Deluxe 4");
        System.out.print("Please choose a port to run the server on:> ");
        serverPort = in.nextInt();
        serverPort = (serverPort > 0 && serverPort < 9999) ? serverPort : 666;
        System.out.println("Creating server...");
    }
}
