package game;

import base.Button;
import base.MyWorld;
import base.NButton;
import java.util.ArrayList;
import greenfoot.GreenfootImage;
import mode.Mode;
import mode.ModeButton;
import selector.Selector;

public class ControlPanel {
    
    private MyWorld world() {return MyWorld.theWorld;}
    private Manager manager;
    private NButton saveGame;
    private NButton nextTurnButton = new NButton(() -> {
        Turn.endCurrentTurn();
        Turn.startNewTurn();}
            , new GreenfootImage("backToHome.png"));
    private ModeButton attackButton = new ModeButton("backToHome.png", Mode.ATTACK, Selector.IS_OWNED_TERRITORY);
    private ModeButton moveButton = new ModeButton("backToHome.png", Mode.MOVE, Selector.IS_OWNED_TERRITORY);
    
    
    //to easlily modify all buttons
    private ArrayList<Button> allButtons = new ArrayList<>();
    
    protected ControlPanel(Manager manager) {
        this.manager = manager;
        this.saveGame = new NButton(manager.saveGame, "save the world!!!");
        allButtons.add(saveGame);
        allButtons.add(nextTurnButton);
        allButtons.add(attackButton);
        allButtons.add(moveButton);
    
    }
    
    protected void addToWorld(int xPos, int yPos) {
        world().addObject(saveGame, xPos, yPos);
        world().addObject(nextTurnButton, xPos, yPos + 100);
        world().addObject(attackButton, xPos, yPos + 200);
        world().addObject(moveButton, xPos, yPos + 300);
        
    }
    
    protected void removeFromWorld() {
        allButtons.forEach(world()::removeObject);
    
    }
    
}
