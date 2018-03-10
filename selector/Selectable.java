package selector;

/**
 * A Selectable is an Actor that can be selected by the User.
 * 
 */
public interface Selectable {
    
    /**
     * Changes the opacity of this Selectable to an opaque value.
     */
    public void makeOpaque();
    
    /**
     * Changes the opacity of this Selectable to a transparent value.
     */
    public void makeTransparent();
    
    /**
     * Changes the color of this Selectable to the selection Color..
     */
    public void makeGreen();
}
