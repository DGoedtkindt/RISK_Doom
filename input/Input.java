package input;

import base.Action;

/**
 * An Input is an Object that allow to enter some information. It can be 
 * added to a Form that will display it and use it's information.
 * 
 */
public abstract class Input {
    
    public static final int HEIGHT = 180;
    public static final int WIDTH = 2*appearance.Appearance.WORLD_WIDTH/3;
    protected static Input activeInput;
    
    /**
     * action this Input is going to perform when submit is called
     */
    Action onSubmitAction;
    
    abstract void addToWorld(int xPos, int yPos);
    abstract void removeFromWorld();
    
    /** Gets the value this Input stores.
     * @return the value that was inputed. Must return "" if nothing
     *      was inputed
     */
    public abstract String value();
    
    /** Performs the on submit action attributed to this Input.
     * 
     */
    protected void submit() {
        if(onSubmitAction != null) onSubmitAction.act();
        if(activeInput == this) activeInput = null;
    
    }
    
}
