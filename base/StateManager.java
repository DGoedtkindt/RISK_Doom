package base;

/** a StateManager manages the setup and proper destruction of a scene in a 
 * certain program state (menu, game, options,...). it also manages the modes,
 * and Map/Game objects, if it's program state requires it
 */
public abstract class StateManager {
    
    public abstract void setupScene();
    public abstract void clearScene();
    public Map map() {throw new UnsupportedOperationException("There are no maps in this program state");}
    public Game game() {throw new UnsupportedOperationException("There are no games in this program state");}
    public abstract void escape();
    
}
