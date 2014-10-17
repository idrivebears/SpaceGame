
package Networking;

import java.util.ArrayList;

/**
 *
 * @author alexwalls
 */
public class ClientMessage {
    
    private String playerID;
    private String keyPressed;
    
    public ClientMessage(String id, String keyPressed){
        playerID = id;
        this.keyPressed = keyPressed;
    }
    public String getID(){
        return playerID;
    }
    public String getPressedKeys(){
        return keyPressed;
    }
    
    
    
}
