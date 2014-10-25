/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacegame.server;

import java.util.ArrayList;

/**
 *
 * @author Cam
 */
public class UpdateHandler {
    //generates the UpdatePackage
    public static ArrayList<VisualPlayer> update(){
        ArrayList<VisualPlayer> update = new ArrayList<VisualPlayer>();
        //dumb test update
        update.add(new VisualPlayer());
        update.add(new VisualPlayer());
        //end of update
        return update();
    }
}
