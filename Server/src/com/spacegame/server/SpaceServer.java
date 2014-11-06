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
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cam
 */
public class SpaceServer extends SimpleApplication {
    int serverPort;
    Server server;
    public static void main(String args[]){
        SpaceServer serverApp = new SpaceServer();
        DisplayInfo display = new DisplayInfo();
        serverApp.runServerSetup();
        serverApp.start(JmeContext.Type.Headless);
        System.out.println("Server is running.");
        
        Timer timer = new Timer();
        timer.schedule(display, 0, 3000);
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
    }
    
    private void runServerSetup(){
        Scanner in = new Scanner(System.in);
        System.out.println("Super Crazy Space Maniac Game Deluxe 4");
        System.out.print("Please choose an available port to run the server on:> ");
        serverPort = in.nextInt();
        serverPort = (serverPort > 0 && serverPort < 65535) ? serverPort : 2526;
        System.out.println("Creating server on port " + serverPort + "...");
    }
}

class DisplayInfo extends TimerTask {
    String info = "";
    @Override
    public void run(){
        displayInfo();
    }
    
    public void publishInfo(String info){
        this.info = info;
    }
    
    private void displayInfo(){
        if(info.equals(""))
            System.out.println("No information to display yet.");
        else
            System.out.println(info);
    }
}
