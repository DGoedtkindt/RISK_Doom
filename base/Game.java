package base;

import game.Difficulty;
import java.util.ArrayList;
import mainObjects.Player;

public class Game {
    
    /**
     * in case we need this
     */
    public enum State {
        HELLO("HELLO THERE!");
        
        State(String description) {}
        
    }
    public State gameState;
    public ArrayList<Player> players = new ArrayList<>();
    public Map map = new Map();
    public Difficulty difficulty;
    
}
