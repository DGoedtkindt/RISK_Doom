package base;

import selector.Selector;
import appearance.Appearance;
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
        
        if(isUsable()) {
            Mode.changeMode(linkedMode);
            Selector.setValidator(validator);
        }
    }
    
    private boolean isUsable(){
        if(this.getImage().getTransparency() == Appearance.OPAQUE){//Le bouton est opaque s'il est utilisable
           return true; 
        }else{return false;}
        
    }
    
}
