package basicChoosers;

import arrowable.Arrowable;
import arrowable.LeftArrow;
import arrowable.RightArrow;
import base.MyWorld;
import greenfoot.Actor;


/**
 * A BasicChooser is a group of actors that allows to choose between the choices
 * of a ChoiceList. Being a group of actor means that it should not be
 * added/removed from a World using World.addObject()/removeObject(), but rather
 * with addToWorld()/destroy().
 */
public abstract class BasicChooser extends Actor implements Arrowable{
    public static final int DEFAULT_HALF_GAP_SIZE = 50;
    public static final int DEFAULT_ARROW_SIZE = 30;
    private int halfGapSize = DEFAULT_HALF_GAP_SIZE;
    private int arrSize = DEFAULT_ARROW_SIZE;
    protected ChoiceList choices;
    private RightArrow rightArrow;
    private LeftArrow leftArrow;
    protected MyWorld world() {return MyWorld.theWorld;}
    private int x;
    private int y;
    
    
    /**
     * @param choiceList The choices this chooser will allow to choose from.
     */
    public BasicChooser(ChoiceList choiceList) {
        choices = choiceList;
        rightArrow = new RightArrow(this);
        rightArrow.scale(arrSize);
        leftArrow = new LeftArrow(this);
        leftArrow.scale(arrSize);
        updateImage();
    
    }
    
    /**
     * Adds the objects to the world.
     * @param xPos The x position.
     * @param yPos The y position.
     */
    public void addToWorld(int xPos, int yPos) {
        x = xPos; y = yPos;
        world().addObject(this,x,y);
        world().addObject(rightArrow, x+halfGapSize,y);
        world().addObject(leftArrow, x-halfGapSize,y);
    
    }
    
    /**
     * Change the location of the objects.
     * @param newX The new x position.
     * @param newY The new y position.
     */
    public void changeLocation(int newX, int newY) {
        this.setLocation(newX, newY); x = newX; y = newY;
        rightArrow.setLocation(x+halfGapSize,y);
        leftArrow.setLocation(x-halfGapSize,y);
    
    }
    
    /**
     * Gives the chooser Arrows.
     * @param gapSize The size of the gaps between the Arrows and the Arrowable.
     * @param arrowSize The size of the Arrows.
     */
    public void setArrows(int gapSize,int arrowSize) {
        halfGapSize = gapSize/2;
        arrSize = arrowSize;
        rightArrow.scale(arrSize);
        leftArrow.scale(arrSize);
        rightArrow.setLocation(x+halfGapSize,y);
        leftArrow.setLocation(x-halfGapSize,y);
    
    }
    
    /**
     * Removes the objects this chooser represents.
     */
    public void destroy() {
        getWorld().removeObject(rightArrow);
        getWorld().removeObject(leftArrow);
        getWorld().removeObject(this);
        choices.liberateChoice();
        
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
    
    /**
     * Returns the choosed value.
     * @return The choosed value.
     */
    public String currentChoice() {
        return choices.choiceValue();
    
    }
    
    /**
     * Updates the image of this chooser.
     */
    private void updateImage() {
        this.setImage(choices.choiceImage());
    
    }
    
}
