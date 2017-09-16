import greenfoot.GreenfootImage;

public class ModeButton extends Button {
    
    Mode linkedMode;
    Validator validator;
    
    public ModeButton(String imageName, Mode mode, Validator validator) throws IllegalArgumentException{
        GreenfootImage img = new GreenfootImage(imageName);
        img.scale(80, 80);
        setImage(img);
        linkedMode = mode;
        this.validator = validator;
        
    }
    
    public void clicked() {
        if(Mode.currentMode() == Mode.DEFAULT) {
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
