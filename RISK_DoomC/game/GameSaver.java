package game;
import java.util.ArrayList;

public class GameSaver {
    protected static ArrayList<Visitable> visitableList = new ArrayList<>();
    
    protected GameSaver(String saveName) {}
    
    /** Must visit the visitable classes and create a GameState from it.
     * then Create a GameXML from the already Made GameXML and the just-made
     * GameState.
     * Finally it must write the GameXML with the correct name
     * @param saveName
     */
    protected void saveGame(String saveName) {
    
    
    }
    
    protected void visit(Player p) {}
    protected void visit(GameScene gs) {}
    protected void visit(Territory terr) {}
    
}
