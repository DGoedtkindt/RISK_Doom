package mainObjects;

import base.GColor;
import base.MyWorld;
import java.util.ArrayList;

public class Player {
    
    public static final String ZOMBIE_NAME = "ZOMBIE";
    
    private final String name;
    private final GColor color;
    private int armiesInHand = 0;
    
    private Combo combos = new Combo();
    
    public Player(String playerName, GColor c){
        name = playerName;
        color = c;
        
    }
    
    public void startTurn(){
        getArmies();
        //UPDATE THE SCREEN --> COMBOS
    }
    
    private void getArmies(){
        
        int n = 0;
        
        for(Territory t : MyWorld.theWorld.stateManager.game().map.territories){
            
            if(t.owner() == this){
                n++;
            }
            
        }
        
        armiesInHand += Math.floor(n / 3);
        
    }
    
    public ArrayList<Territory> territories() {
        throw new UnsupportedOperationException("Getting territories from player is not supported yet");
    
    }
    
    public GColor color(){
        return color;
    }
    
    public String name(){
        return name;
    }
    
    public static Player createZombie(){
        return new Player(ZOMBIE_NAME, new GColor(0, 0, 0));
    }

    public int points() {
        return 0;
    }

}
