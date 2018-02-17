package game;

import base.Action;
import base.Game;
import base.Map;
import base.NButton;
import base.StateManager;
import greenfoot.Actor;
import greenfoot.Greenfoot;
import javax.swing.JOptionPane;
import mainObjects.Continent;
import mainObjects.Links;
import mainObjects.Player;
import mainObjects.Territory;
import mode.Mode;
import mode.ModeMessageDisplay;
import selector.Selector;

public class Manager extends StateManager{
    
    private static final int STARTING_TERRITORIES = 4;
    
    private Game gameToLoad = new Game();
    private Game loadedGame = new Game();
    
    private ControlPanel ctrlPanel;
    private ModeMessageDisplay modeDisp;
    private NButton options;
    
    public Manager(Game loadGame) {
        this.ctrlPanel = new ControlPanel(this);
        this.modeDisp = new ModeMessageDisplay();
        this.options = new NButton(loadOptionsMenu, "Options");
        this.gameToLoad = loadGame;
        
    }
    
    @Override
    public Game game() {return loadedGame;}
    @Override
    public Map map() {return loadedGame.map;}
    
    @Override
    public void setupScene() {
        Mode.setMode(Mode.GAME_DEFAULT);
        world().makeSureSceneIsClear();
        world().placeBlankHexs();
        ctrlPanel.addToWorld(world().getWidth() - 100, 300);
        modeDisp.addToWorld(world().getWidth() - 90, 850);
        world().addObject(Continent.display, 840, 960);
        world().addObject(options, world().getWidth() - 120, 50);
        loadGame();
        for(Territory t : loadedGame.map.territories){
            t.drawTerritory();
        }
    }
    
    private void loadGame(){
        loadMap();
        loadedGame = gameToLoad;
        
        if(loadedGame.gameState == Game.State.INITIALISATION){
            start();
            loadedGame.gameState = Game.State.INGAME;
        }
        
    }
    
    private void loadMap(){
        
        gameToLoad.map.territories.forEach(Territory::addToWorld);
        gameToLoad.map.continents.forEach(Continent::addToWorld);
        gameToLoad.map.links.forEach(Links::addToWorld);
        
    }
    
    @Override
    public void clearScene() {
        
        gameToLoad = loadedGame;
        loadedGame = new Game();
        
        ctrlPanel.removeFromWorld();
        modeDisp.removeFromWorld();
        
        world().removeObjects(world().getObjects(Actor.class));
        
    }
    
    @Override
    public void escape() {
        if(Mode.mode() == Mode.GAME_DEFAULT) {

            int choice = JOptionPane.showConfirmDialog(null, "Do you want to return to the main menu?", 
                                                             "Returning to the menu", JOptionPane.YES_NO_CANCEL_OPTION);
            if(choice == JOptionPane.YES_OPTION){
                clearScene();
                world().load(new menu.Manager());

            } 
        
        } else {
            Selector.clear();
            Mode.setMode(Mode.GAME_DEFAULT);
            
        }
    }
    
    private void giveTerritoriesRandomly(){
        
        for(Player p : loadedGame.players){
            
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
    
    public void start(){
        
        giveTerritoriesRandomly();
        Turn.startNewTurn();
        
    }
    
    protected Action saveGame = () -> {
        (new GameSaver(game())).saveGame();
    
    };
    
    private Action loadOptionsMenu = () -> {
                if(Mode.mode() == Mode.GAME_DEFAULT){
                    clearScene();
                    world().load(new userPreferences.Manager(this));
                }};
    
}
