package loadGameMenu;

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
    private final NButton PLAY_GAME_BUTTON;

    /**
     * Creates a Manager.
     */
    public Manager() {
        PLAY_GAME_BUTTON = new NButton(PLAY_SELECTED_GAME, "Play Game");
        gameC = new GameChooser();
    }

    @Override
    public void setupScene() {
        gameC.addToWorld(world().getWidth() / 2, world().getHeight() / 2 );
        world().addObject(PLAY_GAME_BUTTON, world().getWidth()/2, 3 * world().getHeight() / 4);
        
    }

    @Override
    public void clearScene() {
        gameC.destroy();
        world().removeObject(PLAY_GAME_BUTTON);
    }

    @Override
    public void escape() {
        standardBackToMenu("Do you want to return to the main menu?");
    }

    private final Action PLAY_SELECTED_GAME = () -> {
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
