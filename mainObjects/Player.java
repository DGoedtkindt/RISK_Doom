package mainObjects;

import appearance.ComboDisplayer;
import base.GColor;
import base.MyWorld;
import java.util.ArrayList;

public class Player {
    
    public static final String NEW_PLAYER_NAME = "A New Player";
    
    private final String name;
    private final GColor color;
    public int armiesInHand = 1;
    private int points = 0;
    
    private Combo combos = new Combo();
    
    public Player(String playerName, GColor c){
        name = playerName;
        color = c;
        
    }
    
    public void startTurn(){
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
    
    public boolean hasLostQ() {
        System.out.println("Player.hasLostQ is not supported yet");
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
    
}
