package mapEditor;

import base.Action;
import base.Map;
import base.NButton;
import base.StateManager;
import mode.Mode;
import mode.ModeMessageDisplay;
import greenfoot.Actor;
import greenfoot.GreenfootImage;
import java.util.Collection;
import java.util.HashMap;
import territory.Continent;
import territory.Territory;
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
    private final HashMap<Territory, TerritoryDisplayer> territoryDisplayerMap
            = new HashMap<>();
    
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
        loadedMap.territories.anyChangeListener.add(onTerrAddedDeleted);
        loadedMap.territories.addAll(mapToLoad.territories);
        mapToLoad.continents.forEach(Continent::addToWorld);
        mapToLoad = null;
    
    }
    
    @Override
    public Map map() {return loadedMap;}

    @Override
    public void clearScene() {
        territoryDisplayerMap.values().forEach(TerritoryDisplayer::hide);
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
                
            default : 
                Selector.clear();
                Mode.setMode(Mode.DEFAULT);
            
        }
        
    }
    
    /////Private Methods///////////////////////
    
    private final Action LOAD_OPTIONS_MENU = () -> {
                if(Mode.mode() == Mode.DEFAULT){
                    clearScene();
                    world().load(new userPreferences.Manager(this));
                }};
    
    private final Action onTerrAddedDeleted = ()->{
        Collection<Territory> deleted = territoryDisplayerMap.keySet();
        deleted.removeAll(loadedMap.territories);
        Collection<Territory> added = loadedMap.territories;
        added.removeAll(territoryDisplayerMap.keySet());
        deleted.forEach((deletedTerr)->{
            territoryDisplayerMap.remove(deletedTerr);
        });
        added.forEach((territory)->{
            TerritoryControler terrControl = new TerritoryControler(territory);
            TerritoryDisplayer terrDisplay = new TerritoryDisplayer(territory,terrControl);
            territoryDisplayerMap.put(territory, terrDisplay);
            terrDisplay.show();
        });
        
        
    
    };
    
}
