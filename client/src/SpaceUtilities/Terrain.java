/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SpaceUtilities;

import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture2D;
import com.jme3.water.WaterFilter;

/**
 *
 * @author gwAwr
 */
public class Terrain {
    private Spatial terrain;
    Node node = new Node();
    
    public Terrain(Spatial terrain){
        setTerrain(terrain);
    }
    
    //Sets the terrain's spatial
    public void setTerrain(Spatial terrain){
        this.terrain = terrain;
        this.node.attachChild(this.terrain);
    }
    
    public void loadTerrainTo(Node node){
        node.attachChild(this.node); 
    }
    
    //returns the terrains node
    public Node getNode(){
        return this.node;
    }
    
    //Returns the terrains spatial
    public Spatial getSpatial(){
        return this.terrain;
    }
    
    //Removes the spatial from node
    public void removeTerrain(){
        this.node.detachChild(this.terrain);
    }
    
    public void clearNode(){
        this.node.detachAllChildren();
    }
    
    public void addWater(){
        /*Implement water here*/
    }
}
