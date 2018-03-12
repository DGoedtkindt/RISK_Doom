package game;

import appearance.Appearance;
import appearance.InputPanel;
import appearance.MessageDisplayer;
import base.Action;
import base.Game;
import base.Map;
import base.MyWorld;
import base.NButton;
import base.StateManager;
import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import java.util.ArrayList;
import mainObjects.Continent;
import mainObjects.Links;
import mainObjects.Territory;
import mode.Mode;
import mode.ModeMessageDisplay;
import selector.Selector;

/**
 * The Manager that manages the Game.
 * 
 */
public class Manager extends StateManager{
    
    private static final int STARTING_TERRITORIES = 4;
    
    private Game gameToLoad = new Game();
    private Game loadedGame = new Game();
    
    private ControlPanel ctrlPanel;
    private ModeMessageDisplay modeDisp;
    private NButton options;
    
    /** 
     * Creates a new Manager that will allow to play a certain Game when 
     * setupScene() is called.
     * 
     * @param loadGame the game that will be loaded.
     */
    public Manager(Game loadGame) {
        this.ctrlPanel = new ControlPanel(this);
        this.modeDisp = new ModeMessageDisplay();
        GreenfootImage settings = new GreenfootImage("settings.png");
        this.options = new NButton(loadOptionsMenu, settings, 30,30);
        this.gameToLoad = loadGame;
        
    }
    
    @Override
    public Game game() {return loadedGame;}
    @Override
    public Map map() {return loadedGame.map;}
    
    @Override
    public void setupScene() {
        Mode.setMode(Mode.GAME_DEFAULT);
        world().placeBlankHexs();
        ctrlPanel.addToWorld(world().getWidth() - 100, 300);
        modeDisp.addToWorld(world().getWidth() - 90, 850);
        world().addObject(Continent.display, 840, 960);
        world().addObject(options, world().getWidth() - 60, 30);
        loadGame();
    }
    
    /** 
     * Generates a Map and initiates a Game.
     */
    private void loadGame(){
        loadMap();
        loadedGame = gameToLoad;
        
        if(loadedGame.gameState != null)switch (loadedGame.gameState) {
            case INITIALISATION:
                try{
                    giveTerritoriesRandomly();
                    startNewTurn();
                    loadedGame.gameState = Game.State.INGAME;
                }catch(Exception e){
                    world().load(new menu.Manager());
                    MessageDisplayer.showException(e);
                }
                break;
            case INGAME:
                startNewTurn();
                break;
            case FINISHED:
                //do nothing yet. should maybe show the stats and the EndGamePanel
                break;
            default:
                break;
        } 
        
    }
    
    /** 
     * Generates a Map.
     */
    private void loadMap(){
        
        gameToLoad.map.territories.forEach(Territory::addToWorld);
        gameToLoad.map.continents.forEach(Continent::addToWorld);
        gameToLoad.map.links.forEach(Links::addToWorld);
        
    }
    
    /** 
     * Starts the first Turn of the Game.
     */
    private void startNewTurn() {
        int turnNumber = loadedGame.stats.size();
        Turn.startNewTurn(turnNumber);
    
    }
    
    @Override
    public void clearScene() {
        //Put the active player's armiesInHand to negative. little hack to prevent
        //the player to get the armies multiple times by going to the options and 
        //back
        Player currentPlayer = Turn.currentTurn.player;
        currentPlayer.addArmiesToHand(-currentPlayer.armyGainPerTurn());
        //
        
        Turn.interruptCurrentTurn();
        
        gameToLoad = loadedGame;
        loadedGame = new Game();
        
        ctrlPanel.removeFromWorld();
        modeDisp.removeFromWorld();
        
        world().removeObjects(world().getObjects(Actor.class));
        
    }
    
    @Override
    public void escape() {
        if(Mode.mode() == Mode.GAME_DEFAULT) {
            if(InputPanel.usedPanel == null){
                InputPanel.showConfirmPanel("Do you want to return to the main menu?", 100, "escape", this, Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 2);
            }else{
                InputPanel.usedPanel.destroy();
            }
        
        } else {
            Selector.clear();
            Mode.setMode(Mode.GAME_DEFAULT);
            
        }
        
        if(Territory.actionSource == null || Territory.actionTarget == null){
           Territory.resetSourceAndTarget(); 
        }
        
    }
    
    /** 
     * Gives random Territories to the Players.
     */
    private void giveTerritoriesRandomly() throws Exception{
        
        ArrayList<Territory> capitals = new ArrayList<Territory>();
        ArrayList<Territory> nonCapitals = new ArrayList<Territory>();
        
        for(Territory t : map().territories){
            
            if(t.bonus() == 0){
                nonCapitals.add(t);
            }else{
                capitals.add(t);
            }
            
        }
        
        if(capitals.size() < game().players.size()){
            throw new Exception("The Map doesn't have enough capitals for " + (game().players.size() - 1) + " Players.");
        }
        
        if(nonCapitals.size() < (STARTING_TERRITORIES - 1) * game().players.size()){
            throw new Exception("The Map doesn't have enough Territories for " + (game().players.size() - 1) + " Players.");
        }
        
        
        for(Player playerToGiveTerrs : game().players){
            
            int randomCapital = Greenfoot.getRandomNumber(capitals.size());
            Territory givenCapital = capitals.get(randomCapital);
            
            givenCapital.setOwner(playerToGiveTerrs);
            capitals.remove(givenCapital);
            
            int terrNumber = 1;
            
            while(terrNumber < STARTING_TERRITORIES){
                
                int randomTerritory = Greenfoot.getRandomNumber(nonCapitals.size());
                Territory givenTerritory = nonCapitals.get(randomTerritory);
                
                givenTerritory.setOwner(playerToGiveTerrs);
                nonCapitals.remove(givenTerritory);
                
                terrNumber++;
                
            }
            
            playerToGiveTerrs.updateCapital();
            
        }
        
    }
    
    /**
     * Ends the Game.
     */
    public void end(){
        new EndGamePanel().show();
    }

    private Action loadOptionsMenu = () -> {
        if(Mode.mode() == Mode.GAME_DEFAULT){
            clearScene();
            world().load(new userPreferences.Manager(this));
        } else {
            escape();
            
        }
        
    };

    @Override
    public void useInformations(String information, String type) throws Exception {
        
        if(type.equals("escape")){
            
            if(information.equals(InputPanel.YES_OPTION)){
                MyWorld.theWorld.load(new menu.Manager());

            }
            
        }
        
    }
    
}
