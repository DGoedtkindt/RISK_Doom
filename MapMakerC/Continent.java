import java.awt.Color;
import java.awt.geom.Area;
import java.util.*;
import javax.swing.JOptionPane;

public class Continent implements Selectable
{
    
    private Color continentColor;
    private ArrayList<Territory> territoriesContained = new ArrayList<Territory>();
    private int continentBonus;
    
    public Continent(){
        
        int rColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de rouge (int)"));
        int gColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de vert (int)"));
        int bColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de bleu (int)"));
        
        addToSelectableList();
        
        editColor(new Color(rColor, gColor, bColor));
        
    }
    
    public void editColor(Color newColor){
        
        continentColor = newColor;
        
        for(Territory t : territoriesContained){
            
            t.setContinent(this);
            
        }
        
    }
    
    public Color getContinentColor(){
        
        return continentColor;
        
    }
    
    
    public void editBonus(int newBonus){
        
        continentBonus = newBonus;
        
    }
    
    public void setCapital(int capitalBonus, Territory capital){
        
        for(Territory t : territoriesContained){
            
            t.setBonusPoints(0);
            
        }
        
        capital.setBonusPoints(capitalBonus);
        
    }
    
    public ArrayList<Territory> getContainedTerritories(){
        
        return territoriesContained;
        
    }
    
    public void setContainedTerritories(ArrayList<Territory> territories){
        
        territoriesContained = territories;
        
    }
    
    public void removeTerritory(Territory territoryToRemove){
        
        territoriesContained.remove(territoryToRemove);
        
    }
    
    
    //////////////////////////////////////////////
    
    public void addToSelectableList() 
    {
        Selector.selectableList.add(this);
    }
    
    public void setOpaque()
    {
        for(Territory terr : territoriesContained){
        
            terr.setOpaque();
        
        }
    }
    
    public void setTransparent()
    {
        for(Territory terr : territoriesContained){
        
            terr.setTransparent();
        
        }
    }
    
    public void setSelected()
    {
    
    }
}
