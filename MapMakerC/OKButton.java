import greenfoot.GreenfootImage;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.awt.Color;

public class OKButton extends Button
{
    
    public OKButton(){
        
        GreenfootImage image = new GreenfootImage("Validate", 25, Color.BLACK, Color.WHITE);
        this.setImage(image);
        
    }
    
    public void clicked(){
        
        Mode mode = Mode.currentMode();
        
        if(mode == Mode.CREATE_TERRITORY){
            
            createTerritoryFromSelection();
            
        }else if(mode == Mode.CREATE_CONTINENT){
            
            createContinentFromSelection();
            
        }else if(mode == Mode.DELETE_TERRITORY){
            
            deleteTerritorySelection();
            
        }else if(mode == Mode.SET_LINK){
            
            createLinksForSelection();
            
        }else if(mode == Mode.EDIT_TERRITORY_BONUS){
            
            changeBonusFromTerrSelected();
            
        }else if(mode == Mode.EDIT_CONTINENT_COLOR){
            
            changeColorFromContSelected();
            
        }else if(mode == Mode.EDIT_CONTINENT_BONUS){
            
            changeBonusFromContSelection();
            
        }else if(mode == Mode.DELETE_CONTINENT){
            
            deleteContinentSelection();
            
        }
        
        MyWorld.theWorld.escape();
        
    }
    
    private void createContinentFromSelection(){
        try{
            ArrayList<Territory> selectedTerritories;
            selectedTerritories = Selector.getSelectedTerritories();
            for(Territory t : selectedTerritories){
                
                if(t.getContinent() != null){
                    throw new Exception("A selected territory already has a continent");
                    
                }
                
            }
            new Continent(selectedTerritories);
            
        } catch(Exception e){
           e.printStackTrace(System.out);
           MyWorld.theWorld.escape();
           
           }
        
    }
    
    private void createTerritoryFromSelection(){
        try{
            ArrayList<SingleHex> selectedHexes;
            selectedHexes = Selector.getSelectedHexes();
             new Territory(selectedHexes);
                
        } catch(Exception e){
            e.printStackTrace(System.out);
            MyWorld.theWorld.escape();
           
         }
        
    }
    
    private void deleteTerritorySelection(){
        try{
            ArrayList<Territory> territoriesToDelete;
            territoriesToDelete = Selector.getSelectedTerritories();
            for(Territory toDelete : territoriesToDelete){
                toDelete.destroy();
                 
            }
            
           } catch(Exception e){
            e.printStackTrace(System.out);
            MyWorld.theWorld.escape();
           
           }
        
    }
    
    private void createLinksForSelection(){
        try{
            Territory[] territoriesToLink;
            territoriesToLink = Selector.getSelectedTerritoryPair();
            territoriesToLink[0].setNewLink(territoriesToLink[1]);
            territoriesToLink[1].setNewLink(territoriesToLink[0]);
            
          } catch(Exception e){
            e.printStackTrace(System.out);
            MyWorld.theWorld.escape();
              
          }
            
    }
    
    private void changeBonusFromTerrSelected(){
        try{
            Territory editedTerritory;
            editedTerritory = Selector.getSelectedTerritory();
            int newBonus = Integer.parseInt(JOptionPane.showInputDialog("Entrez le nouveau bonus pour le territoire"));
            editedTerritory.bonusPoints =  newBonus;
            
           } catch(Exception e){
            e.printStackTrace(System.out);
            MyWorld.theWorld.escape();
             
           }
            
    }
    
    private void changeColorFromContSelected(){
        try{
            Continent ContinentForColorChange;
            ContinentForColorChange = Selector.getSelectedContinent();
            int rColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de rouge (int)"));
            int gColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de vert (int)"));
            int bColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de bleu (int)"));
            Color changedColor = new Color(rColor, gColor, bColor);
            ContinentForColorChange.editColor(changedColor);
            
           } catch(Exception e){
            e.printStackTrace(System.out);
            MyWorld.theWorld.escape();
             
           }
              
    }
    
    private void changeBonusFromContSelection(){
        try{
            Continent editedContinent;
            editedContinent = Selector.getSelectedContinent();
            int newContinentBonus = Integer.parseInt(JOptionPane.showInputDialog("Entrez le nouveau bonus de continent"));
            editedContinent.bonus = newContinentBonus;
            
           } catch(Exception e){
            e.printStackTrace(System.out);
            MyWorld.theWorld.escape();
             
           }
                                             
    }
    
    private void deleteContinentSelection(){
        try{
            Continent continentToDelete = Selector.getSelectedContinent();
            continentToDelete.destroy();
            
           } catch(Exception e){
            e.printStackTrace(System.out);
            MyWorld.theWorld.escape();
             
           }
             
    }
    
}
