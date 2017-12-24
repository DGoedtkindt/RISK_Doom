package basicChoosers;

import arrowable.*;
import greenfoot.Actor;
import greenfoot.World;


/**
 * A BasicChooser is a group of actors that allows to choose between the choices
 * of a ChoiceList. It being a group of actor means that it should not be
 * added/removed from a World using World.addObject()/removeObject(), but rather
 * with addToWorld()/destroy().
 */
public class BasicChooser extends Actor implements Arrowable{
    
    private ChoiceList choices;
    private RightArrow rightArrow;
    private LeftArrow leftArrow;
    
    /**
     * @param choiceList the choices this chooser will allow to choose from.
     */
    public BasicChooser(ChoiceList choiceList) {
        choices = choiceList;
        rightArrow = new RightArrow(this);
        rightArrow.getImage().scale(30, 30);
        leftArrow = new LeftArrow(this);
        leftArrow.getImage().scale(30, 30);
        updateImage();
    
    }
    
    public void addToWorld(World world, int xPos, int yPos) {
        world.addObject(this, xPos, yPos);
        world.addObject(rightArrow, xPos+50, yPos);
        world.addObject(leftArrow, xPos-50, yPos);
    
    }
    
    public void destroy() {
        getWorld().removeObject(rightArrow);
        getWorld().removeObject(leftArrow);
        choices.liberateChoice();
        getWorld().removeObject(this);
        
    }

    @Override
    public void next() {
        choices.next();
        this.updateImage();
        
    }

    @Override
    public void previous() {
        choices.previous();
        this.updateImage();
        
    }
    
    private void updateImage() {
        //for the moment it's basic, but it may be more complex in the future
        this.setImage(choices.choiceImage());
    
    }
    
}
