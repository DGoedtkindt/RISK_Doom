package mode;

import selector.Selector;
import base.Button;
import greenfoot.Color;
import greenfoot.GreenfootImage;
import java.util.function.Predicate;

/**
 * A ModeButton is a Button that changes the current Mode when pressed.
 */
public class ModeButton extends Button {
    
    public Mode linkedMode;
    public Predicate validator;
    
    /**
     * Creates a ModeButton.
     * @param img The image of this ModeButton.
     * @param mode The Mode that this Button sets when it's pressed.
     * @param validator The Predicate that allows only some Selectables to be selected.
     */
    public ModeButton(GreenfootImage img, Mode mode, Predicate validator){
        img.scale(80, 80);
        setImage(img);
        linkedMode = mode;
        this.validator = validator;
        
    }
    
    /**
     * Creates a ModeButton.
     * @param imageName The name of the image of this ModeButton.
     * @param mode The Mode that this Button sets when it's pressed.
     * @param validator The Predicate that allows only some Selectables to be selected.
     */
    public ModeButton(String imageName, Mode mode, Predicate validator){
    
        GreenfootImage img = new GreenfootImage("button11-5.png");
        img.scale(175, 80);
        GreenfootImage txtImg = new GreenfootImage(imageName, 20, Color.BLACK, new Color(0,0,0,0));
        int xPos = (175 - txtImg.getWidth()) / 2;
        int yPos = (80 - txtImg.getHeight()) / 2;
        img.drawImage(txtImg, xPos, yPos);

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
    
}
