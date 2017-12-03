package menu;

import base.Button;
import base.MyWorld;


public class PlayGameButton extends Button{
    
    public PlayGameButton(){
        
        //Image
        
    }
    
    public void clicked(){
        
        MyWorld.theWorld.gameMenu();
        
    }
    
}
