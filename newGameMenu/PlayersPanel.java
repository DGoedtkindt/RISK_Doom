package newGameMenu;

import base.NButton;
import base.GColor;
import basicChoosers.BasicChooser;
import basicChoosers.PColorChoices;
import greenfoot.GreenfootImage;
import java.awt.event.ActionEvent;
import appearance.Theme;
import greenfoot.World;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import mainObjects.Player;


/**
 * a PlayersPanel is a group of actors that allows the user to set the 
 * names and colors of the played.
 * The destroy() method must be used to remove the the group of actor
 */
public class PlayersPanel {
    //Player Panel should be maximum 500x500 large
    
    private ArrayList<PlayerOptions> players = new ArrayList<>();
    private NButton newP;
    private World world;
    private int xPos;
    private int yPos;
    
    protected PlayersPanel() {
        addPlayer();addPlayer();addPlayer();
        newP = new NButton((ActionEvent ae) -> {addPlayer();});
        GreenfootImage newPimg = new GreenfootImage("+ New Player",24,Theme.used.textColor,new GColor(0,0,0,0));
        newP.setImage(newPimg);
        
    
    }
    
    protected ArrayList<Player> getPlayers() {
        ArrayList<Player> returnList = new ArrayList<>();
        players.forEach((PlayerOptions po) -> {returnList.add(po.getPlayer());});
        return returnList;
        
    }
    
    protected void addToWorld(World toWorld, int xPos, int yPos) {
        world = toWorld; this.xPos = xPos; this.yPos = yPos;
        for(int i = 0; i < players.size(); i++) {
            int poX = (-1 + 2*((int)(i/3)))*150;
            int poY = (-1 +(i % 3)) * 100;
            players.get(i).addToWorld(world, xPos + poX, yPos + poY);
            
        }
        
        if(players.size() < 6) {
            int newPX = 200;
            int newPY = (-1 +(players.size() % 3)) * 100;
            world.addObject(newP, xPos + newPX, yPos + newPY);
        }
    
    }
    
    protected void destroy() {
        world.removeObject(newP);
        players.forEach(PlayerOptions::delete);
        players = null;
        world = null;
    
    }
    
    private void removePlayer(PlayerOptions toRemove) {
        toRemove.delete();
        players.remove(toRemove);
        addToWorld(world, xPos, yPos);
        
    }
    
    private void addPlayer() {
        PlayerOptions newPlayer = new PlayerOptions(this, "Player" + (players.size()+1));
        players.add(newPlayer);
        if(world != null) addToWorld(world, xPos, yPos);
        if(players.size()>5) world.removeObject(newP);
    
    }
    
    
    
    
    
    
    
    /**
     * PlayersOption is a group of actor that allows the user to delete a player,
     * change it's name, it's color. It also creates a Player Object from it's
     * setting. 
     */
    private class PlayerOptions {
        String name;
        PlayersPanel manager;
        NButton delete; 
        NButton editName;
        BasicChooser colorChooser = new BasicChooser(new PColorChoices());
        World world;
        int xPos;
        int yPos;
        
        PlayerOptions(PlayersPanel panel,String initName) {
            manager = panel;
            delete = new NButton((ActionEvent ae)-> {manager.removePlayer(this);}, new GreenfootImage("delete_Icon.png"));
            editName =  new NButton((ActionEvent ae) -> {setName(askForName());});
            setName(initName);
        
        }
        
        void addToWorld(World toWorld, int xPos, int yPos) {
            world = toWorld; this.xPos = xPos; this.yPos = yPos;
            world.addObject(editName, xPos, yPos-20);
            colorChooser.addToWorld(world, xPos, yPos+20);
            
        };
        
        void makeDeletable() {
            world.addObject(delete, xPos, yPos+25+(editName.getImage().getWidth()/2));
            
        }
        
        void makeUndeletable() {
            world.removeObject(delete);
        
        }
        
        Player getPlayer() {
            throw new UnsupportedOperationException("Not supported yet.");
            
        }
        
        void delete() {
            world.removeObject(delete);
            world.removeObject(editName);
            colorChooser.destroy();
            
        }
        
        String askForName() {
            return JOptionPane.showInputDialog("New Name");
        
        }
        
        void setName(String initName) {
            name = initName;
            GreenfootImage txtImg = new GreenfootImage(name,24,Theme.used.textColor,new GColor(0,0,0,0));
            GreenfootImage editNameImg = new GreenfootImage(txtImg.getWidth()+40, 30);
            editNameImg.drawImage(txtImg, 0, 5);
            GreenfootImage editIcon = new GreenfootImage("edit_icon.png");
            editIcon.scale(30,30);
            editNameImg.drawImage(editIcon, txtImg.getWidth()+10, 0);
            editName.setImage(editNameImg);
        
        }
        
    
    }

}
