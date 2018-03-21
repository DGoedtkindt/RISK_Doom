package game;

import base.GColor;
import base.MyWorld;
import java.util.ArrayList;
import java.util.List;
import mainObjects.Continent;
import mainObjects.Territory;

/**
 * The Class that represents a Player.
 * 
 */
public class Player {
    
    public static final String NEW_PLAYER_NAME = "A New Player";
    public static final int MAX_POINTS = 7;
    
    private final String name;
    private final GColor color;
    private int armiesInHand = 0;
    public int points = 0;
    public boolean fortressProtection = false;
    public int battlecryBonus = 0;
    public Territory capital;
    public boolean conqueredThisTurn = false;
    
    private final Combo COMBOS = new Combo();
    
    /**
     * Creates a Player.
     * @param playerName The Player's name.
     * @param c The Player's GColor.
     */
    public Player(String playerName, GColor c){
        name = playerName;
        color = c;
        
    }
    
    /**
     * Starts the Player's Turn.
     */
    public void startTurn(){
        fortressProtection = false;
        battlecryBonus = 0;
        updateCapital();
        getArmies();
    }
    
    /**
     * Counts the number of armies the Player gains at the start of his Turn.
     * @return The number of armies he gained.
     */
    public int armyGainPerTurn() {
        int terrNumber = 0;
        int continentBonus = 0;
        
        for(Territory t : territories()){
            
            if(t.owner() == this){
                terrNumber++;
            }
            
        }
        
        for(Continent c : continents()) {
            continentBonus += c.bonus();
        
        }
        
        int fromTerritories = (int)Math.floor(terrNumber / 3);
        int total = fromTerritories + capital.bonus() + continentBonus;
         
        return total;
    }
    
    /**
     * @return The continents from theWorld.stateManager.map().continents that
     * This Player controls.
     */
    public List<Continent> continents(){
        List<Continent> continentList = new ArrayList<>();
        
        for(Continent c : MyWorld.theWorld.stateManager.map().continents){
            List<Territory> uncontroledTerrInCont;
            uncontroledTerrInCont = c.containedTerritories();
            uncontroledTerrInCont.removeAll(territories());
            if(uncontroledTerrInCont.isEmpty()) {
                continentList.add(c);
                
            }
            
        }
        return continentList;
    }
    
    /**
     * @return a List of all the Territories in the loaded Map's territories list
     * that are owned by this Player.
     */
    public List<Territory> territories() {
        ArrayList<Territory> ownedterritories = new ArrayList<>();
        
        for(Territory t : MyWorld.theWorld.stateManager.game().map.territories){
            
            if(t.owner() == this){
                ownedterritories.add(t);
            }
            
        }
        
        return ownedterritories;
        
    }
    
    /**
     * Gets new armies.
     */
    private void getArmies() {
        addArmiesToHand(armyGainPerTurn());
    
    }
    
    /**
     * Gains a random combo piece.
     */
    public void gainComboPiece(){
        COMBOS.addRandomCombo();
    }
    
    /**
     * Checks if the Player has lost.
     * @return A boolean representation of the loss of this Player.
     */
    public boolean hasLost() {
        return territories().isEmpty();
        
    }
    
    /**
     * Checks if the Player has won.
     * @return A boolean representation of the victory of this Player.
     */
    public boolean hasWon(){
        return points >= MAX_POINTS;
    }
    
    /**
     * Gets the GColor of this Player.
     * @return The Color of this Player.
     */
    public GColor color(){
        return color;
    }
    
    /**
     * Gets the name of this Player.
     * @return The name of this Player.
     */
    public String name(){
        return name;
    }
    
    /**
     * Gets the Combo object of this Player.
     * @return The Combo object of this Player.
     */
    public Combo combos(){
        return COMBOS;
    }
    
    /**
     * Gets the armies this Player has in his hand..
     * @return The armies in the hand of this Player.
     */
    public int armiesInHand(){
        return armiesInHand;
    }
    
    /**
     * Sets the number of armies a Player has in his hand.
     * @param howMany The number of armies.
     */
    public void setArmiesInHand(int howMany) {
        armiesInHand = howMany;
        ArmiesInHandDisplayer.update();
    }
    
    /**
     * Adds armies to the hand of this Player.
     * @param howMany The number of armies.
     */
    public void addArmiesToHand(int howMany) {
        armiesInHand+= howMany;
        ArmiesInHandDisplayer.update();
    
    }
    
    /**
     * Updates the capital of this Player.
     */
    public void updateCapital(){
        
        Territory formerCapital = capital;
        
        Territory newCapital = territories().get(0);
        
        for(Territory t : territories()){
            if(t.bonus() > newCapital.bonus()){
                newCapital = t;
                
            }

        }
        
        capital = newCapital;
        capital.drawTerritory();
        
        if(formerCapital != null){
            formerCapital.drawTerritory();
            
        }
        
    }
    
}
