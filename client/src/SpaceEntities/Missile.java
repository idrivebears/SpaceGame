package SpaceEntities;

import com.jme3.math.Vector3f;

/**
 *
 * @author Alex
 */
public class Missile extends Element{
    float speed;
    public Missile(Vector3f position, Vector3f direction, float initialSpeed){
        this.position = position;
        this.direction = direction;
        this.speed = initialSpeed;
    }
    
    
    
}
