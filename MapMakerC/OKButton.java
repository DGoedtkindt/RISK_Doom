import greenfoot.GreenfootImage;
import java.util.ArrayList;

public class OKButton extends Button
{
    
    private static MyWorld world() {return MyWorld.theWorld;}
    
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
            if(null != mode) //at this level, the ifs end with escape()
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
                    if(Links.newLinks != null && Links.newLinks.LinkIndicsList().size() == 1){
                        
                        Links.newLinks.LinkIndicsList().get(0).destroy();
                        Links.allLinks().remove(Links.newLinks);
                        System.err.println("You can't create a link that links less than 2 territories");
                        
                    }   Links.newLinks = null;
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
           e.printStackTrace(System.out);
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
            e.printStackTrace(System.out);
            world().escape();
           
           }
        
    }
    
    private void deleteContinentSelection(){
        try{
            Continent continentToDelete = Selector.getSelectedContinent();
            continentToDelete.destroy();
            
           } catch(Exception e){
            e.printStackTrace(System.out);
            world().escape();
             
           }
             
    }
    
    public void makeTransparent() {
        getImage().setTransparency(MyWorld.TRANSPARENT);
    
    }
    
    public void makeOpaque() {
        getImage().setTransparency(MyWorld.OPAQUE);
    
    }
}
