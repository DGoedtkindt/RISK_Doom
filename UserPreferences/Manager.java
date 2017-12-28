package UserPreferences;

import appearance.Theme;
import basicChoosers.BasicChooser;
import basicChoosers.ThemeChoices;
import java.awt.event.ActionEvent;
import base.*;

public class Manager {
    
    private BasicChooser themeChooser = new BasicChooser(new ThemeChoices());
    private NButton saveSettings = new NButton((ActionEvent ae) -> {applySettingsAndToMainMenu();},
            "Apply Changes");
    private MyWorld world;
    
    public void setupScene(MyWorld toWorld) {
        world = toWorld;
        themeChooser.addToWorld(world, world.getWidth()/2, world.getHeight()/2);
        themeChooser.setArrows(500, 40);
        world.addObject(saveSettings, world.getWidth()/2, 5*world.getHeight()/6);
    
    }
    
    private void applySettingsAndToMainMenu() {
        int themeNum = Integer.parseInt(themeChooser.currentChoice());
        Theme.used = Theme.values()[themeNum];
        MyWorld.theWorld.getBackground().setColor(Theme.used.backgroundColor.brighter());
        MyWorld.theWorld.getBackground().fill();
        
        clearScene();
        world.mainMenu();
    
    }
    
    public void clearScene() {
        if(world != null) {
            themeChooser.destroy();
            world.removeObject(saveSettings);
        
        }

    }

}
