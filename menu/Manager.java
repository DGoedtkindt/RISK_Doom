package menu;

import appearance.Appearance;
import appearance.Theme;
import base.NButton;
import base.StateManager;
import greenfoot.Font;
import greenfoot.GreenfootImage;
import input.Form;
import java.awt.FontMetrics;

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
        drawName();
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
     * Draws the Mighty Pineapple of Justice on the background. It's a joke.
     */
    private void drawPineapple() {
        GreenfootImage pineapple = new GreenfootImage("TheMightyPineappleOfJustice.png");
        pineapple.scale(400, 150);
        world().getBackground().drawImage(pineapple, 75, 75);
    
    }
    
    /**
     * Draws the name of the Game on the background.
     */
    private void drawName() {
        GreenfootImage name = new GreenfootImage(1, 1);
        FontMetrics fm = name.getAwtImage().getGraphics().getFontMetrics(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 80));
        
        int width = fm.stringWidth("RISK DOOM") + 2;
        int height = fm.getMaxAscent() + fm.getMaxDescent() + 2;
        
        name.scale(width, height);
        name.setColor(Theme.used.backgroundColor);
        name.fill();
        name.setFont(new Font("monospaced", true, false, 80));
        name.setColor(Theme.used.textColor);
        name.drawString("RISK DOOM", 1, height - 1);
        
        world().getBackground().drawImage(name, (Appearance.WORLD_WIDTH - name.getWidth()) / 2, Appearance.WORLD_HEIGHT / 4);
        
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
