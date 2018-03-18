package game;

import base.Action;
import base.Button;
import base.MyWorld;
import base.NButton;
import greenfoot.GreenfootImage;
import java.util.ArrayList;
import mode.Mode;
import mode.ModeButton;
import selector.Selector;

/**
 * The ControlPanel containing the Buttons used to play the Game.
 * 
 */
public class ControlPanel {
    
    private MyWorld world() {return MyWorld.theWorld;}
    private Manager manager;
    private NButton nextTurnButton = new NButton(() -> {
        if(Mode.mode() == Mode.DEFAULT){
            Turn.endCurrentTurn();
            Turn.startNewTurn();
        }}
            , "End Turn");
    private ModeButton attackButton = new ModeButton(new GreenfootImage("attack.png"), Mode.ATTACK, Selector.CAN_ATTACK);
    private ModeButton moveButton = new ModeButton(new GreenfootImage("moveArmies.png"), Mode.MOVE, Selector.CAN_ATTACK);
    
    private Action updateThis = () -> {};
    
    
    //to easlily modify all buttons
    private ArrayList<Button> allButtons = new ArrayList<>();
    
    /**
     * Creates a ControlPanel from a specific game.Manager.
     * @param manager The game.Manager of this ControlPanel.
     */
    protected ControlPanel(Manager manager) {
        this.manager = manager;
        allButtons.add(nextTurnButton);
        allButtons.add(attackButton);
        allButtons.add(moveButton);
        
        Mode.addModeChangeListener(() -> {
            
            attackButton.toggleUnusable();
            moveButton.toggleUnusable();
            nextTurnButton.toggleUnusable();
            
            switch(Mode.mode()){
                
                case DEFAULT : 
                    attackButton.toggleUsable();
                    moveButton.toggleUsable();
                    nextTurnButton.toggleUsable();
                    break;
                    
                case MOVE : 
                    moveButton.toggleUsable();
                    break;
                    
                case ATTACK : 
                    attackButton.toggleUsable();
                    break;
                    
            }
            
        });
        
    }
    
    /**
     * Adds the ControlPanel in a specific position in the World.
     * @param xPos The x coordinate of the ControlPanel.
     * @param yPos The y coordinate of the ControlPanel.
     */
    protected void addToWorld(int xPos, int yPos) {
        Mode.addModeChangeListener(updateThis);
        world().addObject(nextTurnButton, xPos, yPos + 300);
        world().addObject(attackButton, xPos, yPos + 200);
        world().addObject(moveButton, xPos, yPos + 100);
        
    }
    
    /**
     * Removes the ControlPanel from the World.
     */
    protected void removeFromWorld() {
        Mode.removeModeChangeListener(updateThis);
        allButtons.forEach(world()::removeObject);
    
    }
    
    
    
}
