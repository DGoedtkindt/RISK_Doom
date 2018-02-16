package base;

import game.Difficulty;
import java.util.ArrayList;
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
    
    public State gameState = State.INITIALISATION;
    public ArrayList<Player> players = new ArrayList<>();
    public Map map = new Map();
    public Difficulty difficulty;
    
}
