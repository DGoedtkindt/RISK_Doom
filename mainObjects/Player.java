package mainObjects;

import appearance.ComboDisplayer;
import base.GColor;
import base.MyWorld;
import java.util.ArrayList;
import java.util.List;

public class Player {
    
    public static final String NEW_PLAYER_NAME = "A New Player";
    
    private final String name;
    private final GColor color;
    public int armiesInHand = 0;
    private int points = 0;
    
    private Combo combos = new Combo();
    
    public Player(String playerName, GColor c){
        name = playerName;
        color = c;
        
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
    
    public void getArmies(){
        armiesInHand += armyGainPerTurn();

    }
    
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
    
    public boolean hasLostQ() {
        System.out.println("Player.hasLostQ() is not supported yet");
        return false;
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
  
    public void gainComboPiece(){
          combos.addRandomCombo();
          ComboDisplayer.updateDisplay(this);
    }
    
}
