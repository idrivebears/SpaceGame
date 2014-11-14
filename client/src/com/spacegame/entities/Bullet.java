/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacegame.entities;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import com.spacegame.util.ElementData;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.ColorRGBA;

/**
 *
 * @author Usuario
 */
public class Bullet extends Element {
    
    private Sphere bullet;
    private Geometry bulletg;
    private Material mat;
    private SphereCollisionShape BulletCS;
    private CharacterControl BulletControl;
    private float radius = .4f;
    
    //Not completed
    public Bullet(String model, AssetManager am, Vector3f position, Vector3f direction){
        this.bullet = new Sphere(100,100,radius);
        this.bulletg = new Geometry("bullet", bullet);
        this.mat = new Material(am,"Common/MatDefs/Misc/Unshaded.j3md");
        this.setPosition(position);
        this.mat.setColor("Color", ColorRGBA.Blue);
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
