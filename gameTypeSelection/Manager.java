package gameTypeSelection;

import base.NButton;
import base.StateManager;

/**
 * The Manager that manages the Game type selection Menu.
 * 
 */
public class Manager extends StateManager{

    //Buttons in game menu
    private final NButton NEW_GAME_BUTTON   = new NButton(() -> {newGameMenu();}  , "New Game");
    private final NButton LOAD_GAME_BUTTON  = new NButton(() -> {loadGameMenu();} , "Load Game");
    
    /**
     * Launches the New Game Menu.
     */
    private void newGameMenu() {
        clearScene();
        world().load(new newGameMenu.Manager());
    
    }
    
    /**
     * Launches the Load Game Menu.
     */
    private void loadGameMenu() {
        clearScene();
        world().load(new loadGameMenu.Manager());
    
    }
    
    @Override
    public void setupScene() {
        world().makeSureSceneIsClear();
        world().addObject(NEW_GAME_BUTTON, world().getWidth() / 3, world().getHeight() / 2);
        world().addObject(LOAD_GAME_BUTTON, 2 * world().getWidth() / 3, world().getHeight() / 2);
        
    }

    @Override
    public void clearScene() {
        world().removeObject(NEW_GAME_BUTTON);
        world().removeObject(LOAD_GAME_BUTTON);
    }

    @Override
    public void escape() {
        standardBackToMenu("Do you want to return to the main menu?");
        
    }
    
}
