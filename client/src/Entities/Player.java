/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import com.jme3.asset.AssetManager;

/**
 *
 * @author Usuario
 */
public class Player extends Ship{
    public final String PLAYER_ID;
    
    //Player states
    public boolean isAlive;
    
    public Player(String id, String model, AssetManager am){
        super(model, am);
        this.PLAYER_ID = id;
    }
    
    
    
}
