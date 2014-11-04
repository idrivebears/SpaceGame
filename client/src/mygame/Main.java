package mygame;

import SpaceEntities.Player;
import SpaceUtilities.InputHandler;
import SpaceUtilities.Terrain;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.scene.CameraNode;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.system.AppSettings;


/**
 * Ship test
 * @author Alex Walls
 * Holis
 * Override flycam, add chaseCam, add Mouse rotations, make World Generator, Collision Detection
 * Think about Collidable Interface
 */
public class Main extends SimpleApplication {
    Player player;
    Terrain terrain;
    InputHandler inputHandler;
    //CameraNode camNode;
    
    // Velocity of ship [TEST]
    private int CAMERA_MOVE_SPEED = 50;
    
    
    public static void main(String[] args) {
        Main app = new Main();
        
        // Title and image
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Super Crazy Space Maniac Game Deluxe 4"); //5 star name
        settings.setSettingsDialogImage("Interface/nave.jpg");
        settings.setWidth(1280);
        settings.setHeight(800);
        app.setSettings(settings);
        
        app.start();
    }
    

    @Override
    public void simpleInitApp(){
        
        //Adding path to assetManager lookup table
        assetManager.registerLocator("assets/Models/", FileLocator.class);
        
        //Loading terrain to rootNode
        terrain = new Terrain(assetManager.loadModel("Scenes/TestTerrain.j3o"));
        /* The terrain instance should be the only one attached to the rootNode,
         * All elements and objects that live in the game should be attached to the
         * current terrains node (terrain.getNode())
         */
        terrain.loadTerrainTo(rootNode); //attaching the terrain to the rootNode
        
        // this should change to player = new Player(server.getPlayerID, server.getPlayerSpatial, assetManager);
        player = new Player("Player1", "Ships/round_ship.obj", assetManager);
        
        //Quaternion ROLL045  = new Quaternion().fromAngleAxis(FastMath.PI,   new Vector3f(0,0,1));
        
        //player.getSpatial().rotate(0, 0, FastMath.HALF_PI);
        player.setPosition(new Vector3f(0,0,0));
        player.setDirection(new Vector3f(0,0,0));
        player.getSpatial().scale(0.08f);
        terrain.add(player.getNode()); //attaching the player to the terrain's node
        
        inputHandler = new InputHandler(player); //Loads a new InputHandler instance with instanced player
        
        this.setUpLight();
        //this.setUpCamera(); //changed name to setUpCamera to match setUpLight syntax
                
        this.initKeys();
        
    }
    
    private void initKeys(){
        // New keys, testing rotation
        //inputManager.addMapping("INPUT_Forward", new KeyTrigger(KeyInput.KEY_W));
        //inputManager.addMapping("INPUT_Backward", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("INPUT_RollUp", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("INPUT_RollDown", new KeyTrigger(KeyInput.KEY_S));
        //inputManager.addMapping("INPUT_Right", new KeyTrigger(KeyInput.KEY_D));
        //inputManager.addMapping("INPUT_Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("INPUT_RollRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("INPUT_RollLeft", new KeyTrigger(KeyInput.KEY_A));
        
        
        //Adding to action listener
        //inputManager.addListener(inputHandler,"INPUT_Forward", "INPUT_Backward","INPUT_RollUp","INPUT_RollDown",
          //      "INPUT_Right","INPUT_Left","INPUT_RollLeft","INPUT_RollRight");
        inputManager.addListener(inputHandler,"INPUT_RollUp","INPUT_RollDown","INPUT_RollLeft","INPUT_RollRight");
    }
      
    //Light is needed to make the models visible
    private void setUpLight(){
        
        DirectionalLight sun = new DirectionalLight();
        DirectionalLight backupLights = new DirectionalLight();
        
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        backupLights.setDirection(new Vector3f(-0.1f, 0.7f, -1.0f));
        
        viewPort.setBackgroundColor(ColorRGBA.Yellow);
             
        rootNode.addLight(sun);
        rootNode.addLight(backupLights);
    }
    
    private void updateCamera(){
       //flyCam.setEnabled(false);
        // Cam location is updated according to player's rotation, plus a vector. Difference between cam location and shape location
        cam.setLocation(player.getPosition().add(player.getLocalRotation().normalizeLocal().mult( new Vector3f(0,7,30))));
        cam.lookAt(player.getPosition(), Vector3f.UNIT_Y);
        
    }

    @Override
    public void simpleUpdate(float tpf) {
        player.update(tpf);

        updateCamera();
               
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
    
