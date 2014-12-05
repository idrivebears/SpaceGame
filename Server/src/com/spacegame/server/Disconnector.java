/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacegame.server;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Server;

/**
 *
 * @author Usuario
 */
public class Disconnector implements ConnectionListener {
    
    public void connectionRemoved(Server server, HostedConnection conn){
        int id = conn.getId();
        System.out.println("removed player with id: " + id);
        ElementData aLaVerga = new ElementData(id, new Vector3f(0, -100, 0), Vector3f.ZERO, Quaternion.ZERO, false);
        StateProcessor.updatePlayers(aLaVerga);
    }
    
    public void connectionAdded(Server server, HostedConnection conn) {
        //dont care what happens here
    }
}
