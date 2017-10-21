import greenfoot.GreenfootImage;
import java.util.function.Predicate;

public class ModeButton extends Button {
    
    Mode linkedMode;
    Predicate validator;
    
    public ModeButton(String imageName, Mode mode, Predicate validator) throws IllegalArgumentException{
        GreenfootImage img = new GreenfootImage(imageName);
        img.scale(80, 80);
        setImage(img);
        linkedMode = mode;
        this.validator = validator;
        
    }
    
    @Override
    public void clicked() {
        
        if(Mode.currentMode() == Mode.MAP_EDITOR_DEFAULT) {
            Mode.changeMode(linkedMode);
            Selector.setValidator(validator);
        }
    }
    
    public void makeTransparent() {
        getImage().setTransparency(MyWorld.TRANSPARENT);
    
    }
    
    public void makeOpaque() {
        getImage().setTransparency(MyWorld.OPAQUE);
    
    }
    
}
