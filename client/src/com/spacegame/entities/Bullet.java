/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacegame.entities;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;


import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;
import jme3test.bullet.BombControl;

/**
 *
 * @author Usuario
 */
public class Bullet extends Element {
    
    private BulletAppState BAS;
    private Sphere bullet;
    private Geometry bulletg;
    private Material mat;
    private SphereCollisionShape BulletCS;
    private RigidBodyControl BulletControl;
    private float radius = .4f;
    private float speed = 100f;
    
    //Not completed

    public Bullet(AssetManager am, Vector3f position, Vector3f direction,BulletAppState BAS){
        //elementData = new ElementData();
        bullet = new Sphere(100,100,radius);
        bulletg = new Geometry("bullet", bullet);
        mat = new Material(am,"Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        bulletg.setMaterial(mat);
        bulletg.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        bulletg.setLocalTranslation(position);
        
        BulletCS = new SphereCollisionShape(radius);
        BulletControl = new BombControl(am,BulletCS,1f);
        BulletControl.setLinearVelocity(direction.mult(25));
        bulletg.addControl(BulletControl);


        this.bullet = new Sphere(100,100,.4f);
        this.bulletg = new Geometry("bullet", bullet);
        this.mat = new Material(am,"Common/MatDefs/Misc/Unshaded.j3md");
        this.setPosition(position);
        bulletg.setMaterial(mat);
        //bulletg.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);


        this.currentNode.attachChild(bulletg);
        BAS.getPhysicsSpace().add(BulletControl);
    }
    public RigidBodyControl getBulletControl(){
        return BulletControl;
    }
}
