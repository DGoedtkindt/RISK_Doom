import java.awt.Color;
import java.util.*;
import javax.swing.JOptionPane;

public class Continent
{
    
    private Color continentColor;
    private ArrayList<Territory> territoriesContained = new ArrayList<>();
    private int continentBonus;
    static private HashSet<Continent> continentList = new HashSet<Continent>();
    
    public Continent(ArrayList<Territory> territories){
        
        int rColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de rouge (int)"));
        int gColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de vert (int)"));
        int bColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de bleu (int)"));
        int continentPoints = Integer.parseInt(JOptionPane.showInputDialog("Entrez le bonus de continent"));
        
        editColor(new Color(rColor, gColor, bColor));
        editBonus(continentPoints);
        
        addToContinentList();
        
        territoriesContained.addAll(0,territories);
        
        for(Territory t : territoriesContained){
             
                t.setContinent(this);
             
            }
        
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
    
    public int getContinentBonus(){
        
        return continentBonus;
        
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
    
    public void removeTerritory(Territory territoryToRemove){
        
        territoriesContained.remove(territoryToRemove);
        
    }
    
    public void destroy()
    {
        removeFromContinentList();

        for(Territory terr : territoriesContained){
         
            terr.setContinent(null);
         
           }
               
    }
    
    //////////////////////////////////////////////////
    
    private void addToContinentList(){
        
        continentList.add(this);
        
    }
    
    private void removeFromContinentList(){
        
        continentList.remove(this);
        
    }
    
    public static ArrayList<Continent> getContinentList(){
        
        ArrayList<Continent> continents = new ArrayList<Continent>();
        
        continents.addAll(0, continentList);
        
        return continents;
        
    }
    
    ///////////////////////////////////////////////
    
    public void makeGreen()
    {
        for(Territory terr : territoriesContained) {
        
            terr.makeGreen();
        
        }
    }
    
}
