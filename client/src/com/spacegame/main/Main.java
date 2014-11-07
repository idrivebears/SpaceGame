package com.spacegame.main;

import com.spacegame.entities.Player;
import com.spacegame.util.InputHandler;
import com.spacegame.util.Terrain;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.audio.AudioNode;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.math.ColorRGBA;
import com.jme3.system.AppSettings;
import com.spacegame.util.ElementData;
import com.spacegame.util.PlayerList;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Ship test
 * @author Alex Walls
 * Holis
 * Override flycam, add chaseCam, add Mouse rotations, make World Generator, Collision Detection
 * Think about Collidable Interface
 */
public class Main extends SimpleApplication {
    private AudioNode bgMusic;
    private Player player;
    private Terrain terrain;
    private InputHandler inputHandler;
    private int serverPort;
    
    //Contains the instanciated Player objects to render locally
    PlayerList playerList = new PlayerList();
    
    //CameraNode camNode;
    
    // Velocity of ship [TEST]
    private int CAMERA_MOVE_SPEED = 50;
    private String PLAYER_MODEL = "Ships/round_ship.obj";
    
    
    public static void main(String[] args) {
        
        Main game = new Main();
        
        game.runServerSetup();
        
        // Title and image
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Super Crazy Space Maniac Game Deluxe 4"); //5 star name
        settings.setSettingsDialogImage("Interface/nave.jpg");
        settings.setWidth(1280);
        settings.setHeight(800);
        game.setSettings(settings);
        
        game.start();
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
        player = new Player(1, PLAYER_MODEL, assetManager);
        
               
        //player.getSpatial().rotate(0, 0, FastMath.HALF_PI);
        player.setPosition(new Vector3f(0,0,0));
        player.setDirection(new Vector3f(0,0,0));
        player.getSpatial().scale(0.08f);
        terrain.add(player.getNode()); //attaching the player to the terrain's node
        
        inputHandler = new InputHandler(player); //Loads a new InputHandler instance with instanced player
        
        this.setUpLight();
        
        flyCam.setEnabled(false); 
        
        this.initKeys();
        this.initAudio();
        
    }
    
    private void runServerSetup(){
        
        //MISSING: add loop for failed connections to server
        Scanner in = new Scanner(System.in);
        //LOOP:
        System.out.print("Please enter a server port to connect to:> ");
        serverPort = in.nextInt();
        serverPort = (serverPort > 0 && serverPort < 65535) ? serverPort : 2526;
        System.out.println("Attempting to connect to server " + serverPort + " ...");
        //ENDLOOP
    }
    
    private void initKeys(){
        // Key for movement and shooting
        
        // Basic movement
        inputManager.addMapping("INPUT_PitchUp", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("INPUT_PitchDown", new KeyTrigger(KeyInput.KEY_S));
        
        inputManager.addMapping("INPUT_RollRight", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("INPUT_RollLeft", new KeyTrigger(KeyInput.KEY_LEFT));
        
        // Shooting
        inputManager.addMapping("INPUT_Shoot", new KeyTrigger(KeyInput.KEY_SPACE));
        
        inputManager.addListener(inputHandler,"INPUT_PitchUp","INPUT_PitchDown",
                                          "INPUT_RollLeft","INPUT_RollRight","INPUT_Shoot");
    }
    
    private void initAudio(){
        bgMusic = new AudioNode(assetManager, "Sounds/theme.ogg", true);
        bgMusic.setLooping(false);
        bgMusic.setPositional(false);
        bgMusic.setVolume(2);
        terrain.add(bgMusic);
        //bgMusic.play();
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
       // Camera location is updated according to player's rotation, plus a vector. Difference between cam location and shape location
        if(!(player.getPitchSpeed() < 0.5 && player.getPitchSpeed() > -0.5)){
            cam.setLocation(player.getPosition().add(player.getLocalRotation().mult( new Vector3f(0,7,30))));
        }
        else{
            cam.setLocation(player.getPosition().add(player.getLocalRotation().mult(new Vector3f(0,7,30))));
        }
        cam.lookAt(player.getPosition(), Vector3f.UNIT_Y);
        
        // Rotate camera axes disabled
        //cam.setAxes(player.getLocalRotation().mult(Vector3f.UNIT_X.mult(-1)),player.getLocalRotation().mult(Vector3f.UNIT_Y), player.getLocalRotation().mult(Vector3f.UNIT_Z.mult(-1)));
        
    }

    @Override
    public void simpleUpdate(float tpf) {
       player.update(tpf);
       updateCamera();
       
       
       //get ArrayList<ElementData> serverData for updatePlayerList()
       //updatePlayerList();
       
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
        //renderPlayers();
    }
    
    //serverData should be recieved from the server (StateProcessor.Elements list)
    private void updatePlayerList(ArrayList<ElementData> serverData){
        //Checks the ElementData list for any non-existing players
        //and instanciates and adds a new player to the playerList.
        for(ElementData e : serverData){
            if(!playerList.contains(e)){
                playerList.addPlayer(new Player(e.getID(), PLAYER_MODEL, assetManager));
            }
            else{
                //If the player does exist in the list, it
                //updates the player with matching id to its new ElementData stats
                playerList.getPlayer(e.getID()).updateStats(e);
            }
        }
        
    }
}
    
