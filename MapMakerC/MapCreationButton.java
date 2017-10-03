import greenfoot.GreenfootImage;
import java.awt.Color;

public class MapCreationButton extends Button{
    
    public MapCreationButton(){
        
        GreenfootImage img = new GreenfootImage("Create new map", 18, Color.BLACK, MyWorld.BASE_WORLD_COLOR);
        setImage(img);
        
    }
    
    public void clicked(){
        
        MyWorld.theWorld.setupScene();
        
    }
    
}
