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
    
    private final NButton PLAY_GAME_BUTTON    = new NButton(() -> {gameTypeSelectionMenu();}, "Play");
    private final NButton MAP_EDITOR_BUTTON   = new NButton(() -> {mapEditorMenu();}, "Map Editor");
    private final NButton OPTIONS_BUTTON      = new NButton(() -> {optionsMenu();}, "Options");
    private final NButton RULES_BUTTON        = new NButton(() -> {rulesMenu();}, "Rules");
    
    @Override
    public void setupScene() {
        world().makeSureSceneIsClear();
        drawPineapple();
        world().addObject(PLAY_GAME_BUTTON, world().getWidth() / 2, world().getHeight() / 2);
        world().addObject(MAP_EDITOR_BUTTON, world().getWidth() / 4, world().getHeight() / 2);
        world().addObject(OPTIONS_BUTTON, 3 * world().getWidth() / 4, world().getHeight() / 2);
        world().addObject(RULES_BUTTON, world().getWidth() / 2, 2*world().getHeight()/3);
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
    
    /**
     * Launches the Game type selection Menu.
     */
    private void gameTypeSelectionMenu() {
        clearScene();
        world().load(new gameTypeSelection.Manager());
    
    }
    
    @Override
    public void escape() {
        Form.confirmInput("Do you want to quit the Game?", (input)->{
            if(input.get("confirmation").equals("Yes")) {
                System.exit(0);
            } 
        });
        
    }

}
