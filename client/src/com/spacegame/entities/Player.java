package com.spacegame.entities;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.spacegame.networking.ElementData;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;


/**
 *
 * @author awalls
 * Main Player class
 * The objets for each Player must be instanciated only client-side
 * 
 */
public class Player extends Ship  implements PhysicsCollisionListener{

    //public final int PLAYER_ID;
      
    private float health =0f;
    RigidBodyControl terrainRBC;
    
    //Player states
    public boolean isAlive;
    
    public Player(int id, String model, AssetManager am, BulletAppState BAS, RigidBodyControl terrainRBC){
        super(model, am, BAS);
        this.getElementData().setID(id);
        isAlive = true;
        
        this.getSpatial().scale(0.25f);
        BAS.getPhysicsSpace().addCollisionListener(this);
        health = 100f;
    }
    
    //Does the passed attackDamage to the player
    //If the player has no health left, the player is no
    //longer alive.
    public void attack(float attackDamage){
        float damageDone = this.health - attackDamage;
        if(damageDone <= 0){
            this.isAlive = false;
            this.getShipControl().setPhysicsLocation(Vector3f.ZERO);
            this.setLocalRotation(Quaternion.IDENTITY);
            System.out.println("You Died");
            this.isAlive=true;
            this.health=100f;
        }
        else if(this.isAlive){
            this.health= (damageDone<0)?0:damageDone;    
        }
    }
    
    public int getHealth(){
        return (int)this.health;
    }
    
    //Updates the players Angle, Position and Direction
    public void updateStats(ElementData e){
//        e.setDirection(super.getShipControl().getWalkDirection());
//        e.setPosition(super.getShipControl().getPhysicsLocation());
        this.elementData.setDirection(e.getDirection());
        this.elementData.setPosition(e.getPosition());
        this.elementData.setRotation(e.getRotation());
    }
    
   @Override
    public void update(float tpf){
        super.update(tpf);
        //Keep moving
        super.keepMoving();
    }
   public void collision(PhysicsCollisionEvent event) {
       if(spatial == null) return;
       if (event.getObjectA() == this.getShipControl() || event.getObjectB() == this.getShipControl()) {
           if(event.getObjectA() == terrainRBC|| event.getObjectB() == terrainRBC){
               this.attack(.1f);
           }
          else{
               this.attack(5);
           }
       }
   }
}
