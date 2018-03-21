package mapEditor;

import appearance.MessageDisplayer;
import base.Button;
import mode.Mode;
import selector.Selector;
import mainObjects.Continent;
import mainObjects.Links;
import mainObjects.Territory;
import greenfoot.GreenfootImage;
import java.util.ArrayList;

/**
 * This Button acts like a Validator in the Map Editor. The User clicks on this
 * when he wants to validate his action.
 * 
 */
public class OKButton extends Button{
    
    /**
     * Creates an OKButton.
     */
    public OKButton(){
        
        GreenfootImage image = new GreenfootImage("OKButton.png");
        image.scale(80, 80);
        setImage(image);
        
    }
    
    @Override
    public void clicked(){
        
        if(isUsable()){
            
            //At this level the ifs that must not end with escape()
            if(Mode.mode() == Mode.CREATE_TERRITORY){
                switchToSelectInfoHex();

            }else {
                //At this level, the ifs end with escape()
                switch (Mode.mode()) {
                    case CREATE_CONTINENT:
                        createContinentFromSelection();
                        break;
                        
                    case DELETE_TERRITORY:
                        deleteTerritorySelection();
                        break;
                        
                    case DELETE_CONTINENT:
                        deleteContinentSelection();
                        break;
                        
                    case SET_LINK:
                        if(!(Links.newLinks == null)){
                            if(!Links.newLinks.isLargeEnough()) {
                                Links.newLinks.destroy();
                                MessageDisplayer.showMessage("You cannot create Links with fewer"
                                                           + " than 2 linked Territories.");

                            }
                        }else{
                            MessageDisplayer.showMessage("You must select positions on Territories in order to create a Link between them.");
                        }
                        
                        Links.newLinks = null;
                        break;
                        
                    default:
                        break;
                        
                }
                
                world().stateManager.escape();

            }
            
        }
        
    }
    
    /**
     * Creates a Continent from the selection of Territories that the User 
     * just validated.
     */
    private void createContinentFromSelection(){
        try{
            ArrayList<Territory> selectedTerritories;
            selectedTerritories = Selector.getSelectedTerritories();
            for(Territory t : selectedTerritories){

                if(t.continent() != null){
                    throw new Exception("A selected territory already has a continent.");

                }

            }
            new Continent(selectedTerritories).addToWorld();
            
        } catch(Exception ex){
            String message = "Continent couldn't be created";
            MessageDisplayer.showException(new Exception(message, ex));
           
        }
        
    }
    
    /**
     * Lets the User choose a BlankHex to place the TerrInfo of a Territory, 
     * when creating a Territory.
     */
    private void switchToSelectInfoHex(){
        Selector.setValidator(Selector.NOTHING);
        Mode.setMode(Mode.SELECT_INFO_HEX);
        
    }
    
    /**
     * Deletes the validated selection of Territories.
     */
    private void deleteTerritorySelection(){
        try{
            
            ArrayList<Territory> territoriesToDelete;
            territoriesToDelete = Selector.getSelectedTerritories();
            territoriesToDelete.forEach(Territory::destroy);
            
        } catch(Exception ex){
            String message = "Territories couldn't be destroyed";
            MessageDisplayer.showException(new Exception(message, ex));

        }
        
    }
    
    /**
     * Deletes the validated selection of Continents.
     */
    private void deleteContinentSelection(){
        try{
            ArrayList<Continent> continentsToDelete = Selector.continentSelectedList();
            continentsToDelete.forEach((Continent c) -> {c.destroy();});
            
        }catch(Exception ex){
            String message = "Continents couldn't be deleted";
            MessageDisplayer.showException(new Exception(message, ex));

        }
             
    }
    
}
