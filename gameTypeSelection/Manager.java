package gameTypeSelection;

import appearance.Appearance;
import base.MyWorld;
import base.NButton;
import base.StateManager;
import input.InputPanel;

/**
 * The Manager that manages the Game type selection Menu.
 * 
 */
public class Manager extends StateManager{

    //Buttons in game menu
    private NButton newGameButton            = new NButton(() -> {newGameMenu();}  , "New Game");
    private NButton loadGameButton           = new NButton(() -> {loadGameMenu();} , "Load Game");
    
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
        
        if(InputPanel.usedPanel == null){
            InputPanel.showConfirmPanel("Do you want to return to the main Menu?", 100, "escape", this, Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 2);
        }else{
            InputPanel.usedPanel.destroy();
        }
        
    }

    @Override
    public void useInformations(String information, String type) throws Exception {
        
        if(type.equals("escape")){
            
            if(information.equals(InputPanel.YES_OPTION)){
                MyWorld.theWorld.load(new menu.Manager());

            }
            
        }
        
    }
    
}
