
import java.util.ArrayList;
import java.util.List;


public class Mode
{
    
    static private Mode currentMode;
    
    private String message;
    
    private static MyWorld theWorld() {return MyWorld.theWorld;}
    
    ///Public Methods///////////////////////////////////////
    
    public String message(){
        return message;
        
    }
    
    static public void changeMode(Mode newMode){
        currentMode = newMode;
        makeAllButtonsTransparent();
        makeValidButtonsOpaque(currentMode);
        
    }
    
    static public Mode currentMode() {
        return currentMode;
        
    }
    
    ///Private Method/////////////////////////////////////
    
    private static void makeValidButtonsOpaque(Mode mode){
        
        if(mode == DEFAULT){
            
            ArrayList<Territory> allTerritories = Territory.allTerritories();
            
            int unoccupiedTerritoriesNumber = 0;
            
            for(Territory t : allTerritories){
                
                if(t != null){
                    if(t.continent() == null){
                    unoccupiedTerritoriesNumber++;
                }
                }
            }
            
            theWorld().createTerritory.getImage().setTransparency(MyWorld.OPAQUE);
            if(unoccupiedTerritoriesNumber > 0){
                theWorld().createContinent.getImage().setTransparency(MyWorld.OPAQUE);
            }
            if(!Continent.continentList().isEmpty()){
                theWorld().editContinentBonus.getImage().setTransparency(MyWorld.OPAQUE);
                theWorld().editContinentColor.getImage().setTransparency(MyWorld.OPAQUE);
                theWorld().deleteContinent.getImage().setTransparency(MyWorld.OPAQUE);
            }
            if(!allTerritories.isEmpty()){
                theWorld().editTerritoryBonus.getImage().setTransparency(MyWorld.OPAQUE);
                theWorld().deleteTerritory.getImage().setTransparency(MyWorld.OPAQUE);
            }
            if(allTerritories.size() > 1){
                theWorld().createLink.getImage().setTransparency(MyWorld.OPAQUE);
            }
            theWorld().makeXMLButton.getImage().setTransparency(MyWorld.OPAQUE);
            
        }else{
            
            List<ModeButton> buttonList = theWorld().getObjects(ModeButton.class);
            
            for(ModeButton mb : buttonList){
                
                if(mb.linkedMode == mode){
                    
                    mb.getImage().setTransparency(MyWorld.OPAQUE);
                    
                }
                
            }
            theWorld().okButton.getImage().setTransparency(MyWorld.OPAQUE);
        }
        
    }
    
    private static void makeAllButtonsTransparent() {
        theWorld().createTerritory.getImage().setTransparency(MyWorld.TRANSPARENT);
        theWorld().createContinent.getImage().setTransparency(MyWorld.TRANSPARENT);
        theWorld().editContinentBonus.getImage().setTransparency(MyWorld.TRANSPARENT);
        theWorld().editContinentColor.getImage().setTransparency(MyWorld.TRANSPARENT);
        theWorld().editTerritoryBonus.getImage().setTransparency(MyWorld.TRANSPARENT);
        theWorld().createLink.getImage().setTransparency(MyWorld.TRANSPARENT);
        theWorld().deleteTerritory.getImage().setTransparency(MyWorld.TRANSPARENT);
        theWorld().deleteContinent.getImage().setTransparency(MyWorld.TRANSPARENT);
        theWorld().okButton.getImage().setTransparency(MyWorld.TRANSPARENT);
        theWorld().makeXMLButton.getImage().setTransparency(MyWorld.TRANSPARENT);
    
    }

    
    ///Final Modes//////////////////////////////////////////
    
    static public final Mode DEFAULT                     = new Mode();
    static public final Mode EDIT_TERRITORY              = new Mode();
    static public final Mode EDIT_CONTINENT              = new Mode();
    static public final Mode CREATE_TERRITORY            = new Mode();
    static public final Mode CREATE_CONTINENT            = new Mode();
    static public final Mode EDIT_TERRITORY_BONUS        = new Mode();
    static public final Mode SET_LINK                    = new Mode();
    static public final Mode EDIT_CONTINENT_COLOR        = new Mode();
    static public final Mode EDIT_CONTINENT_BONUS        = new Mode();
    static public final Mode DELETE_TERRITORY            = new Mode();
    static public final Mode DELETE_CONTINENT            = new Mode();
    
}
