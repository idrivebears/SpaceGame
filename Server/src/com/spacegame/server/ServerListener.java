
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
            Input clientInput = (Input)m;
            StateProcessor.updatePlayers(new ElementData(source.getId(), clientInput.position, clientInput.direction)); //processes input into database
        }
    }
}