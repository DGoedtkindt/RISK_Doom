import greenfoot.Greenfoot;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JOptionPane;
import java.awt.Color;

public class OKButton extends Button
{
    
    public void clicked(int mode){
        
        switch(mode){
            
            default : break;
                        
            case Mode.SELECT_HEX : createTerritoryFromSelection();
                                   break;
                        
            case Mode.SELECT_TERRITORY : createContinentFromSelection();
                                         break;
                                         
            case Mode.DELETE_TERRITORY : deleteTerritorySelection();
                                         break;
            
            case Mode.SET_LINKS : createLinksFromSelection();
                                  break;
                                 
            case Mode.CHOOSE_CAPITAL_TERRITORY : changeCapitalFromSelection();
                                                 break;
                                                 
            case Mode.EDIT_CONTINENT_COLOR : changeColorFromSelection();
                                             break;
                                             
            case Mode.EDIT_CONTINENT_BONUS : changeBonusFromSelection();
                                             break;
                                             
            case Mode.DELETE_CONTINENT : deleteContinentSelection();
                                         break;
        }
        
        ((MyWorld)getWorld()).escape();
        
    }
    
    public void act() 
    {
        
    }    
    
    private void createContinentFromSelection(){
        
        ArrayList<Territory> selectedTerritories = null;
        try{
            
            selectedTerritories = Selector.getSelectedTerritories();
            
            for(Territory t : selectedTerritories){
                
                if(t.getContinent() != null){
                    
                    throw new Exception("A selected territory already has a continent");
                    
                }
                
            }
            
            Continent createdContinent = new Continent(selectedTerritories);
            
        }catch(Exception e){
            
           System.out.println(e.getMessage());
           MyWorld.theWorld.escape();
           
           }
        
    }
    
    private void createTerritoryFromSelection(){
        
        ArrayList<SingleHex> selectedHexes = null;
        try{
            
            selectedHexes = Selector.getSelectedHexes();
            HashSet<Coordinates> selectedCoordinates = new HashSet();
       
            for(SingleHex hex : selectedHexes){
               
                selectedCoordinates.add(hex.getCoord());
               
             }
                  
            Territory createdTerritory = new Territory(selectedCoordinates);
                
        }catch(Exception e){
        
            System.out.println(e.getMessage());
            MyWorld.theWorld.escape();
           
         }
        
    }
    
    private void deleteTerritorySelection(){
        
        ArrayList<Territory> territoriesToDelete = null;
        try{
             
            territoriesToDelete = Selector.getSelectedTerritories();
            for(Territory toDelete : territoriesToDelete){
                 
                toDelete.destroy();
                 
            }
            
           }catch(Exception e){
            
           System.out.println(e.getMessage());
           MyWorld.theWorld.escape();
           
           }
        
    }
    
    private void createLinksFromSelection(){
        
        Territory[] territoriesToLink = null;
        try{
              
            territoriesToLink = Selector.getSelectedTerritoryPair();
              
            territoriesToLink[0].setNewLink(territoriesToLink[1]);
            territoriesToLink[1].setNewLink(territoriesToLink[0]);
            
          }catch(Exception e){
            
            System.out.println(e.getMessage());
            MyWorld.theWorld.escape();
              
          }
            
    }
    
    private void changeCapitalFromSelection(){
        
        Territory capitalTerritory = null;
        try{
             
            capitalTerritory = Selector.getSelectedTerritory();
             
            int capitalBonus = Integer.parseInt(JOptionPane.showInputDialog("Entrez le nouveau bonus de capitale"));
         
            capitalTerritory.getContinent().setCapital(capitalBonus, capitalTerritory);
            
           }catch(Exception e){
            
            System.out.println(e.getMessage());
            MyWorld.theWorld.escape();
             
           }
            
    }
    
    private void changeColorFromSelection(){
        
        Territory territoryForContinentColor = null;
        try{
             
            territoryForContinentColor = Selector.getSelectedTerritory();
             
            int rColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de rouge (int)"));
            int gColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de vert (int)"));
            int bColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de bleu (int)"));
         
            Color changedColor = new Color(rColor, gColor, bColor);
         
            territoryForContinentColor.getContinent().editColor(changedColor);
            
           }catch(Exception e){
            
            System.out.println(e.getMessage());
            MyWorld.theWorld.escape();
             
           }
              
    }
    
    private void changeBonusFromSelection(){
        
        Territory territoryForContinentBonus = null;
        try{
             
            territoryForContinentBonus = Selector.getSelectedTerritory();
             
            int newContinentBonus = Integer.parseInt(JOptionPane.showInputDialog("Entrez le nouveau bonus de continent"));
         
            territoryForContinentBonus.getContinent().editBonus(newContinentBonus);
            
           }catch(Exception e){
             
            System.out.println(e.getMessage());
            MyWorld.theWorld.escape();
             
           }
                                             
    }
    
    private void deleteContinentSelection(){
        
        
        try{
             
            Continent continentToDelete = Selector.getSelectedContinent();
            continentToDelete.destroy();
            
            
            
           }catch(Exception e){
             
            System.out.println(e.getMessage());
            MyWorld.theWorld.escape();
             
           }
             
    }
    
}
