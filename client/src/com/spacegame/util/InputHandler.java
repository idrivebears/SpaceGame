package com.spacegame.util;

import com.jme3.input.controls.ActionListener;
import com.spacegame.entities.Player;
import com.jme3.input.controls.AnalogListener;

/**
 *
 * @author Alex
 * 
 * This will send the input pressed by the client to the server, including a client ID
 */
public class InputHandler implements AnalogListener, ActionListener {
    Player thisPlayer;
    
    public InputHandler(Player player){
        thisPlayer = player;
    }
    
    //The commands here should change to something similar to server.sendKey(String playerID, String keyPressed);
    @Override
    public void onAnalog(String name, float value, float tpf){
        // Basic movement. Considers "tpf", making velocity in all directions equal

        //System.out.println(value + " " + tpf); //value is equal to tpf
        /*
        if(name.equals("INPUT_RollUp")){
            
            thisPlayer.PitchUp(tpf);
        }
        if(name.equals("INPUT_RollDown")){
            thisPlayer.PitchDown(tpf);
        }
        if(name.equals("INPUT_RollLeft")){
            thisPlayer.RollLeft(tpf);
            
        }
        if(name.equals("INPUT_RollRight")){
            thisPlayer.RollRight(tpf);
        }
        if(name.equals("INPUT_Shoot")){
            thisPlayer.Shoot();
        }
        */
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        System.out.println(name + " " + isPressed + " " + tpf);
        
                
        if(name.equals("INPUT_RollUp")){
            
            thisPlayer.PitchUp(tpf);
        }
        if(name.equals("INPUT_RollDown")){
            thisPlayer.PitchDown(tpf);
        }
        if(name.equals("INPUT_RollLeft")){
            thisPlayer.RollLeft(tpf);
            
        }
        if(name.equals("INPUT_RollRight")){
            thisPlayer.RollRight(tpf);
        }
        if(name.equals("INPUT_Shoot")){
            thisPlayer.Shoot();
        }
    }
}