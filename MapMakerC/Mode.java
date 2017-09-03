
public class Mode
{
    
    static private Mode currentMode;
    
    private String message;
    private Action SETUP;
    
    private static MyWorld theWorld() {return MyWorld.theWorld;}
    
    private Mode(Action action){
        
        //Needs an action
        //A text
        //Set those in the variables above
        
        SETUP = action;
        
    }
    
    ///Public Methods///////////////////////////////////////
    
    public String message(){
        return message;
        
    }
    
    static public void changeMode(Mode newMode){
        currentMode = newMode;
        removeAllButtons();
        currentMode.SETUP.trigger();
        
    }
    
    static public Mode currentMode() {
        return currentMode;
        
    }
    
    ///Private Method/////////////////////////////////////
    
    private static void removeAllButtons() {
        theWorld().removeObject(theWorld().editTerritory);
        theWorld().removeObject(theWorld().createTerritory);
        theWorld().removeObject(theWorld().editContinent);
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
    
    ///Setups////////////////////////////////////////
    
    static private final Action DEFAULT_SETUP = () -> {
        Selector.setValidator(Selector.EVERYTHING);
        theWorld().addObject(theWorld().createTerritory, MyWorld.WORLD_WIDTH -100, 100);
        theWorld().addObject(theWorld().editTerritory, MyWorld.WORLD_WIDTH -100, 150);
        theWorld().addObject(theWorld().createContinent, MyWorld.WORLD_WIDTH - 100, 200);
        theWorld().addObject(theWorld().editContinent, MyWorld.WORLD_WIDTH -100, 250);
        theWorld().addObject(theWorld().createLink, MyWorld.WORLD_WIDTH - 100, 300);
        theWorld().addObject(theWorld().makeXMLButton, MyWorld.WORLD_WIDTH - 100, 1000);
    
        
    };
    
    static private final Action CREATE_TERRITORY_SETUP = () -> {
        Selector.setValidator(Selector.IS_SINGLEHEX);
        theWorld().addObject(theWorld().okButton, MyWorld.WORLD_WIDTH - 100, 150);
        
    };
    
    static private final Action CREATE_CONTINENT_SETUP = () -> {
        Selector.setValidator(Selector.IS_TERRITORY_NOT_IN_CONTINENT);
        theWorld().addObject(theWorld().okButton, MyWorld.WORLD_WIDTH - 100, 150);
        
    };
    
    static private final Action EDIT_TERRITORY_SETUP = () -> {
        Selector.setValidator(Selector.EVERYTHING);
        theWorld().addObject(theWorld().editTerritoryBonus, MyWorld.WORLD_WIDTH - 100, 100);
        theWorld().addObject(theWorld().deleteTerritory, MyWorld.WORLD_WIDTH - 100, 150);
    };
    
    static private final Action EDIT_CONTINENT_SETUP = () -> {
        Selector.setValidator(Selector.EVERYTHING);
        theWorld().addObject(theWorld().editContinentBonus, MyWorld.WORLD_WIDTH - 100, 100);
        theWorld().addObject(theWorld().editContinentColor, MyWorld.WORLD_WIDTH - 100, 150);
        theWorld().addObject(theWorld().deleteContinent, MyWorld.WORLD_WIDTH - 100, 200);
        
    };
    
    static private final Action SET_LINK_SETUP = () -> {
        Selector.setValidator(Selector.IS_TERRITORY);
        theWorld().addObject(theWorld().okButton, MyWorld.WORLD_WIDTH - 100, 150);

        
        
    };
    
    static private final Action EDIT_CONTINENT_COLOR_SETUP = () -> {
        Selector.setValidator(Selector.IS_CONTINENT);
        theWorld().addObject(theWorld().okButton, MyWorld.WORLD_WIDTH - 100, 150);

        
    };
    
    static private final Action EDIT_CONTINENT_BONUS_SETUP = () -> {
        Selector.setValidator(Selector.IS_CONTINENT);
        theWorld().addObject(theWorld().okButton, MyWorld.WORLD_WIDTH - 100, 150);
        
    };
    
    static private final Action DELETE_TERRITORY_SETUP = () -> {
        Selector.setValidator(Selector.IS_TERRITORY);
        theWorld().addObject(theWorld().okButton, MyWorld.WORLD_WIDTH - 100, 150);
        
    };
    
    static private final Action DELETE_CONTINENT_SETUP = () -> {
        Selector.setValidator(Selector.IS_CONTINENT);
        theWorld().addObject(theWorld().okButton, MyWorld.WORLD_WIDTH - 100, 150);
        
    };
    
    ///Final Modes//////////////////////////////////////////
    
    static public final Mode DEFAULT                     = new Mode(DEFAULT_SETUP);
    static public final Mode EDIT_TERRITORY              = new Mode(EDIT_TERRITORY_SETUP);
    static public final Mode EDIT_CONTINENT              = new Mode(EDIT_CONTINENT_SETUP);
    static public final Mode CREATE_TERRITORY            = new Mode(CREATE_TERRITORY_SETUP);
    static public final Mode CREATE_CONTINENT            = new Mode(CREATE_CONTINENT_SETUP);
    static public final Mode EDIT_TERRITORY_BONUS        = new Mode(EDIT_TERRITORY_SETUP);
    static public final Mode SET_LINK                    = new Mode(SET_LINK_SETUP);
    static public final Mode EDIT_CONTINENT_COLOR        = new Mode(EDIT_CONTINENT_COLOR_SETUP);
    static public final Mode EDIT_CONTINENT_BONUS        = new Mode(EDIT_CONTINENT_BONUS_SETUP);
    static public final Mode DELETE_TERRITORY            = new Mode(DELETE_TERRITORY_SETUP);
    static public final Mode DELETE_CONTINENT            = new Mode(DELETE_CONTINENT_SETUP);
    
}
