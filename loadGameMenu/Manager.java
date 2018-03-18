package loadGameMenu;

import appearance.Appearance;
import appearance.MessageDisplayer;
import base.Action;
import base.Game;
import base.NButton;
import base.StateManager;
import xmlChoosers.GameChooser;

/**
 * This StateManager manages the Game loading Menu.
 * 
 */
public class Manager extends StateManager {
    
    private GameChooser gameC;
    private NButton playGameB;

    /**
     * Creates a Manager.
     */
    public Manager() {
        this.playGameB = new NButton(playSelectedGame, "Play Game");
        this.gameC = new GameChooser();
    }

    @Override
    public void setupScene() {
        gameC.addToWorld(world().getWidth() / 2, world().getHeight() / 2 );
        world().addObject(playGameB, world().getWidth()/2, 3 * world().getHeight() / 4);
        
    }

    @Override
    public void clearScene() {
        gameC.destroy();
        world().removeObject(playGameB);
    }

    @Override
    public void escape() {
        standardBackToMenu("Do you want to return to the main menu?");
    }

    private Action playSelectedGame = () -> {
        try{
            Game game = gameC.getSelectedGame();
            game.Manager newManager = new game.Manager(game);
            world().load(newManager);

        } catch(Exception ex) {
            String message = "Cannot play selected Game";
            MessageDisplayer.showException(new Exception(message, ex));

        }

    };

}
