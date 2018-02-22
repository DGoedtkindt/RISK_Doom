package base;

import appearance.Appearance;
import greenfoot.Actor;

/**
 * Class of every Button.
 * 
 */
public abstract class Button extends Actor {

    /**
     * Action performed when a Button is clicked.
     */
    public abstract void clicked();
    
    static final int TRANSPARENT = 30;
    static final int OPAQUE = 255;
    
    /**
     * Makes the Button almost transparent.
     */
    public void makeTransparent() {
        getImage().setTransparency(Appearance.TRANSPARENT);
    
    }
    
    /**
     * Makes the Button completely opaque.
     */
    public void makeOpaque() {
        getImage().setTransparency(Appearance.OPAQUE);
    
    }
    
    /**
     * Returns the world.
     * @return The world.
     */
    protected static MyWorld world() {return MyWorld.theWorld;}
    
}
