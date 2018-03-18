package input;

import base.Action;
import base.MyWorld;
import base.NButton;
import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.GreenfootImage;
import java.util.HashMap;
import java.util.Map;


public class ChoiceInput extends Input {
    
    private final String TITLE;
    private Map<String,NButton> optionButtons = new HashMap<>();
    private String returnText = "";
    private Actor background;
    
    /**
     * Creates a new ChoiceInput without options
     * 
     * @param title this is the title that will be displayed
     */
    public ChoiceInput(String title) {
        TITLE = title;
        background = new Background(WIDTH,HEIGHT);
        
    }
    
    /**
     * Creates a new ChoiceInput with 2 option already added. The ID and the 
     * text displayed on the option buttons are the same.
     * 
     * @param title this is the title that will be displayed
     * @param firstOption   optionID and optionText of the first option
     * @param secondOption  optionID and optionText of the second option
     */
    public ChoiceInput(String title, String firstOption, String secondOption) {
        this(title);
        addOption(firstOption,firstOption);
        addOption(secondOption,secondOption);
    
    }

    @Override
    void addToWorld(int xPos, int yPos) {
        MyWorld.theWorld.addObject(background, xPos, yPos);
        double i = -0.5;
        for(NButton optionButton : optionButtons.values()) {
            int buttonXPos = xPos + (int)(i * WIDTH/(optionButtons.size() + 1));
            MyWorld.theWorld.addObject(optionButton, buttonXPos, yPos+30);
            i++;
            
        }
        
    }

    @Override
    void removeFromWorld() {
        MyWorld.theWorld.removeObject(background);
        optionButtons.values().forEach((button)->{
            MyWorld.theWorld.removeObject(button);
        });
        
    }

    @Override
    public String value() {
        return returnText;
    }
    
    /**Add an option to this ChoiceInput. 
     * Doesn't work if this ChoiceInput is
     * already on the world.
     *
     * @param optionID  is the value returned if the added option is selected 
     * @param optionText is the text displayed on the option button
     */
    public void addOption(String optionID, String optionText) {
        Action onClickAction = ()->{
            returnText = optionID;
            optionButtons.values().forEach(NButton::toggleUsable);
            optionButtons.get(optionID).toggleUnusable();
            submit();
            
        };
        NButton optionButton = new NButton(onClickAction, optionText);
        optionButtons.put(optionID, optionButton);
        
    }
    
    /**
     * just an actor with a certain image that serves as a background with a title.
     */
    private class Background extends Actor {

        private Background(int width, int height) {
            Color backgroundColor = appearance.Theme.used.backgroundColor.brighter();
            this.setImage(new GreenfootImage(width,height));
            this.getImage().setColor(backgroundColor);
            this.getImage().fill();
            this.getImage().setColor(appearance.Theme.used.textColor);
            this.getImage().setFont(appearance.Appearance.GREENFOOT_FONT);
            this.getImage().drawString(TITLE, 250,40);
        }

    }

}
