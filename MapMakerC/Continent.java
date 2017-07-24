import java.awt.Color;

public class Continent  
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
    
    public Territory[] getContainedTerritories(){
        
        return territoriesContained;
        
    }
    
    
}
