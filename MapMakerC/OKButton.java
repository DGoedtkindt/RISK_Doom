import greenfoot.*;  
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import javax.swing.JOptionPane;
import java.awt.Color;

public class OKButton extends Button
{
    
    public void clicked(int mode){
        
        switch(mode){
            
            default : break;
                        
            case Mode.SELECT_HEX : ArrayList<SingleHex> selectedHexes = null;
                                   try{selectedHexes = Selector.getSelectedHexes();}catch(Exception e){}
                                   HashSet<Coordinates> selectedCoordinates = new HashSet();
                                   
                                   for(SingleHex hex : selectedHexes){
                                       
                                       selectedCoordinates.add(hex.getCoord());
                                       
                                    }
                                   
                                   try{
                                       
                                       Territory createdTerritory = new Territory(selectedCoordinates);
                                       
                                    }catch(Exception e){
                                       
                                       System.out.println("Erreur de création de territoire " + e.getMessage());
                                       
                                    }
                                   
                                   break;
                        
            case Mode.SELECT_TERRITORY : ArrayList<Territory> selectedTerritories = null;
                                         try{selectedTerritories = Selector.getSelectedTerritories();}catch(Exception e){}
                                         
                                         Continent createdContinent = new Continent();
                                         
                                         MyWorld.theWorld.addContinentToList(createdContinent);
                                         
                                         createdContinent.setContainedTerritories(selectedTerritories);
                                         
                                         for(Territory t : selectedTerritories){
                                             
                                             t.setContinent(createdContinent);
                                             
                                            }
                                         
                                         break;
                                         
            case Mode.DELETE_TERRITORY : ArrayList<Territory> territoriesToDelete = null;
                                         try{territoriesToDelete = Selector.getSelectedTerritories();}catch(Exception e){}
                                         
                                         for(Territory toDelete : territoriesToDelete){
                                             
                                             toDelete.destroy();
                                             
                                          }
                                         
                                         break;
            
            case Mode.SET_LINKS : Territory[] territoriesToLink = null;
                                  try{territoriesToLink = Selector.getSelectedTerritoryPair();}catch(Exception e){}
            
                                  territoriesToLink[0].setNewLink(territoriesToLink[1]);
                                  territoriesToLink[1].setNewLink(territoriesToLink[0]);
                                  
                                  break;
                                 
            case Mode.CHOOSE_CAPITAL_TERRITORY : Territory capitalTerritory = null;
                                                 try{capitalTerritory = Selector.getSelectedTerritory();}catch(Exception e){}
            
                                                 int capitalBonus = Integer.parseInt(JOptionPane.showInputDialog("Entrez le nouveau bonus de capitale"));
                                                 
                                                 capitalTerritory.getContinent().setCapital(capitalBonus, capitalTerritory);
                                                 
                                                 break;
                                                 
            case Mode.EDIT_CONTINENT_COLOR : Territory territoryForContinentColor = null;
                                             try{territoryForContinentColor = Selector.getSelectedTerritory();}catch(Exception e){}
                                             
                                             int rColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de rouge (int)"));
                                             int gColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de vert (int)"));
                                             int bColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de bleu (int)"));
                                             
                                             Color changedColor = new Color(rColor, gColor, bColor);
                                             
                                             territoryForContinentColor.getContinent().editColor(changedColor);
                                             
                                             break;
                                             
            case Mode.EDIT_CONTINENT_BONUS : Territory territoryForContinentBonus = null;
                                             try{territoryForContinentBonus = Selector.getSelectedTerritory();}catch(Exception e){}
                                             
                                             int newContinentBonus = Integer.parseInt(JOptionPane.showInputDialog("Entrez le nouveau bonus de continent"));
                                             
                                             territoryForContinentBonus.getContinent().editBonus(newContinentBonus);
                                             
                                             break;
                                             
            case Mode.DELETE_CONTINENT : Territory territoryForContinentDelete = null;
                                         try{territoryForContinentDelete = Selector.getSelectedTerritory();}catch(Exception e){}
                                         
                                         Continent continentToDelete = territoryForContinentDelete.getContinent();
                                         
                                         for(Territory toDelete : continentToDelete.getContainedTerritories()){
                                             
                                             toDelete.destroy();
                                             
                                          }
                                         
                                         MyWorld.theWorld.removeContinentFromList(continentToDelete);
                                          
                                         break;
        }
        
        ((MyWorld)getWorld()).escape();
        
    }
    
    
    
    public void act() 
    {
        
    }    
    
    
    
}
