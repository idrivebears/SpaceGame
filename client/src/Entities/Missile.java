/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import com.jme3.math.Vector3f;

/**
 *
 * @author Alex
 */
public class Missile extends Element{
    
    public Missile(Vector3f position, Vector3f direction, int initialSpeed){
        this.position = position;
        this.direction = direction;
        this.speed = initialSpeed;
    }
    
    
    
}
