/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SpaceEntities;

import com.jme3.asset.AssetManager;

/**
 *
 * @author gwAwr
 */
public class Ship extends Element{
    public Ship(String model, AssetManager am){
        spatial = am.loadModel(model);
        this.currentNode.attachChild(spatial);
    }
}

