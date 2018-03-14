package base;

import java.util.ArrayList;
import mainObjects.Continent;
import mainObjects.Links;
import mainObjects.Territory;

/**
 * This Structured variable type stores the main Objects of a Map.
 */
public class Map {
    public String name;
    public ArrayList<Territory> territories = new ArrayList<>();
    public ArrayList<Continent> continents = new ArrayList<>();
    public ArrayList<Links> links = new ArrayList<>();
    
}
