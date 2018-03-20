package menu;

import base.NButton;
import base.StateManager;
import greenfoot.GreenfootImage;
import input.Form;

/**
 * This Manager creates the Main Menu.
 * 
 */
public class Manager extends StateManager {
    
    private NButton playGameButton           = new NButton(() -> {world().load(new gameTypeSelection.Manager());}, "Play");
    private NButton mapEditorButton          = new NButton(() -> {mapEditorMenu();}, "Map Editor");
    private NButton optionsButton            = new NButton(() -> {optionsMenu();}, "Options");
    private NButton rulesButton              = new NButton(() -> {rulesMenu();}, "Rules");
    
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
    
    /**
     * Draws the Mighty Pineapple of Justice on the Background.
     */
    private void drawPineapple() {
        GreenfootImage pineapple = new GreenfootImage("TheMightyPineappleOfJustice.png");
        pineapple.scale(400, 150);
        world().getBackground().drawImage(pineapple, 75, 75);
    
    }
    
    /**
     * Launches the Menu of the Map editor.
     */
    private void mapEditorMenu() {
        clearScene();
        world().load(new mapEditorMenu.Manager());
    
    }
    
    /**
     * Launches the Options.
     */
    private void optionsMenu() {
        clearScene();
        world().load(new userPreferences.Manager(this));
    
    }
    
    /**
     * Launches the Rules.
     */
    private void rulesMenu() {
        clearScene();
        world().load(new rules.Manager());
    
    }
    
    @Override
    public void escape() {
        Form.confirmInput("Do you want to quit the Game?", (input)->{
            if(input.get("confirmation") == "Yes") {
                System.exit(0);
            } 
        });
        
    }

}
