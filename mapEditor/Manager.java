package mapEditor;

import appearance.Theme;
import mode.Mode;
import mode.ModeMessageDisplay;
import base.*;
import greenfoot.GreenfootImage;
import greenfoot.Actor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import mainObjects.Continent;
import mainObjects.Links;
import mainObjects.Territory;
import selector.Selector;

public class Manager extends StateManager{
    
    private Map loadedMap = new Map();
    private Map mapToLoad;
    private MyWorld world() {return MyWorld.theWorld;}
    
    private ControlPanel ctrlPanel;
    private ModeMessageDisplay modeDisp;
    private NButton options;
    
    public Manager(Map loadMap) {
        mapToLoad = loadMap;
        ctrlPanel = new ControlPanel();
        modeDisp = new ModeMessageDisplay();
        options = new NButton(loadOptionsMenu, "Options");
    
    }
    
    @Override
    public void setupScene() {
        Mode.setMode(Mode.MAP_EDITOR_DEFAULT);
        world().makeSureSceneIsClear();
        world().placeBlankHexs();
        ctrlPanel.addToWorld(world().getWidth()-100, 300);
        modeDisp.addToWorld(world().getWidth()-90, 850);
        world().addObject(Continent.display, 840, 960);
        world().addObject(options, world().getWidth()-120, 50);
        loadMap();

    }
    
    public void loadMap() {
        mapToLoad.territories.forEach(Territory::addToWorld);
        mapToLoad.continents.forEach(Continent::addToWorld);
        mapToLoad.links.forEach(Links::addToWorld);
        mapToLoad = null;
    
    }
    
    @Override
    public Map map() {return loadedMap;}

    @Override
    public void clearScene() {
        mapToLoad = loadedMap;
        loadedMap = new Map();
        
        ctrlPanel.removeFromWorld();
        modeDisp.removeFromWorld();
        
        world().removeObjects(world().getObjects(Actor.class));
        
    }
    
    @Override
    public void escape() {
        if(Mode.mode() == Mode.MAP_EDITOR_DEFAULT) {

            int choice = JOptionPane.showConfirmDialog(null, "Do you want to return to the main menu?", 
                                                             "Returning to the menu", JOptionPane.YES_NO_CANCEL_OPTION);
            if(choice == JOptionPane.YES_OPTION){
                clearScene();
                world().load(new menu.Manager());

            } 
        
        } else {
            Selector.clear();

            if(Links.newLinks != null){
                Links.newLinks.destroy();
                Links.newLinks = null;
            }

            Mode.setMode(Mode.MAP_EDITOR_DEFAULT);
            
        }
        
    }
    
    /////Private Methods///////////////////////
    
    private void paintBackgroundDarker() {
        GreenfootImage bckGrd = world().getBackground();
        bckGrd.setColor(Theme.used.backgroundColor.darker());
        bckGrd.fill();
    
    }
    
    private ActionListener loadOptionsMenu = (ActionEvent ae) -> {
                clearScene();
                world().load(new userPreferences.Manager(this));};

}
