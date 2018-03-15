package base;

/** 
 * A StateManager manages the setup and proper destruction of a scene in a 
 * certain program state (menu, game, options,...). It also manages the 
 * Map/Game objects, if its program state requires it.
 */
public abstract class StateManager {
    
    public abstract void setupScene();
    public abstract void clearScene();
    public Map map() {throw new UnsupportedOperationException("There are no maps in this program state");}
    public Game game() {throw new UnsupportedOperationException("There are no games in this program state");}
    public abstract void escape();
    protected MyWorld world() {return MyWorld.theWorld;}
    
}
