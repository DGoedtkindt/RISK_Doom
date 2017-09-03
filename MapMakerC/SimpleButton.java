import greenfoot.GreenfootImage;
import java.awt.Color;

public class SimpleButton extends Button {
    
    Mode linkedMode;
    
    public SimpleButton(String text, Mode mode){
        
        //createImageFromText to make an image
        //set an action
        
        createImageFromText(text);
        linkedMode = mode;
        
    }
    
    public void clicked() {
        Mode.changeMode(linkedMode);
    }
    
    private void createImageFromText(String textToShow){
        
        GreenfootImage image = new GreenfootImage(textToShow, 25, Color.BLACK, Color.WHITE);
        this.setImage(image);
        
    }   
}
