/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

/**
 *
 * @author Usuario
 */
public class VisualPlayer {
    private boolean isMainPlayer;
    private Vector3f direction;
    private Vector3f position;
    Quaternion currentAngle;
    
    public VisualPlayer(Vector3f position, Vector3f direction, Quaternion angle){
        this.position = position;
        this.direction = direction;
        this.currentAngle = angle;
    }
    
    
}
