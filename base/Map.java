package base;

import java.util.ArrayList;
import java.util.List;
import mainObjects.Continent;
import mainObjects.Links;
import mainObjects.Territory;

/**
 * This Structured variable type stores the main objects of a map.
 */
public class Map {
    public String name;
    public List<Territory> territories = new ArrayList<>();
    public List<Continent> continents = new ArrayList<>();
    public List<Links> links = new ArrayList<>();
    
    
}
