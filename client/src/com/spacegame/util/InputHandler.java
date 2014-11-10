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
    
    float RollFeeling;
    float PitchFeeling;
    
    //The commands here should change to something similar to server.sendKey(String playerID, String keyPressed);
    @Override
    public void onAnalog(String name, float value, float tpf){
        // Basic movement. Considers "tpf", making velocity in all directions equal

        //System.out.println(value + " " + tpf); //value is equal to tpf
        
        if(name.equals("INPUT_PitchUp")){
            thisPlayer.modifyPitch("up");
            thisPlayer.pitch(tpf);
            //System.out.println(thisPlayer.getPitchSpeed());
        }
        if(name.equals("INPUT_PitchDown")){
            thisPlayer.modifyPitch("down");
            thisPlayer.pitch(tpf);
            //System.out.println(thisPlayer.getPitchSpeed());
        }
        if(name.equals("INPUT_RollLeft")){
            thisPlayer.modifyRoll("left");
            thisPlayer.roll(tpf);
            //System.out.println("INPUT_RollLeft" + thisPlayer.getRollSpeed());
        }
        if(name.equals("INPUT_RollRight")){
            thisPlayer.modifyRoll("right");
            thisPlayer.roll(tpf);
            //System.out.println("INPUT_RollRight" + thisPlayer.getRollSpeed());
        }
        if(name.equals("INPUT_Shoot")){
            thisPlayer.Shoot();
        } 
        
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        //System.out.println(name + " " + isPressed + " " + tpf);
        
                
        if(isPressed && name.equals("INPUT_PitchUp")){
            RollFeeling = thisPlayer.getRollSpeed();
        } else if (!isPressed && name.equals("INPUT_PitchUp")){
            thisPlayer.setPitchSpeed(0);
            //thisPlayer.keepPitch(tpf);
        }
        
        if(isPressed && name.equals("INPUT_PitchDown")){
           RollFeeling = thisPlayer.getRollSpeed();
        } else if(!isPressed && name.equals("INPUT_PitchDown")){
            thisPlayer.setPitchSpeed(0);
            //thisPlayer.keepPitch(tpf);
        }
        
        if(isPressed && name.equals("INPUT_RollLeft")){
           RollFeeling = thisPlayer.getRollSpeed();
           thisPlayer.setRollSpeed(0);
        } else if(!isPressed && name.equals("INPUT_RollLeft")){
            thisPlayer.setRollSpeed(0);
            //thisPlayer.keepRoll(tpf);
        }
        
        if(isPressed && name.equals("INPUT_RollRight")){
           RollFeeling = thisPlayer.getRollSpeed();
           thisPlayer.setRollSpeed(0);
        } else if(isPressed && name.equals("INPUT_RollRight")){
            thisPlayer.setRollSpeed(0);
            //thisPlayer.keepRoll(tpf);
        }
        
        if(isPressed && name.equals("INPUT_Shoot")){
            thisPlayer.Shoot();
        } else if(!isPressed && name.equals("INPUT_Shoot")){
            
        }
    }
}