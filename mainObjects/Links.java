package mainObjects;

import base.GColor;
import base.Map;
import base.MyWorld;
import java.util.ArrayList;

/**
 * This Class represents Links between Territories.
 * 
 */
public class Links {
    
    private MyWorld world() {return MyWorld.theWorld;}
    private Map map() {return world().stateManager.map();}
    public static Links newLinks;   //The Links object being modified
    public ArrayList<Territory> linkedTerrs = new ArrayList<>(); //To check whether a terr was already linked
    private final GColor COLOR;
    private final ArrayList<LinkIndic> linkIndicList = new ArrayList<>();
    
    /**
     * Creates a Links Object.
     * @param color The Color of this Link.
     */
    public Links(GColor color) {
        COLOR = color;
        newLinks = this;
    }
    
    /**
     * Adds this Link to the World, and the corresponding LinkIndics.
     */
    public void addToWorld() {
        linkIndicList.forEach(LinkIndic::addToWorld);
        map().links.add(this);
        
    }
    
    /**
     * Adds a LinkIndic and a Territory to the Link.
     * @param linkIndic The addes LinkIndic.
     * @param linkedTerr The added Territory.
     */
    public void addlink(LinkIndic linkIndic, Territory linkedTerr ) {
        linkIndicList.add(linkIndic);
        if(linkedTerrs.contains(linkedTerr)) {
            linkIndic.destroy();
        }
        linkedTerrs.add(linkedTerr);
        
    }
    
    /**
     * Removes a LinkIndic and its Territory from the Link.
     * @param linkToRemove The linkIndic to remove.
     */
    public void removelink(LinkIndic linkToRemove) { 
        linkedTerrs.remove(linkToRemove.linkedTerr());
        linkIndicList.remove(linkToRemove);
        
        if(!isLargeEnough() && newLinks != this) {
            linkIndicList.get(0).destroy();
            map().links.remove(this);
        }
    }
    
    /**
     * Checks if the Link contains at least two Territories.
     * @return A boolean representation of this test.
     */
    public boolean isLargeEnough() {
        return linkIndicList.size() != 1;
        
    }
    
    /**
     * Destroys the Link and its LinkIndics.
     */
    public void destroy() {
        while(!linkIndicList.isEmpty()) {
            linkIndicList.get(0).destroy();
            
        }
        
        map().links.remove(this);
    
    }

    /**
     * Gets the Color of this Link.
     * @return The Color of this Link.
     */
    public GColor color() {return COLOR;}
    
    /**
     * Gets this Link's LinkIndics list.
     * @return The LinkIndics participating in this Link.
     */
    public ArrayList<LinkIndic> LinkIndicsList() {
        return (ArrayList<LinkIndic>)linkIndicList.clone();
    
    }
    
}
