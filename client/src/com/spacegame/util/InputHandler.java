package com.spacegame.util;

import com.spacegame.entities.Player;
import com.jme3.input.controls.AnalogListener;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

/**
 *
 * @author Alex
 * 
 * This will send the input pressed by the client to the server, including a client ID
 */
public class InputHandler implements AnalogListener{
    Player thisPlayer;
    
    public InputHandler(Player player){
        thisPlayer = player;
    }
    
    //The commands here should change to something similar to server.sendKey(String playerID, String keyPressed);
    @Override
    public void onAnalog(String name, float value, float tpf){
        // Basic movement. Considers "tpf", making velocity in all directions equal
       // if(name.equals("INPUT_Forward")){
            //Vector3f mov = ship.getLocalRotation().getRotationColumn(2).normalize();
            
         //   Vector3f mov = thisPlayer.getLocalRotation().getRotationColumn(2).normalize();
          //  thisPlayer.setDirection(mov.mult(-thisPlayer.getSpeed()));
        //}
//        if(name.equals("INPUT_Backward")){
//            Vector3f mov = thisPlayer.getLocalRotation().getRotationColumn(2).normalize();
//            thisPlayer.setDirection(mov.mult(thisPlayer.getSpeed()));
//        }
        //System.out.println(value + " " + tpf); value is equal to tpf
        if(name.equals("INPUT_RollUp")){
            thisPlayer.PitchUp(tpf);
        }
        if(name.equals("INPUT_RollDown")){
            thisPlayer.PitchDown(tpf);
        }/*
        if(name.equals("INPUT_Right")){
            Vector3f mov = thisPlayer.getLocalRotation().getRotationColumn(0).normalize();
            thisPlayer.setDirection(mov.mult(thisPlayer.getSpeed()));
        }
        if(name.equals("INPUT_Left")){
            Vector3f mov = thisPlayer.getLocalRotation().getRotationColumn(0).normalize();
            thisPlayer.setDirection(mov.mult(-thisPlayer.getSpeed()));
        }*/
        if(name.equals("INPUT_RollLeft")){
            thisPlayer.RollLeft(tpf);
            
        }
        if(name.equals("INPUT_RollRight")){
            thisPlayer.RollRight(tpf);
        }
    }
}