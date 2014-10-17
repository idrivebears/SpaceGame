/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SpaceNetworking;

import com.jme3.math.Vector3f;

/**
 *
 * @author alexwalls
 */
public class ServerMessage {
    private Vector3f direction;
    private Vector3f position;
    
    public ServerMessage(Vector3f direction, Vector3f position){
        this.direction = direction;
        this.position = position;
    }
    
    public Vector3f getDirection(){
        return direction;
    }
    
    public Vector3f getPosition(){
        return position;
    }
    
    
    
}
