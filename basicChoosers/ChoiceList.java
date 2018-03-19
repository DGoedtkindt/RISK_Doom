package basicChoosers;

import greenfoot.GreenfootImage;

/** Manages the choices for a BasicChooser. Must be able to generate the image
 * that the BasicChooser will display representing the current choice
 * and get the string value of the current choice
 */
public abstract class ChoiceList {
    
    protected abstract void next();
    
    protected abstract void previous();
    
    protected abstract GreenfootImage choiceImage();
    
    protected abstract String choiceValue();
    
    /**
     * Lets a particular choice be selected.
     */
    protected void liberateChoice() {}

}
