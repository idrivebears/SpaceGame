/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacegame.networking;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author awalls
 * This is the data package to be sent between classes
 * Facilitates transfer of position data in the scene graph
 */
@Serializable
public class ElementData {
    private int id;
    private Vector3f direction;
    private Vector3f position;
    private Quaternion angle;
    
    public ElementData(){
        //constructor for uknown reasons (mainly, the call in client-side Bullet
        //& the call in client-side Ship
    }
    public ElementData(int id, Vector3f d, Vector3f p, Quaternion a){
        this.id = id;
        direction = new Vector3f(d);
        position = new Vector3f(p);
        angle = new Quaternion(a);
    }

    public Vector3f getDirection() {
        return direction;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Quaternion getAngle() {
        return angle;
    }
    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setAngle(Quaternion angle) {
        this.angle = angle;
    }
    
    public void setID(int id){
        this.id = id;
    }
    
    public int getID(){
        return this.id;
    }
        
    @Override
    public String toString(){
        return "ID:" + this.id + " Pos: " + this.position.toString();
    }
}