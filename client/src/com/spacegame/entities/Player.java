package com.spacegame.entities;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.spacegame.util.ElementData;

/**
 *
 * @author Usuario
 */
public class Player extends Ship{
<<<<<<< HEAD
    public final String PLAYER_ID;
    private float speed = 64f; //default speed
=======
    public final int PLAYER_ID;
    private float speed = 32f; //default speed
>>>>>>> master
    
    //Player states
    public boolean isAlive;
    
    public Player(int id, String model, AssetManager am){
        super(model, am);
        this.PLAYER_ID = id;
        isAlive = true;
    }
    
    public void setSpeed(float speed){
        this.speed = speed;
    }
    public float getSpeed(){
        return speed;
    }
    
    public void updateStats(ElementData e){
        this.elementData.setAngle(e.getAngle());
        this.elementData.setDirection(e.getDirection());
        this.elementData.setPosition(e.getPosition());
    }
    
   @Override
    public void update(float tpf){
        super.update(tpf);
        Vector3f mov = this.getLocalRotation().getRotationColumn(2).normalize();
        this.setDirection(mov.mult(-this.getSpeed()));
    }
    
    
    
}
