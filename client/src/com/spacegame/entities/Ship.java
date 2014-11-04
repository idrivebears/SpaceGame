/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacegame.entities;

import com.spacegame.util.ElementData;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;

/**
 *
 * @author gwAwr
 */
public class Ship extends Element{
    public Ship(String model, AssetManager am){
        elementData = new ElementData();
        spatial = am.loadModel(model);
        this.currentNode.attachChild(spatial);
    }
    
    /*update method is automatically called by SimpleAppUpdate method, theres
     no need to call it anywhere*/
    public void update(float tpf){
        
        //elementData is inherited from Element class, and is private to the object
        //elementData contains direction, position and angle
        this.spatial.move(new Vector3f(
                this.elementData.getDirection().x*tpf, 
                this.elementData.getDirection().y*tpf,
                this.elementData.getDirection().z*tpf)
                );
        elementData.setPosition(spatial.getLocalTranslation());
        
       
        
    }
}

