package game;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import territory.Territory;

/**
 * The Class of the Objects that contain stats about the Turns.
 * 
 */
public class TurnStat {
    public int turnNumber;
    public Map<Player, Integer> numberOfArmies  = new HashMap<>();
    public Map<Player, Integer> numberOfTerritories = new HashMap<>();
    public Map<Player, Integer> numberOfArmiesPerTurn = new HashMap<>();
    public Map<Player, Integer> numberOfPoints = new HashMap<>();
    public Map<Player, Integer> numberOfContinents = new HashMap<>();
    
    /**
     * Creates a TurnStat Object.
     */
    public TurnStat() {}
    
    /**
     * Creates a TurnStat Object.
     * @param players The currently playing Players.
     * @param turnNumber The number of the current Turn.
     */
    public TurnStat(Collection<Player> players, int turnNumber) { 
        this.turnNumber = turnNumber;
        for(Player p : players) {
            Collection<Territory> terrs = p.territories();
            numberOfTerritories.put(p, terrs.size());
            
            int armies = 0;
            for(Territory t : terrs) {
                armies += t.armies();
                
            }
            numberOfArmies.put(p, armies);
            numberOfArmiesPerTurn.put(p, p.armyGainPerTurn());
        
        }
    
    }

}
