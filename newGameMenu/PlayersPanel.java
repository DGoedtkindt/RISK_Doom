package newGameMenu;

import base.NButton;
import base.GColor;
import basicChoosers.PColorChooser;
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
    private final int WIDHT = 200;
    private final int HEIGHT = 300;
    
    
    private ArrayList<PlayerOptions> players = new ArrayList<>();
    private NButton newP;
    private World world;
    private int xPos;
    private int yPos;
    
    protected PlayersPanel() {
        addPlayer();addPlayer();addPlayer();
        initNewP();
        
    }
    
    protected ArrayList<Player> getPlayers() {
        ArrayList<Player> returnList = new ArrayList<>();
        players.forEach((PlayerOptions po) -> {returnList.add(po.getPlayer());});
        return returnList;
        
    }
    
    protected void addToWorld(World toWorld, int xPos, int yPos) {
        world = toWorld; this.xPos = xPos; this.yPos = yPos;
        players.forEach((PlayerOptions po) -> {po.addToWorld(world,0, 0);});
        world.addObject(newP,0,0);
        updatePositions();
    
    }
    
    protected void destroy() {
        if(world != null) world.removeObject(newP);
        players.forEach(PlayerOptions::delete);
        players = null;
        world = null;
        
    }
    
    private void updatePositions() {
        for(int i = 0; i < players.size(); i++) {
            players.get(i).changeLocation(objPos(i)[0], objPos(i)[1]);
        
        }
        newP.setLocation(objPos(players.size())[0], objPos(players.size())[1]);
    
    }
    
    private int[] objPos(int num) {
        int relXPos = (-1 + 2*((int)(num/3)))*HEIGHT/2;
        int relYPos = (-1 +(num % 3)) * WIDHT/2;
        return new int[]{xPos+relXPos,yPos+relYPos};

        
    }
    
    private void removePlayer(PlayerOptions toRemove) {
        toRemove.delete();
        players.remove(toRemove);
        if(world != null) addToWorld(world,xPos, yPos);
        if(players.size()<= 3) players.forEach(PlayerOptions::makeUndeletable);
        
    }
    
    private void addPlayer() {
        PlayerOptions newPlayer = new PlayerOptions(this, "A New Player");
        players.add(newPlayer);
        if(world != null) addToWorld(world, xPos, yPos);
        if(players.size()>5) world.removeObject(newP);
        if(players.size()>3) players.forEach(PlayerOptions::makeDeletable);
    
    }
    
    private void initNewP() {
        newP = new NButton((ActionEvent ae) -> {addPlayer();});
        GreenfootImage newPimg = new GreenfootImage("+ New Player",24,Theme.used.textColor,new GColor(0,0,0,0));
        newP.setImage(newPimg);
        
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
        PColorChooser colorChooser = new PColorChooser();
        int xPos;
        int yPos;
        
        PlayerOptions(PlayersPanel panel,String initName) {
            manager = panel;
            delete = new NButton((ActionEvent ae)-> {manager.removePlayer(this);}, new GreenfootImage("delete_Icon.png"),20,20);
            editName =  new NButton((ActionEvent ae) -> {setName(askForName());});
            setName(initName);
        
        }
        
        void addToWorld(World toWorld, int xPos, int yPos) {
            this.xPos = xPos; this.yPos = yPos;
            world.addObject(editName, xPos,yPos-20);
            colorChooser.addToWorld(xPos, yPos+20);
            
        };
        
        void changeLocation(int newX, int newY) {
            xPos = newX; yPos = newY;
            editName.setLocation(xPos, yPos-20);
            colorChooser.changeLocation(xPos, yPos+20);
            delete.setLocation(xPos+100, yPos);
        
        }
        
        void makeDeletable() {
            if (world != null) world.addObject(delete, xPos+100, yPos);
            
        }
        
        void makeUndeletable() {
            if(world != null) world.removeObject(delete);
        
        }
        
        Player getPlayer() {
            GColor colorChoice = GColor.fromRGB(colorChooser.currentChoice());
            return new Player(name, colorChoice);
        }
        
        void delete() {
            if(world != null) {
                world.removeObject(delete);
                world.removeObject(editName);
            }
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
