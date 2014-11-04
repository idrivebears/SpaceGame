package SpaceEntities;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;

/**
 *
 * @author Usuario
 */
public class Player extends Ship{
    public final String PLAYER_ID;
    private float speed = 32f; //default speed
    
    //Player states
    public boolean isAlive;
    
    public Player(String id, String model, AssetManager am){
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
    
   @Override
    public void update(float tpf){
        super.update(tpf);
        Vector3f mov = this.getLocalRotation().getRotationColumn(2).normalize();
        this.setDirection(mov.mult(-this.getSpeed()));
    }
    
    
    
}
