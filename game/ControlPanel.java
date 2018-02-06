package game;

import base.NButton;
import greenfoot.GreenfootImage;
import java.awt.event.ActionEvent;

public class ControlPanel {
    
    private NButton nextTurnButton = new NButton((ActionEvent ae) -> {Turn.nextTurn();}, new GreenfootImage(""));
    
    /*public void updateScene(Player player){
    throw new UnsupportedOperationException("Not supported yet.");
    }*/
    
}
