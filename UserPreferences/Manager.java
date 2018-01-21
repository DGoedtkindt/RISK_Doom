package userPreferences;

import appearance.Theme;
import basicChoosers.ThemeChooser;
import java.awt.event.ActionEvent;
import base.*;
import javax.swing.JOptionPane;
import mainObjects.BlankHex;

public class Manager extends StateManager{
    
    private ThemeChooser themeChooser = new ThemeChooser();
    private NButton saveSettings = new NButton((ActionEvent ae) -> {applySettingsAndBack();},
            "Apply Changes");
    private MyWorld world() {return MyWorld.theWorld;}
    private StateManager previous;
    
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
    
    private void applySettingsAndBack() {
        int themeNum = Integer.parseInt(themeChooser.currentChoice());
        Theme.used = Theme.values()[themeNum];
        MyWorld.theWorld.getBackground().setColor(Theme.used.backgroundColor.brighter());
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
        int choice = JOptionPane.showConfirmDialog(null, "Do you want to return to what you were doing before", 
                                                             "Yes", JOptionPane.YES_NO_CANCEL_OPTION);
            if(choice == JOptionPane.YES_OPTION){
                clearScene();
                world().load(new menu.Manager());

            }
    }

}
