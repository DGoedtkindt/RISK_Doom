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
    private final RightArrow rightArrow;
    private final LeftArrow leftArrow;
    protected MyWorld world() {return MyWorld.theWorld;}
    private int x;
    private int y;
    
    
    /**
     */
    public BasicChooser() {
        rightArrow = new RightArrow(this);
        rightArrow.scale(arrSize);
        leftArrow = new LeftArrow(this);
        leftArrow.scale(arrSize);
    
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
        updateImage();
    
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
        liberateChoice();
        
    }
    
    /**
     * Updates the image of this BasicChooser.
     */
    protected abstract void updateImage();
    
     /**
     * Returns the chosen value.
     * @return The chosen value.
     */
    public abstract String choiceValue();
    
    //if This blocks other Choosers from using its current
    //choice, this method allows to liberate the choice when This is destroyed
    //for example, it should not be allowed for two players to choose the same 
    //color, thus the ChoiceList for a color will block its selected color.
    protected void liberateChoice() {}
    
}
