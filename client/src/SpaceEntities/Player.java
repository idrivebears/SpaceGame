package SpaceEntities;

import com.jme3.asset.AssetManager;

/**
 *
 * @author Usuario
 */
public class Player extends Ship{
    public final String PLAYER_ID;
    private float speed;
    
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
    
    
    
    
}
