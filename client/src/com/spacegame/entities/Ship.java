/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacegame.entities;

import com.spacegame.networking.ElementData;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import jme3test.bullet.BombControl;

/**
 *
 * @author gwAwr
 */
public class Ship extends Element{

    private SphereCollisionShape CShip;
    private CharacterControl ShipControl;
    private Sphere bullet;
    private Geometry bulletg;
    private AssetManager am;
    private SphereCollisionShape BulletCS;
    private RigidBodyControl BulletControl;
    private BulletAppState BAS;
    
    private float speed = 1f; //default speed
    private float radius = 5f; // default radius of collision shape
    private float bulletradius = 1f;
    private float bulletspeed = 500f;
    // Speed of rotation, 3 Axes
    private float pitchSpeed; 
    private float rollSpeed;
    private float yawSpeed;
    
    //Angle between camera and ship
    private float angleCamX;
    private float angleCamY;
    private float angleCamZ;
    
    public Ship(String model, AssetManager am,BulletAppState BAS){
        elementData = new ElementData();
        
        spatial = am.loadModel(model);
        this.currentNode.attachChild(spatial);
        CShip = new SphereCollisionShape(radius);
        ShipControl = new CharacterControl(CShip,1f);
        spatial.addControl(ShipControl);
        BAS.getPhysicsSpace().add(ShipControl);
        this.am=am;
        this.BAS=BAS;
    }
    
    // Getters and setters
    public SphereCollisionShape getShipCollisionShape(){
        return CShip;
    }
    
    public CharacterControl getShipControl(){
        return ShipControl;
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
     * pitch is around x axis. "Up" and "down"
     * roll is around z axis. Like a wheel.
     * yaw is around y axis. Like a dancer around a tube.*/
     
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
        this.ShipControl.setWalkDirection(mov.mult(-this.getSpeed()));
        //this.setDirection(mov.mult(-this.getSpeed()));
        //this.setDirection(this.ShipControl.getWalkDirection());
    }
    
    //Shooting. 
    public void shoot(){
        
        bullet = new Sphere(100,100,bulletradius);
        bulletg = new Geometry("bullet", bullet);
        mat = new Material(am,"Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        bulletg.setMaterial(mat);
        bulletg.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        //bulletg.setLocalTranslation(0,0,0);
        bulletg.setLocalTranslation(this.ShipControl.getPhysicsLocation().add(this.getLocalRotation().mult(new Vector3f(0,0,-radius-bulletradius-1))));
        BulletCS = new SphereCollisionShape(bulletradius);
        BulletControl = new BombControl(am,BulletCS,1f);
        BulletControl.setLinearVelocity(this.getLocalRotation().getRotationColumn(2).normalize().mult(-bulletspeed));
        bulletg.addControl(BulletControl);
        this.currentNode.attachChild(bulletg);
        BAS.getPhysicsSpace().add(BulletControl);
        //System.out.println(this.getLocalRotation().getRotationColumn(2));
        //Bullet bullet = new Bullet(,am,this.getPosition(), this.getLocalRotation().getRotationColumn(2).normalize());
        
    }    
    /*update method is automatically called by SimpleAppUpdate method, theres
     no need to call it anywhere*/
    public void update(float tpf){
        
        //elementData is inherited from Element class, and is private to the object
        //elementData contains direction, position and angle
        //this.elementData.setDirection(this.getShipControl().getWalkDirection());
        
        this.spatial.move(new Vector3f(
               // this.getShipControl().getPhysicsLocation().x*tpf,
               // this.getShipControl().getPhysicsLocation().y*tpf,
               // this.getShipControl().getPhysicsLocation().z*tpf)
                this.elementData.getDirection().x*tpf, 
                this.elementData.getDirection().y*tpf,
                this.elementData.getDirection().z*tpf)
                );
                //spatial.setLocalTranslation(this.getShipControl().getPhysicsLocation());
        elementData.setPosition(spatial.getLocalTranslation());
        //this.spatial.setLocalTranslation(this.getShipControl().getPhysicsLocation());
        elementData.setPosition(this.getShipControl().getPhysicsLocation());
    }
}

