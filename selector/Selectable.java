package selector;

/**
 * A Selectable is an Object that can be selected by the user via Selector.
 * 
 */
public interface Selectable {
    
    /**
     * Changes the opacity of this Selectable to an opaque value.
     * This shows that this Selectable is a valid Object for Selection.
     */
    public void makeOpaque();
    
    /**
     * Changes the opacity of this Selectable to a transparent value.
     * This Shows that this Selectable isn't a valid Object for selection.
     */
    public void makeTransparent();
    
    /**
     * Changes the color of this Selectable to the selection Color.
     * This shows that this Selectable is selected.
     */
    public void makeSelected();
}
