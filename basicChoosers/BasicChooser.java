package basicChoosers;

import arrowable.Arrowable;
import arrowable.LeftArrow;
import arrowable.RightArrow;
import base.MyWorld;
import greenfoot.Actor;


/**
 * A BasicChooser is a group of actors that allows to choose between the choices
 * of a ChoiceList. It being a group of actor means that it should not be
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
     * @param choiceList the choices this chooser will allow to choose from.
     */
    public BasicChooser(ChoiceList choiceList) {
        choices = choiceList;
        rightArrow = new RightArrow(this);
        rightArrow.scale(arrSize);
        leftArrow = new LeftArrow(this);
        leftArrow.scale(arrSize);
        updateImage();
    
    }
    
    public void addToWorld(int xPos, int yPos) {
        x = xPos; y = yPos;
        world().addObject(this,x,y);
        world().addObject(rightArrow, x+halfGapSize,y);
        world().addObject(leftArrow, x-halfGapSize,y);
    
    }
    
    public void changeLocation(int newX, int newY) {
        this.setLocation(newX, newY); x = newX; y = newY;
        rightArrow.setLocation(x+halfGapSize,y);
        leftArrow.setLocation(x-halfGapSize,y);
    
    }
    
    public void setArrows(int gapSize,int arrowSize) {
        halfGapSize = gapSize/2;
        arrSize = arrowSize;
        rightArrow.scale(arrSize);
        leftArrow.scale(arrSize);
        rightArrow.setLocation(x+halfGapSize,y);
        leftArrow.setLocation(x-halfGapSize,y);
    
    }
    
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
    
    public String currentChoice() {
        return choices.choiceValue();
    
    }
    
    private void updateImage() {
        //for the moment it's basic, but it may be more complex in the future
        this.setImage(choices.choiceImage());
    
    }
    
}
