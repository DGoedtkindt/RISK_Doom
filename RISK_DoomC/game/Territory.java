package game;

/**
 *  stores all infos of a Territory
 */
public class Territory implements Visitable{
    protected TerritoryDrawer drawer;

    @Override
    public void accept(GameSaver gs) {
        gs.visit(this);
    }
    
}
