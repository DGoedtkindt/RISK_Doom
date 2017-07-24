import greenfoot.*;  
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.awt.Color;

public class OKButton extends Button
{
    
    
    public void clicked(int mode){
        
        switch(((MyWorld)getWorld()).currentMode){
            
            default : ((MyWorld)getWorld()).escape();
                      break;
                        
            case Mode.SELECT_HEX : SingleHex[] selectedHexes = ((MyWorld)getWorld()).singleHexesCurrentlySelected;
                                   ArrayList<Coordinates> selectedCoordinates = new ArrayList();
                                   int hexNumber = 0;
                                   
                                   for(SingleHex hex : selectedHexes){
                                       
                                       selectedCoordinates.add(hex.getCoord());
                                       hexNumber++;
                                       
                                    }
                                   
                                   try{
                                       
                                       Territory createdTerritory = new Territory(selectedCoordinates);
                                       
                                    }catch(Exception e){
                                       
                                       System.out.println("Erreur de création de territoire " + e.getMessage());
                                       
                                    }
                                   
                                   for(SingleHex sh : ((MyWorld)getWorld()).singleHexesCurrentlySelected){
                                       
                                       sh = null;
                                       
                                    }
                                   ((MyWorld)getWorld()).singleHexesCurrentlySelectedNumber = 0;
                                    
                                   ((MyWorld)getWorld()).changeMode(Mode.DEFAULT);
                                   break;
                        
            case Mode.SELECT_TERRITORY : Territory[] selectedTerritories = ((MyWorld)getWorld()).territoriesCurrentlySelected;
                                         Continent createdContinent = new Continent();
                                         createdContinent.territoriesContained = selectedTerritories;
                                         ((MyWorld)getWorld()).changeMode(Mode.DEFAULT);
                                         
                                         for(Territory t : ((MyWorld)getWorld()).territoriesCurrentlySelected){
                                             
                                             t = null;
                                             
                                            }
                                         
                                         ((MyWorld)getWorld()).territoriesCurrentlySelectedNumber = 0;
                                            
                                         break;
                                         
            case Mode.DELETE_TERRITORY : Territory[] territoriesToDelete = ((MyWorld)getWorld()).territoriesCurrentlySelected;
                                         
                                         for(Territory toDelete : territoriesToDelete){
                                             
                                             toDelete.destroy();
                                             
                                          }
                                         
                                         ((MyWorld)getWorld()).changeMode(Mode.DEFAULT);
                                         
                                          for(Territory t : ((MyWorld)getWorld()).territoriesCurrentlySelected){
                                             
                                             t = null;
                                             
                                            }
                                         
                                         ((MyWorld)getWorld()).territoriesCurrentlySelectedNumber = 0;
                                            
                                         break;
            
            case Mode.SET_LINKS : Territory[] territoriesToLink = ((MyWorld)getWorld()).territoriesCurrentlySelected;
            
                                  if(territoriesToLink.length > 2){
                                    
                                      System.out.println("Trop de territoires à lier");
                                      break;
                                      
                                   }
                                  
                                  territoriesToLink[0].setNewLink(territoriesToLink[1]);
                                  territoriesToLink[1].setNewLink(territoriesToLink[0]);
                                  ((MyWorld)getWorld()).changeMode(Mode.DEFAULT);
                                  
                                  for(Territory t : ((MyWorld)getWorld()).territoriesCurrentlySelected){
                                             
                                        t = null;
                                             
                                     }
                                         
                                  ((MyWorld)getWorld()).territoriesCurrentlySelectedNumber = 0;
                                            
                                  break;
                                 
            case Mode.CHOOSE_CAPITAL_TERRITORY : Territory[] capitalTerritory = ((MyWorld)getWorld()).territoriesCurrentlySelected;
            
                                                 if(capitalTerritory.length > 1){
                                                     
                                                     System.out.println("Trop de nouvelles capitales");
                                                     break;
                                      
                                                  }
                                                 
                                                 int capitalBonus = Integer.parseInt(JOptionPane.showInputDialog("Entrez le nouveau bonus de capitale"));
                                                 
                                                 capitalTerritory[0].changeCapital(capitalBonus);
                                                 
                                                 ((MyWorld)getWorld()).changeMode(Mode.DEFAULT);
                                                 
                                                  for(Territory t : ((MyWorld)getWorld()).territoriesCurrentlySelected){
                                             
                                                      t = null;
                                             
                                                    }
                                         
                                                 ((MyWorld)getWorld()).territoriesCurrentlySelectedNumber = 0;
                                            
                                                 break;
                                                 
            case Mode.EDIT_CONTINENT_COLOR : Territory[] territoryForContinentColor = ((MyWorld)getWorld()).territoriesCurrentlySelected;
                                             
                                             if(territoryForContinentColor.length > 1){
                                                     
                                                     System.out.println("Trop de continents sélectionnés");
                                                     break;
                                      
                                              }
                                             
                                             int rColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de rouge (int)"));
                                             int gColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de vert (int)"));
                                             int bColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de bleu (int)"));
                                             
                                             Color changedColor = new Color(rColor, gColor, bColor);
                                             
                                             Continent continentToModifyColor = territoryForContinentColor[0].getContinent();
                                             
                                             continentToModifyColor.editColor(changedColor);
                                             
                                             ((MyWorld)getWorld()).changeMode(Mode.DEFAULT);
                                             
                                             for(Territory t : ((MyWorld)getWorld()).territoriesCurrentlySelected){
                                             
                                                 t = null;
                                             
                                               }
                                            
                                             ((MyWorld)getWorld()).territoriesCurrentlySelectedNumber = 0;
                                            
                                             break;
                                             
            case Mode.EDIT_CONTINENT_BONUS : Territory[] territoryForContinentBonus = ((MyWorld)getWorld()).territoriesCurrentlySelected;
                                             
                                             if(territoryForContinentBonus.length > 1){
                                                     
                                                     System.out.println("Trop de continents sélectionnés");
                                                     break;
                                      
                                              }
                                             
                                             int newContinentBonus = Integer.parseInt(JOptionPane.showInputDialog("Entrez le nouveau bonus de continent"));
                                             
                                             Continent continentToModifyBonus = territoryForContinentBonus[0].getContinent();
                                             
                                             continentToModifyBonus.editBonus(newContinentBonus);
                                             
                                             ((MyWorld)getWorld()).changeMode(Mode.DEFAULT);
                                             
                                              for(Territory t : ((MyWorld)getWorld()).territoriesCurrentlySelected){
                                             
                                                  t = null;
                                                  
                                                }
                                         
                                             ((MyWorld)getWorld()).territoriesCurrentlySelectedNumber = 0;
                                            
                                             break;
                                            
            case Mode.DELETE_CONTINENT : Territory[] territoryForContinentDelete = ((MyWorld)getWorld()).territoriesCurrentlySelected;
                                         
                                         if(territoryForContinentDelete.length > 1){
                                                     
                                              System.out.println("Trop de continents sélectionnés");
                                              break;
                                      
                                          }
                                         
                                         Continent continentToDelete = territoryForContinentDelete[0].getContinent();
                                         
                                         for(Territory toDelete : continentToDelete.getContainedTerritories()){
                                             
                                             toDelete.destroy();
                                             
                                          }
                                         
                                         ((MyWorld)getWorld()).changeMode(Mode.DEFAULT);
                                         
                                          for(Territory t : ((MyWorld)getWorld()).territoriesCurrentlySelected){
                                             
                                              t = null;
                                             
                                            }
                                         
                                         ((MyWorld)getWorld()).territoriesCurrentlySelectedNumber = 0;
                                            
                                         break;
        }
        
    }
    
    
    
    public void act() 
    {
        
    }    
    
    
    
    
}
