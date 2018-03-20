package newGameMenu;

import appearance.Theme;
import base.GColor;
import base.NButton;
import basicChoosers.PColorChooser;
import game.Player;
import greenfoot.GreenfootImage;
import greenfoot.World;
import input.Form;
import java.util.ArrayList;

/**
 * A PlayersPanel is a group of actors that allows the user to set the 
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
    
    /**
     * Creates a PlayersPanel.
     */
    protected PlayersPanel() {
        addPlayer();addPlayer();addPlayer();
        initNewP();
        
    }
    
    /**
     * Obtains the list of the Players.
     * @return A list of Players.
     */
    protected ArrayList<Player> getPlayers() {
        ArrayList<Player> returnList = new ArrayList<>();
        players.forEach((PlayerOptions po) -> {
            returnList.add(po.getPlayer());
            po.colorChooser.clearChoices();
        });
        return returnList;
        
    }
    
    /**
     * Adds the PlayersPanel to the World.
     * 
     * @param toWorld The World on which the Panel will be added.
     * @param xPos The x coordinate of this Panel.
     * @param yPos The y coordinate of this Panel.
     */
    protected void addToWorld(World toWorld, int xPos, int yPos) {
        world = toWorld;
        this.xPos = xPos;
        this.yPos = yPos;
        players.forEach((PlayerOptions po) -> {po.addToWorld(world,0, 0);});
        world.addObject(newP,0,0);
        updatePositions();
    
    }
    
    /**
     * Destroys this Panel.
     */
    protected void destroy() {
        if(world != null) world.removeObject(newP);
        players.forEach(PlayerOptions::delete);
        players = null;
        world = null;
        
    }
    
    /**
     * Updates the positions of each Player in the selection screen.
     */
    private void updatePositions() {
        for(int i = 0; i < players.size(); i++) {
            players.get(i).changeLocation(objPos(i)[0], objPos(i)[1]);
        
        }
        newP.setLocation(objPos(players.size())[0], objPos(players.size())[1]);
    
    }
    
    /**
     * Gets the position of a PlayerOptions.
     * @param num The number of the PlayerOptions.
     */
    private int[] objPos(int num) {
        int relXPos = (-1 + 2*((int)(num/3)))*HEIGHT/2;
        int relYPos = (-1 +(num % 3)) * WIDHT/2;
        return new int[]{xPos+relXPos,yPos+relYPos};

        
    }
    
    /**
     * Removes a Player from the Game creation.
     * @param toRemove The PlayerOptions to remove.
     */
    private void removePlayer(PlayerOptions toRemove) {
        toRemove.delete();
        players.remove(toRemove);
        if(world != null) addToWorld(world,xPos, yPos);
        if(players.size()<= 3) players.forEach(PlayerOptions::makeUndeletable);
        
    }
    
    /**
     * Adds a PlayerOptions to the list of Players.
     */
    private void addPlayer() {
        PlayerOptions newPlayer = new PlayerOptions(this, Player.NEW_PLAYER_NAME);
        players.add(newPlayer);
        if(world != null) addToWorld(world, xPos, yPos);
        if(players.size()>5) world.removeObject(newP);
        if(players.size()>3) players.forEach(PlayerOptions::makeDeletable);
    
    }
    
    /**
     * Instanciates the 'New Player' Button.
     */
    private void initNewP() {
        newP = new NButton(() -> {addPlayer();});
        GreenfootImage newPimg = new GreenfootImage("+ New Player",24,Theme.used.textColor,new GColor(0,0,0,0));
        newP.setImage(newPimg);
        
    }
    
    
    /**
     * PlayerOption is a group of actor that allows the user to delete a player,
     * change its name, its color. It also creates a Player Object from its
     * settings. 
     */
    private class PlayerOptions{
        String name;
        PlayersPanel manager;
        NButton delete; 
        NButton editName;
        PColorChooser colorChooser = new PColorChooser();
        int xPos;
        int yPos;
        
        /**
         * Creates a PlayerOptions.
         * @param panel The PlayersPanel that manages this Object.
         * @param initName The initial name of this future Player.
         */
        PlayerOptions(PlayersPanel panel,String initName) {
            manager = panel;
            delete = new NButton(()-> {manager.removePlayer(this);}, new GreenfootImage("delete_Icon.png"),20,20);
            editName =  new NButton(() -> {askForName();});
            setName(initName);
        
        }
        
        /**
         * Adds the PlayerOptions to the World.
         * 
         * @param toWorld The World on which the PlayerOptions will be added.
         * @param xPos The x coordinate of this PlayerOptions.
         * @param yPos The y coordinate of this PlayerOptions.
         */
        void addToWorld(World toWorld, int xPos, int yPos) {
            this.xPos = xPos;
            this.yPos = yPos;
            world.addObject(editName, xPos,yPos-20);
            colorChooser.addToWorld(xPos, yPos+20);
            
        };
        
        /**
         * Changes the position of this PlayerOptions.
         * @param newX The new x coordinate.
         * @param newY The new y coordinate.
         */
        void changeLocation(int newX, int newY) {
            xPos = newX; yPos = newY;
            editName.setLocation(xPos, yPos-20);
            colorChooser.changeLocation(xPos, yPos+20);
            delete.setLocation(xPos+100, yPos);
        
        }
        
        /**
         * Adds a Button that can delete this PlayerOptions.
         */
        void makeDeletable() {
            if (world != null) world.addObject(delete, xPos+100, yPos);
            
        }
        
        /**
         * Removes the Button that can delete this PlayerOptions.
         */
        void makeUndeletable() {
            if(world != null) world.removeObject(delete);
        
        }
        
        /**
         * Creates the Player represented by this Object.
         * @return A new Player.
         */
        Player getPlayer() {
            GColor colorChoice = GColor.fromRGB(colorChooser.choiceValue());
            return new Player(name, colorChoice);
        }
        
        /**
         * Destroys this playerOptions.
         */
        void delete() {
            if(world != null) {
                world.removeObject(delete);
                world.removeObject(editName);
            }
            colorChooser.destroy();
            
        }
        
        /**
         * Changes the name of this PlayerOptions.
         * @return The name that the User entered.
         */
        void askForName() {
            Form.inputText("Enter the new Name", (input)->{
                setName(input.get("inputedText"));
            });
            
        }
        
        /**
         * Changes the name of this PlayerOptions.
         * @param initName The new name of this PlayerOptions.
         */
        void setName(String initName) {
            name = initName;
            if(name != null) {
                GreenfootImage txtImg = new GreenfootImage(name, 24, Theme.used.textColor, new GColor(0, 0, 0, 0));
                GreenfootImage editNameImg = new GreenfootImage(txtImg.getWidth() + 40, 30);
                editNameImg.drawImage(txtImg, 0, 5);
                GreenfootImage editIcon = new GreenfootImage("edit_icon.png");
                editIcon.scale(30, 30);
                editNameImg.drawImage(editIcon, txtImg.getWidth() + 10, 0);
                editName.setImage(editNameImg);
            }
        
        }
        
    }

}
