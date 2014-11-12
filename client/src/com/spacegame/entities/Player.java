package com.spacegame.entities;

import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.spacegame.util.ElementData;

/**
 *
 * @author awalls
 * Main Player class
 * The objets for each Player must be instanciated only client-side
 * 
 */
public class Player extends Ship{

    public final int PLAYER_ID;
      
    private int health;
    
    //Player states
    public boolean isAlive;
    
    public Player(int id, String model, AssetManager am){
        super(model, am);
        this.PLAYER_ID = id;
        isAlive = true;
        health = 100;
        this.getSpatial().scale(0.08f);
    }
    
    //Does the passed attackDamage to the player
    //If the player has no health left, the player is no
    //longer alive.
    public void attack(int attackDamage){
        int damageDone = this.health - attackDamage;
        if(damageDone <= 0){
            this.isAlive = false;
        }
        else{
            this.health = damageDone;
        }
    }
    
    //Brings the player back to life to a starting position.
    public void respawn(Vector3f position){
        if(!this.isAlive){
            this.elementData.setPosition(position);
            this.health = 100;
            this.isAlive = true;
        }
    }
    
    public int getHealth(){
        return this.health;
    }
    
    //Updates the players Angle, Position and Direction
    public void updateStats(ElementData e){
        this.elementData.setAngle(e.getAngle());
        this.elementData.setDirection(e.getDirection());
        this.elementData.setPosition(e.getPosition());
    }
    
   @Override
    public void update(float tpf){
        super.update(tpf);
        
        //Keep moving
        super.keepMoving();
    }

    
    
}
