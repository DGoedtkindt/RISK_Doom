package mapEditor;

import appearance.Theme;
import mode.Mode;
import base.*;
import greenfoot.GreenfootImage;
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
    
    public Manager(Map loadMap) {
        mapToLoad = loadMap;
        this.ctrlPanel = new ControlPanel();
    
    }
    
    @Override
    public void setupScene() {
        Mode.setMode(Mode.MAP_EDITOR_DEFAULT);
        world().makeSureSceneIsClear();
        world().placeBlankHexs();
        ctrlPanel.addToWorld(world().getWidth()-100, 300);
        world().addObject(Mode.display, world().getWidth()-90, 850);
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
    
    private void paintBackgroundDarker() {
        GreenfootImage bckGrd = world().getBackground();
        bckGrd.setColor(Theme.used.backgroundColor.darker());
        bckGrd.fill();
    
    }

    @Override
    public void clearScene() {
        mapToLoad = loadedMap;
        loadedMap = new Map();
        
        world().removeObject(world().getObjects(null));
        
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void escape() {
        if(Mode.mode() == Mode.MAP_EDITOR_DEFAULT) {

            int choice = JOptionPane.showConfirmDialog(null, "Do you want to return to the main menu?", 
                                                             "Returning to the menu", JOptionPane.YES_NO_CANCEL_OPTION);
            if(choice == JOptionPane.YES_OPTION){
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

}
