package mygame;

import SpaceEntities.Ship;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import com.jme3.math.ColorRGBA;
import com.jme3.system.AppSettings;
//import javax.swing.Box;


import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.post.FilterPostProcessor;
import com.jme3.texture.Texture2D;
import com.jme3.water.WaterFilter;

/**
 * Ship test
 * @author Alex Walls
 * Override flycam, add chaseCam, add Mouse rotations, make World Generator, Collision Detection
 * Think about Collidable Interface
 */
public class Main extends SimpleApplication {
    Spatial ship;
    Ship myShip;

    private Spatial sceneTestTerrain;
    private WaterFilter water;
    private Vector3f lightdir = new Vector3f (-4f,-1f,5f);
    
    // Velocity of ship [TEST]
    private float SPEED = 32;
    private int CAMERA_MOVE_SPEED = 50;
    
    
    public static void main(String[] args) {
        Main app = new Main();
        
        // Title and image
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Space Game");
        settings.setSettingsDialogImage("Interface/nave.jpg");
        app.setSettings(settings);
        
        app.start();  
    }
    

    @Override
    public void simpleInitApp(){
        
        initTestTerrain();  // Carga del Terreno de prueba
        //initTestWater();    // Carga el agua para el terreno 
                            //para desacticarla (Nacho o Walls por el rendimiento) 
                            //solo ponganlo como comentario
       

        assetManager.registerLocator("assets/Models/Ships/", FileLocator.class);
        
        //Testing ship class
        myShip = new Ship("Wraith.obj", assetManager);
        myShip.setPosition(new Vector3f(0,0,0));
        myShip.setDirection(new Vector3f(0,0,0));
        myShip.setSpeed(1.0f);
        myShip.getSpatial().scale(0.01f);
        myShip.attachToNode(rootNode);
        
        setUpLight();
        setCamera();
                
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
        
        // New keys, testing rotation
        inputManager.addMapping("INPUT_Forward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("INPUT_Backward", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("INPUT_Up", new KeyTrigger(KeyInput.KEY_E));
        inputManager.addMapping("INPUT_Down", new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addMapping("INPUT_Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("INPUT_Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("INPUT_RollLeft", new KeyTrigger(KeyInput.KEY_C));
        inputManager.addMapping("INPUT_RollRight", new KeyTrigger(KeyInput.KEY_Z));
        
        
        //Adding to action listener
        inputManager.addListener(analogListener, "Up", "Down", "Left", "Right");
        inputManager.addListener(analogListener,"INPUT_Forward", "INPUT_Backward","INPUT_Up","INPUT_Down",
                "INPUT_Right","INPUT_Left","INPUT_RollLeft","INPUT_RollRight");
    }
      
    //Light is needed to make the model visible
    private void setUpLight(){
        
        DirectionalLight sun = new DirectionalLight();
        DirectionalLight backupLights = new DirectionalLight();
        
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        backupLights.setDirection(new Vector3f(-0.1f, 0.7f, -1.0f));
        
        viewPort.setBackgroundColor(ColorRGBA.Yellow);
             
        rootNode.addLight(sun);
        rootNode.addLight(backupLights);
    }
    
    private void setCamera(){
        //flyCam.setEnabled(false);
        flyCam.setMoveSpeed(CAMERA_MOVE_SPEED);
        
        cam.setLocation(myShip.getPosition().add(new Vector3f(0,10,-10)));
        cam.lookAt(myShip.getPosition(), myShip.getPosition());
    }
    
    private AnalogListener analogListener = new AnalogListener(){
        // TODO: Combine SPEED with tpf
        
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
            if(name.equals("INPUT_Forward")){
                //Vector3f mov = ship.getLocalRotation().getRotationColumn(2).normalize();
                Vector3f mov = myShip.getLocalRotation().getRotationColumn(2).normalize();
                myShip.setDirection(mov.mult(SPEED));
            }
            if(name.equals("INPUT_Backward")){
                Vector3f mov = myShip.getLocalRotation().getRotationColumn(2).normalize();
                myShip.setDirection(mov.mult(-SPEED));
            }
            if(name.equals("INPUT_Up")){
                Vector3f mov = myShip.getLocalRotation().getRotationColumn(1).normalize();
                myShip.setDirection(mov.mult(SPEED));
            }
            if(name.equals("INPUT_Down")){
                Vector3f mov = myShip.getLocalRotation().getRotationColumn(1).normalize();
                myShip.setDirection(mov.mult(-SPEED));
            }
            if(name.equals("INPUT_Right")){
                Vector3f mov = myShip.getLocalRotation().getRotationColumn(0).normalize();
                myShip.setDirection(mov.mult(-SPEED));
            }
            if(name.equals("INPUT_Left")){
                Vector3f mov = myShip.getLocalRotation().getRotationColumn(0).normalize();
                myShip.setDirection(mov.mult(SPEED));
            }
            if(name.equals("INPUT_RollLeft")){
                //TODO 
            }
            if(name.equals("INPUT_RollRight")){
                //TODO
            }
        }
    };

    @Override
    public void simpleUpdate(float tpf) {
        myShip.update(tpf);
        
        // Camera. Set location and direction
        cam.setLocation(myShip.getPosition().add(new Vector3f(0,10,-20)));
        cam.lookAt(myShip.getPosition(), Vector3f.UNIT_Y);
        cam.update();

    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    private void initTestTerrain(){
        sceneTestTerrain = assetManager.loadModel("Scenes/TestTerrain.j3o");
        rootNode.attachChild(sceneTestTerrain);
    }
    
    public void initTestWater(){ 
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager); 
        water = new WaterFilter(rootNode, lightdir); 
        water.setCenter(Vector3f.ZERO); 
        water.setRadius(2600); 
        water.setWaveScale(0.003f); 
        water.setMaxAmplitude(2f); 
        water.setFoamExistence(new Vector3f(1f, 4f, 0.5f)); 
        water.setFoamTexture((Texture2D) assetManager.loadTexture("Common/MatDefs/Water/Textures/foam2.jpg")); 
        water.setRefractionStrength(0.2f); 
        water.setWaterHeight(1f); 
        fpp.addFilter(water); 
        viewPort.addProcessor(fpp); }
}
    
