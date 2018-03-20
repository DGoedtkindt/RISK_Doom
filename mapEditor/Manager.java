package mapEditor;

import base.Action;
import base.Map;
import base.NButton;
import base.StateManager;
import mode.Mode;
import mode.ModeMessageDisplay;
import greenfoot.Actor;
import greenfoot.GreenfootImage;
import mainObjects.Continent;
import mainObjects.LinkIndic;
import mainObjects.Links;
import mainObjects.Territory;
import selector.Selector;

/**
 * This Manager manages the Map Editor.
 * 
 */
public class Manager extends StateManager{
    
    private Map loadedMap = new Map();
    private Map mapToLoad;
    
    private final ControlPanel CONTROL_PANEL;
    private final ModeMessageDisplay MODE_MESSAGE_DISPLAY;
    private final NButton OPTIONS_BUTTON;
    
    /**
     * Creates a Map Editor.
     * @param loadMap The edited map.
     */
    public Manager(Map loadMap) {
        mapToLoad = loadMap;
        CONTROL_PANEL = new ControlPanel(this);
        MODE_MESSAGE_DISPLAY = new ModeMessageDisplay();
        OPTIONS_BUTTON = new NButton(LOAD_OPTIONS_MENU, new GreenfootImage("settings.png"), 30, 30);
    
    }
    
    @Override
    public void setupScene() {
        world().makeSureSceneIsClear();
        world().placeBlankHexs();
        CONTROL_PANEL.addToWorld(world().getWidth()-100, 300);
        MODE_MESSAGE_DISPLAY.addToWorld(world().getWidth()-90, 850);
        world().addObject(Continent.DISPLAY, 840, 960);
        world().addObject(OPTIONS_BUTTON, world().getWidth() - 60, 30);
        loadMap();
        Mode.setMode(Mode.DEFAULT);

    }
    
    /**
     * Loads the Map and adds its elements to the World.
     */
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
        
        CONTROL_PANEL.removeFromWorld();
        MODE_MESSAGE_DISPLAY.removeFromWorld();
        
        world().removeObjects(world().getObjects(Actor.class));
        
    }
    
    @Override
    public void escape() {
        
        switch(Mode.mode()){
            case DEFAULT : 
                standardBackToMenu("Do you want to return to the main menu?");
                break;
                
            case ACTION_ON_LINK :
                world().removeObject(LinkIndic.destroyLink);
                world().removeObject(LinkIndic.destroySpot);
                world().removeObject(LinkIndic.extendLink);
                world().removeObject(LinkIndic.nothing);
                Mode.setMode(Mode.DEFAULT);
                break;
                
            default : 
                Selector.clear();

                if(Links.newLinks != null){
                    Links.newLinks.destroy();
                    Links.newLinks = null;
                }
                Mode.setMode(Mode.DEFAULT);
            
        }
        
        Territory.resetSourceAndTarget();
        
    }
    
    /////Private Methods///////////////////////
    
    private final Action LOAD_OPTIONS_MENU = () -> {
                if(Mode.mode() == Mode.DEFAULT){
                    clearScene();
                    world().load(new userPreferences.Manager(this));
                }};
    
}
