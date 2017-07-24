import java.awt.Color;
import java.awt.geom.Area;

public class Continent implements Maskable
{
    
    Color continentColor;
    Territory[] territoriesContained = new Territory[30];
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
    
    public Area getAreaShape()
    {
        return null;
    }
}
