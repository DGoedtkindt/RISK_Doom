package game;

import base.Action;
import base.Button;
import base.MyWorld;
import base.NButton;
import java.util.ArrayList;
import mode.Mode;
import mode.ModeButton;
import selector.Selector;

public class ControlPanel {
    
    private MyWorld world() {return MyWorld.theWorld;}
    private Manager manager;
    private NButton nextTurnButton = new NButton(() -> {
        Turn.endCurrentTurn();
        Turn.startNewTurn();}, "End Turn");
    private ModeButton attackButton = new ModeButton("attack.png", Mode.ATTACK, Selector.IS_OWNED_TERRITORY);
    private ModeButton moveButton = new ModeButton("moveArmies.png", Mode.MOVE, Selector.IS_OWNED_TERRITORY);
    
    private Action updateThis = () -> {};
    
    
    //to easlily modify all buttons
    private ArrayList<Button> allButtons = new ArrayList<>();
    
    protected ControlPanel(Manager manager) {
        this.manager = manager;
        allButtons.add(nextTurnButton);
        allButtons.add(attackButton);
        allButtons.add(moveButton);
    
    }
    
    protected void addToWorld(int xPos, int yPos) {
        Mode.addModeChangeListener(updateThis);
        world().addObject(nextTurnButton, xPos, yPos + 300);
        world().addObject(attackButton, xPos, yPos + 200);
        world().addObject(moveButton, xPos, yPos + 100);
        
    }
    
    protected void removeFromWorld() {
        Mode.removeModeChangeListener(updateThis);
        allButtons.forEach(world()::removeObject);
    
    }
    
    
    
}
