
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
            
            theWorld().createTerritory.makeOpaque();
            if(unoccupiedTerritoriesNumber > 0){
                theWorld().createContinent.makeOpaque();
            }
            if(!Continent.continentList().isEmpty()){
                theWorld().editContinentBonus.makeOpaque();
                theWorld().editContinentColor.makeOpaque();
                theWorld().deleteContinent.makeOpaque();
            }
            if(!allTerritories.isEmpty()){
                theWorld().editTerritoryBonus.makeOpaque();
                theWorld().deleteTerritory.makeOpaque();
            }
            if(allTerritories.size() > 1){
                theWorld().createLink.makeOpaque();
            }
            theWorld().makeXMLButton.makeOpaque();
            
        }else{
            
            List<ModeButton> buttonList = theWorld().getObjects(ModeButton.class);
            
            for(ModeButton mb : buttonList){
                
                if(mb.linkedMode == mode){
                    
                    mb.makeOpaque();
                    
                }
                
            }
            theWorld().okButton.makeOpaque();
        }
        
    }
    
    private static void makeAllButtonsTransparent() {
        theWorld().createTerritory.makeTransparent();
        theWorld().createContinent.makeTransparent();
        theWorld().editContinentBonus.makeTransparent();
        theWorld().editContinentColor.makeTransparent();
        theWorld().editTerritoryBonus.makeTransparent();
        theWorld().createLink.makeTransparent();
        theWorld().deleteTerritory.makeTransparent();
        theWorld().deleteContinent.makeTransparent();
        theWorld().okButton.makeTransparent();
        theWorld().makeXMLButton.makeTransparent();
    
    }

    
    ///Final Modes//////////////////////////////////////////
    
    static public final Mode DEFAULT                     = new Mode();
    static public final Mode CREATE_TERRITORY            = new Mode();
    static public final Mode CREATE_CONTINENT            = new Mode();
    static public final Mode EDIT_TERRITORY_BONUS        = new Mode();
    static public final Mode SET_LINK                    = new Mode();
    static public final Mode EDIT_CONTINENT_COLOR        = new Mode();
    static public final Mode EDIT_CONTINENT_BONUS        = new Mode();
    static public final Mode DELETE_TERRITORY            = new Mode();
    static public final Mode DELETE_CONTINENT            = new Mode();
    static public final Mode SELECT_INFO_HEX             = new Mode();
    
}
