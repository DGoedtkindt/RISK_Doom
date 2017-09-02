
public class Mode
{
    
    static Mode currentMode;
    
    private String message;
    private Action SETUP;
    
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
        
    }
    
    ///Final Actions////////////////////////////////////////
    
    static public final Action DEFAULT_MODE_ACTION = () -> {
        
        
        
    };
    
    static public final Action SELECT_HEX_MODE_ACTION = () -> {
        
        
        
    };
    
    static public final Action SELECT_TERRITORY_MODE_ACTION = () -> {
        
        
        
    };
    
    static public final Action CHOOSE_CAPITAL_TERRITORY_MODE_ACTION = () -> {
        
        
        
    };
    
    static public final Action SET_LINKS_MODE_ACTION = () -> {
        
        
        
    };
    
    static public final Action EDIT_CONTINENT_COLOR_MODE_ACTION = () -> {
        
        
        
    };
    
    static public final Action EDIT_CONTINENT_BONUS_MODE_ACTION = () -> {
        
        
        
    };
    
    static public final Action DELETE_TERRITORY_MODE_ACTION = () -> {
        
        
        
    };
    
    static public final Action DELETE_CONTINENT_MODE_ACTION = () -> {
        
        
        
    };
    
    ///Final Modes//////////////////////////////////////////
    
    static public final Mode DEFAULT                     = new Mode(DEFAULT_MODE_ACTION);
    static public final Mode SELECT_HEX                  = new Mode(SELECT_HEX_MODE_ACTION);
    static public final Mode SELECT_TERRITORY            = new Mode(SELECT_TERRITORY_MODE_ACTION);
    static public final Mode CHOOSE_CAPITAL_TERRITORY    = new Mode(CHOOSE_CAPITAL_TERRITORY_MODE_ACTION);
    static public final Mode SET_LINKS                   = new Mode(SET_LINKS_MODE_ACTION);
    static public final Mode EDIT_CONTINENT_COLOR        = new Mode(EDIT_CONTINENT_COLOR_MODE_ACTION);
    static public final Mode EDIT_CONTINENT_BONUS        = new Mode(EDIT_CONTINENT_BONUS_MODE_ACTION);
    static public final Mode DELETE_TERRITORY            = new Mode(DELETE_TERRITORY_MODE_ACTION);
    static public final Mode DELETE_CONTINENT            = new Mode(DELETE_CONTINENT_MODE_ACTION);
    
}
