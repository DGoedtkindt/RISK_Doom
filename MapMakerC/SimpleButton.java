import greenfoot.GreenfootImage;
import java.awt.Color;

public class SimpleButton extends Button {
    
    Mode linkedMode;
    Validator validator;
    
    public SimpleButton(String text, Mode mode, Validator validator){
        createImageFromText(text);
        linkedMode = mode;
        this.validator = validator;
        
    }
    
    public void clicked() {
        Mode.changeMode(linkedMode);
        Selector.setValidator(validator);
        
    }
    
    private void createImageFromText(String textToShow){
        GreenfootImage image = new GreenfootImage(textToShow, 22, Color.BLACK, Color.WHITE);
        this.setImage(image);
        
    }   
}
