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
     * Returns the world.
     * @return The world.
     */
    protected static MyWorld world() {return MyWorld.theWorld;}
    
    /**Returns whether the button is usable.
     * 
     * @return whether the button is usable.
     */
    public boolean isUsable(){
        return usable;
        
    }
    
    /**
     * makes the button active and opaque.
     */
    public void toggleUsable() {
        usable = true;
        this.getImage().setTransparency(Appearance.OPAQUE);

    }
    
    /**
     * makes the button inactive and transparent.
     */
    public void toggleUnusable() {
        usable = false;
        this.getImage().setTransparency(Appearance.TRANSPARENT);
    
    }
    
}
