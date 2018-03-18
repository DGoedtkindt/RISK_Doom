package base;

import game.Difficulty;
import game.Player;
import game.TurnStat;
import java.util.ArrayList;
import java.util.List;

/**
 * An Object representing the current state of the Game.
 * 
 */
public class Game {
    
    /**
     * Different states in wich the Game can be.
     */
    public enum State {
        INITIALISATION("stuff still needs to be done for the game to truly start"),
        INGAME("normal state of the game. players take turns"),
        FINISHED("the game is finished");
        
        State(String description) {}
        
    }
    
    public int turnNumber = 0;
    public State gameState = State.INITIALISATION;
    public List<Player> players = new ArrayList<>();
    public Map map = new Map();
    public Difficulty difficulty;
    
    //for autosave
    public boolean autoSave = false;
    public String name = "";
    public String description = "";
    
}
