import java.awt.Color;
import java.awt.geom.Area;
import java.util.*;
import javax.swing.JOptionPane;

public class Continent implements Maskable
{
    
    private Color continentColor;
    private HashSet<Territory> territoriesContainedSet = new HashSet<Territory>();
    private int continentBonus;
    
    public Continent(){
        
        int rColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de rouge (int)"));
        int gColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de vert (int)"));
        int bColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de bleu (int)"));
        
    }
    
    public void editColor(Color newColor){
        
        continentColor = newColor;
        
    }
    
    public Color getContinentColor(){
        
        return continentColor;
        
    }
    
    
    public void editBonus(int newBonus){
        
        continentBonus = newBonus;
        
    }
    
    
    public ArrayList<Territory> getContainedTerritories(){
        
        ArrayList<Territory> ContainedTerritoryList = new ArrayList<Territory>();
        for(Territory terr : territoriesContainedSet){
        
            ContainedTerritoryList.add(terr);
            
        }
        
        return ContainedTerritoryList;
        
    }
    
    public void removeTerritory(Territory territoryToRemove){
        
        territoriesContainedSet.remove(territoryToRemove);
        
    }
    
    public Area getAreaShape()
    {
        return null;
    }

}
