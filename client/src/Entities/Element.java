/**
 *
 * @author Alex
 * 
 * This class defines the methods and variables all Element-type objects will
 * have.
 */
package Entities;

import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.collision.UnsupportedCollisionException;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.math.Quaternion;


public abstract class Element implements Collidable{
    protected Quaternion currentAngle;
    protected Spatial spatial;
    protected Material mat;
    protected Vector3f position;
    protected Vector3f direction;
    protected float speed;    //speed should be a float value from 0 - 1
    protected Node currentNode;
    
    //Direction get and set
    public Vector3f getDirection(){
        return direction;
    }
    public void setDirection(Vector3f direction){
        this.direction = direction;
    }
    
    //Position get and set
    public Vector3f getPosition(){
        return position;
    }
    public void setPosition(Vector3f position){
        this.position = position;
    }
    
    //Speed get and set
    public float getSpeed(){
        return speed;
    }
    public void setSpeed(float speed){
        this.speed = Math.min(1, speed);
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
    
    //Angle methods
    public Quaternion getAngle(){
        return currentAngle;
    }
    public void setAngle(Quaternion angle){
        currentAngle = angle;
    }
    
    //Node methods
    public void attachToNode(Node node){
        node.attachChild(spatial);
    }
    public void attachToNode(Node node, int index){
        node.attachChildAt(node, index);
    }
    public Node getCurrentNode(){
        return currentNode;
    }
    //Will update all states of the Element when called, this includes position, rotation, yaw, etc.
    public void update(float tpf){
        this.spatial.move(new Vector3f(direction.x*tpf, direction.y*tpf, direction.z*tpf));
        this.position = spatial.getLocalTranslation();
    }
    
    @Override
    public int collideWith(Collidable other, CollisionResults results) throws UnsupportedCollisionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
