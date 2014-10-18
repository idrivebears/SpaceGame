package mygame;

import SpaceEntities.Ship;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;


/**
 * Ship test
 * @author Alex Walls
 * Override flycam, add chaseCam, add Mouse rotations, make World Generator, Collision Detection
 * Think about Collidable Interface
 */
public class Main extends SimpleApplication {
    Spatial ship;
    Ship myShip;
      
    public static void main(String[] args) {
        Main app = new Main();
        
        // Title and image
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Space Game");
        settings.setSettingsDialogImage("Interface/nave.jpg");
        app.setSettings(settings);
        
        app.start();   
    }
    
    // Prepare materials
    Material floor_mat;
    
    private static final Box floor;
    
    static{
        floor = new Box(Vector3f.ZERO, 10f, 0.1f, 5f);
        floor.scaleTextureCoordinates(new Vector2f(3, 6));
    } 
    
    @Override
    public void simpleInitApp(){
        
        flyCam.setEnabled(false);
        
        assetManager.registerLocator("assets/Models/Ships/", FileLocator.class);
        
        //Testing ship class
        myShip = new Ship("Wraith.obj", assetManager);
        myShip.setPosition(new Vector3f(0,0,0));
        myShip.setDirection(new Vector3f(0,0,0));
        myShip.setSpeed(1.0f);
        myShip.getSpatial().scale(0.01f);
        myShip.attachToNode(rootNode);
        
        //Light is needed to make the model visible
        DirectionalLight sun = new DirectionalLight();
        DirectionalLight backupLights = new DirectionalLight();
        
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        backupLights.setDirection(new Vector3f(-0.1f, 0.7f, -1.0f));
        
        // Camera
        cam.setLocation(myShip.getPosition().add(new Vector3f(0,10,-10)));
        cam.lookAt(myShip.getPosition(), myShip.getPosition());
                
        // Test Map
        viewPort.setBackgroundColor(ColorRGBA.Blue);
        
        // Init materials, scene and space*
        //initMaterials();
        //initFloor();
        
        rootNode.addLight(sun);
        rootNode.addLight(backupLights);
        
        this.initKeys();
        
    }
    
    private void initKeys(){
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_H));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_L));
        
        inputManager.addMapping("MouseX", new MouseAxisTrigger(MouseInput.BUTTON_LEFT, true));
        inputManager.addMapping("MouseY", new MouseAxisTrigger(MouseInput.AXIS_X, true));
        
        //Adding to action listener
        inputManager.addListener(analogListener, "Up", "Down", "Left", "Right");
    }
    
    // Initialize materials used in the scene
    public void initMaterials(){
        floor_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey key3 = new TextureKey("Textures/Terrain/Pond/Pond.jpg");
        key3.setGenerateMips(true);
        Texture tex3 = assetManager.loadTexture(key3);
        tex3.setWrap(WrapMode.Repeat);
        floor_mat.setTexture("ColorMap", tex3);
    }
    
    // Make a floor and add it to scene
    public void initFloor(){
        Geometry floor_geo = new Geometry("Floor", floor);
        floor_geo.setMaterial(floor_mat);
        floor_geo.setLocalTranslation(0, -0.1f, 0);
        this.rootNode.attachChild(floor_geo);
    }
    
    private AnalogListener analogListener = new AnalogListener(){
        public void onAnalog(String name, float value, float tpf){
            if(name.equals("Up")){
                myShip.setDirection(new Vector3f(0,1,0));
            }
            if(name.equals("Down")){
                myShip.setDirection(new Vector3f(0,-1,0));
            }
            if(name.equals("Left")){
                myShip.setDirection(new Vector3f(-1,0,0));
            }
            if(name.equals("Right")){
                myShip.setDirection(new Vector3f(1,0,0));
            }
            if(name.equals("MouseX")){
                //myShip.setAngle(new Quaternion(MouseInput.AXIS_X, MouseInput.AXIS_Y, 0,0));
                //cam.setLocation((int)MouseInput.AXIS_X,(float)MouseInput.AXIS_Y,0);
                //cam.setLocation(new Vector3f(MouseInput.AXIS_X, MouseInput.AXIS_Y,0));
                // Modify myShip direction and position, camera is already set
            }
        }
    };

    @Override
    public void simpleUpdate(float tpf) {
        myShip.update(tpf);
        
        // Camera
        cam.setLocation(myShip.getPosition().add(new Vector3f(0,10,-10)));
        cam.lookAt(myShip.getPosition(), myShip.getPosition());
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
