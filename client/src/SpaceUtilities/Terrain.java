/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SpaceUtilities;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author gwAwr
 */
public class Terrain {
    private Spatial terrain = null;
    Node node = new Node();
    
    public Terrain(Spatial terrain){
        this.terrain = terrain;
    }
    
    public void setTerrain(Spatial terrain){
        this.terrain = terrain;
        this.node.attachChild(this.terrain);
    }
    
    public void loadTerrainTo(Node node){
        if(terrain != null){
            node.attachChild(this.node);
        }
        else{
            System.out.println("Error(Terrain):No Terrain assigned.");
        }
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
    
}
