
package com.spacegame.util;

import com.spacegame.entities.Player;
import java.util.ArrayList;

/**
 *
 * @author Alex
 */

/*
 * This class is a container for all players in the client
 * The client takes the ElementData list from the server
 * and then locally instanciates a Player object for each of the 
 * unique ID's contained in the ElementData list.
 * 
 */
public class PlayerList{
    
    //Here we override the contains() method of the ArrayList class
    //We can check if the playerList contains a Player or an ElementData
    //By checking the corresponding id's 
    public static ArrayList<Player> playerList = new ArrayList<Player>(){
        @Override
        public boolean contains(Object o){
        if(o instanceof ElementData){
            ElementData e = (ElementData)o;
            for(int i = 0; i < this.size(); i++){
                if(e.getID() == this.get(i).PLAYER_ID){
                    return true;
                }
            }
            return false;
        }
        else if( o instanceof Player){
            Player p = (Player)o;
            for(int i = 0; i < this.size(); i++){
                if(p.PLAYER_ID == this.get(i).PLAYER_ID){
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    };
    
    //Wrapper for contains method of arraylist
    public boolean contains(Object o){
        return playerList.contains(o);
    }
    
    //Wrappers for making access to playerlist easier
    public void addPlayer(Player p){
        playerList.add(p);
    }
    
    public void removePlayer(Player p){
        playerList.remove(p);
    }
    
    public Player getPlayer(int id){
        for(Player p : playerList){
            if(p.PLAYER_ID == id)
                return p;
        }
        return null;
    }
    
}