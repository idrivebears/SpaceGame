/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SpaceEntities;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

/**
 *
 * @author awalls
 */
public class VisualPlayer {
    private Vector3f direction;
    private Vector3f position;
    Quaternion currentAngle;
    
    public VisualPlayer(Vector3f position, Vector3f direction, Quaternion angle){
        this.position = position;
        this.direction = direction;
        this.currentAngle = angle;
    }  
    
    public Vector3f getDirection() {
        return direction;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Quaternion getCurrentAngle() {
        return currentAngle;
    }

    

    
    
}
