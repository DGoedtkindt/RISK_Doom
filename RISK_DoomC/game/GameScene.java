package game;

import greenfoot.World;
import basepackage.GameXML;
import basepackage.Difficulty;


public class GameScene extends World implements Visitable{
    protected Difficulty difficulty;
    
    /**create the game scene from a GameXML
     * @param gameXML
     */
    public GameScene(GameXML gameXML) {
        super(1920,1080,1);
        GameSaver.visitableList.add(this);
        
    }

    @Override
    public void accept(GameSaver gs) {
        gs.visit(this);
        
    }
    
}
