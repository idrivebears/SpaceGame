/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacegame.entities;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
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
import com.spacegame.util.BombControl;

public class Ship extends Element{
    
    private final float FINAL_SPEED = 2.0f;

    private SphereCollisionShape CShip;
    private CharacterControl ShipControl;
    private Sphere bullet;
    private Geometry bulletg;
    private AssetManager am;
    private SphereCollisionShape BulletCS;
    private RigidBodyControl BulletControl;
    private BulletAppState BAS;
    
    //Sounds
    private AudioNode audio_gun;
    private AudioNode audio_vehicleLaunch;

    protected float speed = FINAL_SPEED; //default speed

    private float radius = 3f; // default radius of collision shape
    private float bulletradius = 1f;
    public boolean isAlive;
    private float bulletspeed = 500f;
    // Speed of rotation, 3 Axes
    private float pitchSpeed; 
    private float rollSpeed;
    private float yawSpeed;
    
    //Angle between camera and ship
    
    public Ship(String model, AssetManager am,BulletAppState BAS){
        //elementData = new ElementData();
        
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

    
    @Override
    public Vector3f getPosition(){
        return this.ShipControl.getPhysicsLocation();
    }
    
    @Override
    public Vector3f getDirection(){
        return this.getLocalRotation().getRotationColumn(2).normalize();
    }   
    public Quaternion getRotadion(){
        return this.getLocalRotation();
    }
    
    //Modify pitchspeed, up and down. Between interval [-1,1].
    public void modifyPitch(String direction){
        if(direction.equals("up")){
            if(pitchSpeed < 1) pitchSpeed += .5;
        } else if(direction.equals("down")){
            if(pitchSpeed > -1) pitchSpeed -= .5;
        }
    }
    
    //Modify rollspeed, up and down. Between interval [-1,1].
    public void modifyRoll(String direction){
        if(direction.equals("left")){
            if(rollSpeed < 1) rollSpeed += .1;
        } else if(direction.equals("right")){
            if(rollSpeed > -1) rollSpeed -= .1;
        }
    }
    
    //Modify yawSpeed, up and down. Between interval [-1,1].
     public void modifyYaw(String direction){
        if(direction.equals("left")){
            if(yawSpeed < 1) yawSpeed += .05;
        } else if(direction.equals("right")){
            if(yawSpeed > -1) yawSpeed -= .05;
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
    public void keepMoving() {
        
        Vector3f mov = this.getLocalRotation().getRotationColumn(2).normalize();
        this.ShipControl.setWalkDirection(mov.mult(-this.getSpeed()));
        //this.setDirection(mov.mult(-this.getSpeed()));
        //this.setDirection(this.ShipControl.getWalkDirection());
        
    }
    
    //Shooting. 
    public void shoot(){
        audio_gun.playInstance();
        if(this.isAlive){
            this.createBullet(this.getDirection(), this.getPosition());
            
            if(!this.elementData.isShooting()){
                this.elementData.setShooting(true);
            }
        }
        else{
            this.isAlive=true;
            this.speed=FINAL_SPEED;
        }
        //this.ExplodeEffect();
        //System.out.println(this.getLocalRotation().getRotationColumn(2));
        //Bullet bullet = new Bullet(,am,this.getPosition(), this.getLocalRotation().getRotationColumn(2).normalize());
    }  
    public void createBullet(Vector3f direction,Vector3f position){
        bullet = new Sphere(100,100,bulletradius);
        bulletg = new Geometry("bullet", bullet);
        
        mat = new Material(am,"Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        
        bulletg.setMaterial(mat);
        bulletg.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        //bulletg.setLocalTranslation(0,0,0);
        bulletg.setLocalTranslation(position.add(this.getLocalRotation().mult(new Vector3f(0,0,-radius-bulletradius-1))));
        
        BulletCS = new SphereCollisionShape(bulletradius);
        BulletControl = new BombControl(am,BulletCS,1f);
        BulletControl.setLinearVelocity(direction.mult(-bulletspeed));
        
        bulletg.addControl(BulletControl);
        this.currentNode.attachChild(bulletg);
        BAS.getPhysicsSpace().add(BulletControl);
    }
    public void ExplodeEffect(){
        bullet = new Sphere(100,100,0);
        bulletg = new Geometry("bullet", bullet);
        
        mat = new Material(am,"Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        
        bulletg.setMaterial(mat);
        bulletg.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        //bulletg.setLocalTranslation(0,0,0);
        bulletg.setLocalTranslation(this.getPosition());
        
        BulletCS = new SphereCollisionShape(radius);
        BulletControl = new BombControl(am,BulletCS,1f);
        BulletControl.setLinearVelocity(this.getDirection());
        
        bulletg.addControl(BulletControl);
        this.currentNode.attachChild(bulletg);
        
        BAS.getPhysicsSpace().add(BulletControl);
    }
    /*update method is automatically called by SimpleAppUpdate method, theres
     no need to call it anywhere*/
    public void update(float tpf){
        
        //elementData is inherited from Element class, and is private to the object
        //elementData contains direction, position and angle
        this.spatial.move(new Vector3f(
                            this.elementData.getDirection().x*tpf, 
                            this.elementData.getDirection().y*tpf,
                            this.elementData.getDirection().z*tpf));
       if(FastMath.abs(this.ShipControl.getPhysicsLocation().x) > 1024f || FastMath.abs(this.ShipControl.getPhysicsLocation().z) > 1024f){
           this.attack(.01f);
       }
    }
    public void attack(float attackDamage){
        return;
    }
    
    //Sounds
    public void setAudioGun(AudioNode aud){
        this.audio_gun = aud;
    }
    
    public void setAudioLaunch(AudioNode aud){
        this.audio_vehicleLaunch = aud;
    }
}

