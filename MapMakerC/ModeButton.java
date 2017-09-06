import greenfoot.GreenfootImage;
import java.awt.Color;

public class ModeButton extends Button {
    
    Mode linkedMode;
    Validator validator;
    
    public ModeButton(String text, Mode mode, Validator validator){
        createImageFromText(text);
        linkedMode = mode;
        this.validator = validator;
        
    }
    
    public void clicked() {
        if(Mode.currentMode() == Mode.DEFAULT) {
            Mode.changeMode(linkedMode);
            Selector.setValidator(validator);
        }
    }
    
    private void createImageFromText(String textToShow){
        GreenfootImage image = new GreenfootImage(textToShow, 22, Color.BLACK, Color.WHITE);
        this.setImage(image);
        
    }   
}
