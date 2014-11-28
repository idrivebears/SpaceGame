/**
 *
 * @author Alex
 * 
 * This class defines the methods and variables all Element-type objects will
 * have.
 */
package com.spacegame.entities;

import com.spacegame.networking.ElementData;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.math.Quaternion;


public abstract class Element extends PhysicsRigidBody{
    protected Spatial spatial;
    protected Material mat;
    protected ElementData elementData;
    protected Node currentNode = new Node();
    
    //Direction get and set
    public Vector3f getDirection(){
        return elementData.getDirection();
    }
    public void setDirection(Vector3f direction){
        elementData.setDirection(direction);
    }
    
    //Position get and set
    public Vector3f getPosition(){
        return elementData.getPosition();
    }
    public void setPosition(Vector3f position){
        elementData.setPosition(position);
    }

    //Spatial get and set
    public Spatial getSpatial(){
        return spatial;
    }
    public void setSpatial(Spatial spatial){
        this.spatial = spatial;
    }
    
    //Material get and set
    public Material getMaterial(){
        return mat;
    }
    public void setMaterial(Material material){
        mat = material;
    }
    
    //Node methods
    public void attachToNode(Node node){
        node.attachChild(spatial);
    }
    public void attachToNode(Node node, int index){
        node.attachChildAt(node, index);
    }
    
    //returns instance of the this node
    public Node getNode(){
        return currentNode;
    }
    //Will update all states of the Element when called, this includes position, rotation, yaw, etc.
    
    public Quaternion getLocalRotation(){
        return spatial.getLocalRotation();
    }
    
    public void setLocalRotation(Quaternion currentAngle){
        spatial.setLocalRotation(currentAngle);
    }
    
}
