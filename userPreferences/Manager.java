package userPreferences;

import appearance.Appearance;
import appearance.InputPanel;
import appearance.Theme;
import base.MyWorld;
import base.NButton;
import base.StateManager;
import basicChoosers.ThemeChooser;
import mainObjects.BlankHex;

/**
 * StateManager that shows the Theme options.
 * 
 */
public class Manager extends StateManager{
    
    private ThemeChooser themeChooser = new ThemeChooser();
    private NButton saveSettings = new NButton(() -> {applySettingsAndBack();},
            "Apply Changes");
    private StateManager previous;
    
    /**
     * Creates a Manager
     * @param previousManager The last manager. We want to come back to this manager when the user quits the options. 
     *                        For example, we want the user to return to the game if he entered the options from the game.
     */
    public Manager(StateManager previousManager) {
        previous = previousManager;
    }
    
    @Override
    public void setupScene() {
        world().makeSureSceneIsClear();
        themeChooser.addToWorld(world().getWidth()/2, world().getHeight()/2);
        themeChooser.setArrows(500, 40);
        world().addObject(saveSettings, world().getWidth()/2, 5*world().getHeight()/6);
    
    }
    
    /**
     * Method used to apply the chosen settings.
     */
    private void applySettingsAndBack() {
        int themeNum = Integer.parseInt(themeChooser.currentChoice());
        Theme.used = Theme.values()[themeNum];
        MyWorld.theWorld.getBackground().setColor(Theme.used.backgroundColor);
        MyWorld.theWorld.getBackground().fill();
        BlankHex.updateAllImages();
        
        clearScene();
        world().load(previous);
    
    }
    
    @Override
    public void clearScene() {
        themeChooser.destroy();
        world().removeObject(saveSettings);

    }
    
    @Override
    public void escape() {
        InputPanel.showConfirmPanel("Do you want to return to what you were doing before?", 100, "escape", this, Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 2);
        
    }

    @Override
    public void useInformations(String information, String type) throws Exception {
        
        if(type.equals("escape")){
            
            if(information.equals(InputPanel.YES_OPTION)){
                MyWorld.theWorld.load(((userPreferences.Manager)this).previous);

            }
            
        }
        
    }

}
