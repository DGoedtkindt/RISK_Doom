package userPreferences;

import appearance.Theme;
import base.MyWorld;
import base.NButton;
import base.StateManager;
import basicChoosers.ThemeChooser;
import input.Form;
import base.BlankHex;

/**
 * StateManager that shows the Theme options.
 * 
 */
public class Manager extends StateManager{
    
    private final ThemeChooser THEME_CHOOSER = new ThemeChooser();
    private final NButton SAVE_SETTINGS = new NButton(() -> {applySettingsAndBack();}, "Apply Changes");
    private final StateManager PREVIOUS_MANAGER;
    
    /**
     * Creates a Manager
     * @param previousManager The last manager. We want to come back to this manager when the user quits the options. 
     *                        For example, we want the user to return to the game if he entered the options from the game.
     */
    public Manager(StateManager previousManager) {
        PREVIOUS_MANAGER = previousManager;
    }
    
    @Override
    public void setupScene() {
        world().makeSureSceneIsClear();
        THEME_CHOOSER.addToWorld(world().getWidth()/2, world().getHeight()/2);
        THEME_CHOOSER.setArrows(500, 40);
        world().addObject(SAVE_SETTINGS, world().getWidth()/2, 5*world().getHeight()/6);
    
    }
    
    /**
     * Method used to apply the chosen settings.
     */
    private void applySettingsAndBack() {
        int themeNum = Integer.parseInt(THEME_CHOOSER.choiceValue());
        Theme.used = Theme.values()[themeNum];
        MyWorld.theWorld.getBackground().setColor(Theme.used.backgroundColor);
        MyWorld.theWorld.getBackground().fill();
        BlankHex.updateAllImages();
        
        clearScene();
        world().load(PREVIOUS_MANAGER);
    
    }
    
    @Override
    public void clearScene() {
        THEME_CHOOSER.destroy();
        world().removeObject(SAVE_SETTINGS);

    }
    
    @Override
    public void escape() {
        Form.confirmInput("Do you want to return to what you were doing before?", (input)->{
            if(input.get("confirmation").equals("Yes")) {
                this.clearScene();
                MyWorld.theWorld.load(PREVIOUS_MANAGER);
            } 
        }); 
        
    }

}
