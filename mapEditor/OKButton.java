package mapEditor;

import selector.Selector;
import appearance.Appearance;
import mainObjects.Continent;
import links.Links;
import base.*;
import mainObjects.Territory;
import greenfoot.GreenfootImage;
import java.util.ArrayList;

public class OKButton extends Button
{
    
    private MyWorld world() {return (MyWorld)this.getWorld();}
    
    public OKButton(){
        
        GreenfootImage image = new GreenfootImage("OKButton.png");
        image.scale(80, 80);
        this.setImage(image);
        
    }
    
    @Override
    public void clicked(){
        
        Mode mode = Mode.currentMode();
        
        //at this level the ifs that must not end with escape()
        if(mode == Mode.CREATE_TERRITORY){
            switchToSelectInfoHex();
            
        }else {
            //at this level, the ifs end with escape()
            switch (mode) {
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
                    if(!Links.newLinks.isLargeEnough()) {
                        Links.newLinks.destroy();
                        System.err.println("You cannot create Links with fewer"
                                + " than 2 linked Territories");
                    
                    }
                    Links.newLinks = null;
                    break;
                default:
                    break;
            }
                world().escape();
            
        }
        
    }
    
    private void createContinentFromSelection(){
        try{
            ArrayList<Territory> selectedTerritories;
            selectedTerritories = Selector.getSelectedTerritories();
            for(Territory t : selectedTerritories){
                
                if(t.continent() != null){
                    throw new Exception("A selected territory already has a continent");
                    
                }
                
            }
            new Continent(selectedTerritories);
            
        } catch(Exception e){
           System.err.println(e);
           world().escape();
           
           }
        
    }
    
    private void switchToSelectInfoHex(){
        Selector.setValidator(Selector.NOTHING);
        Mode.changeMode(Mode.SELECT_INFO_HEX);
        
    }
    
    private void deleteTerritorySelection(){
        try{
            ArrayList<Territory> territoriesToDelete;
            territoriesToDelete = Selector.getSelectedTerritories();
            for(Territory toDelete : territoriesToDelete){
                toDelete.destroy();
                 
            }
            
           } catch(Exception e){
            System.err.println(e);
            world().escape();
           
           }
        
    }
    
    private void deleteContinentSelection(){
        try{
            ArrayList<Continent> continentsToDelete = Selector.continentSelectedList();
            continentsToDelete.forEach((Continent c) -> {c.destroy();});
            
           } catch(Exception e){
            System.err.println(e);
            world().escape();
             
           }
             
    }
    
    public void makeTransparent() {
        getImage().setTransparency(Appearance.TRANSPARENT);
    
    }
    
    public void makeOpaque() {
        getImage().setTransparency(Appearance.OPAQUE);
    
    }
}
