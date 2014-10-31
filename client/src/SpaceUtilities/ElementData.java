
package SpaceUtilities;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

/**
 *
 * @author awalls
 * This is the data package to be sent between classes
 * Facilitates transfer of position data in the scene graph
 */
public class ElementData {
    private Vector3f direction;
    private Vector3f position;
    private Quaternion angle;

    public Vector3f getDirection() {
        return direction;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Quaternion getAngle() {
        return angle;
    }
    
    public ElementData(Vector3f d, Vector3f p, Quaternion a){
        direction = d;
        position = p;
        angle = a;
    }
    
}
