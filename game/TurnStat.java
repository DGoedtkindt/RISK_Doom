package game;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import mainObjects.Player;
import mainObjects.Territory;


public class TurnStat {
    public int turnNumber;
    public Map<Player, Integer> numberOfArmies  = new HashMap<>();
    public Map<Player, Integer> numberOfTerritories = new HashMap<>();
    public Map<Player, Integer> numberOfArmiesPerTurn = new HashMap<>();
    public Map<Player, Integer> numberOfPoints = new HashMap<>();
    public Map<Player, Integer> numberOfContinents = new HashMap<>();
    
    public TurnStat() {}
    
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
