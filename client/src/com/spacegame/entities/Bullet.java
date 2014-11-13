/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacegame.entities;

import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import com.spacegame.util.ElementData;

/**
 *
 * @author Usuario
 */
public class Bullet extends Element {
    
    
    private Material mat;
    
    public Bullet(Vector3f position, Vector3f direction){
        Sphere bullet = new Sphere(100,100,.4f);
        Geometry bulletg = new Geometry("bullet", bullet);
        bulletg.setMaterial(mat);
        //bulletg.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        elementData = new ElementData();
        
        this.currentNode.attachChild(bulletg);
        /*
         *             
            
    Box cube1Mesh = new Box( 1f,1f,1f);
    Geometry cube1Geo = new Geometry("My Textured Box", cube1Mesh);
    cube1Geo.setLocalTranslation(new Vector3f(-3f,1.1f,0f));
    Material cube1Mat = new Material(assetManager, 
        "Common/MatDefs/Misc/Unshaded.j3md");
    Texture cube1Tex = assetManager.loadTexture(
        "Interface/Logo/Monkey.jpg");
    cube1Mat.setTexture("ColorMap", cube1Tex);
    cube1Geo.setMaterial(cube1Mat);
    rootNode.attachChild(cube1Geo);
         */
    }
    
}
