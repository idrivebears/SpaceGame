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
    private Quaternion rotation;
    
    public ElementData() {
        //constructor for uknown reasons (mainly, the call in client-side Bullet
        //& the call in client-side Ship
    }
    public ElementData(ElementData e) {
        this.updateData(e);
    }
    public ElementData(int id, Vector3f d, Vector3f p,Quaternion r) {
        this.id = id;
        this.direction = d;
        this.position = p;
        this.rotation = r;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public Vector3f getPosition() {
        return position;
    }
    
    public Quaternion getRotation(){
        return rotation;
    }
    
    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }
    
    public void setRotation(Quaternion rotation) {
        this.rotation = rotation;
    }

    public void setID(int id){
        this.id = id;
    }
    
    public int getID(){
        return this.id;
    }
    
    public void updateData(ElementData e){
        this.id = e.id;
        this.direction = e.direction;
        this.position = e.position;
        this.rotation = e.rotation;
    }
        
    @Override
    public String toString(){
        return "ID:" + this.id + " Pos: " + this.position.toString();
    }
}
