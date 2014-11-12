/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacegame.networking;

import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;

/**
 *
 * @author Cam
 * DEPRECATED
 * Implemented this class straight into the client
 */
public class ClientListener implements MessageListener<Client> {
  public void messageReceived(Client source, Message message) {
    if (message instanceof Test) {
      // do something with the message
      System.out.println("UPDATE ARRIVED");
      //CONSIDER
      //    >this is assuming "Update" will be the actuall updtade message
      //    >this is assuming the file "Element data" @ com.spacegame.util.ElementData is correct
      // problem arises, the updatePlayerList function MUST be called from here
      // probable solution is to make the listener a nested class of main, and call it from there.
      // awating walls' confirmation...
     
      //WALLS ALL UPDATE CALLS ARE MADE FROM HERE
      
      
    }
  }
}
