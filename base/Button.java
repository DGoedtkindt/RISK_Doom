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
    protected boolean usable = true;

    
    /**
     * Returns the world.
     * @return The world.
     */
    protected static MyWorld world() {return MyWorld.theWorld;}
    
    /**
     * @return whether the button is usable.
     */
    public boolean isUsable(){
        return usable;
        
        /*boolean isOpaque = getImage().getTransparency() == Appearance.OPAQUE;
        boolean noUsedInputPanel = InputPanel.usedPanel == null;
        boolean inputPanelIsUsed = InputPanel.usedPanel != null;
        boolean isButtonOnInputPanel = InputPanel.YES == this || InputPanel.NO == this || InputPanel.SELECT == this;
        
        return isOpaque && (noUsedInputPanel || (inputPanelIsUsed && isButtonOnInputPanel));*/
        
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
