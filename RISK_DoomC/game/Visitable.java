package game;

import java.util.ArrayList;
/**
 * Contains the infos for a GameState
 * Every Visitable should add itself to the GameSaver.visitableList
 */
public interface Visitable {
    //for the visitor pattern
    void accept(GameSaver gs);
    
}
