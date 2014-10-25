/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacegame.server;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.spacegame.server.messages.*;

/**
 *
 * @author Cam
 */
public class ServerListener implements MessageListener<HostedConnection> {
    public void messageReceived(HostedConnection source, Message m) {
        if(m instanceof Input){
            //recieved player input
            source.send(new Update(UpdateHandler.update()));
        }
    }
}