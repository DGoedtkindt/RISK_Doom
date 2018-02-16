package mainObjects;

import base.GColor;
import base.MyWorld;
import java.util.ArrayList;

public class Player {
    
    public static final String NEW_PLAYER_NAME = "A New Player";
    
    private final String name;
    private final GColor color;
    private int armiesInHand = 0;
    private int points = 0;
    
    private Combo combos = new Combo();
    
    public Player(String playerName, GColor c){
        name = playerName;
        color = c;
        
    }
    
    public void startTurn(){
        getArmies();
        //UPDATE THE SCREEN --> COMBOS
    }
    
    public int armyGainPerTurn() {
        int n = 0;
        
        for(Territory t : MyWorld.theWorld.stateManager.game().map.territories){
            
            if(t.owner() == this){
                n++;
            }
            
        }
        
        int fromTerritories = (int)Math.floor(n / 3);
        int total = fromTerritories; //+other things
        
        return total;
    }
    
    private void getArmies(){
        armiesInHand += armyGainPerTurn();
        
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

}
