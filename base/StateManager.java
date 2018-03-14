package base;

import input.InputPanelUser;

/** 
 * A StateManager manages the setup and proper destruction of a scene in a 
 * certain program state (menu, game, options,...). It also manages the modes,
 * and Map/Game objects, if its program state requires it.
 */
public abstract class StateManager implements InputPanelUser{
    
    public abstract void setupScene();
    public abstract void clearScene();
    public Map map() {return null;}
    public Game game() {return null;}
    public abstract void escape();
    protected MyWorld world() {return MyWorld.theWorld;}
    
}
