package mainObjects;

import appearance.ComboDisplayer;
import base.GColor;
import base.MyWorld;
import java.util.ArrayList;
import java.util.HashSet;

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
    
    private void getArmies(){
        
        int terrNumber = 0;
        int terrBonus = 0;
        
        for(Territory t : territories()){
            
            if(t.owner() == this){
                terrNumber++;
                terrBonus += t.bonus();
            }
            
        }
        
        int continentBonus = 0;
        
        for(Continent c : MyWorld.theWorld.stateManager.map().continents){
            
            int ownedTerrsInContinent = 0;
            
            for(Territory terrInContinent : c.containedTerritories()){
                
                if(terrInContinent.owner() == this){
                    
                    ownedTerrsInContinent ++;
                    
                }
                
            }
            
            if(ownedTerrsInContinent == c.containedTerritories().size()){
                continentBonus += c.bonus();
            }
            
        }
        
        armiesInHand += Math.floor(terrNumber / 3) + terrBonus + continentBonus;
        
    }
    
    public void gainComboPiece(){
        combos.addRandomCombo();
        ComboDisplayer.updateDisplay(this);
    }
    
    public ArrayList<Territory> territories() {
        ArrayList<Territory> ownedterritories = new ArrayList<Territory>();
        
        for(Territory t : MyWorld.theWorld.stateManager.game().map.territories){
            
            if(t.owner() == this){
                ownedterritories.add(t);
            }
            
        }
        
        return ownedterritories;
        
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
