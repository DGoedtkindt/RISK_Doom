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
     * Action performed when this Button is clicked.
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
<<<<<<< HEAD
     * is opaque, then it can be used.
=======
     * is opaque, and if no InputPanel is currently being used, then it can be used.
>>>>>>> MassiveUpdate
     * @return A boolean representation of the fact that this ModeButton
     *         can be used in the current situation.
     */
    protected boolean isUsable(){
        
        boolean isOpaque = getImage().getTransparency() == Appearance.OPAQUE;
        boolean noUsedInputPanel = InputPanel.usedPanel == null;
        boolean inputPanelIsUsed = InputPanel.usedPanel != null;
        boolean isButtonOnInputPanel = InputPanel.YES == this || InputPanel.NO == this || InputPanel.SELECT == this;
        
        return isOpaque && (noUsedInputPanel || (inputPanelIsUsed && isButtonOnInputPanel));
        
    }
    
}
