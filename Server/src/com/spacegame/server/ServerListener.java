
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
            int id = source.getId();
            Input clientInput = (Input)m;
            //System.out.println("client["+id+"] :: " + input);
            StateProcessor.updatePlayer(new KeyData(id, clientInput)); //processes input into database
        }
        
        if(m instanceof Test)
        {
            System.out.println("Client says" + ((Test)m).getMessage());
        }
    }
}