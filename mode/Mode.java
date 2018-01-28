package mode;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public enum Mode
{
    //Map Editor modes
    MAP_EDITOR_DEFAULT(""),
    CREATE_TERRITORY("Create a territory by selecting at least two blank hexes."),
    CREATE_CONTINENT("Create a continent by selecting at least one territory that is not already part of a continent."),
    EDIT_TERRITORY_BONUS("Select a territory to change its bonus."),
    SET_LINK("Click on the territories you want to connect. \n A dot will be placed at the exact place you click."
                                                            + "\n You will be asked to chose a color the first time."),
    EDIT_CONTINENT_COLOR("Select a continent and change its color."),
    EDIT_CONTINENT_BONUS("Select a continent and change its bonus."),
    DELETE_TERRITORY("Select a territory to delete it."),
    DELETE_CONTINENT("Select a continent to delete it (without destroying its composing territories)."),
    SELECT_INFO_HEX("Select the hex wich will show the current bonus of this territory.");
    
    Mode(String text){
        message = text;
        
    }
    
    private static Mode currentMode;
    private static final ArrayList<ActionListener> actionsWhenModeChanges = new ArrayList<>();
    protected final String message;
    
    public static Mode mode() {
        return currentMode;
        
    };
    
    public static void setMode(Mode newMode) {
        currentMode = newMode;
        actionsWhenModeChanges.forEach((ActionListener al)->(al.actionPerformed(null)));
        
    }
    
    public static void addModeChangeListener(ActionListener al) {
        actionsWhenModeChanges.add(al);
    
    }
    
    public static void removeModeChangeListener(ActionListener al) {
        actionsWhenModeChanges.remove(al);
    
    }
    
}