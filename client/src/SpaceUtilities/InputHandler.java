package SpaceUtilities;

import SpaceEntities.Player;
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
        if(name.equals("INPUT_Forward")){
            //Vector3f mov = ship.getLocalRotation().getRotationColumn(2).normalize();
            Vector3f mov = thisPlayer.getLocalRotation().getRotationColumn(2).normalize();
            thisPlayer.setDirection(mov.mult(thisPlayer.getSpeed()));
        }
        if(name.equals("INPUT_Backward")){
            Vector3f mov = thisPlayer.getLocalRotation().getRotationColumn(2).normalize();
            thisPlayer.setDirection(mov.mult(-thisPlayer.getSpeed()));
        }
        if(name.equals("INPUT_RollUp")){
            //Vector3f mov = thisPlayer.getLocalRotation().getRotationColumn(1).normalize();
            //thisPlayer.setDirection(mov.mult(thisPlayer.getSpeed()));
            
            Quaternion PITCH045 = new Quaternion().fromAngleAxis(-FastMath.PI/6, new Vector3f(1,0,0));
            thisPlayer.setLocalRotation(thisPlayer.getLocalRotation().add(PITCH045));
            
                       
            //thisPlayer.update(tpf);
            
        }
        if(name.equals("INPUT_RollDown")){
            Quaternion PITCH045 = new Quaternion().fromAngleAxis(FastMath.PI/6, new Vector3f(1,0,0));
            thisPlayer.setLocalRotation(thisPlayer.getLocalRotation().add(PITCH045));
        }
        if(name.equals("INPUT_Right")){
            Vector3f mov = thisPlayer.getLocalRotation().getRotationColumn(0).normalize();
            thisPlayer.setDirection(mov.mult(-thisPlayer.getSpeed()));
        }
        if(name.equals("INPUT_Left")){
            Vector3f mov = thisPlayer.getLocalRotation().getRotationColumn(0).normalize();
            thisPlayer.setDirection(mov.mult(thisPlayer.getSpeed()));
        }
        if(name.equals("INPUT_RollLeft")){
            Quaternion PITCH045 = new Quaternion().fromAngleAxis(FastMath.PI/180, new Vector3f(0,1,0));
            thisPlayer.setLocalRotation(thisPlayer.getLocalRotation().add(PITCH045));
            thisPlayer.update(tpf);
        }
        if(name.equals("INPUT_RollRight")){
            Quaternion PITCH045 = new Quaternion().fromAngleAxis(-FastMath.PI/180, new Vector3f(0,1,0));
            thisPlayer.setLocalRotation(thisPlayer.getLocalRotation().mult(PITCH045));
            thisPlayer.update(tpf);
        }
    }
}