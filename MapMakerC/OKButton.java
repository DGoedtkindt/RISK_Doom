import greenfoot.GreenfootImage;
import java.util.ArrayList;
import java.util.HashSet;
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
            
            createLinksFromSelection();
            
        }else if(mode == Mode.EDIT_TERRITORY){
            
            changeCapitalFromSelection();
            
        }else if(mode == Mode.EDIT_CONTINENT_COLOR){
            
            changeColorFromSelection();
            
        }else if(mode == Mode.EDIT_CONTINENT_BONUS){
            
            changeBonusFromSelection();
            
        }else if(mode == Mode.DELETE_CONTINENT){
            
            deleteContinentSelection();
            
        }
        
        MyWorld.theWorld.escape();
        
    }
    
    private void createContinentFromSelection(){
        
        ArrayList<Territory> selectedTerritories;
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
        
        ArrayList<SingleHex> selectedHexes;
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
        
        ArrayList<Territory> territoriesToDelete;
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
        
        Territory[] territoriesToLink;
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
        
        Territory capitalTerritory;
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
        
        Continent ContinentForColorChange;
        try{
             
            ContinentForColorChange = Selector.getSelectedContinent();
             
            int rColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de rouge (int)"));
            int gColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de vert (int)"));
            int bColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de bleu (int)"));
         
            Color changedColor = new Color(rColor, gColor, bColor);
         
            ContinentForColorChange.editColor(changedColor);
            
           }catch(Exception e){
            
            System.out.println(e.getMessage());
            MyWorld.theWorld.escape();
             
           }
              
    }
    
    private void changeBonusFromSelection(){
        
        Continent ContinentForBonusChange;
        try{
             
            ContinentForBonusChange = Selector.getSelectedContinent();
             
            int newContinentBonus = Integer.parseInt(JOptionPane.showInputDialog("Entrez le nouveau bonus de continent"));
         
            ContinentForBonusChange.editBonus(newContinentBonus);
            
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
