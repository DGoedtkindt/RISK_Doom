package game;

import base.Game;
import base.Map;
import base.StateManager;
import mainObjects.Player;

public class Manager extends StateManager{

    private Game gameToLoad = new Game();
    private Game loadedGame = new Game();
    
    public Manager(Game loadGame) {
        throw new UnsupportedOperationException("Not supported yet.");
    
    }
    
    public Game game() {return loadedGame;}
    public Map map() {return loadedGame.map;}

    /*public void updateScene(Player player){
    throw new UnsupportedOperationException("Not supported yet.");
    }*/
    
    @Override
    public void setupScene() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void clearScene() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void escape() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
