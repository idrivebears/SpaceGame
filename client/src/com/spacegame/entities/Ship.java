/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacegame.entities;

import com.spacegame.util.ElementData;
import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

/**
 *
 * @author gwAwr
 */
public class Ship extends Element{
    
    private float speed = 32f; //default speed
    private float pitchSpeed = 0; 
    private float rollSpeed = 0;
    
    public Ship(String model, AssetManager am){
        elementData = new ElementData();
        spatial = am.loadModel(model);
        this.currentNode.attachChild(spatial);
    }
    
    public void setSpeed(float speed){
        this.speed = speed;
    }
    public float getSpeed(){
        return speed;
    }
    
    public void setPitchSpeed(float s){
        this.pitchSpeed = s;
    }
    public float getPitchSpeed(){
        return this.pitchSpeed;
    }
    public void setRollSpeed(float s){
        this.rollSpeed = s;
    }
    public float getRollSpeed(){
        return this.rollSpeed;
    }
    
    ///Movement
    // Around x axe. Positive
    public void PitchUp(float tpf){
        Quaternion PITCH = new Quaternion().fromAngleAxis((FastMath.PI * tpf * pitchSpeed)/10, new Vector3f(1,0,0));
        this.setLocalRotation(this.getLocalRotation().mult(PITCH)); 
    }
    // Around x axe. Negative
    public void PitchDown(float tpf){
        Quaternion PITCH = new Quaternion().fromAngleAxis((FastMath.PI * tpf * pitchSpeed)/10, new Vector3f(1,0,0));
        this.setLocalRotation(this.getLocalRotation().mult(PITCH));
    }
    // Around z axe. Positive
    public void RollRight(float tpf){
        Quaternion ROLL = new Quaternion().fromAngleAxis((FastMath.PI * tpf * rollSpeed)/5, new Vector3f(0,0,1));
        this.setLocalRotation(this.getLocalRotation().mult(ROLL));
    }
    // Around z axe. Negative
    public void RollLeft(float tpf){
        Quaternion ROLL = new Quaternion().fromAngleAxis((FastMath.PI * tpf * rollSpeed)/5, new Vector3f(0,0,1));
        this.setLocalRotation(this.getLocalRotation().mult(ROLL));
    }
    
    //Shooting. TO DO
    public void Shoot(){
        
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

