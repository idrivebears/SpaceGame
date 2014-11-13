/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacegame.entities;

import com.spacegame.util.ElementData;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

/**
 *
 * @author gwAwr
 */
public class Ship extends Element{

    private SphereCollisionShape CShip;    
    
    private float speed = 0f; //default speed
    private float radius = 5f; // default radius of collision shape
    
    // Speed of rotation, 3 Axes
    private float pitchSpeed; 
    private float rollSpeed;
    private float yawSpeed;
    
    //Angle between camera and ship
    private float angleCamX;
    private float angleCamY;
    private float angleCamZ;
    
    public Ship(String model, AssetManager am){
        elementData = new ElementData();
        spatial = am.loadModel(model);
        this.currentNode.attachChild(spatial);
        CShip = new SphereCollisionShape(radius);
    }
    
    // Getters and setters
    public SphereCollisionShape getShipCollisionShape(){
        return CShip;
    }
    
    public void setSpeed(float speed){
        this.speed = speed;
    }
    public void setPitchSpeed(float s){
        this.pitchSpeed = s;
    }
    public void setRollSpeed(float s){
        this.rollSpeed = s;
    }
    public void setYawSpeed(float s){
        this.yawSpeed = s;
    }
    
    public float getSpeed(){
        return speed;
    }
    public float getPitchSpeed(){
        return this.pitchSpeed;
    }
    
    public float getRollSpeed(){
        return this.rollSpeed;
    }
    
    public float getYawSpeed(){
        return this.yawSpeed;
    }

    
    public float getAngleCamX() {
        return angleCamX;
    }

    public float getAngleCamY() {
        return angleCamY;
    }

    public float getAngleCamZ() {
        return angleCamZ;
    }

    public void setAngleCamX(float angleCamX) {
        this.angleCamX = angleCamX;
    }

    public void setAngleCamY(float angleCamY) {
        this.angleCamY = angleCamY;
    }

    public void setAngleCamZ(float angleCamZ) {
        this.angleCamZ = angleCamZ;
    }
    
    //Modify pitchspeed, up and down. Between interval [-1,1].
    public void modifyPitch(String direction){
        if(direction.equals("up")){
            if(pitchSpeed < 1) pitchSpeed += .1;
        } else if(direction.equals("down")){
            if(pitchSpeed > -1) pitchSpeed -= .1;
        }
    }
    
    //Modify rollspeed, up and down. Between interval [-1,1].
    public void modifyRoll(String direction){
        if(direction.equals("left")){
            if(rollSpeed < 1) rollSpeed += .05;
        } else if(direction.equals("right")){
            if(rollSpeed > -1) rollSpeed -= .05;
        }
    }
    
    //Modify yawSpeed, up and down. Between interval [-1,1].
     public void modifyYaw(String direction){
        if(direction.equals("left")){
            if(yawSpeed < 1) yawSpeed += .01;
        } else if(direction.equals("right")){
            if(yawSpeed > -1) yawSpeed -= .01;
        }
    }

    /* Real Movement
     * pitch is around x axe. "Up" and "down"
     * roll is around z axe. Like a wheel.
     * yaw is around y axe. Like a dancer around a tube.*/
     
    public void pitch(float tpf){
        Quaternion PITCH = new Quaternion().fromAngleAxis((FastMath.PI * tpf * pitchSpeed)/5, new Vector3f(1,0,0));
        this.setLocalRotation(this.getLocalRotation().mult(PITCH)); 
    }
    
    public void roll(float tpf){
        Quaternion ROLL = new Quaternion().fromAngleAxis((FastMath.PI * tpf * rollSpeed), new Vector3f(0,0,1));
        this.setLocalRotation(this.getLocalRotation().mult(ROLL));
    }
    
    public void yaw (float tpf){
        Quaternion YAW = new Quaternion().fromAngleAxis((FastMath.PI * tpf * yawSpeed), new Vector3f(0,1,0));
        this.setLocalRotation(this.getLocalRotation().mult(YAW));
    }
    
    // Always forward
    public void keepMoving(){
        Vector3f mov = this.getLocalRotation().getRotationColumn(2).normalize();
        this.setDirection(mov.mult(-this.getSpeed()));
    }
    
    //Shooting. 
    public void shoot(){
        //System.out.println(this.getLocalRotation().getRotationColumn(2));
        //Bullet bullet = new Bullet(,am,this.getPosition(), this.getLocalRotation().getRotationColumn(2).normalize());
        
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

