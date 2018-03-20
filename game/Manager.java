package game;

import appearance.MessageDisplayer;
import base.Action;
import base.Game;
import base.Map;
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
    
    private final ControlPanel CONTROL_PANEL;
    private final ModeMessageDisplay MODE_MESSAGE_DISPLAY;
    private final NButton OPTIONS_BUTTON;
    
    /** 
     * Creates a new Manager that will allow to play a certain Game when 
     * setupScene() is called.
     * 
     * @param loadGame The Game that will be loaded.
     */
    public Manager(Game loadGame) {
        CONTROL_PANEL = new ControlPanel();
        MODE_MESSAGE_DISPLAY = new ModeMessageDisplay();
        GreenfootImage settings = new GreenfootImage("settings.png");
        OPTIONS_BUTTON = new NButton(LOAD_OPTIONS, settings, 30,30);
        gameToLoad = loadGame;
        
    }
    
    @Override
    public Game game() {return loadedGame;}
    @Override
    public Map map() {return loadedGame.map;}
    
    @Override
    public void setupScene() {
        Mode.setMode(Mode.DEFAULT);
        world().placeBlankHexs();
        CONTROL_PANEL.addToWorld(world().getWidth() - 100, 300);
        MODE_MESSAGE_DISPLAY.addToWorld(world().getWidth() - 90, 850);
        world().addObject(Continent.DISPLAY, 840, 960);
        world().addObject(OPTIONS_BUTTON, world().getWidth() - 60, 30);
        loadGame();
    }
    
    /** 
     * Generates a Map and initiates a Game.
     */
    private void loadGame(){
        loadMap();
        loadedGame = gameToLoad;
        
        if(loadedGame.gameState != null) switch (loadedGame.gameState) {
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
                playersUpdateCapital();
                startNewTurn();
                break;
            case FINISHED:
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
        Turn.startNewTurn();
    
    }
    
    @Override
    public void clearScene() {
        //Put the active Player's armiesInHand to negative. A little hack to prevent
        //the Player to get the armies multiple times by going to the options and 
        //back.
        Player currentPlayer = Turn.currentTurn.player;
        currentPlayer.addArmiesToHand(-currentPlayer.armyGainPerTurn());
        
        Turn.interruptCurrentTurn();
        
        gameToLoad = loadedGame;
        loadedGame = new Game();
        
        CONTROL_PANEL.removeFromWorld();
        MODE_MESSAGE_DISPLAY.removeFromWorld();
        
        world().removeObjects(world().getObjects(Actor.class));
        
    }
    
    @Override
    public void escape() {
        if(Mode.mode() == Mode.DEFAULT) {
            standardBackToMenu("Do you want to return to the main menu?");
        
        } else {
            Selector.clear();
            Mode.setMode(Mode.DEFAULT);
            
        }
        
        if(Territory.actionSource == null || Territory.actionTarget == null){
           Territory.resetSourceAndTarget();
        }
        
    }
    
    /** 
     * Gives random Territories to the Players.
     */
    private void giveTerritoriesRandomly() throws Exception{
        
        //Checks whether the Map can be played
        ArrayList<Territory> capitals = new ArrayList<>();
        ArrayList<Territory> nonCapitals = new ArrayList<>();
        
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
        
        //Gives the Territories randomly
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
     * Updates the capitals of each Player.
     */
    private void playersUpdateCapital() {
        loadedGame.players.forEach(Player::updateCapital);
    }
    
    /**
     * Ends the Game.
     */
    public void end(){
        new EndGamePanel().show();
    }

    private final Action LOAD_OPTIONS = () -> {
        if(Mode.mode() == Mode.DEFAULT){
            clearScene();
            world().load(new userPreferences.Manager(this));
        } else {
            escape();
            
        }
        
    };
    
}
