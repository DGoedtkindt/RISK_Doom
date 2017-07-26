import java.awt.Color;
import java.awt.geom.Area;
import java.util.HashSet;
import javax.swing.JOptionPane;

public class Continent implements Maskable
{
    
    Color continentColor;
    HashSet<Territory> territoriesContained = new HashSet<Territory>();
    int continentBonus;
    
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
    
    
    public HashSet<Territory> getContainedTerritories(){
        
        return territoriesContained;
        
    }
    
    public void removeTerritory(Territory territoryToRemove){
        
        territoriesContained.remove(territoryToRemove);
        
    }
    
    public Area getAreaShape()
    {
        return null;
    }

}
