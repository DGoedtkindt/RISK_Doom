package base;

import appearance.Appearance;
import greenfoot.Actor;

/**
 * A Button is an actor that executes an action when the user clicks it.
 * The World must detect the clicks and call the clicked() method.
 * 
 */
public abstract class Button extends Actor {

    /**
     * Action performed when this Button is clicked.
     */
    public abstract void clicked();
    protected boolean usable = true;

    
    /**
     * Returns the World.
     * @return The World.
     */
    protected static MyWorld world() {return MyWorld.theWorld;}
    

    /**Returns whether this Button is usable.
     * 
     * @return whether this Button is usable.
     */
    public boolean isUsable(){
        return usable;
        
    }
    
    /**
     * Makes this Button active and opaque.
     */
    public void toggleUsable() {
        usable = true;
        getImage().setTransparency(Appearance.OPAQUE);

    }
    
    /**
     * Makes this Button inactive and transparent.
     */
    public void toggleUnusable() {
        usable = false;
        getImage().setTransparency(Appearance.TRANSPARENT);
    
    }
    
}
