package territory;

import base.Action;
import base.Map;
import base.MyWorld;
import game.Player;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import base.BlankHex;
import java.util.ArrayList;
import selector.Selectable;

/**
 * A Territory is the model of a territory, the Main object of this game.
 * A Territory is composed of hexes, one of which should display more information
 * about the Territory.
 * It can belong to a Continent and a Player which can have armies on it.
 * The territory can give a certain number of bonus armies to the player.
 */
public class Territory implements Selectable{
  
    /**
     * final immutable collection of BlankHex that represents the coordinates of 
     * the hex composing this Territory.
     */
    public final Set<BlankHex> blankHexSet; //Should try and see if this List really is unmodifiable
    
    /**
     * final BlankHex representing the coordinate of the infoHex of this Territory.
     */
    public final BlankHex infoHex;
    
    private Continent continent;
    private int bonus = 0;
    private int armies = 1;
    private Player owner;
    private SelectionStatus status = SelectionStatus.OPAQUE;
    private ArrayList<Links> links = new ArrayList<>();
    
    private Map map() {return MyWorld.theWorld.stateManager.map();}
    
    /** 
     * Creates a Territory with composing hexes and an infoHex.  
     * @param hexes The BlankHexes that represent the coordinates composing this
     * Territory.
     * @param infoHex The BlankHex that represents the coordinate of the infoHex.
     * @throws java.lang.Exception Cannot create a Territory with less than 2
     * hexes.
     * @throws java.lang.IllegalArgumentException infoHex must be part of hexes.
     */
    public Territory(Set<BlankHex> hexes, BlankHex infoHex) throws Exception{
        if(hexes.size() < 2) throw new Exception("At least 2 BlankHexes must be selected.");
        if(!hexes.contains(infoHex)) throw new IllegalArgumentException(
                "infoHex is not part of the hexes list");
        blankHexSet = Collections.unmodifiableSet(hexes);
        this.infoHex = infoHex; 
    
    }
    
    /**
     * Normal constructor but it set the bonus immediately.
     * @param hexes The BlankHexes that represent the coordinates composing this
     * Territory.
     * @param infoHex The BlankHex that represents the coordinate of the infoHex.
     * @param bonus The bonus number that will be set.
     * @throws java.lang.Exception Cannot create a Territory with less than 2
     * hexes.
     * @throws java.lang.IllegalArgumentException infoHex must be part of hexes.
     */
    public Territory(Set<BlankHex> hexes, BlankHex infoHex, int bonus) throws Exception {
        this(hexes, infoHex);
        setBonus(bonus);
    
    }
    
    /**
     *  Removes all references to this Territory.
     */
    public void destroy() {
        if(continent != null) continent.removeTerritory(this);
        while(!links.isEmpty()) {
            links.get(0).removeLink(this);
        
        }
        map().territories.remove(this);
        System.out.println("Method destroy() in class Territory is not supported yet");
    
    }
    
    /**
     *  Sets the Continent of this Territory.
     * @param newContinent The new Continent.
     */
    public void setContinent(Continent newContinent) {
        if(continent != newContinent) {
            continent = newContinent;
            continentListener.act();
            
        }
        
    
    }
    
    /**
     *  Gets the Continent of this Territory.
     * @return the Continent of this Territory.
     */
    public Continent continent() {
        return continent;
    
    }
    
    /**
     * Gets a number that can be used as an ID.
     * @return The index of this Territory in the stateManager's Map.
     */
    public int id() {
        return map().territories.indexOf(this);
        
    }
    
    /**
     * Sets the bonus points this Territory gives.
     * @param newBonus The new bonus points.
     */
    public void setBonus(int newBonus) {
        newBonus = Math.max(0, newBonus);
        if(bonus != newBonus){
            bonus = newBonus;
            bonusListener.act();
            
        }
        
    
    }
    
    /**
     * Gets the bonus points this Territory gives.
     * @return The bonus of this Territory.
     */
    public int bonus() {
        return bonus;
    
    }
    
    /**
     * Gets the armies on this Territory.
     * @return the armies on this Territory.
     */
    public int armies() {
        return armies;
    
    }
    
    /**
     * Adds/Subtract armies to the current armies number. Final number of armies
     * can be negative !
     * @param howMany The number of armies you want to add or subtract.
     */
    public void addArmies(int howMany) {
        if(howMany != 0){
            armies += howMany;
            armiesListener.act();
        
        }
    
    }
    
