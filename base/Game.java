package base;

import game.Difficulty;
import game.TurnStat;
import java.util.ArrayList;
import java.util.List;
import mainObjects.Player;

public class Game {
    
    /**
     * in case we need this
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
    
}
