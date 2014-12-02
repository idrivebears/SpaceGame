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
            //Main.client.send(new Input('s'));
        }
        if(name.equals("INPUT_PitchDown")){
            thisPlayer.modifyPitch("down");
            thisPlayer.pitch(tpf);
            //System.out.println(thisPlayer.getPitchSpeed());
            //Main.client.send(new Input('w'));
        }
        if(name.equals("INPUT_RollLeft")){
            thisPlayer.modifyRoll("left");
            thisPlayer.roll(tpf);
            //System.out.println("INPUT_RollLeft" + thisPlayer.getRollSpeed());
            //Main.client.send(new Input('a'));
        }
        if(name.equals("INPUT_RollRight")){
            thisPlayer.modifyRoll("right");
            thisPlayer.roll(tpf);
            //System.out.println("INPUT_RollRight" + thisPlayer.getRollSpeed());
            //Main.client.send(new Input('d'));
        }
        
        if(name.equals("INPUT_YawLeft")){
            thisPlayer.modifyYaw("left");
            thisPlayer.yaw(tpf);
            //Main.client.send(new Input('q'));
        }
        if(name.equals("INPUT_YawRight")){
            thisPlayer.modifyYaw("right");
            thisPlayer.yaw(tpf);
            //Main.client.send(new Input('e'));
        }
      
        if(name.equals("INPUT_Shoot")){
            //thisPlayer.Shoot();
            //System.out.println("PIUM");
        } 
        
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        //System.out.println(name + " " + isPressed + " " + tpf);
        
                
        if(isPressed && name.equals("INPUT_PitchUp")){
            //RollFeeling = thisPlayer.getRollSpeed();
            thisPlayer.setPitchSpeed(0);
        } else if (!isPressed && name.equals("INPUT_PitchUp")){
            thisPlayer.setPitchSpeed(0);
            //thisPlayer.keepPitch(tpf);
        }
        
        if(isPressed && name.equals("INPUT_PitchDown")){
           //RollFeeling = thisPlayer.getRollSpeed();
            thisPlayer.setPitchSpeed(0);
        } else if(!isPressed && name.equals("INPUT_PitchDown")){
            thisPlayer.setPitchSpeed(0);
            //thisPlayer.keepPitch(tpf);
        }
        
        if(isPressed && name.equals("INPUT_RollLeft")){
            //RollFeeling = thisPlayer.getRollSpeed();
            thisPlayer.setRollSpeed(0);
        } else if(!isPressed && name.equals("INPUT_RollLeft")){
            thisPlayer.setRollSpeed(0);
            //thisPlayer.keepRoll(tpf);
        }
        
        if(isPressed && name.equals("INPUT_RollRight")){
            //RollFeeling = thisPlayer.getRollSpeed();
            thisPlayer.setRollSpeed(0);
        } else if(isPressed && name.equals("INPUT_RollRight")){
            thisPlayer.setRollSpeed(0);
            //thisPlayer.keepRoll(tpf);
        }
        if(isPressed && name.equals("INPUT_YawLeft")){
            //RollFeeling = thisPlayer.getRollSpeed();
            thisPlayer.setYawSpeed(0);
        } else if(isPressed && name.equals("INPUT_YawLeft")){
            thisPlayer.setYawSpeed(0);
        }
        if(isPressed && name.equals("INPUT_YawRight")){
            //RollFeeling = thisPlayer.getRollSpeed();
            thisPlayer.setYawSpeed(0);
        } else if(isPressed && name.equals("INPUT_YawRight")){
            thisPlayer.setYawSpeed(0);
        }
        
        if(isPressed && name.equals("INPUT_Shoot")){
            thisPlayer.shoot();
            //thisPlayer.attack(1); // TESTING HUD Bar Health
            System.out.println("PIUM");
        } else if(!isPressed && name.equals("INPUT_Shoot")){
            
        }
    }
}