package userPreferences;

import appearance.Appearance;
import appearance.Theme;
import base.MyWorld;
import base.NButton;
import base.StateManager;
import basicChoosers.ThemeChooser;
import input.ChoiceInput;
import input.Form;
import input.Input;
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
        Form confirmForm = new Form();
        Input confirmInput = new ChoiceInput("Do you want to return to what you were doing before?", "Yes", "No");
        confirmForm.addInput("confirm", confirmInput, false);
        confirmForm.submitAction = (input)->{
            if(input.get("confirm") == "Yes") {
                this.clearScene();
                MyWorld.theWorld.load(this.previous);
            } 
        };
        confirmForm.addToWorld();
        
    }

}
