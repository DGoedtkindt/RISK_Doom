import greenfoot.*;  
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import javax.swing.JOptionPane;
import java.awt.Color;

public class OKButton extends Button
{
    
    
    public void clicked(int mode){
        
        switch(mode){
            
            default : ((MyWorld)getWorld()).escape();
                      break;
                        
            case Mode.SELECT_HEX : HashSet<SingleHex> selectedHexes = ((MyWorld)getWorld()).singleHexesCurrentlySelected;
            
                                   HashSet<Coordinates> selectedCoordinates = new HashSet();
                                   
                                   for(SingleHex hex : selectedHexes){
                                       
                                       selectedCoordinates.add(hex.getCoord());
                                       
                                    }
                                   
                                   try{
                                       
                                       Territory createdTerritory = new Territory(selectedCoordinates);
                                       
                                    }catch(Exception e){
                                       
                                       System.out.println("Erreur de création de territoire " + e.getMessage());
                                       
                                    }
                                   
                                   ((MyWorld)getWorld()).singleHexesCurrentlySelected.clear();
                                    
                                   ((MyWorld)getWorld()).changeMode(Mode.DEFAULT);
                                   break;
                        
            case Mode.SELECT_TERRITORY : HashSet<Territory> selectedTerritories = ((MyWorld)getWorld()).territoriesCurrentlySelected;
                                         Continent createdContinent = new Continent();
                                         createdContinent.territoriesContained = selectedTerritories;
                                         ((MyWorld)getWorld()).changeMode(Mode.DEFAULT);
                                         
                                         ((MyWorld)getWorld()).territoriesCurrentlySelected.clear();
                                         
                                         break;
                                         
            case Mode.DELETE_TERRITORY : HashSet<Territory> territoriesToDelete = ((MyWorld)getWorld()).territoriesCurrentlySelected;
                                         
                                         for(Territory toDelete : territoriesToDelete){
                                             
                                             toDelete.destroy();
                                             
                                          }
                                         
                                         ((MyWorld)getWorld()).changeMode(Mode.DEFAULT);
                                         
                                         ((MyWorld)getWorld()).territoriesCurrentlySelected.clear();
                                          
                                         break;
            
            case Mode.SET_LINKS : HashSet<Territory> territoriesToLink = ((MyWorld)getWorld()).territoriesCurrentlySelected;
            
                                  if(territoriesToLink.size() > 2){
                                      
                                      System.out.println("Trop de territoires à lier");
                                      break;
                                      
                                   }
                                  
                                  ArrayList territoriesToLinkList = new ArrayList();
                                  
                                  for(Territory t : territoriesToLink){
                                      
                                      territoriesToLinkList.add(t);
                                      
                                    }
                                   
                                  ((Territory)(territoriesToLinkList.get(0))).setNewLink((Territory)(territoriesToLinkList.get(1)));
                                  ((Territory)(territoriesToLinkList.get(1))).setNewLink((Territory)(territoriesToLinkList.get(0)));
                                  
                                  ((MyWorld)getWorld()).changeMode(Mode.DEFAULT);
                                  
                                  ((MyWorld)getWorld()).territoriesCurrentlySelected.clear();
                                     
                                  break;
                                 
            case Mode.CHOOSE_CAPITAL_TERRITORY : HashSet<Territory> capitalTerritory = ((MyWorld)getWorld()).territoriesCurrentlySelected;
            
                                                 if(capitalTerritory.size() > 1){
                                                     
                                                     System.out.println("Trop de nouvelles capitales");
                                                     break;
                                      
                                                  }
                                                 
                                                 int capitalBonus = Integer.parseInt(JOptionPane.showInputDialog("Entrez le nouveau bonus de capitale"));
                                                 
                                                 for(Territory t : capitalTerritory){
                                                     
                                                     t.changeCapital(capitalBonus);
                                                     
                                                    }
                                                 
                                                 ((MyWorld)getWorld()).changeMode(Mode.DEFAULT);
                                                 
                                                 ((MyWorld)getWorld()).territoriesCurrentlySelected.clear();
                                            
                                                 break;
                                                 
            case Mode.EDIT_CONTINENT_COLOR : HashSet<Territory> territoryForContinentColor = ((MyWorld)getWorld()).territoriesCurrentlySelected;
                                             
                                             if(territoryForContinentColor.size() > 1){
                                                     
                                                     System.out.println("Trop de continents sélectionnés");
                                                     break;
                                      
                                              }
                                             
                                             int rColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de rouge (int)"));
                                             int gColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de vert (int)"));
                                             int bColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de bleu (int)"));
                                             
                                             Color changedColor = new Color(rColor, gColor, bColor);
                                             
                                             Continent continentToModifyColor = null;
                                             
                                             for(Territory t : territoryForContinentColor){
                                      
                                                 continentToModifyColor = t.getContinent();
                                                 
                                                }
                                             
                                             continentToModifyColor.editColor(changedColor);
                                             
                                             ((MyWorld)getWorld()).changeMode(Mode.DEFAULT);
                                             
                                             ((MyWorld)getWorld()).territoriesCurrentlySelected.clear();
                                            
                                             break;
                                             
            case Mode.EDIT_CONTINENT_BONUS : HashSet<Territory> territoryForContinentBonus = ((MyWorld)getWorld()).territoriesCurrentlySelected;
                                             
                                             if(territoryForContinentBonus.size() > 1){
                                                     
                                                     System.out.println("Trop de continents sélectionnés");
                                                     break;
                                      
                                              }
                                             
                                             int newContinentBonus = Integer.parseInt(JOptionPane.showInputDialog("Entrez le nouveau bonus de continent"));
                                             
                                             Continent continentToModifyBonus = null;
                                             
                                             for(Territory t : territoryForContinentBonus){
                                      
                                                 continentToModifyBonus = t.getContinent();
                                                 
                                                }
                                             
                                             continentToModifyBonus.editBonus(newContinentBonus);
                                             
                                             ((MyWorld)getWorld()).changeMode(Mode.DEFAULT);
                                             
                                             ((MyWorld)getWorld()).territoriesCurrentlySelected.clear();
                                            
                                             break;
                                            
            case Mode.DELETE_CONTINENT : HashSet<Territory> territoryForContinentDelete = ((MyWorld)getWorld()).territoriesCurrentlySelected;
                                         
                                         if(territoryForContinentDelete.size() > 1){
                                                     
                                              System.out.println("Trop de continents sélectionnés");
                                              break;
                                      
                                          }
                                         
                                         Continent continentToDelete = null;
                                         
                                         for(Territory t : territoryForContinentDelete){
                                  
                                             continentToDelete = t.getContinent();
                                             
                                            }
                                         
                                         for(Territory toDelete : continentToDelete.getContainedTerritories()){
                                             
                                             toDelete.destroy();
                                             
                                          }
                                         
                                         ((MyWorld)getWorld()).changeMode(Mode.DEFAULT);
                                         
                                         ((MyWorld)getWorld()).territoriesCurrentlySelected.clear();
                                            
                                         break;
        }
        
    }
    
    
    
    public void act() 
    {
        
    }    
    
    
    
    
}
