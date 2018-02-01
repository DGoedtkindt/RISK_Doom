package game;

import base.Game;
import base.Map;

public class Manager {

    private Game gameToLoad = new Game();
    private Game loadedGame = new Game();
    
    public Manager(Game loadGame) {
        throw new UnsupportedOperationException("Not supported yet.");
    
    }
    
    public Game game() {return loadedGame;}
    public Map map() {return loadedGame.map;}
    
}
