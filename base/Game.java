package base;

import game.Difficulty;
import game.Player;

/**
 * An a structured variable representing the current state of a Game.
 * 
 */
public class Game {
    
    /**
     * Different states in which this Game can be.
     */
    public enum State {
        INITIALISATION("stuff still needs to be done for the game to truly start"),
        INGAME("normal state of the game. players take turns"),
        FINISHED("the game is finished");
        
        State(String description) {}
        
    }
    
    public int turnNumber = 0;
    public State gameState = State.INITIALISATION;
    public final ListenerList<Player> players = new ListenerList<>();
    public Map map = new Map();
    public Difficulty difficulty;
    
    //Autosave variables
    public boolean autoSave = false;
    public String name = "";
    public String description = "";
    
}
