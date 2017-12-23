package newGameMenu;

import base.Button;
import base.NButton;
import basicChoosers.BasicChooser;
import basicChoosers.PColorChoices;
import mainObjects.Player;
import greenfoot.World;
import java.util.ArrayList;


/**
 * a PlayersPanel is a group of actors that allows the user to set the 
 * names and colors of the played.
 * the destroy() method must be used to remove the the group of actor
 */
public class PlayersPanel {
    //Player Panel should be maximum 500x500 large
    
    private ArrayList<PlayerOptions> players = new ArrayList<>();
    
    protected ArrayList<Player> getPlayers() {
        throw new UnsupportedOperationException("Not supported yet.");
        
    }
    
    protected void addToWorld(World world, int xPos, int yPos) {
        throw new UnsupportedOperationException("Not supported yet.");
    
    }
    
    protected void destroy() {
        throw new UnsupportedOperationException("Not supported yet.");
    
    }
    
    private void removePlayer(PlayerOptions toRemove) {
        throw new UnsupportedOperationException("Not supported yet.");
        
    }
    
    private class PlayerOptions {
        String name;
        NButton delete = new NButton(null, "Delete");
        NButton editName = new NButton(null, "Edit Name");
        BasicChooser colorChooser = new BasicChooser(new PColorChoices());
        
        PlayerOptions(String initName) {
            name = initName;
            throw new UnsupportedOperationException("Not supported yet.");
        
        }
        
        void addToWorld(World world, int xPos, int yPos) {
            throw new UnsupportedOperationException("Not supported yet.");
            
        };
        
        void makeDeletable() {
            throw new UnsupportedOperationException("Not supported yet.");
            
        }
        
        void makeUndeletable() {
            throw new UnsupportedOperationException("Not supported yet.");
        
        }
        
        void delete() {
            throw new UnsupportedOperationException("Not supported yet.");
            
        }
        
    
    }
    
    private class NewPlayerB extends Button {

        @Override
        public void clicked() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        NewPlayerB() {
            //ajouter l'image
            throw new UnsupportedOperationException("Not supported yet.");
            
        }
    
    }

}
