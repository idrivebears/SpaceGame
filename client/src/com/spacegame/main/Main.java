package com.spacegame.main;

import com.spacegame.entities.Player;
import com.spacegame.entities.Bullet;
import com.spacegame.util.InputHandler;
import com.spacegame.util.Terrain;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.math.ColorRGBA;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.serializing.Serializer;
import com.jme3.system.AppSettings;
import com.spacegame.networking.Input;
import com.spacegame.networking.Test;
import com.spacegame.networking.Update;
import com.spacegame.util.ElementData;
import com.spacegame.util.PlayerList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Ship test
 * @author Alex Walls
 * Holis
 * Override flycam, add chaseCam, add Mouse rotations, make World Generator, Collision Detection
 * Think about Collidable Interface
 */
public class Main extends SimpleApplication implements MessageListener<Client> {
    private AudioNode bgMusic;
    private Player player;
    private Terrain terrain;
    private Bullet bullet;
    private RigidBodyControl terrainRBC;
//    private CharacterControl ShipControl;
    private InputHandler inputHandler;
    
    private Client client;
    
    BitmapText displayText;
    
    //Contains the instanciated Player objects to render locally
    PlayerList playerList = new PlayerList();
    
    //CameraNode camNode;
    
    // Velocity of ship [TEST]
    private int CAMERA_MOVE_SPEED = 50;
    private String PLAYER_MODEL = "Ships/round_ship.obj";
    
    
    public static void main(String[] args) {
        
        Main game = new Main();
        game.setDisplayFps(true);
        game.setDisplayStatView(false); //removes default show of text stats
        game.setShowSettings(false);
        game.runServerSetup();
        
        // Title and image
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Super Crazy Space Maniac Game Deluxe 4"); //5 star name
        settings.setSettingsDialogImage("Interface/nave.jpg");
        settings.setWidth(640);
        settings.setHeight(400);
        game.setSettings(settings);
        
        game.start();
    }
    private BulletAppState BAS;
    
    @Override
    public void simpleInitApp(){
        BAS = new BulletAppState();
        stateManager.attach(BAS);
        
        //Adding path to assetManager lookup table
        assetManager.registerLocator("assets/Models/", FileLocator.class);
        
        //Loading terrain to rootNode
        terrain = new Terrain(assetManager.loadModel("Scenes/TestTerrain.j3o"));
        /* The terrain instance should be the only one attached to the rootNode,
         * All elements and objects that live in the game should be attached to the
         * current terrains node (terrain.getNode())
         */
        CollisionShape cs = CollisionShapeFactory.createMeshShape(terrain.getSpatial());
        terrainRBC = new RigidBodyControl(cs, 0);
        BAS.getPhysicsSpace().add(terrainRBC);
        
        terrain.loadTerrainTo(rootNode); //attaching the terrain to the rootNode
        
        // this should change to player = new Player(server.getPlayerID, server.getPlayerSpatial, assetManager);
        player = new Player(client.getId(), PLAYER_MODEL, assetManager,BAS);
        
//        ShipControl = new CharacterControl(player.getShipCollisionShape(),1f);
        player.getNode().addControl(player.getShipControl());
        //BAS.getPhysicsSpace().add(player.getShipControl());
        player.getShipControl().setGravity(0);
        //player.getSpatial().rotate(0, 0, FastMath.HALF_PI);
        player.setPosition(new Vector3f(0,0,0));
        player.setDirection(new Vector3f(0,0,0));
        terrain.add(player.getNode()); //attaching the player to the terrain's node
        
        inputHandler = new InputHandler(player); //Loads a new InputHandler instance with instanced player
        
        this.setUpLight();
        
        flyCam.setEnabled(false); 
        
        this.initKeys();
        this.initAudio();
        
        displayText = new BitmapText(guiFont, false);
        displayText.setSize(guiFont.getCharSet().getRenderedSize());
        displayText.setColor(ColorRGBA.Green);
        displayText.setLocalTranslation(10, displayText.getLineHeight()+30,0);
        
        guiNode.attachChild(displayText);
        
        /*
        //Tester for updatePlayerList()
        ArrayList<ElementData> sd = new ArrayList<ElementData>();
        sd.add(new ElementData(0, new Vector3f(0,0,0), new Vector3f(1,1,1), new Quaternion()));
        sd.add(new ElementData(1, new Vector3f(0,0,0), new Vector3f(1,1,0), new Quaternion()));
        sd.add(new ElementData(2, new Vector3f(0,0,0), new Vector3f(0,1,1), new Quaternion()));
        sd.add(new ElementData(1, new Vector3f(0.2f,0.4f,0.5f), new Vector3f(1,1,0), new Quaternion()));
        sd.add(new ElementData(3, new Vector3f(0,0,0), new Vector3f(-1,-2,0), new Quaternion()));
        updatePlayerList(sd);
        playerList.printAllPlayers();
        */
        
        
    }
    
    private void runServerSetup(){
        int serverPort;
        String serverAddress;
        boolean connectionSuccess = false;
        Scanner in = new Scanner(System.in);
        
        while(!connectionSuccess){
            System.out.print("Please enter a server address to connect to:> ");
            serverAddress = in.next();
            System.out.print("Please enter a server port to connect to:> ");
            serverPort = in.nextInt();
            //serverPort = (serverPort > 0 && serverPort < 65535) ? serverPort : 2526;
            System.out.println("Attempting to connect to server " + serverAddress + " at port:  "+ serverPort + " ...");
            try {
                serverAddress = "localhost"; //default
                client = Network.connectToServer(serverAddress, serverPort);
                //DEVCAM setup
                client.addMessageListener(this); //adds the listener
                //serialize packages
                System.out.println("Listener added");
                Serializer.registerClass(Update.class);
                Serializer.registerClass(Input.class); //assuming client will send inputs
                Serializer.registerClass(Test.class);
                //end of setup
                connectionSuccess = true;
            }
            catch(IOException e){
                System.out.println("Server Address/Port not valid or server not running, please try again.");
                System.out.println("Error:: " + e.getLocalizedMessage());
            }
        }
        client.start();
        
        
    }
    
