package game;

import base.MyWorld;
import base.NButton;
import greenfoot.GreenfootImage;
import mode.Mode;
import mode.ModeButton;
import selector.Selector;

public class ControlPanel {
    
    private MyWorld world() {return MyWorld.theWorld;}
    
    private NButton nextTurnButton = new NButton(() -> {Turn.nextTurn();}, new GreenfootImage("backToHome.png"));
    private ModeButton attackButton = new ModeButton("backToHome.png", Mode.ATTACK, Selector.IS_OWNED_TERRITORY);
    private ModeButton moveButton = new ModeButton("backToHome.png", Mode.MOVE, Selector.IS_NOT_OWNED_TERRITORY);
    
    public void addToWorld(int xPos, int yPos) {
        
    }
    
}
