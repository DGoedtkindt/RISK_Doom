package territory;

import base.GColor;
import java.util.ArrayList;
import java.util.HashMap;
import mode.Mode;

/**
 * This Class represents Links between Territories.
 * 
 */
public class Links {
    /**
     * The Links Object currently being modified
     */
    public static Links modifying() {return modifying;}
    private static Links modifying;
    
    static{
        //to make sure 'modifying' is null when not in Mode 'SET_LINK'
        Mode.addModeChangeListener(()->{
            if(Mode.mode() != Mode.SET_LINK & modifying != null) {
                modifying.destroyIf();
                modifying = null;
            }
        });
    }
    
    public final GColor COLOR;
    
    private final HashMap<Territory,LinkIndic> terrIndicMap = new HashMap<>();
    
    /**
     * Creates a Links Object.
     * @param color The Color of this Link.
     */
    public Links(GColor color) {
        COLOR = color;
        
    }
    
    /**
     * Allows the user to add new Links to
     */
    public void edit() {
        if(modifying != null) System.err.println("A Links was already being modified");
        else {
            Mode.setMode(Mode.SET_LINK);
            modifying = this;
            
        }
    
    }
    
    /**
     * Adds a LinkIndic and a Territory to the Link.
     * @param linkedTerr The added Territory.
     * @param coordinates the coordinates where to draw the Link indicator.
     */
    public void addlink(Territory linkedTerr , int[] coordinates) {
        if(terrIndicMap.containsKey(linkedTerr)) {
            terrIndicMap.get(linkedTerr).setLocation(coordinates);
            
        } else {
            LinkIndic newLinkIndic = new LinkIndic(this, linkedTerr, coordinates);
            terrIndicMap.put(linkedTerr, newLinkIndic);
            linkedTerr.addLink(this);
        
        }
        
    }
    
    /**
     * Removes a Territory from the Links
     * @param terrToRemove Territory that will be removed;
     */
    public void removeLink(Territory terrToRemove) {
        terrIndicMap.get(terrToRemove).removeFromWorld();
        terrIndicMap.remove(terrToRemove);
        terrToRemove.removeLink(this);
        destroyIf();
        
    }
    
    /**
     * Gets the Territories this Links links together with the coordinates of
     * where the Links indicator should be.
     * @return A HashMap of the Territories with the coordinates of the Links indicator.
     */
    public HashMap<Territory,LinkIndic> terrIndicMap() {
        return new HashMap<>(terrIndicMap);
    
    }
    
    /**
     * Checks if this Links contains at least two Territories. If not destroy it.
     * @return whether this Links has been destroyed.
     */
    private boolean destroyIf() {
        boolean destroy = terrIndicMap.size() == 1;
        if(destroy) {
            destroy();
            
        }
        return destroy;
        
    }
    
    /**
     * Destroys this Links
     */
    public void destroy() {
        while(!terrIndicMap.isEmpty()) {
            removeLink((Territory)(new ArrayList(terrIndicMap.keySet()).get(0)));
            
        }
        
    
    }
    
}
