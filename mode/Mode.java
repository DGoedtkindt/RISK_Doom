package mode;

import base.Action;
import java.util.ArrayList;

/**
 * This enum contains Modes, which are states in which the Game can be.
 * 
 */
public enum Mode{
    
    //Default Mode
    DEFAULT(""),
    //Map Editor modes
    CREATE_TERRITORY("Create a territory by selecting at least two blank hexes."),
    CREATE_CONTINENT("Create a continent by selecting at least one territory that is not already part of a continent."),
    EDIT_TERRITORY_BONUS("Select a territory to change its bonus."),
    SET_LINK("Click on the territories you want to connect. \n A dot will be placed at the exact place you click."
                                                            + "\n You will be asked to choose a color the first time."),
    EDIT_CONTINENT_COLOR("Select a continent and change its color."),
    EDIT_CONTINENT_BONUS("Select a continent and change its bonus."),
    DELETE_TERRITORY("Select a territory to delete it."),
    DELETE_CONTINENT("Select a continent to delete it (without destroying its composing territories)."),
    SELECT_INFO_HEX("Select the hex wich will show the current bonus of this territory."),
    ACTION_ON_LINK("Select an action to perform on the Link you selected."),
    //Game modes
    ATTACK("Click on one of your territories, then on an adjacent ennemy one. "
            + "Finally, choose the number of armies you're using."),
    MOVE("Move your armies from one of your territories to another."),
    SELECTING_COMBO("Choose the combo you want to use."),
    SAP("Click on an ennemy territory to clear all the troops on it."),
    CLEARING_HAND("Place your new armies on your territories.");
    
    /**
     * Creates a Mode.
     */
    Mode(String text){
        message = text;
        
    }
    
    private static Mode currentMode = DEFAULT;
    private static final ArrayList<Action> actionsWhenModeChanges = new ArrayList<>();
    protected final String message;
    
    /**
     * Gets the current Mode.
     * @return The current Mode.
     */
    public static Mode mode() {
        return currentMode;
        
    };
    
    /**
     * Changes the current Mode.
     * @param newMode The new Mode.
     */
    public static void setMode(Mode newMode) {
        currentMode = newMode;
        actionsWhenModeChanges.forEach((Action action)->(action.act()));
        
    }
    
    /**
     * Adds an Action to the list of Actions that must happen whenever the 
     * current Mode changes.
     * @param action The Action that triggers when the current Mode changes that 
     *               is added to the list.
     */
    public static void addModeChangeListener(Action action) {
        actionsWhenModeChanges.add(action);
    
    }
    
    /**
     * Removes an Action from the list of Actions that must happen whenever the 
     * current Mode changes.
     * @param action The Action that triggers when the current Mode changes that 
     *               is removed from the list.
     */
    public static void removeModeChangeListener(Action action) {
        actionsWhenModeChanges.remove(action);
    
    }
    
}