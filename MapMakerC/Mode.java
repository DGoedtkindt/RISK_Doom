
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
        if(currentMode == DEFAULT) {}
        else {}
        
    }
    
    static public Mode currentMode() {
        return currentMode;
        
    }
    
    ///Private Method/////////////////////////////////////
    
    private static void removeAllButtons() {
        theWorld().removeObject(theWorld().createTerritory);
        theWorld().removeObject(theWorld().createContinent);
        theWorld().removeObject(theWorld().editContinentBonus);
        theWorld().removeObject(theWorld().editContinentColor);
        theWorld().removeObject(theWorld().editTerritoryBonus);
        theWorld().removeObject(theWorld().createLink);
        theWorld().removeObject(theWorld().deleteTerritory);
        theWorld().removeObject(theWorld().deleteContinent);
        theWorld().removeObject(theWorld().okButton);
        theWorld().removeObject(theWorld().makeXMLButton);
    
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
