package base;

import java.util.HashSet;
import territory.Continent;
import territory.Links;
import territory.Territory;

/**
 * This Structured variable type stores the main Objects of a Map.
 */
public class Map {
    public String name;
    public final ListenerList<Territory> territories = new ListenerList<>();
    public final ListenerList<Continent> continents = new ListenerList<>();
    public HashSet<Links> links() {
        HashSet<Links> links = new HashSet<>();
        for(Territory terr : territories) {
            links.addAll(terr.links());
        
        }
        return links;
    
    }
    
}
