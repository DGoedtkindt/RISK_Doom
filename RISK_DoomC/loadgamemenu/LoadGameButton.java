package loadgamemenu;

import basepackage.Button;

public class LoadGameButton extends Button{
    private GameChooser linkedGameChooser;
    
    protected LoadGameButton(GameChooser gc) {
        this.linkedGameChooser = gc;
    
    }
    
    /**Gets the game name from the GameChooser
     * creates a GameXML from it
     * passes the GameXML to the game.GameScene to start the game
     */
    @Override
    protected void clicked() {}
    
}
