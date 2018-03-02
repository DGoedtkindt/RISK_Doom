package menu;

import base.MyWorld;
import base.NButton;
import base.StateManager;
import greenfoot.Actor;
import greenfoot.GreenfootImage;
import javax.swing.JOptionPane;

public class Manager extends StateManager {
    
    //Buttons in the main menu
    private NButton playGameButton          = new NButton(() -> {world().load(new gameTypeSelection.Manager());}     , "Play");
    private NButton mapEditorButton         = new NButton(() -> {mapEditorMenu();}, "Map Editor");
    private NButton optionsButton           = new NButton(() -> {optionsMenu();}  , "Options");
    private NButton rulesButton             = new NButton(() -> {rulesMenu();}, "Rules");
    
    @Override
    public void setupScene() {
        world().makeSureSceneIsClear();
        drawPineapple();
        world().addObject(playGameButton, world().getWidth() / 2, world().getHeight() / 2);
        world().addObject(mapEditorButton, world().getWidth() / 4, world().getHeight() / 2);
        world().addObject(optionsButton, 3 * world().getWidth() / 4, world().getHeight() / 2);
        world().addObject(rulesButton, world().getWidth() / 2, 2*world().getHeight()/3);
    }

    @Override
    public void clearScene() {
        world().makeSureSceneIsClear();
        
    }
    
    private void drawPineapple() {
        GreenfootImage pineapple = new GreenfootImage("TheMightyPineappleOfJustice.png");
        pineapple.scale(400, 150);
        world().getBackground().drawImage(pineapple, 75, 75);
    
    }
    
    private void mapEditorMenu() {
        clearScene();
        world().load(new mapEditorMenu.Manager());
    
    }
    
    private void optionsMenu() {
        clearScene();
        world().load(new userPreferences.Manager(this));
    
    }
    
    @Override
    public void escape() {
        int choice = JOptionPane.showConfirmDialog(null, "Do you really want to quit the game?",
                                                   "Quit game", JOptionPane.YES_NO_CANCEL_OPTION);
        if(choice == JOptionPane.YES_OPTION){
            System.exit(0);
        }
        
    }

    private void rulesMenu() {
        clearScene();
        world().load(new rules.Manager());
    }

}
