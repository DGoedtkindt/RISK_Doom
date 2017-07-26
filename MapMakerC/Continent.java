import java.awt.Color;
import java.awt.geom.Area;
import java.util.HashSet;

public class Continent implements Maskable
{
    
    Color continentColor;
    HashSet<Territory> territoriesContained = new HashSet<Territory>();
    int continentBonus;
    
    public void editColor(Color newColor){
        
        continentColor = newColor;
        
    }
    
    public Color getContinentColor(){
        
        return continentColor;
        
    }
    
    
    public void editBonus(int newBonus){
        
        continentBonus = newBonus;
        
    }
    
    
    public HashSet<Territory> getContainedTerritories(){
        
        return territoriesContained;
        
    }
    
    
    
    public Area getAreaShape()
    {
        return null;
    }

}
