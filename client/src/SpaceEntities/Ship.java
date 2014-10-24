/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SpaceEntities;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;

/**
 *
 * @author gwAwr
 */
public class Ship extends Element{
    public Ship(String model, AssetManager am){
        spatial = am.loadModel(model);
        this.currentNode.attachChild(spatial);
    }
    
    /*update method is automatically called by SimpleAppUpdate method, theres
     no need to call it anywhere*/
    public void update(float tpf){
        this.spatial.move(new Vector3f(direction.x*tpf, direction.y*tpf, direction.z*tpf));
        this.position = spatial.getLocalTranslation();
    }
}

