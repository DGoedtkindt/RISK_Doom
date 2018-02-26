package base;

import game.Difficulty;
import game.Player;
import game.TurnStat;
import java.util.ArrayList;
import java.util.List;

public class Game {
    
    /**
     * different states the game can be in.
     */
    public enum State {
        INITIALISATION("stuff still needs to be done for the game to truly start"),
        INGAME("normal state of the game. players take turns"),
        FINISHED("the game is finished");
        
        State(String description) {}
        
    }
    
    public List<TurnStat> stats = new ArrayList<>();
    public State gameState = State.INITIALISATION;
    public List<Player> players = new ArrayList<>();
    public Map map = new Map();
    public Difficulty difficulty;
    
    //for autosave
    public boolean autoSave = false;
    public String name = "";
    public String description = "";
    
}
