package game;

import base.GColor;
import base.MyWorld;
import java.util.ArrayList;
import java.util.List;
import mainObjects.Continent;
import mainObjects.Territory;

public class Player {
    
    public static final String NEW_PLAYER_NAME = "A New Player";
    
    private final String name;
    private final GColor color;
    public int armiesInHand = 1;
    public int points = 0;
    public boolean fortressProtection = false;
    public int battlecryBonus = 0;
    public Territory capital;
    
    private Combo combos = new Combo();
    
    public Player(String playerName, GColor c){
        name = playerName;
        color = c;
        
    }
    
    public void startTurn(){
        fortressProtection = false;
        battlecryBonus = 0;
        getArmies();
        Combo.display(this);
    }
    
    public int armyGainPerTurn() {
        int terrNumber = 0;
        int terrBonus = 0;
        int continentBonus = 0;
        
        for(Territory t : territories()){
            
            if(t.owner() == this){
                terrNumber++;
                terrBonus += t.bonus();
            }
            
        }
        
        for(Continent c : continents()) {
            continentBonus += c.bonus();
        
        }
        
        int fromTerritories = (int)Math.floor(terrNumber / 3);
        int total = fromTerritories + terrBonus + continentBonus; // possibly +other things
         
        return total;
    }
    
    /**
     * @return the continents from theWorld.stateManager.map().continents that
     * This Player controls
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
    
    void getArmies() {
        armiesInHand += armyGainPerTurn();
    
    }
    
    public void gainComboPiece(){
        combos.addRandomCombo();
        ComboDisplayer.updateDisplay(this);
    }
    
    public boolean hasLost() {
        return territories().isEmpty();
        
    }
    
    public boolean hasWon(){
        return points >= 7;
    }
    
    public GColor color(){
        return color;
    }
    
    public String name(){
        return name;
    }

    public int points() {
        return points;
    }
    
    public int comboPiecesNumber(){
        return combos.comboPiecesNumber();
    }
    
    public Combo combos(){
        return combos;
    }
    
    public void getnewCapital(){
        
        for(Territory t : territories()){
            
            Territory capitalTerritory = null;
            int capitalBonus = 0;
            
            if(t.bonus() >= capitalBonus){
                capitalTerritory = t;
                capitalBonus = capitalTerritory.bonus();
            }
            
        }
        
    }
    
}