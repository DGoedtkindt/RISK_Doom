package base;

import appearance.Appearance;
import greenfoot.Actor;
import input.InputPanel;

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
    
    /**
     * Checks if this ModeButton can be used in the current situation. If the Button 
     * is opaque, and if no InputPanel is currently being used, then it can be used.
     * @return A boolean representation of the fact that this ModeButton
     *         can be used in the current situation.
     */
    protected boolean isUsable(){
        return this.getImage().getTransparency() == Appearance.OPAQUE && InputPanel.usedPanel == null;
        
    }
    
}
