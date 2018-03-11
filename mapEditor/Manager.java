package mapEditor;

//<editor-fold defaultstate="collapsed" desc="Imports">
import appearance.Appearance;
import appearance.InputPanel;
import base.Action;
import base.Map;
import base.MyWorld;
import base.NButton;
import base.StateManager;
import mode.Mode;
import mode.ModeMessageDisplay;
import greenfoot.Actor;
import greenfoot.GreenfootImage;
import mainObjects.Continent;
import mainObjects.Links;
import mainObjects.Territory;
import selector.Selector;
//</editor-fold>

/**
 * This Manager manages the Map Editor.
 * 
 */
public class Manager extends StateManager{
    
    private Map loadedMap = new Map();
    private Map mapToLoad;
    
    private ControlPanel ctrlPanel;
    private ModeMessageDisplay modeDisp;
    private NButton options;
    
    /**
     * Creates a Map Editor.
     * @param loadMap The edited map.
     */
    public Manager(Map loadMap) {
        mapToLoad = loadMap;
        ctrlPanel = new ControlPanel(this);
        modeDisp = new ModeMessageDisplay();
        options = new NButton(loadOptionsMenu, new GreenfootImage("settings.png"), 30, 30);
    
    }
    
    @Override
    public void setupScene() {
        world().makeSureSceneIsClear();
        world().placeBlankHexs();
        ctrlPanel.addToWorld(world().getWidth()-100, 300);
        modeDisp.addToWorld(world().getWidth()-90, 850);
        world().addObject(Continent.display, 840, 960);
        world().addObject(options, world().getWidth() - 60, 30);
        loadMap();
        Mode.setMode(Mode.MAP_EDITOR_DEFAULT);

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
        
        ctrlPanel.removeFromWorld();
        modeDisp.removeFromWorld();
        
        world().removeObjects(world().getObjects(Actor.class));
        
    }
    
    @Override
    public void escape() {
        if(Mode.mode() == Mode.MAP_EDITOR_DEFAULT) {
            InputPanel.showConfirmPanel("Do you want to return to the main Menu?", 100, "escape", this, Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 2);
        
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
    
    private Action loadOptionsMenu = () -> {
                if(Mode.mode() == Mode.MAP_EDITOR_DEFAULT){
                    clearScene();
                    world().load(new userPreferences.Manager(this));
                }};

    @Override
    public void useInformations(String information, String type) throws Exception {
        
        if(type.equals("escape")){
            
            if(information.equals(InputPanel.YES_OPTION)){
                MyWorld.theWorld.load(new menu.Manager());

            }
            
        }
        
    }
    
}
