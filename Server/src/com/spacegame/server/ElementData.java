/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacegame.server;

/**
 *
 * @author Cam
 */
public class ElementData {
    public boolean isValid = false;
    ElementData(){
        this.isValid = true;
    }
    ElementData(boolean b){
        this.isValid = b;
    }
}
