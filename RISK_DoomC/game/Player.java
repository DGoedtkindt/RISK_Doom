package game;

/**
 * Contains infos about a player should not have any methods normally
 */
public class Player implements Visitable{
    
    protected Player() {
        GameSaver.visitableList.add(this);
    
    }
    @Override
    public void accept(GameSaver gs) {
        gs.visit(this);
    }
    
}
