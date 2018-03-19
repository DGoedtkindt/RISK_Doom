package base;

import appearance.Appearance;
import greenfoot.Actor;

/**
 * Class of every Button.
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
    
    /**
     * @return Whether this Button is usable.
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
