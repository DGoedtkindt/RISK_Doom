package base;

import java.util.ArrayList;
import mainObjects.Continent;
import mainObjects.Links;
import mainObjects.Territory;

/**
 * This Structured variable type stores the main objects of a map.
 */
public class Map {
    public ArrayList<Territory> territories = new ArrayList<>();
    public ArrayList<Continent> continents = new ArrayList<>();
    public ArrayList<Links> links = new ArrayList<>();
    
    
}
