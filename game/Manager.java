package game;

import base.Action;
import base.Game;
import base.Map;
import base.NButton;
import base.StateManager;
import greenfoot.Greenfoot;
import mainObjects.Continent;
import mainObjects.Links;
import mainObjects.Player;
import mainObjects.Territory;
import mode.Mode;
import mode.ModeMessageDisplay;

public class Manager extends StateManager{

    private static final int STARTING_TERRITORIES = 1;
    
    private Game gameToLoad = new Game();
    private Game loadedGame = new Game();
    
    private ControlPanel ctrlPanel;
    private ModeMessageDisplay modeDisp;
    private NButton options;
    
    public Manager(Game loadGame) {
        this.ctrlPanel = new ControlPanel();
        this.modeDisp = new ModeMessageDisplay();
        this.options = new NButton(loadOptionsMenu, "Options");
        gameToLoad = loadGame;
        
    }
    
    public Game game() {return loadedGame;}
    public Map map() {return loadedGame.map;}

    /*public void updateScene(Player player){
    }*/
    
    @Override
    public void setupScene() {
        Mode.setMode(Mode.GAME_DEFAULT);
        world().makeSureSceneIsClear();
        world().placeBlankHexs();
        ctrlPanel.addToWorld(world().getWidth()-100, 300);
        modeDisp.addToWorld(world().getWidth()-90, 850);
        world().addObject(Continent.display, 840, 960);
        world().addObject(options, world().getWidth()-120, 50);
        loadGame();
        
    }
    
    private void loadGame(){
        loadMap();
        loadedGame = gameToLoad;
        start();
    
    }
    
    private void loadMap(){
        
        gameToLoad.map.territories.forEach(Territory::addToWorld);
        gameToLoad.map.continents.forEach(Continent::addToWorld);
        gameToLoad.map.links.forEach(Links::addToWorld);
        
    }
    
    @Override
    public void clearScene() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void escape() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private void giveTerritoriesRandomly(){
        
        for(Player p : loadedGame.players){
            
            if(!p.name().equals(Player.ZOMBIE_NAME)){
                
                int terrNumber = 0;
                Territory terrToAttribute;

                while(terrNumber < STARTING_TERRITORIES){

                    terrToAttribute = loadedGame.map.territories.get(Greenfoot.getRandomNumber(loadedGame.map.territories.size()));

                    if(terrToAttribute.owner() == null){
                        terrToAttribute.setOwner(p);
                        terrNumber++;
                    }

                }
                
            }
            
        }
        
    }
    
    public void start(){
        
        giveTerritoriesRandomly();
        Turn.start();
        
    }
    
    private Action loadOptionsMenu = () -> {
                clearScene();
                world().load(new userPreferences.Manager(this));};
    
}
