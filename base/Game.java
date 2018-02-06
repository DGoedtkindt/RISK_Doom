package base;

import game.Difficulty;
import game.Turn;
import greenfoot.Greenfoot;
import java.util.ArrayList;
import mainObjects.Player;
import mainObjects.Territory;

public class Game {
    
    private static final int STARTING_TERRITORIES = 1;
    
    /**
     * in case we need this
     */
    public enum State {
        HELLO("HELLO THERE!");
        
        State(String description) {}
        
    }
    public State gameState;
    public ArrayList<Player> players = new ArrayList<>();
    public Map map;
    public Difficulty difficulty;
    
    public void start(){
        
        MyWorld.theWorld.load(new game.Manager(this));
        giveTerritoriesRandomly();
        Turn.start();
        
    }
    
    public void end(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private void giveTerritoriesRandomly(){
        
        for(Player p : players){
            
            int terrNumber = 0;
            Territory terrToAttribute;
            
            while(terrNumber < STARTING_TERRITORIES){
                
                terrToAttribute = map.territories.get(Greenfoot.getRandomNumber(map.territories.size()));
                
                if(terrToAttribute.owner() == null){
                    terrToAttribute.setOwner(p);
                    terrNumber++;
                }
                
            }
            
        }
        
    }
    
}
