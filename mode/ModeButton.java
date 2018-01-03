package mode;

import selector.Selector;
import appearance.Appearance;
import base.Button;
import greenfoot.GreenfootImage;
import java.util.function.Predicate;

public class ModeButton extends Button {
    
    public Mode linkedMode;
    public Predicate validator;
    
    public ModeButton(String imageName, Mode mode, Predicate validator){
        GreenfootImage img = new GreenfootImage(imageName);
        img.scale(80, 80);
        setImage(img);
        linkedMode = mode;
        this.validator = validator;
        
    }
    
    @Override
    public void clicked() {
        
        if(isUsable()) {
            Mode.setMode(linkedMode);
            Selector.setValidator(validator);
            makeOpaque();
        }
    }
    
    private boolean isUsable(){
        return this.getImage().getTransparency() == Appearance.OPAQUE; //Le bouton est utilisable s'il est opaque
        
    }
    
}