    /**
     * Sets the number of armies.
     * @param newArmies The new number of armies.
     */
    public void setArmies(int newArmies) {
        if(armies != newArmies) {
            armies = newArmies;
            armiesListener.act();
            
        }
        
    }
    
    /**
     * Gets the Player that owns this Territory.
     * @return The owner of this Territory.
     */
    public Player owner() {
        return owner;
    
    }
    
    /**
     * Sets the Player that owns this Territory.
     * @param newOwner the new Owner of this Territory.
     */
    public void setOwner(Player newOwner) {
        if(owner != newOwner) {
            owner = newOwner;
            if(newOwner == null) {
                setArmies(1);
            }
            ownerListener.act();
        }
    
    }
    
    /**
     * Gets the neighbouring Territories.
     * @return The neighbouring according to the StateManagers Map.
     */
    public HashSet<Territory> neighbours() {
        HashSet<BlankHex> neighbourHexes = new HashSet<>();
        for(BlankHex bh : blankHexSet) {
            neighbourHexes.addAll(bh.neighbours());
        }
        neighbourHexes.removeAll(blankHexSet);
        return BlankHex.territoriesOverHex(neighbourHexes);
    
    }
    
    /**
     *  Adds a Links to the links set.
     * @param newLink The link that will be added.
     */
    public void addLink(Links newLink) {
        if(!links.contains(newLink)) {
            links.add(newLink);
            linksListener.act();
        }
    
    }
    
    /**
     *  Removes a Links from the links set
     * @param linkToRemove The Links that will be removed
     */
    public void removeLink(Links linkToRemove) {
        if(links.remove(linkToRemove)) linksListener.act();
    
    }
    
    /**
     * Gets the links of this Territory
     * @return The links of this Territory
     */
    public Set<Links> links() {
        return new HashSet(links);
    
    }
    
    /**
     * Contains the constants to indicate a Territory's Selection status.
     */
    public enum SelectionStatus{
        OPAQUE,
        TRANSPARENT,
        SELECTED;
    
    }
    
    /**
     * Gets this Territory's Selection status
     * @return The Selection status of this Territory 
     */
    public SelectionStatus selectionStatus() {
        return status;
        
    }
    
    @Override
    public void makeOpaque() {
        if(status != SelectionStatus.OPAQUE)
        {
            status = SelectionStatus.OPAQUE;
            statusListener.act();
            
        }
        
    }

    @Override
    public void makeTransparent() {
        if(status != SelectionStatus.TRANSPARENT)
        {
            status = SelectionStatus.TRANSPARENT;
            statusListener.act();
            
        }
    }

    @Override
    public void makeSelected() {
        if(status != SelectionStatus.SELECTED)
        {
            status = SelectionStatus.SELECTED;
            statusListener.act();
            
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////Listeners///////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    
    /**
     * Listens to changes to the continent of this Territory.
     */
    public final Listener continentListener = new Listener();
    
    /**
     * Listens to changes to the bonus points of this Territory.
     */
    public final Listener bonusListener = new Listener();
    
    /**
     * Listens to changes to the armies on this Territory.
     */
    public final Listener armiesListener = new Listener();
    
    /**
     * Listens to changes to the Player owning this Territory.
     */
    public final Listener ownerListener = new Listener();
    
    /**
     * Listens to changes to the Selection status of this Territory.
     */
    public final Listener statusListener = new Listener();
    
    /**
     *  Listens to changes to the links Set of this Territory.
     */
    public final Listener linksListener = new Listener();
            
    /**
     * This class makes Listeners for this Territory
     */
    public class Listener{
        
        private Set<Action> actionSet;
        
        Listener() {
            actionSet = new HashSet<>();
        
        }
        
        /**
         * Adds an Action that will be executed when the property that the
         * listener is listening to changes. The same Action Object can only be
         * added once.
         * @param action The Action to be added.
         */
        public void add(Action action) {
            if(action != null) actionSet.add(action);
            
        }
        
        /**
         * Removes an Action. It will no longer be executed when the property 
         * that the listener is listening to changes.
         * @param action The action to remove.
         */
        public void remove(Action action) {
            actionSet.remove(action);
        
        }
        
        //method this Territory needs to call when the property changes
        private void act() {
            actionSet.forEach(Action::act);
            
        }
            
    
    }

}
