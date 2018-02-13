package game;

import base.Button;
import base.MyWorld;
import base.NButton;
import java.util.ArrayList;

public class ControlPanel {
    
    private MyWorld world() {return MyWorld.theWorld;}
    private Manager manager;
    //private NButton nextTurnButton = new NButton((ActionEvent ae) -> {Turn.nextTurn();}, new GreenfootImage(""));
    private NButton saveGame;
    
    
    //to easlily modify all buttons
    private ArrayList<Button> allButtons = new ArrayList<>();
    
    protected ControlPanel(Manager manager) {
        this.manager = manager;
        this.saveGame = new NButton(manager.saveGame, "save the world!!!");
        allButtons.add(saveGame);
    
    }
    
    protected void addToWorld(int xPos, int yPos) {
        world().addObject(saveGame, xPos, yPos);
        
    }
    
    protected void removeFromWorld() {
        allButtons.forEach(world()::removeObject);
    
    }
    
}
