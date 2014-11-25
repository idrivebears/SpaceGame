
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
            char input = ((Input)m).input; //gets the player input, which could be any Object
            System.out.println("client["+source.getId()+"] :: " + input);
            StateProcessor.updatePlayer(new KeyData(source.getId(), input));
        }
        
        if(m instanceof Test)
        {
            System.out.println("Client says" + ((Test)m).getMessage());
        }
    }
}