    private void initKeys(){
        // Key for movement and shooting
        
        // Basic movement WASD
        inputManager.addMapping("INPUT_PitchUp", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("INPUT_PitchDown", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("INPUT_RollLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("INPUT_RollRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("INPUT_YawLeft", new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addMapping("INPUT_YawRight", new KeyTrigger(KeyInput.KEY_E));
        
        //Arrow keys
        inputManager.addMapping("INPUT_PitchUp", new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping("INPUT_PitchDown", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("INPUT_RollRight", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("INPUT_RollLeft", new KeyTrigger(KeyInput.KEY_LEFT));
        
        // Shooting
        inputManager.addMapping("INPUT_Shoot", new KeyTrigger(KeyInput.KEY_SPACE));
        
        inputManager.addListener(inputHandler,"INPUT_PitchUp","INPUT_PitchDown","INPUT_RollLeft",
                "INPUT_RollRight","INPUT_Shoot","INPUT_YawLeft","INPUT_YawRight");
    }
    
    private void initAudio(){
        bgMusic = new AudioNode(assetManager, "Sounds/theme.ogg", true);
        bgMusic.setLooping(false);
        bgMusic.setPositional(false);
        bgMusic.setVolume(2);
        terrain.add(bgMusic);
        //bgMusic.play();
    }
    
    private void updateStatusText(){
        displayText.setText("ID: " + player.PLAYER_ID + " Health: " + player.getHealth());
    }
      
    //Light is needed to make the models visible
    private void setUpLight(){
        
        DirectionalLight sun = new DirectionalLight();
        DirectionalLight backupLights = new DirectionalLight();
        
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        backupLights.setDirection(new Vector3f(-0.1f, 0.7f, -1.0f));
        
        viewPort.setBackgroundColor(ColorRGBA.Gray);
             
        rootNode.addLight(sun);
        rootNode.addLight(backupLights);
    }
    
    private void updateCamera(){
       // Camera location is updated according to player's rotation, plus a vector. Difference between cam location and shape location
        /*
        if(!(player.getPitchSpeed() < 0.7 && player.getPitchSpeed() > -0.7)){
            cam.setLocation(player.getPosition().add(player.getLocalRotation().mult( new Vector3f(0,7,30))));
        }
        else{
            cam.setLocation(player.getPosition().add(player.getLocalRotation().mult( new Vector3f(0,7,30))));
        }*/
        cam.lookAt(player.getPosition(), Vector3f.UNIT_Y);
        cam.setLocation(player.getPosition().add(player.getLocalRotation().mult( new Vector3f(0,0,30))));
        //cam.lookAt(player.getShipControl().getPhysicsLocation(), Vector3f.UNIT_Y);
       // cam.setLocation(player.getShipControl().getPhysicsLocation().add(player.getLocalRotation().mult( new Vector3f(0,0,30))));
        
        /*
         * ToDo: Implement elastic cam
        float angle = cam.getRotation().mult(Vector3f.UNIT_Z).normalizeLocal().angleBetween(player.getLocalRotation().mult(Vector3f.UNIT_Z.mult(-1)).normalizeLocal());
        System.out.println(angle + " ANGLE");
         
        if(angle > .5){
            cam.setLocation(player.getPosition().add(player.getLocalRotation().mult( new Vector3f(0,0,30))));
        }
        */
    }

    @Override
    public void simpleUpdate(float tpf) {
       player.update(tpf);
       updateCamera();
       updateStatusText();
       
       
       //get ArrayList<ElementData> serverData for updatePlayerList() from server
       //updatePlayerList()
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
        //renderPlayers();
    }
    
    //serverData should be recieved from the server (StateProcessor.Elements list)
    //Missing: consider isAlive
    
    private void updatePlayerList(ArrayList<ElementData> serverData){
        //Checks the ElementData list for any non-existing players
        //and instanciates and adds a new player to the playerList.
        for(ElementData e : serverData){
            //If the player is not existant in the playerList
            if(!playerList.contains(e)){
                Player temp = new Player(e.getID(), PLAYER_MODEL, assetManager,BAS);
                playerList.addPlayer(temp);
                terrain.add(temp.getNode());
            }
            else{
                //If the player exists in the list, it
                //updates the player with matching id to its new ElementData stats
                playerList.getPlayer(e.getID()).updateStats(e);
            }
        }
        
    }
    
    @Override //override implemented interface MessageListener
    public void messageReceived(Client source, Message message) {
        System.out.println("MESSAGE ARRIVED");
        
        if (message instanceof Update) {
            Update update = (Update)message;
            // do something with the message
            System.out.println("UPDATE ARRIVED");
            System.out.println(update.getInfo().toString());
            this.updatePlayerList(update.getInfo());
            //CONSIDER
            //    >this is assuming "Update" will be the actuall updtade message
            //    >this is assuming the file "Element data" @ com.spacegame.util.ElementData is correct
        }
    }
}
    
