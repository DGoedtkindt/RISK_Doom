import java.awt.Color;
import java.util.*;
import javax.swing.JOptionPane;

public class Continent implements Selectable
{
    
    private Color continentColor;
    private ArrayList<Territory> territoriesContained = new ArrayList<>();
    private int bonus;
    static private HashSet<Continent> continentList = new HashSet<Continent>();
    
    public Continent(ArrayList<Territory> territories) throws Exception{
        editColor();
        editBonus();
        
        continentList.add(this);
        Selector.selectableSet.add(this);
        
        territoriesContained.addAll(0,territories);
        
        for(Territory t : territoriesContained){
             
                t.setContinent(this);
             
            }
        
    }
    
    public void editColor() throws Exception {
            int rColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de rouge (int)"));
            int gColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de vert (int)"));
            int bColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de bleu (int)"));
            continentColor = new Color(rColor,gColor,bColor);
            for(Territory t : territoriesContained){
                t.setContinent(this);

            }
    }
    
    public Color color(){
        return continentColor;
        
    }
    
    public ArrayList<Territory> ContainedTerritories(){
        return territoriesContained;
        
    }
    
    public void removeTerritory(Territory territoryToRemove){
        
        territoriesContained.remove(territoryToRemove);
        
    }
    
    public void destroy() {
        continentList.remove(this);
        
        for(Territory terr : territoriesContained){
         
            terr.setContinent(null);
         
           }
        
        Selector.selectableSet.remove(this);
               
    }
    
    public void editBonus() throws Exception {
            int newBonus = Integer.parseInt(JOptionPane.showInputDialog("Entrez le nouveau bonus pour le continent"));
            bonus = newBonus;
    }
    
    public int bonus() {
        return bonus;
    
    }
    
    //////////////////////////////////////////////////
    
    public static ArrayList<Continent> continentList(){
        ArrayList<Continent> continents = new ArrayList<>();
        
        continents.addAll(0, continentList);
        
        return continents;
        
    }
    
    //Selectable methods/////////////////////////////////
    
    public void makeGreen() {
        for(Territory terr : territoriesContained) {
        
            terr.makeGreen();
        
        }
    }
    
    public void makeTransparent() {
        for(Territory terr : territoriesContained) {
        
            terr.makeTransparent();
        
        }
    }
    
    public void makeOpaque() {
        for(Territory terr : territoriesContained) {
        
            terr.makeOpaque();
        
        }
    }
            
}
