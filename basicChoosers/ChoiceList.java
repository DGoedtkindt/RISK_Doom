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
    
    //if This blocks other ChoiceLists from using its current
    //choice, this method allows to liberate the choice when This is destroyed
    //for example, it should not be allowed for two players to choose the same 
    //color, thus the ChoiceList for a color will block its selected color.
    protected void liberateChoice() {}

}
