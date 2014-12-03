package com.spacegame.main;

import com.spacegame.entities.Player;
import com.spacegame.util.InputHandler;
import com.spacegame.util.Terrain;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
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
import com.jme3.ui.Picture;
import com.spacegame.networking.Input;
import com.spacegame.networking.Test;
import com.spacegame.networking.Update;
import com.spacegame.networking.ElementData;
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
public class Main extends SimpleApplication{
    private AudioNode bgMusic;
    private Player player;
    private Terrain terrain;
    public RigidBodyControl terrainRBC;
    private InputHandler inputHandler;
    
    public static Client client; //client was originally private non-static
    
    private ArrayList<ElementData> serverData = new ArrayList<ElementData>();
    
    BitmapText displayText;
    Picture pic;
    
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
        player = new Player(client.getId(), PLAYER_MODEL, assetManager,BAS,terrainRBC);
        player.getNode().addControl(player.getShipControl());
        player.getShipControl().setGravity(0);
        player.setPosition(new Vector3f(0,0,0));
        player.setDirection(new Vector3f(0,0,0));
        terrain.add(player.getNode()); //attaching the player to the terrain's node
        
        inputHandler = new InputHandler(player); //Loads a new InputHandler instance with instanced player
        
        this.setUpLight();
        
        flyCam.setEnabled(false);
        
        this.initKeys();
        this.initAudio();
        this.initHUD();
        
        /*
        //Tester for updatePlayerList()
        ArrayList<ElementData> sd = new ArrayList<ElementData>();
        sd.add(new ElementData(0, new Vector3f(0,0,0), new Vector3f(1,1,1)));
        sd.add(new ElementData(1, new Vector3f(0,0,0), new Vector3f(1,1,0)));
        sd.add(new ElementData(2, new Vector3f(0,0,0), new Vector3f(0,1,1)));
        sd.add(new ElementData(1, new Vector3f(0.2f,0.4f,0.5f), new Vector3f(1,1,0)));
        sd.add(new ElementData(3, new Vector3f(0,0,0), new Vector3f(-1,-2,0)));
        updatePlayerList(sd);
        playerList.printAllPlayers();
        */
        
        //add collision listeners
        this.player.getNode().setName("player_"+player.getElementData().getID());
        //System.out.println(this.player.getNode().getName());
        
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
                client = Network.connectToServer(serverAddress, serverPort);
                //DEVCAM setup
                client.addMessageListener(new MyClientListener(), Update.class); //adds the listener
                client.addMessageListener(new MyClientListener(), Test.class);
                //serialize packages
                log("Listener added");
                Serializer.registerClass(Update.class);
                Serializer.registerClass(Input.class); //assuming client will send inputs
                Serializer.registerClass(Test.class);
                Serializer.registerClass(ElementData.class);
                //end of setup
                connectionSuccess = true;
            }
            catch(IOException e){
                log("Server Address/Port not valid or server not running, please try again.");
                log("Error:: " + e.getLocalizedMessage());
            }
        }
        client.start();
    }
    
    //embedded listener class
    public class MyClientListener implements MessageListener<Client> {
        public void messageReceived( Client source, Message m ) {
            if(m instanceof Test){
                source.send(new Input());
            }
            if(m instanceof Update){
                //log("Update arrived properly");
                Update update = (Update)m; //typecast m into update
                serverData = update.getInfo();
            }
        }
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
        /*
        inputManager.addMapping("INPUT_PitchUp", new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping("INPUT_PitchDown", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("INPUT_RollRight", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("INPUT_RollLeft", new KeyTrigger(KeyInput.KEY_LEFT));
        */
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
    
    private void initHUD(){
        displayText = new BitmapText(guiFont, false);
        displayText.setSize(guiFont.getCharSet().getRenderedSize());
        displayText.setColor(ColorRGBA.Green);
        displayText.setLocalTranslation(10, displayText.getLineHeight()+40,0);
        
        pic = new Picture("HUD Bar Health");
        pic.setImage(assetManager, "Interface/hpUnit.png", true);
        pic.setWidth(settings.getWidth()/4);
        pic.setHeight(settings.getHeight()/14);
        pic.setPosition(0, settings.getHeight() - settings.getHeight()/10);
        
        guiNode.attachChild(displayText);
        guiNode.attachChild(pic);
    }
    
    private void updateHUD(){
        displayText.setText("ID: " + player.getElementData().getID() + " Health: " + player.getHealth() + "\nPosition: " + player.getPosition());
        //System.out.println((int)(settings.getWidth()/4 * (player.getHealth()/100.)));
        pic.setWidth( (int)(settings.getWidth()/4 * (player.getHealth()/100.)));
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
    
    private void updateCamera() {
       // Camera location is updated according to player's rotation, plus a vector. Difference between cam location and shape location

        cam.lookAt(player.getPosition(), Vector3f.UNIT_Y);
        cam.setLocation(player.getPosition().add(player.getLocalRotation().mult( new Vector3f(0,0,50))));
        
        //cam.lookAt(player.getShipControl().getPhysicsLocation(), Vector3f.UNIT_Y);
       // cam.setLocation(player.getShipControl().getPhysicsLocation().add(player.getLocalRotation().mult( new Vector3f(0,0,30))));
        
        
    }

    @Override
    public void simpleUpdate(float tpf) {
       player.update(tpf);
       updateCamera();
       updateHUD();
       
       updatePlayerList(serverData);
       
       //add Nodes to terrain
       playerList.printAllPlayers();
       
       client.send(new Input(player.getPosition(), player.getDirection(),player.getLocalRotation()));
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    //serverData should be recieved from the server (StateProcessor.Elements list)
    //Missing: consider isAlive
    
    private void updatePlayerList(ArrayList<ElementData> serverData) {
        //Checks the ElementData list for any non-existing players
        //and instanciates and adds a new player to the playerList.
        if(!serverData.isEmpty()){
            for(ElementData e : serverData) {
                //If the player is not existant in the playerList
                if(e.getID() == client.getId())
                {
                    //dont add duplicate players
                    //dont add code here
                }
                else if(!playerList.contains(e)){
                    //if its a new player, add it to the list
                    Player temp = new Player(e.getID(), PLAYER_MODEL, assetManager, BAS,terrainRBC);
                    
                    temp.getNode().setName("player_" + e.getID());
                    temp.getNode().addControl(temp.getShipControl());
                    temp.getShipControl().setGravity(0);
                    temp.setDirection(e.getDirection());
                    temp.getShipControl().setPhysicsLocation(e.getPosition());
                    //temp.setPosition(e.getPosition());
                    BAS.getPhysicsSpace().addCollisionListener(temp);
                    
                    playerList.addPlayer(temp);
                    terrain.add(temp.getNode());
                }
                else{
                    //If the player exists in the list, it
                    //updates the player with matching id to its new ElementData stats
                    //playerList.getPlayer(e.getID()).updateStats(e);
                    playerList.getPlayer(e.getID()).getShipControl().setPhysicsLocation(e.getPosition());
                    playerList.getPlayer(e.getID()).setLocalRotation(e.getRotation());
                }
            }
        }
    }
    
    private void log(String message){
        System.out.println(message);
        //add writing to log file
    }
}
