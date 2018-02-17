package gameTypeSelection;

import base.NButton;
import base.StateManager;
import javax.swing.JOptionPane;

public class Manager extends StateManager{

    //Buttons in game menu
    private NButton newGameButton            = new NButton(() -> {newGameMenu();}  , "New Game");
    private NButton loadGameButton           = new NButton(() -> {loadGameMenu();} , "Load Game");
    
    private void newGameMenu() {
        clearScene();
        world().load(new newGameMenu.Manager());
    
    }
    
    private void loadGameMenu() {
        clearScene();
        throw new UnsupportedOperationException("There is no loadGameMenu Manager yet");
    
    }
    
    @Override
    public void setupScene() {
        world().makeSureSceneIsClear();
        world().addObject(newGameButton, world().getWidth() / 3, world().getHeight() / 2);
        world().addObject(loadGameButton, 2 * world().getWidth() / 3, world().getHeight() / 2);
        
    }

    @Override
    public void clearScene() {
        world().removeObject(newGameButton);
        world().removeObject(loadGameButton);
    }

    @Override
    public void escape() {
        int choice = JOptionPane.showConfirmDialog(null, "Do you want to return to the main menu?",
                                                   "Returning to the menu", JOptionPane.YES_NO_CANCEL_OPTION);
        if(choice == JOptionPane.YES_OPTION){
            world().load(new menu.Manager());
        }
    }
    
}
