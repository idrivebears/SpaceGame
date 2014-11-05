
package com.spacegame.server;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.spacegame.data.KeyData;
import com.spacegame.server.messages.*;

/**
 *
 * @author Cam
 */
public class ServerListener implements MessageListener<HostedConnection> {
    
    public void messageReceived(HostedConnection source, Message m) {
        if(m instanceof Input){
            //recieved player input, update library
            char input = ((Input)m).input; //gets the player input, which could be any Object
            StateProcessor.updatePlayer(new KeyData(source.getId(), input));
            //the player updates itself in the server library.
        }
    }
}