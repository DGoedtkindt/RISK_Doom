package territory;

/**
 * A Controler of a Territory controls what happens when the Territory gets clicked on. 
 */
public abstract class Controler {
    protected final Territory territory;
    
    public Controler(Territory terrToControl) {
        territory = terrToControl;
    
    }
    
    public abstract void clicked();
    
}
