package menu;

import base.Button;
import base.MyWorld;


public class MapEditorButton extends Button{
    
    public MapEditorButton(){
        
        //Image
        
    }
    
    public void clicked(){
        
        MyWorld.theWorld.mapEditorMenu();
        
    }
    
}
