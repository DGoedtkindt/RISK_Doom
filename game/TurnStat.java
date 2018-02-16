package game;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import mainObjects.Player;
import mainObjects.Territory;


public class TurnStat {
    public int turnNumber;
    public Map<Player, Integer> numberOfArmies;
    public Map<Player, Integer> numberOfTerritories;
    public Map<Player, Integer> numberOfArmiesPerTurn;
    
    public TurnStat() {}
    
    public TurnStat(Collection<Player> players, int turnNumber) {
        numberOfArmies = new HashMap<>();
        numberOfTerritories = new HashMap<>();
        numberOfArmiesPerTurn = new HashMap<>();   
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
