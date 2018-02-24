package base;

import game.Difficulty;
import java.util.ArrayList;
import game.Player;

/**
 * An object representing the current state of the Game.
 * 
 */
public class Game {
    
    /**
     * The states of a Game.
     */
    public enum State {
        INITIALISATION("The game is being created."),
        INGAME("Normal state of the game. Players take turns."),
        FINISHED("The game is finished.");
        
        State(String description) {}
        
    }
    
    public State gameState = State.INITIALISATION;
    public ArrayList<Player> players = new ArrayList<>();
    public Map map = new Map();
    public Difficulty difficulty;
    
}
